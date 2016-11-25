package com.alma.boutique.infrastructure.factories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderStatus;

/**
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class OrderFactory implements IFactory<Order> {

	protected List<Product> products;
	protected OrderStatus orderStatus;
	protected String deliverer;

	

  public OrderFactory(String deliverer) {
		this.products = new ArrayList<>();
		this.orderStatus = OrderStatus.ORDERED;
		this.deliverer = deliverer;
	}



	@Override
  public Order create() throws IOException {
		
      return new Order(orderStatus, deliverer);
  }
}
