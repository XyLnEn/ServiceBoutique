package com.alma.boutique.application.controllers;

import org.junit.Test;

import com.alma.boutique.domain.Shop;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonControllerTest {

	@Test
	public void testInit() {
		Shop s = new Shop();
		PersonController c = new PersonController(s);
		c.init();
		assertThat(true).as("test init for PersonController").isTrue();
	}

}
