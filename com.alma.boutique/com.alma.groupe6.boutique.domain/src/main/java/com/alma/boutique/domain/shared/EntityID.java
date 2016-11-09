package com.alma.boutique.domain.shared;

import java.util.UUID;

/**
 *
 * @user thomas
 */
public class EntityID {
    private UUID id;

    public EntityID() {
        this.id = UUID.randomUUID();
    }
}
