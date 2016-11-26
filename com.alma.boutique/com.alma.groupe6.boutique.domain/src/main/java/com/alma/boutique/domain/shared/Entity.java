package com.alma.boutique.domain.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Root class of the non-object value
 * @author Thomas Minier
 */
public abstract class Entity {
  protected int id;
  protected final Logger log =  LoggerFactory.getLogger(Entity.class);

  public Entity() {
      this.id = UUID.randomUUID().hashCode();
  }

  public int getId() {
      return this.id;
  }

  public void setId(int id) {
      this.id = id;
  }

  public boolean sameIdentityAs(Entity entity) {
      return entity != null && this.id == entity.id;
  }

}
