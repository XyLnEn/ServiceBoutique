package com.alma.boutique.domain.shared;

import java.util.UUID;

/**
 *
 * @author Thomas Minier
 */
public class EntityID {
    private UUID id;

    public EntityID() {
      this.id = UUID.randomUUID();
  }

	public UUID getId() {
		return id;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntityID entityID = (EntityID) o;
        return id.equals(entityID.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
