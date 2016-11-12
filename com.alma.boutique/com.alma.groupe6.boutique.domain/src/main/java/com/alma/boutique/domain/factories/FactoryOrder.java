package com.alma.boutique.domain.factories;

import com.alma.boutique.domain.shared.FactoryProduct;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderStatus;

public class FactoryOrder {

	public Order make(String deliverer, FactoryProduct factory) {
		return new Order(OrderStatus.ORDERED, deliverer, factory);
	}
}
