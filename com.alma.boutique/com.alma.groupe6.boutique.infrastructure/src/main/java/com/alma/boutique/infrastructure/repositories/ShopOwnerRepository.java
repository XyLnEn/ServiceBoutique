package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.IRepository;
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
    public ShopOwner read(int id) {
        return database.retrieve(id, ShopOwner.class);
    }

    @Override
    public void edit(int id, ShopOwner entity) {
        database.update(id, entity);
    }

    @Override
    public void delete(int id) {
        database.delete(id, ShopOwner.class);
    }

    @Override
    public void add(int id, ShopOwner value) {
        database.create(id, value);
    }
}
