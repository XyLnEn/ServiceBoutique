package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.ShopOwner;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class ShopOwnerFactory implements IFactory<ShopOwner> {
  private String supplierName;
  private String address;
  private String telNumber;

  public ShopOwnerFactory(String supplierName, String address, String telNumber) {
      this.supplierName = supplierName;
      this.address = address;
      this.telNumber = telNumber;
  }

  @Override
  public ShopOwner create() throws IOException {
      Identity supplierId = new Identity(address, telNumber);
      return new ShopOwner(supplierName, supplierId);
  }
}
