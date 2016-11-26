package com.alma.boutique.application.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.OrderNotFoundException;

public class TransactionControllerTest {
	
	private TransactionController c;
	
	@Before
	public void setUp() throws Exception {
		Shop s = new Shop();
		c = new TransactionController(s);
	}

	@Test
	public void testInit() {
		c.init();
		assertThat(true).as("test init for TransactionController").isTrue();
	}

	@Test
	public void testBuy() throws IOException, IllegalDiscountException, OrderNotFoundException {
		//todo
	}

	@Test
	public void testResupply() {
		//todo
	}

}
