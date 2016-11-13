package com.alma.boutique.domain.factories;

import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.Shop;

public class FactoryShop {

	public Shop make(String shopName, String address, String telNumber) {
		Identity supplierId = new Identity(address, telNumber);
		return new Shop(shopName, supplierId);
	}
}
