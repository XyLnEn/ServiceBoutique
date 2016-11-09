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
    private float discount;
    private int stock;

    public Product(String name, float price, String description, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock = stock;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getDiscount() {
        return discount;
    }
}
