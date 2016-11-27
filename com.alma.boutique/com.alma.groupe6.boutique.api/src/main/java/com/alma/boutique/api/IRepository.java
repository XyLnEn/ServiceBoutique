package com.alma.boutique.api;

import java.util.List;

/**
 * Interface representing a generic repository who's supporting CRUD operations
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public interface IRepository<T> {
    List<T> browse();
    T read(int id);
    void edit(int id, T entity);
    void delete(int id);
    void add(int id, T value);
}
