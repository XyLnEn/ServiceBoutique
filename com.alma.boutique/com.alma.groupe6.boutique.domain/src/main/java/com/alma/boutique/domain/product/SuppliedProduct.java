package com.alma.boutique.domain.product;

import com.alma.boutique.domain.thirdperson.Supplier;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        SuppliedProduct rhs = (SuppliedProduct) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.supplier, rhs.supplier)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(supplier)
                .toHashCode();
    }
}
