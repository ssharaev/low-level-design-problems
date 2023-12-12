package com.ssharaev.stock.exchange.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@ToString
@Getter
@AllArgsConstructor
public class Order implements Comparable<Order> {

    private final String id;
    private final LocalDateTime time;
    private final OrderType type;
    private final BigDecimal price;
    @Setter
    private int count;

    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int compareTo(@NotNull Order o) {
        return time.compareTo( o.time );
    }
}
