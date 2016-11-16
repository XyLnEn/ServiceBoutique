package com.alma.boutique.domain.thirdperson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.shared.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * @author lenny
 *
 */
public abstract class ThirdParty extends Entity {

	private List<Order> orderHistory;

	public ThirdParty() {
		this.orderHistory = new ArrayList<>();
	}
	
	public abstract Order createOrder(IFactory factoryOrd);

	
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

	public void setOrderHistory(List<Order> orderHistory) {
		this.orderHistory = orderHistory;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		ThirdParty rhs = (ThirdParty) obj;
		return new EqualsBuilder()
				.append(this.orderHistory, rhs.orderHistory)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(orderHistory)
				.toHashCode();
	}
}
