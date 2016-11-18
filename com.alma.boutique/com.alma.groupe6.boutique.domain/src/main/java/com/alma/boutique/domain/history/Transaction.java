package com.alma.boutique.domain.history;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderSuppliedProduct;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Transaction extends Entity {
	
	private Order order;
	private ThirdParty from;
	private ThirdParty to;
	
	public Transaction() {
		super();
		this.order = new OrderSuppliedProduct();
	}
	
	public Transaction(Order order, ThirdParty from, ThirdParty to) {
		super();
		this.order = order;
		this.from = from;
		this.to = to;
	}

	public float getAmount() throws IllegalDiscountException {
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
		Transaction rhs = (Transaction) obj;
		return new EqualsBuilder()
				.append(this.order, rhs.order)
				.append(this.from, rhs.from)
				.append(this.to, rhs.to)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(order)
				.append(from)
				.append(to)
				.toHashCode();
	}
}
