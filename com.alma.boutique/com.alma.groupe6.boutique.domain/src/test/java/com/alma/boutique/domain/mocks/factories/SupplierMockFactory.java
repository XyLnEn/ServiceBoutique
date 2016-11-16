package com.alma.boutique.domain.mocks.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.Supplier;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class SupplierMockFactory implements IFactory<Supplier> {
    private String supplierName;
    private String address;
    private String telNumber;

    public SupplierMockFactory(String supplierName, String address, String telNumber) {
        this.supplierName = supplierName;
        this.address = address;
        this.telNumber = telNumber;
    }

    @Override
    public Supplier create() throws IOException {
        Identity supplierId = new Identity(address, telNumber);
        return new Supplier(supplierName, supplierId);
    }
}
