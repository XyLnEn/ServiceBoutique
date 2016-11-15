package com.alma.boutique.api;

import java.util.List;

/**
 * @author Thomas Minier
 */
public interface IRepository<T> {
    void create(ID id, T entity);
    T retrieve(ID id);
    List<T> retrieveAll();
    void update(ID id, T entity);
    void delete(ID id);
}
