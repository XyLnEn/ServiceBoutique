package com.alma.boutique.domain.mocks.factories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.OrderSoldProduct;
import com.alma.boutique.domain.thirdperson.OrderStatus;

public class OrderSoldProductMockFactory implements IFactory<OrderSoldProduct> {

	protected List<Product> products;
	protected OrderStatus orderStatus;
	protected String deliverer;

	

  public OrderSoldProductMockFactory(String deliverer) {
		this.products = new ArrayList<>();
		this.orderStatus = OrderStatus.ORDERED;
		this.deliverer = deliverer;
	}



	@Override
  public OrderSoldProduct create() throws IOException {
		
      return new OrderSoldProduct(orderStatus, deliverer);
  }
}
