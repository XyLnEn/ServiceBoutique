package com.alma.boutique.api;

import java.util.List;

/**
 * Interface représentant un Repository offrant des services Browse, Read, Edit et Delete
 * @author Thomas Minier
 */
public interface IRepository<T> {
    List<T> browse();
    T read(ID id);
    void edit(ID id, T entity);
    void delete(ID id);
    void add(ID id, T value);
}
