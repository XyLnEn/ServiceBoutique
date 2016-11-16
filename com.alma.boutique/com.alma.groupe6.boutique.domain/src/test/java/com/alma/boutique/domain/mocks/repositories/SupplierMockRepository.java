package com.alma.boutique.domain.mocks.repositories;

import com.alma.boutique.api.ID;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Supplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Minier
 */
public class SupplierMockRepository implements IRepository<Supplier> {
    private Map<ID, Supplier> elements = new HashMap<>();

    @Override
    public List<Supplier> browse() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public Supplier read(ID id) {
        return elements.get(id);
    }

    @Override
    public void edit(ID id, Supplier entity) {
        if(elements.containsKey(id)) {
            elements.put(id, entity);
        }
    }

    @Override
    public void delete(ID id) {
        elements.remove(id);
    }

    @Override
    public void add(ID id, Supplier value) {
        elements.put(id, value);
    }
}
