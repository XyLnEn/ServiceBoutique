package com.alma.boutique.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.exceptions.TransactionNotFoundException;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.mocks.ExchangeRateServiceMock;
import com.alma.boutique.domain.mocks.factories.ClientMockFactory;
import com.alma.boutique.domain.mocks.factories.OrderSoldProductMockFactory;
import com.alma.boutique.domain.mocks.factories.SoldProductMockFactory;
import com.alma.boutique.domain.mocks.factories.TransactionMockFactory;
import com.alma.boutique.domain.mocks.repositories.ShopOwnerMockRepository;
import com.alma.boutique.domain.mocks.repositories.SoldProductMockRepository;
import com.alma.boutique.domain.mocks.repositories.TransactionMockRepository;
import com.alma.boutique.domain.product.SoldProduct;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ShopOwner;

import pl.pojo.tester.api.assertion.Method;

public class ShopTest {

	private Shop shop;
	
	@Before
	public void setUp() throws Exception {
		ShopOwnerMockRepository ownerRepo = new ShopOwnerMockRepository();
		ShopOwner shopOwner = new ShopOwner("bob",new Identity("house", "11111111"));
		ownerRepo.add(shopOwner.getID(), shopOwner);
		this.shop = new Shop(ownerRepo);
	}
	
	@Test
	public void testBrowseStock() throws IOException {
		SoldProductMockRepository soldRepo = new SoldProductMockRepository();
		assertThat(shop.browseStock(soldRepo)).as("assert that the shop can browse an empty stock").isEmpty();
		SoldProductMockFactory soldFacto = new SoldProductMockFactory("lemon", 5, "EUR", "bitter", "fruit");
		SoldProduct prod = soldFacto.create();
		soldRepo.add(prod.getID(), prod);
		assertThat(shop.browseStock(soldRepo)).as("assert that the shop return the correct product").contains(prod);
	}
	
	@Test
	public void testBuyProduct() throws IOException, ProductNotFoundException, IllegalDiscountException {
		SoldProductMockRepository soldRepo = new SoldProductMockRepository();
		
		SoldProductMockFactory soldFacto = new SoldProductMockFactory("lemon", 5, "EUR", "bitter", "fruit");
		SoldProduct prod1 = soldFacto.create();

		soldFacto = new SoldProductMockFactory("apple", 7, "EUR", "sweet", "fruit");
		SoldProduct prod2 = soldFacto.create();
		
		OrderSoldProductMockFactory soldOrder = new OrderSoldProductMockFactory("bob");
		List<Integer> listeAchat = new ArrayList<>();
		Order emptyOrder = shop.buyProduct(soldRepo, soldOrder, listeAchat, "EUR", new ExchangeRateServiceMock());
		
		assertThat(emptyOrder.getProducts()).as("test that the order created from the purchase is correct for an empty purchase").isEmpty();

		soldRepo.add(prod1.getID(), prod1);
		soldRepo.add(prod2.getID(), prod2);
		listeAchat.add(prod1.getID());
		listeAchat.add(prod2.getID());
		Order realOrder = shop.buyProduct(soldRepo, soldOrder, listeAchat, "EUR", new ExchangeRateServiceMock());
		assertThat(realOrder.getProduct(prod1.getID())).as("test that the order is bought successfully").isEqualTo(prod1);
	}
	
	@Test
	public void testSaveTransaction() throws IOException, TransactionNotFoundException, IllegalDiscountException {
		SoldProductMockRepository soldRepo = new SoldProductMockRepository();
		
		SoldProductMockFactory soldFacto = new SoldProductMockFactory("lemon", 5, "EUR", "bitter", "fruit");
		SoldProduct prod1 = soldFacto.create();

		soldFacto = new SoldProductMockFactory("apple", 7, "EUR", "sweet", "fruit");
		SoldProduct prod2 = soldFacto.create();
		
		OrderSoldProductMockFactory soldOrder = new OrderSoldProductMockFactory("bob");
		List<Integer> listeAchat = new ArrayList<>();
		Order emptyOrder = shop.buyProduct(soldRepo, soldOrder, listeAchat, "EUR", new ExchangeRateServiceMock());
		
		assertThat(emptyOrder.getProducts()).as("test that the order created from the purchase is correct for an empty purchase").isEmpty();
		
		soldRepo.add(prod1.getID(), prod1);
		soldRepo.add(prod2.getID(), prod2);
		listeAchat.add(prod1.getID());
		listeAchat.add(prod2.getID());
		Order ordToSave = shop.buyProduct(soldRepo, soldOrder, listeAchat, "EUR", new ExchangeRateServiceMock());
		
		ClientMockFactory clientFacto = new ClientMockFactory("Remi", "grep", "somewhere", "888888");
		Client mockClient = clientFacto.create();
		
		TransactionMockRepository transRepo = new TransactionMockRepository();
		
		TransactionMockFactory transToSave = new TransactionMockFactory(ordToSave, shop.getShopOwner(), mockClient);
		Transaction generatedTrans = shop.saveTransaction(shop.getShopHistory(), transRepo, transToSave);
		assertThat(shop.getShopHistory().getTransaction(generatedTrans.getID(), transRepo)).as("check that the transaction was added successfully").isEqualTo(generatedTrans);
		
	}

	@Test
	public void testPojoStandard() {
		assertPojoMethodsFor(Shop.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}

}
