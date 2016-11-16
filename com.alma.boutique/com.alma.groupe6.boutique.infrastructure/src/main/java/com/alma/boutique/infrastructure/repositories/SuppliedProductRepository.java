package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.ID;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.product.SuppliedProduct;
import com.alma.boutique.infrastructure.database.Database;

import java.util.List;

/**
 * @author Thomas Minier
 */
public class SuppliedProductRepository implements IRepository<SuppliedProduct> {
    private Database database;

    public SuppliedProductRepository(Database database) {
        this.database = database;
    }

    @Override
    public List<SuppliedProduct> browse() {
        return database.retrieveAll(SuppliedProduct.class);
    }

    @Override
    public SuppliedProduct read(int id) {
        return database.retrieve(id, SuppliedProduct.class);
    }

    @Override
    public void edit(int id, SuppliedProduct entity) {
        database.update(id, entity);
    }

    @Override
    public void delete(int id) {
        database.delete(id, SuppliedProduct.class);
    }

    @Override
    public void add(int id, SuppliedProduct value) {
        database.create(id, value);
    }
}
