package com.alma.boutique.domain.product;

import com.alma.boutique.domain.shared.Entity;

/**
 * @author Thomas Minier
 */
public class Category extends Entity {
    private String name;
    
    public Category() {
    	super();
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
			this.name = name;
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

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
