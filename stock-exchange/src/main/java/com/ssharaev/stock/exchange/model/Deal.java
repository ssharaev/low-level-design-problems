package com.ssharaev.stock.exchange.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Deal {
    private final String buyOrderId;
    private final String sellOrderId;
    private BigDecimal price;
    private int count;
}
