package com.alma.boutique.domain.product;

import com.alma.boutique.domain.exceptions.NotEnoughStockException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Thomas Minier
 */
public class StockProductTest {

    @Test
    public void supply() throws Exception {
        StockProduct stockProduct = new StockProduct("stub", 10, "description", 0);

        assertThat(stockProduct.getStock()).as("stock should be empty by default").isEqualTo(0);
        stockProduct.supply(5);
        assertThat(stockProduct.getStock()).as("stock should have been supplied").isEqualTo(5);

    }

    @Test
    public void sell() throws Exception {
        StockProduct stockProduct = new StockProduct("stub", 10, "description", 1);

        assertThat(stockProduct.sell()).as("should sell when the product is in stock").isInstanceOf(SoldProduct.class);
        assertThatExceptionOfType(NotEnoughStockException.class).isThrownBy(() -> stockProduct.sell())
                .as("should throw an exception when the stock is empty");
    }
}