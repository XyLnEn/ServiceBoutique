package com.alma.boutique.application.data;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;


public class PurchaseTest {

  @Test
  public void testPojoStandard() {
      assertPojoMethodsFor(Purchase.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
  }

}
