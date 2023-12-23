package com.ssharaev.lld.stock.exchange.service;

import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import com.ssharaev.lld.stock.exchange.model.Order;
import com.ssharaev.lld.stock.exchange.model.OrderBucket;
import com.ssharaev.lld.stock.exchange.model.OrderType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderHolder {

    public static final int INITIAL_CAPACITY = 11;

    private final Queue<OrderBucket> buyOrderBuckets = new PriorityBlockingQueue<>( INITIAL_CAPACITY, Comparator.reverseOrder() );
    private final Queue<OrderBucket> sellOrderBuckets = new PriorityBlockingQueue<>();

    public void putBuyBucket(Order order) {
        log.info( "Save new order {}", order );
        if (order.getType() == OrderType.BUY) {
            putIn(order, buyOrderBuckets);
        }
        if (order.getType() == OrderType.SELL) {
            putIn(order, sellOrderBuckets);
        }
    }

    private void putIn(Order order, Queue<OrderBucket> orderBuckets) {
        for ( OrderBucket bucket: orderBuckets ) {
            if (bucket.getPrice().equals( order.getPrice() )) {
                bucket.put( order );
                return;
            }
        }
        OrderBucket orderBucket = new OrderBucket( order.getPrice(), order );
        orderBuckets.add( orderBucket );
    }

    public void putBuyBucket(OrderBucket orderBucket) {
        log.info( "Save orderBucket {}", orderBucket );
        buyOrderBuckets.add( orderBucket );
    }

    public void putSellBucket(OrderBucket orderBucket) {
        log.info( "Save orderBucket {}", orderBucket );
        sellOrderBuckets.add( orderBucket );
    }

    public OrderBucket getHighestByOrders() {
        return buyOrderBuckets.poll();
    }

    public OrderBucket getLowestByOrders() {
        return sellOrderBuckets.poll();
    }
}
