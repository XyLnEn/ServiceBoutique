package com.alma.boutique.domain.shared;

import com.alma.boutique.domain.product.Product;

@FunctionalInterface
public interface FactoryProduct {

	Product make(String name, float price, String description,  String categoryName);
}
