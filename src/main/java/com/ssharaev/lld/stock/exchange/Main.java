package com.ssharaev.lld.stock.exchange;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ssharaev.lld.stock.exchange.model.Order;
import com.ssharaev.lld.stock.exchange.model.OrderType;
import com.ssharaev.lld.stock.exchange.service.DealHolder;
import com.ssharaev.lld.stock.exchange.service.OrderHolder;
import com.ssharaev.lld.stock.exchange.service.OrderMatcher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        List<BigDecimal> prices = Stream.of(10, 15, 20, 25, 30)
            .map( BigDecimal::new )
            .collect( Collectors.toList());
        DealHolder dealHolder = new DealHolder();
        OrderMatcher orderMatcher = new OrderMatcher( new OrderHolder(), dealHolder );
        System.out.println( "Hello world!" );
        int ordersCount = 1000;
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        for ( int i = 0; i < ordersCount; i++ ) {
            int price = localRandom.nextInt(0, 5);
            int count = localRandom.nextInt(1, 20);
            int type = localRandom.nextInt(0, 2);
            String id = UUID.randomUUID().toString();
            orderMatcher.receiveOrder( new Order( id, LocalDateTime.now(),
                OrderType.values()[type], prices.get( price ), count) );
        }
        orderMatcher.printOrders();
        dealHolder.printDeals();
        log.info( "The end!" );
    }
}