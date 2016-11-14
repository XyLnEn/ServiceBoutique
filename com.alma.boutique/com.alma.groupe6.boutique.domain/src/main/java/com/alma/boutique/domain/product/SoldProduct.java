package com.alma.boutique.domain.product;

/**
 * @author Thomas Minier
 */
public class SoldProduct extends Product {
    public SoldProduct(String name, Price price, String description, Category category) {
        super(name, price, description, category);
    }

    public SoldProduct(Product reference) {
        super(reference.getName(), reference.getPrice(), reference.getDescription(), reference.getCategory());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SoldProduct product = (SoldProduct) o;
        return getPrice().equals(product.getPrice()) && getName().equals(product.getName())
                && getDescription().equals(product.getDescription()) && getCategory().equals(product.getCategory());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
