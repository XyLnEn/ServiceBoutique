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
	
}
