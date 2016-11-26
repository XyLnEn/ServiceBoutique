package com.alma.boutique.domain.history;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Order;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * method that represent a transaction between 2 persons 
 * @author Lenny Lucas
 *
 */
public class Transaction extends Entity {
	
	private int orderId;
	private int shopOwnerId;
	private int partyId;
	
	public Transaction() {
		super();
	}
	
	public Transaction(int orderId, int shopOwnerId, int partyId) {
		super();
		this.orderId = orderId;
		this.shopOwnerId = shopOwnerId;
		this.partyId = partyId;
	}

	public float getAmount(IRepository<Order> orderRepo) throws IllegalDiscountException {
		return orderRepo.read(this.orderId).TotalPrice();
	}

	

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getShopOwnerId() {
		return shopOwnerId;
	}

	public void setShopOwnerId(int shopOwnerId) {
		this.shopOwnerId = shopOwnerId;
	}

	public int getPartyId() {
		return partyId;
	}

	public void setPartyId(int partyId) {
		this.partyId = partyId;
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
				.append(this.orderId, rhs.orderId)
				.append(this.shopOwnerId, rhs.shopOwnerId)
				.append(this.partyId, rhs.partyId)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(orderId)
				.append(shopOwnerId)
				.append(partyId)
				.toHashCode();
	}
}
