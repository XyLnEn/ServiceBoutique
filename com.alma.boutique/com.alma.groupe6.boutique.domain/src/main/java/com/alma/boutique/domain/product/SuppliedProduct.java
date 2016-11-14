package com.alma.boutique.domain.product;

import com.alma.boutique.domain.thirdperson.Supplier;

/**
 * @author Thomas Minier
 */
public class SuppliedProduct extends Product {
    private Supplier supplier;

    public SuppliedProduct(String name, Price price, String description, Category category) {
        super(name, price, description, category);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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
        return getPrice().equals(product.getPrice()) && getName().equals(product.getName())
                && getDescription().equals(product.getDescription()) && getCategory().equals(product.getCategory());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
