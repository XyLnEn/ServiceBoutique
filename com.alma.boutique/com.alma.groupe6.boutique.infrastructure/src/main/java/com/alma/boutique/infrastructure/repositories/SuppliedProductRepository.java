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
    public SuppliedProduct read(ID id) {
        return database.retrieve(id.getId(), SuppliedProduct.class);
    }

    @Override
    public void edit(ID id, SuppliedProduct entity) {
        database.update(id.getId(), entity);
    }

    @Override
    public void delete(ID id) {
        database.delete(id.getId(), SuppliedProduct.class);
    }

    @Override
    public void add(ID id, SuppliedProduct value) {
        database.create(id.getId(), value);
    }
}
