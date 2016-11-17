package com.alma.boutique.infrastructure.util;

import com.alma.boutique.infrastructure.database.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Minier
 */
public class DatabaseMock implements Database {
    private Map<Integer, Object> db = new HashMap<>();

    @Override
    public void create(int id, Object entity) {
        db.put(id, entity);
    }

    @Override
    public <C> C retrieve(int id, Class<C> entityType) {
        return (C) db.get(id);
    }

    @Override
    public <C> List<C> retrieveAll(Class<C> entityType) {
        List<C> results = new ArrayList<>();
        for(Map.Entry<Integer, Object> pair : db.entrySet()) {
            results.add((C) pair.getValue());
        }
        return results;
    }

    @Override
    public void update(int id, Object entity) {
        if(db.containsKey(id)) {
            db.put(id, entity);
        }
    }

    @Override
    public void delete(int id, Class entityType) {
        if(db.containsKey(id)) {
            db.remove(id);
        }
    }
}
