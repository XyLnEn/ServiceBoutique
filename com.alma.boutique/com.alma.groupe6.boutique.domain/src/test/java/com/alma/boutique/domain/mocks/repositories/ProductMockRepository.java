package com.alma.boutique.domain.mocks.repositories;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.product.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Minier
 */
public class ProductMockRepository implements IRepository<Product> {
    private Map<Integer, Product> elements = new HashMap<>();

    @Override
    public List<Product> browse() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public Product read(int id) {
        return elements.get(id);
    }

    @Override
    public void edit(int id, Product entity) {
        if(elements.containsKey(id)) {
            elements.put(id, entity);
        }
    }

    @Override
    public void delete(int id) {
        elements.remove(id);
    }

    @Override
    public void add(int id, Product value) {
        elements.put(id, value);
    }
}
