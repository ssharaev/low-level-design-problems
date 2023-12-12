package com.ssharaev.event.calendar.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ssharaev.event.calendar.model.Entity;

public abstract class Repository<T extends Entity> {

    private final Map<String, T> entities = new ConcurrentHashMap<>();

    public void save(T entity) {
        if (entity.getId() == null) {
            throw new RuntimeException("Unable to save entity without id!");
        }
        entities.put( entity.getId(), entity);
    }

    public T getById(String id) {
        if (id == null) {
            throw new NullPointerException("Entity id mustn't be null!");
        }
        return entities.get( id );
    }
}
