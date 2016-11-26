package com.alma.boutique.application.controllers;

import org.junit.Test;

import com.alma.boutique.domain.Shop;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderControllerTest {

	@Test
	public void testInit() {
		Shop s = new Shop();
		OrderController c = new OrderController(s);
		c.init();
		assertThat(true).as("test init for OrderController").isTrue();
	}

}
