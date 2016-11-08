package com.alma.boutique.domain.shared;

import java.util.List;

/**
 * Created by thomas on 07/11/16.
 */
public interface IRepository<T> {
  void create(EntityID id, Entity entity);
  T retrieve(EntityID id);
  List<T> retrieveAll();
  void update(EntityID id, Entity entity);
  T delete(EntityID id);
}
