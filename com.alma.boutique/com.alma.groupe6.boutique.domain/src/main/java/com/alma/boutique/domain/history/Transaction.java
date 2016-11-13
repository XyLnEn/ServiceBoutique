package com.alma.boutique.domain.history;

import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class Transaction extends Entity {
	
	private Order order;
	private ThirdParty from;
	private ThirdParty to;
	
	public Transaction(Order order, ThirdParty from, ThirdParty to) {
		super();
		this.order = order;
		this.from = from;
		this.to = to;
	}

	public float getAmount() {
		return this.order.getTotalPrice();
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ThirdParty getFrom() {
		return from;
	}

	public void setFrom(ThirdParty from) {
		this.from = from;
	}

	public ThirdParty getTo() {
		return to;
	}

	public void setTo(ThirdParty to) {
		this.to = to;
	}
	
	
}
