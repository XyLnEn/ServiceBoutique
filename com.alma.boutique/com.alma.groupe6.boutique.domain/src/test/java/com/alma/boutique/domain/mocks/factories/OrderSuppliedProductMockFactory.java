package com.alma.boutique.domain.mocks.factories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.OrderSoldProduct;
import com.alma.boutique.domain.thirdperson.OrderStatus;
import com.alma.boutique.domain.thirdperson.OrderSuppliedProduct;

public class OrderSuppliedProductMockFactory implements IFactory<OrderSuppliedProduct> {

	protected List<Product> products;
	protected OrderStatus orderStatus;
	protected String deliverer;

	

  public OrderSuppliedProductMockFactory(String deliverer) {
		this.products = new ArrayList<>();
		this.orderStatus = OrderStatus.ORDERED;
		this.deliverer = deliverer;
	}



	@Override
  public OrderSuppliedProduct create() throws IOException {
		
      return new OrderSuppliedProduct(orderStatus, deliverer);
  }
}
