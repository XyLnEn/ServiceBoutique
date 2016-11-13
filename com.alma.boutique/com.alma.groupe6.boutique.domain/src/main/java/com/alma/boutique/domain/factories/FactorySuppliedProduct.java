package com.alma.boutique.domain.factories;

import com.alma.boutique.domain.product.Category;
import com.alma.boutique.domain.product.SuppliedProduct;
import com.alma.boutique.domain.shared.FactoryProduct;

public class FactorySuppliedProduct implements FactoryProduct{

	@Override
	public SuppliedProduct make(String name, float price, String description,  String categoryName) {
		Category category = new Category(categoryName);
		return new SuppliedProduct(name, price, description, category);
	}
}
