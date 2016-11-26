package com.alma.boutique.application.controllers;

import org.junit.Test;

import com.alma.boutique.domain.Shop;

import static org.assertj.core.api.Assertions.assertThat;

public class SupplierCatalogControllerTest {

	@Test
	public void testInit() {
		Shop s = new Shop();
		SupplierCatalogController c = new SupplierCatalogController(s);
		c.init();
		assertThat(true).as("test init for SupplierCatalogController").isTrue();
	}

}
