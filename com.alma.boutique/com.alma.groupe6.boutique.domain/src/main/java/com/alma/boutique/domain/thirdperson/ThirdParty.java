package com.alma.boutique.domain.thirdperson;

import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.factories.FactoryOrder;
import com.alma.boutique.domain.shared.*;

public abstract class ThirdParty extends Entity {

	private List<Order> orderHistory;
	private FactoryOrder factoryOrd;
	private FactoryProduct factoryProd;

	public ThirdParty() {
		this.orderHistory = new ArrayList<>();
		this.factoryOrd =  new FactoryOrder();
	}
	
	
	
	public Order createOrder(String deliverer) {
		Order newOrd = factoryOrd.make(deliverer,factoryProd);
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
	
	public void updateOrder(Order ordBase, Order newOrd) throws OrderNotFoundException {
		for (Order order : orderHistory) {
			if (order.sameIdentityAs(ordBase)){
				order.updateOrder(newOrd);
				return ;
			}
		}
		throw new OrderNotFoundException("Order not found");//in case the order doesn't exist
	}
	
	public void deleteOrder(Order ord) {
		orderHistory.remove(ord);
	}

	
	
	public List<Order> getOrderHistory() {
		return orderHistory;
	}

	public FactoryOrder getFactoryOrd() {
		return factoryOrd;
	}

	public void setFactoryOrd(FactoryOrder factoryOrd) {
		this.factoryOrd = factoryOrd;
	}

	public FactoryProduct getFactoryProd() {
		return factoryProd;
	}

	public void setFactoryProd(FactoryProduct factoryProd) {
		this.factoryProd = factoryProd;
	}
	
	
	
	
}
