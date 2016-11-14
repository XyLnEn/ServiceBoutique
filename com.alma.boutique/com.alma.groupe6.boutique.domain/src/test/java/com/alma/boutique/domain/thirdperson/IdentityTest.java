package com.alma.boutique.domain.thirdperson;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

/**
 * @author Thomas Minier
 */
public class IdentityTest {
    @Test
    public void testPojoStandard() {
        assertPojoMethodsFor(Identity.class).testing(Method.GETTER, Method.SETTER, Method.EQUALS, Method.HASH_CODE).areWellImplemented();
    }
}