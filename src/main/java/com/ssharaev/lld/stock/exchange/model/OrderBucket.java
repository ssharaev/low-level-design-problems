package com.ssharaev.lld.stock.exchange.model;

import java.math.BigDecimal;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@ToString
@Getter
@AllArgsConstructor
public class OrderBucket implements Comparable<OrderBucket> {

    private final BigDecimal price;
    private final Queue<Order> orders = new ConcurrentLinkedDeque<>();

    public OrderBucket(BigDecimal price, Order order) {
        this.price = price;
        this.orders.add( order );
    }


    public void put(Order order) {
        orders.add( order );
    }

    @Override
    public int compareTo(@NotNull OrderBucket o) {
        return this.price.compareTo( o.price );
    }

    public Order poll() {
        return orders.poll();
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }
}
