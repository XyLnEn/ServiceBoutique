package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Category;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.domain.product.Product;

import java.io.IOException;

/**
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class ProductFactory implements IFactory<Product> {
    private String name;
    private float priceValue;
    private String currency;
    private String description;
    private String categoryName;

    public ProductFactory(String name, float priceValue, String currency, String description, String categoryName) {
        this.name = name;
        this.priceValue = priceValue;
        this.currency = currency;
        this.description = description;
        this.categoryName = categoryName;
    }

    @Override
    public Product create() throws IOException {
        Category category = new Category(categoryName);
        Price price = new Price(priceValue, currency);
        return new Product(name, price, description, category);
    }
}
