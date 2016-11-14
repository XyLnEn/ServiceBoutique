package com.alma.boutique.domain.thirdperson;

import com.alma.boutique.domain.factories.FactorySoldProduct;
/**
 * 
 * @author lenny
 *
 */
public class Shop extends ThirdParty {

	private String shopName;
	private Identity shopId;
	
	public Shop() {
		super();
		this.setFactoryProd(new FactorySoldProduct());
		this.shopName = "";
		this.shopId = new Identity();
	}
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
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public void setShopId(Identity shopId) {
		this.shopId = shopId;
	}
	
	
}
