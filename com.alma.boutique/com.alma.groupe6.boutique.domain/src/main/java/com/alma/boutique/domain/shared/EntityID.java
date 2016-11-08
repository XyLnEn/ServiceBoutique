package com.alma.boutique.domain.shared;

import java.util.UUID;

/**
 * Created by thomas on 07/11/16.
 */
public class EntityID {
  private UUID id;

  public EntityID() {
      this.id = UUID.randomUUID();
  }
}
