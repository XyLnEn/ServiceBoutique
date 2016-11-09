package com.alma.boutique.domain.thirdperson;

import java.util.List;

import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.shared.*;

abstract class ThirdParty extends Entity {

	private List<Order> orderHistory;

	public void createOrder() {
		//creation of the Order with the factory and addition to the orderHistory
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
	
	public EntityID getId() {
		return id;
	}
	public void setId(EntityID id) {
		this.id = id;
	}
	public List<Order> getHistoriqueTransaction() {
		return orderHistory;
	}
	public void setHistoriqueTransaction(List<Order> historiqueTransaction) {
		this.orderHistory = historiqueTransaction;
	}
	
}
