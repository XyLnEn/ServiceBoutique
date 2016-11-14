package com.alma.boutique.domain.factories;

import com.alma.boutique.domain.product.Category;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.domain.product.SuppliedProduct;

public class FactorySuppliedProduct implements FactoryProduct{

	@Override
	public SuppliedProduct make(String name, float priceValue, String currency, String description,  String categoryName) {
		Category category = new Category(categoryName);
		Price price = new Price(priceValue, currency);
		return new SuppliedProduct(name, price, description, category);
	}
}
