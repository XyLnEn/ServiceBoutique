package com.alma.boutique.domain.factories;

import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.ShopOwner;

public class FactoryShop {

	public ShopOwner make(String shopName, String address, String telNumber) {
		Identity supplierId = new Identity(address, telNumber);
		return new ShopOwner(shopName, supplierId);
	}
}
