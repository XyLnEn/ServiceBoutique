package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.infrastructure.database.Database;

import java.util.List;

/**
 * Class to access a database to manage instances of the class Order
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class OrderRepository implements IRepository<Order> {
	private Database database;

  public OrderRepository(Database database) {
      this.database = database;
  }

  @Override
  public List<Order> browse() {
      return database.retrieveAll(Order.class);
  }

  @Override
  public Order read(int id) {
      return database.retrieve(id, Order.class);
  }

  @Override
  public void edit(int id, Order entity) {
      database.update(id, entity);
  }

  @Override
  public void delete(int id) {
      database.delete(id, Order.class);
  }

  @Override
  public void add(int id, Order value) {
      database.create(id, value);
  }
}
