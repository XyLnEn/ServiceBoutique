package com.alma.boutique.domain.history;

import com.alma.boutique.domain.thirdperson.ShopOwner;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Account {
	private float currentBalance;
	private ThirdParty owner;
	
	public Account() {
		this.owner = new ShopOwner();
		this.currentBalance = 0;
	}
	
	public Account(ThirdParty owner) {
		this.owner = owner;
		this.currentBalance = 0;
	}
	
	public float getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(float currentBalance) {
		this.currentBalance = currentBalance;
	}
	public ThirdParty getOwner() {
		return owner;
	}
	public void setOwner(ThirdParty owner) {
		this.owner = owner;
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
		Account rhs = (Account) obj;
		return new EqualsBuilder()
				.append(this.currentBalance, rhs.currentBalance)
				.append(this.owner, rhs.owner)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(currentBalance)
				.append(owner)
				.toHashCode();
	}
}
