package com.alma.boutique.infrastructure.repositories;

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
    public Transaction read(int id) {
        return database.retrieve(id, Transaction.class);
    }

    @Override
    public void edit(int id, Transaction entity) {
        database.update(id, entity);
    }

    @Override
    public void delete(int id) {
        database.delete(id, Client.class);
    }

    @Override
    public void add(int id, Transaction value) {
        database.create(id, value);
    }
}