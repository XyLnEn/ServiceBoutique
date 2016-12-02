package com.alma.boutique.infrastructure.conversion;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;

import pl.pojo.tester.api.assertion.Method;

public class FluffyProductTest {

  @Test
  public void testPojoStandard() {
      assertPojoMethodsFor(FluffyProduct.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
  }

}
