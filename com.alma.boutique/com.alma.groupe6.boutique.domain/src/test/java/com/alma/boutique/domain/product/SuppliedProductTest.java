package com.alma.boutique.domain.product;

import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.Supplier;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Thomas Minier
 */
public class SuppliedProductTest {
    private Category category;
    private Price price;
    private Supplier supplier;

    @Before
    public void setUp() throws Exception {
        category = new Category("reference category");
        price = new Price(15, "EUR");
        supplier = new Supplier("supplier", new Identity("adresse", "0607470638"));
    }

    @Test
    public void setAndGetSupplier() throws Exception {
        SuppliedProduct product = new SuppliedProduct("provision product", price, "a provision", category);
        product.setSupplier(supplier);
        assertThat(product.getSupplier()).as("supplier should be settable and gettable").isEqualTo(supplier);
    }

    @Test
    public void equals() throws Exception {

    }

    @Test
    public void testHashCode() throws Exception {
        SuppliedProduct product = new SuppliedProduct("provision product", price, "a provision", category);
        assertThat(product.hashCode()).as("hashCode should always be equals to the identity of the product").isEqualTo(product.hashCode());
    }

}