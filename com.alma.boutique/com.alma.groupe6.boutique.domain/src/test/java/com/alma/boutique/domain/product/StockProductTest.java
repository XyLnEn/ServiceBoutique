package com.alma.boutique.domain.product;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Thomas Minier
 */
public class StockProductTest {

    @Test
    public void addDiscount() throws Exception {
        StockProduct stockProduct = new StockProduct("stub", 10, "descripyion", 1);
        stockProduct.addDiscount(5);
        assertThat(stockProduct.getDiscount()).as("add a first discount").isEqualTo(5);
        stockProduct.addDiscount(2);
        assertThat(stockProduct.getDiscount()).as("add another discount").isEqualTo(7);
    }

    @Test
    public void calculatePrice() throws Exception {
        StockProduct stockProduct = new StockProduct("stub", 10, "descripyion", 1);
        assertThat(stockProduct.calculatePrice()).as("calculate price without any discount").isEqualTo(10);
        stockProduct.addDiscount(5);
        assertThat(stockProduct.calculatePrice()).as("calculate price with a discount").isEqualTo(5);
    }
}