package com.alma.boutique.domain.product;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.shared.Entity;

/**
 * @author Thomas Minier
 */
public abstract class Product extends Entity {
    private String name;
    private float price;
    private String description;
    private Category category;
    private float discount;

    public Product(String name, float price, String description, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        discount = 0;
    }

    public void addDiscount(float discount) {
        this.discount += discount;
    }

    public float calculatePrice() throws IllegalDiscountException {
        if(price - discount < 0) {
            throw new IllegalDiscountException("Total discounts is superior to product's prize");
        }
        return price - discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDiscount() {
        return discount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean sameCategoryAs(Product product) {
        return category.equals(product.getCategory());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Float.compare(product.getPrice(), getPrice()) == 0 && getName().equals(product.getName())
                && getDescription().equals(product.getDescription()) && getCategory().equals(product.getCategory());

    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}



