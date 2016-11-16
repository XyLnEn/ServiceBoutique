package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.ID;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.Client;
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
    public Supplier read(ID id) {
        return database.retrieve(id.getId(), Supplier.class);
    }

    @Override
    public void edit(ID id, Supplier entity) {
        database.update(id.getId(), entity);
    }

    @Override
    public void delete(ID id) {
        database.delete(id.getId(), Supplier.class);
    }

    @Override
    public void add(ID id, Supplier value) {
        database.create(id.getId(), value);
    }
}
