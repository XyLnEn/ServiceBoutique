package com.alma.boutique.domain.product;

import com.alma.boutique.domain.shared.Entity;

/**
 * @author Thomas Minier
 */
public class Category extends Entity {
    private final String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return getName().equals(category.getName());
    }
}
