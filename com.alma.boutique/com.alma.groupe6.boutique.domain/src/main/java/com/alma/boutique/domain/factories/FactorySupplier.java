package com.alma.boutique.domain.factories;

import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.Supplier;

public class FactorySupplier {

	public Supplier make(String supplierName, String address, String telNumber) {
		Identity supplierId = new Identity(address, telNumber);
		return new Supplier(supplierName, supplierId);
	}
}
