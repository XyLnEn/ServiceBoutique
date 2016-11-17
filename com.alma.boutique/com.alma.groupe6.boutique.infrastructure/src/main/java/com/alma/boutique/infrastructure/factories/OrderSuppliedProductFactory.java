package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.OrderStatus;
import com.alma.boutique.domain.thirdperson.OrderSuppliedProduct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderSuppliedProductFactory implements IFactory<OrderSuppliedProduct> {

	private List<Product> products;
	private OrderStatus orderStatus;
	private String deliverer;

    public OrderSuppliedProductFactory(String deliverer) {
		this.products = new ArrayList<>();
		this.orderStatus = OrderStatus.ORDERED;
		this.deliverer = deliverer;
	}

	@Override
    public OrderSuppliedProduct create() throws IOException {
        return new OrderSuppliedProduct(orderStatus, deliverer);
    }
}
