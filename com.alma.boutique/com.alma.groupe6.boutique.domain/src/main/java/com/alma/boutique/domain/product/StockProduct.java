package com.alma.boutique.domain.product;

import com.alma.boutique.domain.exceptions.NotEnoughStockException;

/**
 * @author Thomas Minier
 */
public class StockProduct extends Product {
    private int stock;

    public StockProduct(String name, float price, String description, int stock) {
        super(name, price, description, stock);
    }

    public SoldProduct sell() throws NotEnoughStockException {
        if(stock < 0) {
            throw new NotEnoughStockException("not enough stock for the product " + getName());
        }
        return null;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
