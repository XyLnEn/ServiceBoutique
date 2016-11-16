package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.ID;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.ShopOwner;
import com.alma.boutique.infrastructure.database.Database;

import java.util.List;

/**
 * @author Thomas Minier
 */
public class ShopOwnerRepository implements IRepository<ShopOwner> {
    private Database database;

    public ShopOwnerRepository(Database database) {
        this.database = database;
    }

    @Override
    public List<ShopOwner> browse() {
        return database.retrieveAll(ShopOwner.class);
    }

    @Override
    public ShopOwner read(ID id) {
        return database.retrieve(id.getId(), ShopOwner.class);
    }

    @Override
    public void edit(ID id, ShopOwner entity) {
        database.update(id.getId(), entity);
    }

    @Override
    public void delete(ID id) {
        database.delete(id.getId(), ShopOwner.class);
    }

    @Override
    public void add(ID id, ShopOwner value) {
        database.create(id.getId(), value);
    }
}
