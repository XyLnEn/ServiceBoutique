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
}
