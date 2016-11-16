package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.ID;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.product.SoldProduct;
import com.alma.boutique.infrastructure.database.Database;

import java.util.List;

/**
 * @author Thomas Minier
 */
public class SoldProductRepository implements IRepository<SoldProduct> {
    private Database database;

    public SoldProductRepository(Database database) {
        this.database = database;
    }

    @Override
    public List<SoldProduct> browse() {
        return database.retrieveAll(SoldProduct.class);
    }

    @Override
    public SoldProduct read(ID id) {
        return database.retrieve(id.getId(), SoldProduct.class);
    }

    @Override
    public void edit(ID id, SoldProduct entity) {
        database.update(id.getId(), entity);
    }

    @Override
    public void delete(ID id) {
        database.delete(id.getId(), SoldProduct.class);
    }

    @Override
    public void add(ID id, SoldProduct value) {
        database.create(id.getId(), value);
    }
}
