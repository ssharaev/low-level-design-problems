package com.ssharaev.lld.stock.exchange.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.ssharaev.lld.stock.exchange.model.Deal;
import com.ssharaev.lld.stock.exchange.model.Order;
import com.ssharaev.lld.stock.exchange.model.OrderBucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OrderMatcher {

    private final OrderHolder orderHolder;
    private final DealHolder dealHolder;

    private final List<Order> orders = new ArrayList<>();

    public void receiveOrder(Order order) {
        orders.add( order );
        orderHolder.putBuyBucket( order );
        runMatching();
    }

    private void runMatching() {
        OrderBucket buyOrders = orderHolder.getHighestByOrders();
        OrderBucket sellOrders = orderHolder.getLowestByOrders();
        while ( buyOrders != null && sellOrders != null &&
            buyOrders.getPrice().compareTo( sellOrders.getPrice() ) >= 0 ) {
            processDeals( buyOrders, sellOrders );

            buyOrders = orderHolder.getHighestByOrders();
            sellOrders = orderHolder.getLowestByOrders();
        }
        if ( buyOrders != null && !buyOrders.isEmpty() ) {
            orderHolder.putBuyBucket( buyOrders );
        }
        if ( sellOrders != null && !sellOrders.isEmpty() ) {
            orderHolder.putSellBucket( sellOrders );
        }
        log.info( "There are no more matched orders!" );
    }

    private void processDeals(OrderBucket buyOrders, OrderBucket sellOrders) {
        Order buyOrder = buyOrders.poll();
        Order sellOrder = sellOrders.poll();
        while ( buyOrder != null && !buyOrder.isEmpty() && sellOrder != null && !sellOrder.isEmpty() ) {
            createDeal( buyOrder, sellOrder );
            if ( buyOrder.isEmpty() ) {
                buyOrder = buyOrders.poll();
            }
            if ( sellOrder.isEmpty() ) {
                sellOrder = sellOrders.poll();
            }
        }
    }

    private void createDeal(Order buyOrder, Order sellOrder) {
        int count = Math.min( buyOrder.getCount(), sellOrder.getCount() );
        BigDecimal price = sellOrder.getPrice();
        Deal deal = new Deal( buyOrder.getId(), sellOrder.getId(), price, count );
        buyOrder.setCount( buyOrder.getCount() - count );
        sellOrder.setCount( sellOrder.getCount() - count );
        dealHolder.addDeal( deal );
    }

    public void printOrders() {
        log.info( "{}", orders );
    }

}
