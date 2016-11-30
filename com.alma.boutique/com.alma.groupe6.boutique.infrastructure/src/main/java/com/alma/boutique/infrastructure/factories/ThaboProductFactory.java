package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.infrastructure.conversion.ThaboProduct;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * @author Thomas Minier
 */
public class ThaboProductFactory implements IFactory<ThaboProduct> {
	
	private String id;
  private String name;
  private String price;
  private String description;
  private String productType;
  private int quantity;
  


	public ThaboProductFactory(String id, String name, String price, String description, String productType, int quantity) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.productType = productType;
		this.quantity = quantity;
	}



	@Override
  public ThaboProduct create() throws IOException {
		return new ThaboProduct(id, name, price, description, productType, quantity);
  }
}
