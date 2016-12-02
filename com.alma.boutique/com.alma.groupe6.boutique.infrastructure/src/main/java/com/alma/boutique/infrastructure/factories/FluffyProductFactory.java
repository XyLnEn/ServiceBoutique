package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.infrastructure.conversion.FluffyProduct;
import com.alma.boutique.infrastructure.webservice.JSONPOSTWebservice;

import java.io.IOException;

/**
 * Factory used to instantiate a product from the supplier FluffyStock
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class FluffyProductFactory implements IFactory<FluffyProduct> {
	
	private String id;

	public FluffyProductFactory(String id) {
		
		this.id = id;
	}

	@Override
  public FluffyProduct create() throws IOException {
		JSONPOSTWebservice<FluffyProduct> web = new JSONPOSTWebservice<>("https://fluffy-stock.herokuapp.com/api/product/", FluffyProduct.class);
		return web.read(id + "/order/1");
  }
}
