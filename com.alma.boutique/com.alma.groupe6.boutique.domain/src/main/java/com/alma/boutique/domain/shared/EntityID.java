package com.alma.boutique.domain.shared;

import com.alma.boutique.api.ID;

import java.util.UUID;

/**
 *
 * @author Thomas Minier
 */
public class EntityID implements ID {
    private UUID id;

    public EntityID() {
      this.id = UUID.randomUUID();
  }

  @Override
  public int getId() {
		return id.hashCode();
	}
}
