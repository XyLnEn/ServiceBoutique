package com.alma.boutique.domain.factories;

import com.alma.boutique.domain.product.Category;
import com.alma.boutique.domain.product.SoldProduct;
import com.alma.boutique.domain.shared.FactoryProduct;

public class FactorySoldProduct implements FactoryProduct{

	public SoldProduct make(String name, float price, String description, String categoryName) {
		Category category = new Category(categoryName);
		return new SoldProduct(name, price, description, category);
	}
}
