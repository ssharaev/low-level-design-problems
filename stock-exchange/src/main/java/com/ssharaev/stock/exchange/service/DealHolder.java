package com.ssharaev.stock.exchange.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ssharaev.stock.exchange.model.Deal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DealHolder {

    private final List<Deal> deals = new CopyOnWriteArrayList<>();


    public void addDeal(Deal deal) {
        deals.add( deal );
        log.info( "Saved new deal {}", deal );
    }

    public void printDeals() {
        log.info( "Total deal : {}", deals.size() );
        log.info( "\n{}", deals.stream() );
    }
}
