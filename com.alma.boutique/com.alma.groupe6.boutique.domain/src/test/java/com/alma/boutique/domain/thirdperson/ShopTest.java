package com.alma.boutique.domain.thirdperson;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 * @author Thomas Minier
 */
public class ShopTest {
    @Test
    public void testPojoStandard() {
        assertPojoMethodsFor(Shop.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }
}