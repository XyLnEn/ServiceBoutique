package com.alma.boutique.domain.mocks.repositories;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Minier
 */
public class OrderMockRepository implements IRepository<Order> {
    private Map<Integer, Order> elements = new HashMap<>();

    @Override
    public List<Order> browse() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public Order read(int id) {
        return elements.get(id);
    }

    @Override
    public void edit(int id, Order entity) {
        if(elements.containsKey(id)) {
            elements.put(id, entity);
        }
    }

    @Override
    public void delete(int id) {
        elements.remove(id);
    }

    @Override
    public void add(int id, Order value) {
        elements.put(id, value);
    }
}
