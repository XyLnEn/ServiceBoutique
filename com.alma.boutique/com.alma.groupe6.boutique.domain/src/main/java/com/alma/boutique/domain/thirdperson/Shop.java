package com.alma.boutique.domain.thirdperson;

import com.alma.boutique.domain.factories.FactorySoldProduct;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
		Shop rhs = (Shop) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(this.shopName, rhs.shopName)
				.append(this.shopId, rhs.shopId)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(shopName)
				.append(shopId)
				.toHashCode();
	}
}
