package com.alma.boutique.domain.thirdperson;

import java.io.IOException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.alma.boutique.api.IFactory;

/**
 * 
 * @author lenny
 *
 */
public class ShopOwner extends ThirdParty {

	private String shopOwnerName;
	private Identity shopId;
	
	public ShopOwner() {
		super();
		this.shopOwnerName = "";
		this.shopId = new Identity();
	}
	public ShopOwner(String shopName, Identity shopId) {
		super();
		this.shopOwnerName = shopName;
		this.shopId = shopId;
	}
	
	public Order createOrder(IFactory factoryOrd) {
		OrderSuppliedProduct newOrd = null;
		try {
			newOrd = (OrderSuppliedProduct) factoryOrd.create();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		this.getOrderHistory().add(newOrd);
		return newOrd;
	}
	
	
	public String getShopOwnerName() {
		return shopOwnerName;
	}
	public Identity getShopId() {
		return shopId;
	}
	public void setShopOwnerName(String shopName) {
		this.shopOwnerName = shopName;
	}
	public void setShopId(Identity shopId) {
		this.shopId = shopId;
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
		ShopOwner rhs = (ShopOwner) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(this.shopOwnerName, rhs.shopOwnerName)
				.append(this.shopId, rhs.shopId)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(shopOwnerName)
				.append(shopId)
				.toHashCode();
	}
}
