package com.alma.boutique.domain.mocks.repositories;

import com.alma.boutique.api.ID;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Minier
 */
public class ClientMockRepository implements IRepository<Client> {
    private Map<ID, Client> elements = new HashMap<>();

    @Override
    public List<Client> browse() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public Client read(ID id) {
        return elements.get(id);
    }

    @Override
    public void edit(ID id, Client entity) {
        if(elements.containsKey(id)) {
            elements.put(id, entity);
        }
    }

    @Override
    public void delete(ID id) {
        elements.remove(id);
    }

    @Override
    public void add(ID id, Client value) {
        elements.put(id, value);
    }
}
