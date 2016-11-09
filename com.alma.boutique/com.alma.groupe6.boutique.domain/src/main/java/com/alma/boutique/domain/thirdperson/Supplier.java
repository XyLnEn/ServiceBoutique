package com.alma.boutique.domain.thirdperson;

public class Supplier extends ThirdParty {

	private final String supplierName;
	private final Identity supplierId;
	
	
	public Supplier(String supplierName, Identity supplierId) {
		super();
		this.supplierName = supplierName;
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public Identity getSupplierId() {
		return supplierId;
	}
	
	

}
