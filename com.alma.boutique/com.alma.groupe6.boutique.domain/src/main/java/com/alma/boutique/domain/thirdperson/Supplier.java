package com.alma.boutique.domain.thirdperson;

import com.alma.boutique.domain.factories.FactorySuppliedProduct;
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
}
