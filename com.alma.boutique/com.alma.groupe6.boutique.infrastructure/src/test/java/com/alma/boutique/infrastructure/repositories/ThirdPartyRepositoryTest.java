package com.alma.boutique.infrastructure.repositories;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import com.alma.boutique.infrastructure.database.Database;

import java.util.List;

/**
 * @author Thomas Minier
 */
public class ThirdPartyRepositoryTest implements IRepository<ThirdParty> {
  private Database database;

  public ThirdPartyRepositoryTest(Database database) {
      this.database = database;
  }

  @Override
  public List<ThirdParty> browse() {
      return database.retrieveAll(ThirdParty.class);
  }

  @Override
  public ThirdParty read(int id) {
      return database.retrieve(id, ThirdParty.class);
  }

  @Override
  public void edit(int id, ThirdParty entity) {
      database.update(id, entity);
  }

  @Override
  public void delete(int id) {
      database.delete(id, ThirdParty.class);
  }

  @Override
  public void add(int id, ThirdParty value) {
      database.create(id, value);
  }
}