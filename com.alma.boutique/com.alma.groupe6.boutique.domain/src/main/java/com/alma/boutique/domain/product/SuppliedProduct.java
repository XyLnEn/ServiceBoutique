package com.alma.boutique.domain.product;

/**
 * @author Thomas Minier
 */
public class SuppliedProduct extends Product {
    private String provider; // TODO replace this wwith the good class from Tiers aggregate

    public SuppliedProduct(String name, float price, String description, Category category) {
        super(name, price, description, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SuppliedProduct product = (SuppliedProduct) o;
        return Float.compare(product.getPrice(), getPrice()) == 0 && getName().equals(product.getName())
                && getDescription().equals(product.getDescription()) && getCategory().equals(product.getCategory());    }
}
