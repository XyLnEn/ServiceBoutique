package com.alma.boutique.domain.history;

import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class Transaction extends Entity {
	
	private Order order;
	private ThirdParty from;
	private ThirdParty to;
	
	public Transaction() {
		super();
		this.order = new Order();
		this.from = new ThirdParty() {
		};
		this.to = new ThirdParty() {
		};
	}
	
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Transaction that = (Transaction) o;
		return order.equals(that.order) && from.equals(that.from) && to.equals(that.to);

	}

	@Override
	public int hashCode() {
		int result = order.hashCode();
		result = 31 * result + from.hashCode();
		result = 31 * result + to.hashCode();
		return result;
	}
}
