package com.alma.boutique.domain.thirdperson;

import com.alma.boutique.domain.factories.FactorySuppliedProduct;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
		this.setFactoryProd(new FactorySuppliedProduct());
		this.supplierName = "";
		this.supplierId = new Identity();
	}
	
	
	public Supplier(String supplierName, Identity supplierId) {
		super();
		this.setFactoryProd(new FactorySuppliedProduct());
		this.supplierName = supplierName;
		this.supplierId = supplierId;
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
