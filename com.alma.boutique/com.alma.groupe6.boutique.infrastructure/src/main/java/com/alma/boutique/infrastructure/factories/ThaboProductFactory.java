package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.infrastructure.conversion.ThaboProduct;
import com.alma.boutique.infrastructure.webservice.JSONPOSTWebservice;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class ThaboProductFactory implements IFactory<ThaboProduct> {
	
	private String id;
  


	public ThaboProductFactory(String id) {
		
		this.id = id;
	}

	@Override
  public ThaboProduct create() throws IOException {
		JSONPOSTWebservice<ThaboProduct> web = new JSONPOSTWebservice<>("https://fluffy-stock.herokuapp.com/api/product/", ThaboProduct.class);
		return web.read(id + "/order/1");
  }
}
