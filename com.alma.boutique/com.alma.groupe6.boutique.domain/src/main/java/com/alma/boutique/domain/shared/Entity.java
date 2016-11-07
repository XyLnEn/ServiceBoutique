package com.alma.boutique.domain.shared;

/**
 * Created by thomas on 07/11/16.
 */
public abstract class Entity {
    protected EntityID id;

    public Entity() {
        this.id = new EntityID();
    }

    public boolean sameIdentityAs(Entity entity) {
        return entity != null && this.id.equals(entity.id);
    }
}
