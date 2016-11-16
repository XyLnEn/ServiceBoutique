package com.alma.boutique.domain.thirdperson;

import java.io.IOException;
import java.util.ArrayList;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.product.SuppliedProduct;

/**
 * 
 * @author lenny
 *
 */
public class OrderSuppliedProduct extends Order {

	public OrderSuppliedProduct(){
		super();
		this.products = new ArrayList<>();
		this.orderStatus = OrderStatus.ORDERED;
		this.deliverer = "";
	}
	
	public OrderSuppliedProduct(OrderStatus orderStatus, String deliverer) {
		super();
		this.products = new ArrayList<>();
		this.orderStatus = orderStatus;
		this.deliverer = deliverer;
	}
	
	public Product createProduct(IFactory factoryProd) {
		SuppliedProduct prod = null;
		try {
			prod = (SuppliedProduct) factoryProd.create();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		products.add(prod);
		return prod;
	}
}
