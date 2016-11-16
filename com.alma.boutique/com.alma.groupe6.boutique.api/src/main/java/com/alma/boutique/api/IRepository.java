package com.alma.boutique.api;

import java.util.List;

/**
 * Interface représentant un Repository offrant des services Browse, Read, Edit et Delete
 * @author Thomas Minier
 */
public interface IRepository<T> {
    List<T> browse();
    T read(int id);
    void edit(int id, T entity);
    void delete(int id);
    void add(int id, T value);
}
