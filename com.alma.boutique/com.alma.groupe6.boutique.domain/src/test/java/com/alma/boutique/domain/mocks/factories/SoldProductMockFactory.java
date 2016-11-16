package com.alma.boutique.domain.mocks.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Category;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.domain.product.SoldProduct;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class SoldProductMockFactory implements IFactory<SoldProduct> {
    private String name;
    private float priceValue;
    private String currency;
    private String description;
    private String categoryName;

    public SoldProductMockFactory(String name, float priceValue, String currency, String description, String categoryName) {
        this.name = name;
        this.priceValue = priceValue;
        this.currency = currency;
        this.description = description;
        this.categoryName = categoryName;
    }

    @Override
    public SoldProduct create() throws IOException {
        Category category = new Category(categoryName);
        Price price = new Price(priceValue, currency);
        return new SoldProduct(name, price, description, category);
    }
}
