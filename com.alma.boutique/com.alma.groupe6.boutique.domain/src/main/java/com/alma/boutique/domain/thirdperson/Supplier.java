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
public class Supplier extends ThirdParty {

	private String supplierName;
	private Identity supplierId;
	
	public Supplier() {
		super();
		this.supplierName = "";
		this.supplierId = new Identity();
	}
	
	
	public Supplier(String supplierName, Identity supplierId) {
		super();
		this.supplierName = supplierName;
		this.supplierId = supplierId;
	}
	
	public Order createOrder(IFactory factoryOrd) {
		OrderSuppliedProduct newOrd = null;
		try {
			newOrd = (OrderSuppliedProduct) factoryOrd.create();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getOrderHistory().add(newOrd);
		return newOrd;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public Identity getSupplierId() {
		return supplierId;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setSupplierId(Identity supplierId) {
		this.supplierId = supplierId;
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
		Supplier rhs = (Supplier) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(this.supplierName, rhs.supplierName)
				.append(this.supplierId, rhs.supplierId)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(supplierName)
				.append(supplierId)
				.toHashCode();
	}
}
