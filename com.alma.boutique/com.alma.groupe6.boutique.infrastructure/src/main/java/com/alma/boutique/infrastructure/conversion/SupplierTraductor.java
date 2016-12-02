package com.alma.boutique.infrastructure.conversion;

import com.alma.boutique.domain.product.Product;

/**
 * Interface which represent a class which can be translated to a Product
 * @author Lenny Lucas
 * @author Thomas Minier
 */
@FunctionalInterface
public interface SupplierTraductor {

	public Product translate();
}
