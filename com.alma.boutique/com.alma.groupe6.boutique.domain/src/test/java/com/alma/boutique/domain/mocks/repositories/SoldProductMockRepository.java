package com.alma.boutique.domain.mocks.repositories;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.product.SoldProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Minier
 */
public class SoldProductMockRepository implements IRepository<SoldProduct> {
    private Map<Integer, SoldProduct> elements = new HashMap<>();

    @Override
    public List<SoldProduct> browse() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public SoldProduct read(int id) {
        return elements.get(id);
    }

    @Override
    public void edit(int id, SoldProduct entity) {
        if(elements.containsKey(id)) {
            elements.put(id, entity);
        }
    }

    @Override
    public void delete(int id) {
        elements.remove(id);
    }

    @Override
    public void add(int id, SoldProduct value) {
        elements.put(id, value);
    }
}
