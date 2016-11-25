package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.infrastructure.database.Database;

import java.util.List;

/**
 * @author Thomas Minier
 */
public class ProductRepositoryTest implements IRepository<Product> {
	private Database database;

  public ProductRepositoryTest(Database database) {
      this.database = database;
  }

  @Override
  public List<Product> browse() {
      return database.retrieveAll(Product.class);
  }

  @Override
  public Product read(int id) {
      return database.retrieve(id, Product.class);
  }

  @Override
  public void edit(int id, Product entity) {
      database.update(id, entity);
  }

  @Override
  public void delete(int id) {
      database.delete(id, Product.class);
  }

  @Override
  public void add(int id, Product value) {
      database.create(id, value);
  }
}
