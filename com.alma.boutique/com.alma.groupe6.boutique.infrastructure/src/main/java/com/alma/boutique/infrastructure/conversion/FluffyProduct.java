package com.alma.boutique.infrastructure.conversion;

import org.slf4j.LoggerFactory;

import com.alma.boutique.domain.product.Category;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;

public class FluffyProduct implements SupplierTraductor {
	private String id;
  private String name;
  private String price;
  private String description;
  private String productType;
  private int quantity;
  
  public FluffyProduct() {
  	//empty constructor for the serialisation
  }
  
	public FluffyProduct(String id, String name, String price, String description, String productType, int quantity) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.productType = productType;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Product translate() {
		float prod = 0;
		try {
			prod = Float.parseFloat(price);
		} catch (Exception e) {
			LoggerFactory.getLogger(Entity.class).warn(e.getMessage(),e);
			prod = Integer.MAX_VALUE;	
		}
		return new Product(this.getName(), new Price(prod, "EUR") , this.getDescription(), new Category(this.getProductType()));
	}
}
