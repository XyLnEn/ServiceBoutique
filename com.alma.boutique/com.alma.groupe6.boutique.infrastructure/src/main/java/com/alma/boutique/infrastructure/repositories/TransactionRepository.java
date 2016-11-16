package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.ID;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.infrastructure.database.Database;

import java.util.List;

/**
 * @author Thomas Minier
 */
public class TransactionRepository implements IRepository<Transaction> {
    private Database database;

    public TransactionRepository(Database database) {
        this.database = database;
    }

    @Override
    public List<Transaction> browse() {
        return database.retrieveAll(Transaction.class);
    }

    @Override
    public Transaction read(ID id) {
        return database.retrieve(id.getId(), Transaction.class);
    }

    @Override
    public void edit(ID id, Transaction entity) {
        database.update(id.getId(), entity);
    }

    @Override
    public void delete(ID id) {
        database.delete(id.getId(), Client.class);
    }

    @Override
    public void add(ID id, Transaction value) {
        database.create(id.getId(), value);
    }
}
