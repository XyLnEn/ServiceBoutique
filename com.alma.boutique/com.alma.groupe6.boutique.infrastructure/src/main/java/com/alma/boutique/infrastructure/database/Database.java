package com.alma.boutique.infrastructure.database;

import java.util.List;

/**
 * Interface of a random database using CRUD
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public interface Database {
    void create(int id, Object entity);
    <C> C retrieve(int id, Class<C> entityType);
    <C> List<C> retrieveAll(Class<C> entityType);
    void update(int id, Object entity);
    void delete(int id, Class entityType);
}
