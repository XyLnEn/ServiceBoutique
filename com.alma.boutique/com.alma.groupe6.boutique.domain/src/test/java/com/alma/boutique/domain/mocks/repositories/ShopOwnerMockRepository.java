package com.alma.boutique.domain.mocks.repositories;

import com.alma.boutique.api.ID;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.ShopOwner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Minier
 */
public class ShopOwnerMockRepository implements IRepository<ShopOwner> {
    private Map<ID, ShopOwner> elements = new HashMap<>();

    @Override
    public List<ShopOwner> browse() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public ShopOwner read(ID id) {
        return elements.get(id);
    }

    @Override
    public void edit(ID id, ShopOwner entity) {
        if(elements.containsKey(id)) {
            elements.put(id, entity);
        }
    }

    @Override
    public void delete(ID id) {
        elements.remove(id);
    }

    @Override
    public void add(ID id, ShopOwner value) {
        elements.put(id, value);
    }
}
