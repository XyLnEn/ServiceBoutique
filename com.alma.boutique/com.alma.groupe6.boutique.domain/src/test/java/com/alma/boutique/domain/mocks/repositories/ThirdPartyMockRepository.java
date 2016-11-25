package com.alma.boutique.domain.mocks.repositories;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.ThirdParty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Thomas Minier
 */
public class ThirdPartyMockRepository implements IRepository<ThirdParty> {
    private Map<Integer, ThirdParty> elements = new HashMap<>();

    @Override
    public List<ThirdParty> browse() {
        return new ArrayList<>(elements.values());
    }

    @Override
    public ThirdParty read(int id) {
        return elements.get(id);
    }

    @Override
    public void edit(int id, ThirdParty entity) {
        if(elements.containsKey(id)) {
            elements.put(id, entity);
        }
    }

    @Override
    public void delete(int id) {
        elements.remove(id);
    }

    @Override
    public void add(int id, ThirdParty value) {
        elements.put(id, value);
    }
}
