package com.alma.boutique.domain.mocks.factories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderStatus;

public class OrderMockFactory implements IFactory<Order> {

	protected List<Product> products;
	protected OrderStatus orderStatus;
	protected String deliverer;

	

  public OrderMockFactory(String deliverer) {
		this.products = new ArrayList<>();
		this.orderStatus = OrderStatus.ORDERED;
		this.deliverer = deliverer;
	}



	@Override
  public Order create() throws IOException {
		
      return new Order(orderStatus, deliverer);
  }
}
