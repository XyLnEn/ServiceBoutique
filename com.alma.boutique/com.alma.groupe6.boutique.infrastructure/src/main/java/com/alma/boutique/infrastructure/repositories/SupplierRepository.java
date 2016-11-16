package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.Supplier;
import com.alma.boutique.infrastructure.database.Database;

import java.util.List;

/**
 * @author Thomas Minier
 */
public class SupplierRepository implements IRepository<Supplier> {
    private Database database;

    public SupplierRepository(Database database) {
        this.database = database;
    }

    @Override
    public List<Supplier> browse() {
        return database.retrieveAll(Supplier.class);
    }

    @Override
    public Supplier read(int id) {
        return database.retrieve(id, Supplier.class);
    }

    @Override
    public void edit(int id, Supplier entity) {
        database.update(id, entity);
    }

    @Override
    public void delete(int id) {
        database.delete(id, Supplier.class);
    }

    @Override
    public void add(int id, Supplier value) {
        database.create(id, value);
    }
}
