package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.ID;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.infrastructure.database.Database;

import java.util.List;

/**
 * @author Thomas Minier
 */
public class ClientRepository implements IRepository<Client> {
    private Database database;

    public ClientRepository(Database database) {
        this.database = database;
    }

    @Override
    public List<Client> browse() {
        return database.retrieveAll(Client.class);
    }

    @Override
    public Client read(int id) {
        return database.retrieve(id, Client.class);
    }

    @Override
    public void edit(int id, Client entity) {
        database.update(id, entity);
    }

    @Override
    public void delete(int id) {
        database.delete(id, Client.class);
    }

    @Override
    public void add(int id, Client value) {
        database.create(id, value);
    }
}
