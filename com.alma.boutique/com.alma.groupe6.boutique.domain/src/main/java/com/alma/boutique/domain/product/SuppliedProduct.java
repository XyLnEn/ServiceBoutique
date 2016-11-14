package com.alma.boutique.domain.product;

import com.alma.boutique.domain.thirdperson.Supplier;

/**
 * @author Thomas Minier
 */
public class SuppliedProduct extends Product {
    private Supplier supplier;
    
    public SuppliedProduct() {
    	super();
    	this.supplier = new Supplier();
		}

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

        if(!supplier.equals(product.getSupplier())) {
            return false;
        }
        return getPrice().equals(product.getPrice()) && getName().equals(product.getName())
                && getDescription().equals(product.getDescription()) && getCategory().equals(product.getCategory());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + supplier.hashCode();
        return result;
    }
}
