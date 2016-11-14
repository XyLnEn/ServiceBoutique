package com.alma.boutique.domain.factories;

import com.alma.boutique.domain.product.Product;

@FunctionalInterface
public interface FactoryProduct {

	Product make(String name, float priceValue, String currency, String description,  String categoryName);
}
