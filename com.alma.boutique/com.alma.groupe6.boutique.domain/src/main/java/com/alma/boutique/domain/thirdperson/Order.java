package com.alma.boutique.domain.thirdperson;

import java.util.List;

import com.alma.boutique.domain.shared.Entity;

public class Order extends Entity {

//	private List<StockProduct> products; \\TODO class StockProduct
	private OrderStatus orderStatus;
	private float totalPrice;
	private String deliverer;
	
	
	public void updateOrder(Order ord) {
		this.orderStatus = ord.getOrderStatus();
		this.deliverer = ord.getDeliverer();
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public float getTotalPrice() {//calculated
		return totalPrice;
	}
//	public void setTotalPrice(float totalPrice) {//useless
//		this.totalPrice = totalPrice;
//	}
	public String getDeliverer() {
		return deliverer;
	}
	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}
	
	

}
