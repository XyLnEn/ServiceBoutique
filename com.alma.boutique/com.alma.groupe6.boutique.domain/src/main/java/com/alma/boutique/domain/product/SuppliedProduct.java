package com.alma.boutique.domain.product;

/**
 * @author Thomas Minier
 */
public class SuppliedProduct extends Product {
    private String provider; // TODO replace this wwith the good class from Tiers aggregate
    public SuppliedProduct(String name, float price, String description, int stock) {
        super(name, price, description, stock);
    }
}
