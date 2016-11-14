package com.alma.boutique.domain.history;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 * @author Thomas Minier
 */
public class TransactionTest {
    @Test
    public void testPojoStandard() {
        assertPojoMethodsFor(Transaction.class).testing(Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE).areWellImplemented();
    }
}