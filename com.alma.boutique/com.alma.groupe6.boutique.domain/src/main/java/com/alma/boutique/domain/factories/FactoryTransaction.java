package com.alma.boutique.domain.factories;

import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class FactoryTransaction {

	public Transaction make(Order ord, ThirdParty from, ThirdParty to) {
		return new Transaction(ord, from, to);
	}
}
