package com.alma.boutique.domain.product;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 * @author Thomas Minier
 */
public class CategoryTest {
    @Test
    public void testPojoStandard() {
        final Class<?> referenceClass = Category.class;
        assertPojoMethodsFor(referenceClass).testing(Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE).areWellImplemented();
    }
}