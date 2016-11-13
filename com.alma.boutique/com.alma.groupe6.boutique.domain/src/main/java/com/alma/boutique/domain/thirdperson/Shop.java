package com.alma.boutique.domain.thirdperson;

import com.alma.boutique.domain.factories.FactorySoldProduct;

public class Shop extends ThirdParty {

	private final String shopName;
	private final Identity shopId;
	
	
	public Shop(String shopName, Identity shopId) {
		super();
		this.setFactoryProd(new FactorySoldProduct());
		this.shopName = shopName;
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public Identity getShopId() {
		return shopId;
	}
	
	
	
}
