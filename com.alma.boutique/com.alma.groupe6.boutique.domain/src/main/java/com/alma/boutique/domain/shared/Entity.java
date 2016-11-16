package com.alma.boutique.domain.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thomas Minier
 */
public abstract class Entity {
  protected final EntityID id;
  protected final Logger log =  LoggerFactory.getLogger(Entity.class);

  public Entity() {
      this.id = new EntityID();
  }

  public int getID() {
      return this.id.getId();
  }

  public boolean sameIdentityAs(Entity entity) {
      return entity != null && this.id.equals(entity.id);
  }

}
