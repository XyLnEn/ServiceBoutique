package com.alma.boutique.domain.thirdperson;

import java.io.IOException;
import java.util.ArrayList;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.product.SoldProduct;

public class OrderSoldProduct extends Order {

	public OrderSoldProduct(){
		super();
		this.products = new ArrayList<>();
		this.orderStatus = OrderStatus.ORDERED;
		this.deliverer = "";
	}
	
	public OrderSoldProduct(OrderStatus orderStatus, String deliverer) {
		super();
		this.products = new ArrayList<>();
		this.orderStatus = orderStatus;
		this.deliverer = deliverer;
	}
	
	public Product createProduct(IFactory factoryProd) {
		SoldProduct prod = null;
		try {
			prod = (SoldProduct) factoryProd.create();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		products.add(prod);
		return prod;
	}

}
