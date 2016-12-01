package com.alma.boutique.infrastructure.conversion;

import com.alma.boutique.domain.product.Product;
@FunctionalInterface
public interface SupplierTraductor {

	public Product translate();
}
