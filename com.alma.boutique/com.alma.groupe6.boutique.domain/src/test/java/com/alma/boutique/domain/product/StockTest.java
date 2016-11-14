package com.alma.boutique.domain.product;

import com.alma.boutique.domain.exceptions.NotEnoughStockException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Thomas Minier
 */
public class StockTest {

    private Category referenceCategory;
    private Product suppliedProduct;

    @Before
    public void setUp() throws Exception {
        referenceCategory = new Category("reference category");
        Price price = new Price(15, "EUR");
        suppliedProduct = new SuppliedProduct("provision product", price, "a provision", referenceCategory);
    }

    @Test
    public void sellAndSupply() throws Exception {
        Stock stock = new Stock();
        SoldProduct expected = new SoldProduct(suppliedProduct);

        // test when there's not enough stock
        assertThatExceptionOfType(NotEnoughStockException.class).isThrownBy(() -> stock.sell(referenceCategory))
                .as("cannot sell when there's not enough stock");

        // test when there is is enough stock
        stock.supply(suppliedProduct);
        Product p = stock.sell(referenceCategory);
        assertThat(p).as("a product can be sell where there's enough stock").isEqualTo(expected);

        // test when all the stock has been sold
        assertThatExceptionOfType(NotEnoughStockException.class).isThrownBy(() -> stock.sell(referenceCategory))
                .as("stock should be depleted when selling the only item it contains");

    }

}