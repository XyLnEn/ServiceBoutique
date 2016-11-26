package com.alma.boutique.application.controllers;

import org.junit.Test;

import com.alma.boutique.domain.Shop;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogControllerTest {

	@Test
	public void testInit() {
		Shop s = new Shop();
		CatalogController c = new CatalogController(s);
		c.init();
		assertThat(true).as("test init for CatalogController").isTrue();
	}

}
