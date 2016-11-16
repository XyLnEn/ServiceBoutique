package com.alma.boutique.domain.shared;

/**
 *
 * @author Thomas Minier
 */
public abstract class Entity {
  protected final EntityID id;

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
