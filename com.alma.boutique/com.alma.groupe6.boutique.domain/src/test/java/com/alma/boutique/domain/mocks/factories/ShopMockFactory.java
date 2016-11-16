package com.alma.boutique.domain.mocks.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.ShopOwner;
import com.alma.boutique.domain.thirdperson.Supplier;
import com.alma.boutique.domain.thirdperson.ThirdParty;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class ShopMockFactory implements IFactory<ShopOwner> {
  private String supplierName;
  private String address;
  private String telNumber;

  public ShopMockFactory(String supplierName, String address, String telNumber) {
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
