package com.alma.boutique.domain.product;

import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.Supplier;
import org.junit.Before;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

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
    public void testPojoStandard() {
        assertPojoMethodsFor(SuppliedProduct.class).testing(Method.GETTER, Method.SETTER/*, Method.EQUALS, Method.HASH_CODE */).areWellImplemented();
    }
}