package com.alma.boutique.infrastructure.database;

import java.util.List;

/**
 * Interface représentant une facade pour une base de données quelconque, en respectant le modèle CRUD
 * @author Thomas Minier
 */
public interface DatabaseFacade {
    void create(int id, Object entity);
    <C> C retrieve(int id, Class<C> entityType);
    <C> List<C> retrieveAll(Class<C> entityType);
    void update(int id, Object entity);
    void delete(int id, Class entityType);
}
