package com.alma.boutique.domain.history;

import com.alma.boutique.domain.thirdperson.Shop;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class Account {
	private float currentBalance;
	private ThirdParty owner;
	
	public Account() {
		this.owner = new Shop();
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
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Float.compare(account.currentBalance, currentBalance) == 0 && owner.equals(account.owner);
    }

	@Override
	public int hashCode() {
		int result = Float.compare(currentBalance, +0.0f) != 0 ? Float.floatToIntBits(currentBalance) : 0;
		result = 31 * result + owner.hashCode();
		return result;
	}
}
