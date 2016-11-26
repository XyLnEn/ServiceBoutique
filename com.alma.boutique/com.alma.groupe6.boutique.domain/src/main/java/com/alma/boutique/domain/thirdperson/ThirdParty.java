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
 * class that represent a person with his/her order history
 * @author Lenny Lucas
 *
 */
public class ThirdParty extends Entity {

	private List<Order> orderHistory;
	private String name;
	private Identity info;
	private boolean isSupplier;
	
	public ThirdParty() {
		this.orderHistory = new ArrayList<>();
		this.name = "";
		this.info = new Identity();
		this.isSupplier = false;
	}
	
	public ThirdParty(String name, Identity info, boolean isSupplier) {
		this.orderHistory = new ArrayList<>();
		this.name = name;
		this.info = info;
		this.isSupplier = isSupplier;
	}

	/**
	 * create an order
	 * @param factoryOrd the factory that will supply the order
	 * @return the created order
	 */
	public Order createOrder(IFactory<Order> factoryOrd) {
		Order newOrd = null;
		try {
			newOrd = factoryOrd.create();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		this.getOrderHistory().add(newOrd);
		return newOrd;
	}

	/**
	 * get the order if it is in the OrderHistory
	 * @param prodId the unique Id of the order
	 * @return the order 
	 * @throws OrderNotFoundException
	 */
	public Order getOrder(int ordId) throws OrderNotFoundException {
		for (Order order : orderHistory) {
			if (order.getId() == ordId){
				return order;
			}
		}
		throw new OrderNotFoundException("Order not found");//in case the order doesn't exist
	}
	
	/**
	 * Update a pre-existing order with new values
	 * @param oldOrdId the Id of the order to update
	 * @param newOrd the parton that will be used to update the order
	 * @throws OrderNotFoundException
	 */
	public void updateOrder(int oldOrdId, Order newOrd) throws OrderNotFoundException {
		for (Order order : orderHistory) {
			if (order.getId() == oldOrdId){
				order.updateOrder(newOrd);
				return ;
			}
		}
		throw new OrderNotFoundException("Order not found");//in case the order doesn't exist
	}
	
	/**
	 * Delete an order
	 * @param ord the order to delete
	 */
	public void deleteOrder(Order ord) {
		orderHistory.remove(ord);
	}
	
	
	
	public List<Order> getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(List<Order> orderHistory) {
		this.orderHistory = orderHistory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Identity getInfo() {
		return info;
	}

	public void setInfo(Identity info) {
		this.info = info;
	}

	public boolean isSupplier() {
		return isSupplier;
	}

	public void setSupplier(boolean isSupplier) {
		this.isSupplier = isSupplier;
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
