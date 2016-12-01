package com.alma.boutique.infrastructure.conversion;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;

import pl.pojo.tester.api.assertion.Method;

public class ThaboProductTest {

  @Test
  public void testPojoStandard() {
      assertPojoMethodsFor(ThaboProduct.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
  }

}
