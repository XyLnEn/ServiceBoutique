package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.OrderSoldProduct;
import com.alma.boutique.domain.thirdperson.OrderStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderSoldProductFactory implements IFactory<OrderSoldProduct> {

	private List<Product> products;
	private OrderStatus orderStatus;
	private String deliverer;

  	public OrderSoldProductFactory(String deliverer) {
		this.products = new ArrayList<>();
		this.orderStatus = OrderStatus.ORDERED;
		this.deliverer = deliverer;
  	}

	@Override
  	public OrderSoldProduct create() throws IOException {
		return new OrderSoldProduct(orderStatus, deliverer);
  	}
}
