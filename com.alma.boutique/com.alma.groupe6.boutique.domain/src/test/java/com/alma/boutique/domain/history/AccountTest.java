package com.alma.boutique.domain.history;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 * @author Thomas Minier
 */
public class AccountTest {
    @Test
    public void testPojoStandard() {
        assertPojoMethodsFor(Account.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }
}