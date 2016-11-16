package com.alma.boutique.infrastructure.repositories;

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
    public SoldProduct read(int id) {
        return database.retrieve(id, SoldProduct.class);
    }

    @Override
    public void edit(int id, SoldProduct entity) {
        database.update(id, entity);
    }

    @Override
    public void delete(int id) {
        database.delete(id, SoldProduct.class);
    }

    @Override
    public void add(int id, SoldProduct value) {
        database.create(id, value);
    }
}
