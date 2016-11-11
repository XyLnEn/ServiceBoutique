package com.alma.boutique.domain.thirdperson;

import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.*;

abstract class ThirdParty extends Entity {

	private List<Order> orderHistory;

	public ThirdParty() {
		this.orderHistory = new ArrayList<>();
	}
	
	public Order createOrder(List<Product> products, OrderStatus orderStatus, String deliverer) {
		//creation of the Order with the factory and addition to the orderHistory
		Order newOrd = new Order(orderStatus, deliverer);
		this.orderHistory.add(newOrd);
		return newOrd;
	}
	
	public Order getOrder(Order ord) throws OrderNotFoundException {
		for (Order order : orderHistory) {
			if (order.sameIdentityAs(ord)){
				return order;
			}
		}
		throw new OrderNotFoundException("Order not found");//in case the order doesn't exist
	}
	
	public void updateOrder(Order ord) {
		for (Order order : orderHistory) {
			if (order.sameIdentityAs(ord)){
				order.updateOrder(ord);
			}
		}
	}
	
	public void deleteOrder(Order ord) {
		orderHistory.remove(ord);
	}

	public List<Order> getOrderHistory() {
		return orderHistory;
	}
	
	
}
