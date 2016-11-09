package com.alma.boutique.domain.product;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.*;

/**
 * @author Thomas Minier
 */
public class ProductTest {

    private class StubProduct extends Product {
        public StubProduct(String name, float price, String description) {
            super(name, price, description);
        }
    }
    @Test
    public void addDiscount() throws Exception {
        Product stockProduct = new StubProduct("stub", 10, "description");
        stockProduct.addDiscount(5);
        assertThat(stockProduct.getDiscount()).as("add a first discount").isEqualTo(5);
        stockProduct.addDiscount(2);
        assertThat(stockProduct.getDiscount()).as("add another discount").isEqualTo(7);
    }

    @Test
    public void calculatePrice() throws Exception {
        Product stockProduct = new StubProduct("stub", 10, "description");
        assertThat(stockProduct.calculatePrice()).as("calculate price without any discount").isEqualTo(10);
        stockProduct.addDiscount(5);
        assertThat(stockProduct.calculatePrice()).as("calculate price with a discount").isEqualTo(5);

        // check for exception thrown
        stockProduct.addDiscount(50);
        assertThatExceptionOfType(IllegalDiscountException.class).isThrownBy(() -> stockProduct.calculatePrice())
                .as("cannot have sum of discounts > price");
    }

}