package com.alma.boutique.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Before;
import org.junit.Test;

import com.alma.boutique.domain.thirdperson.Client;

import pl.pojo.tester.api.assertion.Method;

public class ShopTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPojoStandard() {
		assertPojoMethodsFor(Client.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}

}
