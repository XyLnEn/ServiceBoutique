package com.alma.boutique.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Before;
import org.junit.Test;

import com.alma.boutique.domain.mocks.factories.ClientMockFactory;
import com.alma.boutique.domain.mocks.factories.OrderSoldProductMockFactory;
import com.alma.boutique.domain.mocks.factories.OrderSuppliedProductMockFactory;
import com.alma.boutique.domain.mocks.factories.SoldProductMockFactory;
import com.alma.boutique.domain.mocks.factories.SuppliedProductMockFactory;
import com.alma.boutique.domain.mocks.factories.SupplierMockFactory;
import com.alma.boutique.domain.mocks.factories.TransactionMockFactory;
import com.alma.boutique.domain.mocks.repositories.ClientMockRepository;
import com.alma.boutique.domain.mocks.repositories.ShopOwnerMockRepository;
import com.alma.boutique.domain.mocks.repositories.SoldProductMockRepository;
import com.alma.boutique.domain.mocks.repositories.SupplierMockRepository;
import com.alma.boutique.domain.mocks.repositories.TransactionMockRepository;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.ShopOwner;

import pl.pojo.tester.api.assertion.Method;

public class ShopTest {

	@Before
	public void setUp() throws Exception {
		ShopOwnerMockRepository ownerRepo = new ShopOwnerMockRepository();
		ShopOwner shopOwner = new ShopOwner("bob",new Identity("house", "11111111"));
		ownerRepo.add(shopOwner.getID(), shopOwner);
		SupplierMockFactory supplier1 = new SupplierMockFactory("supp", "here", "888");
		SupplierMockFactory supplier2 = new SupplierMockFactory("supp", "here", "888");
		Shop shop = new Shop(supplier1, new ClientMockFactory("bob", "ross", "there", "777"), 
				new OrderSuppliedProductMockFactory("jon"), new OrderSoldProductMockFactory("tron"), new SuppliedProductMockFactory("mousse", 1, "EUR", "a raser", "hygienne"),
				new SoldProductMockFactory("mousse", 2, "EUR", "a raser", "hygienne"), 
				new TransactionMockFactory(new OrderSuppliedProductMockFactory("jon").create(), supplier2.create(), shopOwner), 
				new SupplierMockRepository(), new ClientMockRepository(), ownerRepo, null, new SoldProductMockRepository(), new TransactionMockRepository());
	}

	@Test
	public void testPojoStandard() {
		assertPojoMethodsFor(Shop.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}

}
