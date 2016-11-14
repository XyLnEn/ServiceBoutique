package com.alma.boutique.domain.product;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Thomas Minier
 */
public class SoldProduct extends Product {

	public SoldProduct() {
		super();
	}

	public SoldProduct(String name, Price price, String description, Category category) {
		super(name, price, description, category);
	}

	public SoldProduct(Product reference) {
		super(reference.getName(), reference.getPrice(), reference.getDescription(), reference.getCategory());
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
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.toHashCode();
	}
}
