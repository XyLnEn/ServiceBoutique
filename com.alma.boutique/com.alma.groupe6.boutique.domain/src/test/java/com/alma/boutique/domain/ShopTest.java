package com.alma.boutique.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.api.services.ExchangeRateService;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.exceptions.TransactionNotFoundException;
import com.alma.boutique.domain.history.History;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.mocks.ExchangeRateServiceMock;
import com.alma.boutique.domain.mocks.factories.ClientMockFactory;
import com.alma.boutique.domain.mocks.factories.OrderSoldProductMockFactory;
import com.alma.boutique.domain.mocks.factories.OrderSuppliedProductMockFactory;
import com.alma.boutique.domain.mocks.factories.SoldProductMockFactory;
import com.alma.boutique.domain.mocks.factories.SuppliedProductMockFactory;
import com.alma.boutique.domain.mocks.factories.SupplierMockFactory;
import com.alma.boutique.domain.mocks.factories.TransactionMockFactory;
import com.alma.boutique.domain.mocks.repositories.ShopOwnerMockRepository;
import com.alma.boutique.domain.mocks.repositories.SoldProductMockRepository;
import com.alma.boutique.domain.mocks.repositories.TransactionMockRepository;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.product.SoldProduct;
import com.alma.boutique.domain.product.SuppliedProduct;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderStatus;
import com.alma.boutique.domain.thirdperson.OrderSuppliedProduct;
import com.alma.boutique.domain.thirdperson.ShopOwner;
import com.alma.boutique.domain.thirdperson.ThirdParty;

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
	public void testRestock() throws IOException {
		SoldProductMockRepository soldRepo = new SoldProductMockRepository();
		
		SuppliedProductMockFactory suppProd1 = new SuppliedProductMockFactory("duck", 1, "EUR", "motherducker", "food");
		SuppliedProductMockFactory suppProd2 = new SuppliedProductMockFactory("canari", 1, "EUR", "cute", "food");
		
		OrderSuppliedProductMockFactory ordSupp = new OrderSuppliedProductMockFactory("the poste");
		
		List<IFactory<SuppliedProduct>> testList = new ArrayList<>();
		
		Order ord = shop.restock(soldRepo, testList, ordSupp, "EUR", new ExchangeRateServiceMock());
		assertThat(ord.getDeliverer()).as("test that we can restock 0 items").isEqualTo("the poste");
		
		ordSupp = new OrderSuppliedProductMockFactory("the true poste");
		
		testList.add(suppProd1);
		testList.add(suppProd2);
		
		ord = shop.restock(soldRepo, testList, ordSupp, "EUR", new ExchangeRateServiceMock());
		assertThat(ord.getProducts().size()).as("test that the restock is working for a non-empty list of products").isEqualTo(2);
		
	}
	
	@Test
	public void testAdvanceOrder() throws IOException, OrderNotFoundException {
		History history = new History();
		TransactionMockRepository transHist = new TransactionMockRepository();
		SuppliedProductMockFactory suppProd1 = new SuppliedProductMockFactory("duck", 1, "EUR", "motherducker", "food");
		SuppliedProductMockFactory suppProd2 = new SuppliedProductMockFactory("canari", 1, "EUR", "cute", "food");
		
		OrderSuppliedProductMockFactory ordSupp = new OrderSuppliedProductMockFactory("the poste");
		Order ord = shop.getShopOwner().createOrder(ordSupp);
		ord.createProduct(suppProd1);
		ord.createProduct(suppProd2);
		
		SupplierMockFactory factoSupp = new SupplierMockFactory("LIDL", "somewhere", "00000000");
		TransactionMockFactory transFact = new TransactionMockFactory(ord, factoSupp.create(), shop.getShopOwner());
		
		Transaction t = history.createTransaction(transFact, transHist);
		assertThat(ord.getOrderStatus()).as("assert that the order is created is the base state").isEqualTo(OrderStatus.ORDERED);
		
		shop.advanceOrder(history, transHist, ord.getID());
		assertThat(transHist.read(t.getID()).getOrder().getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history").isEqualTo(OrderStatus.TRAVELING);
		assertThat(shop.getShopOwner().getOrder(ord.getID()).getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history").isEqualTo(OrderStatus.TRAVELING);
		
		shop.advanceOrder(history, transHist, ord.getID());
		assertThat(transHist.read(t.getID()).getOrder().getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history the second time").isEqualTo(OrderStatus.ARRIVED);
		assertThat(shop.getShopOwner().getOrder(ord.getID()).getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history the second time").isEqualTo(OrderStatus.ARRIVED);
		
		shop.advanceOrder(history, transHist, ord.getID());
		assertThat(transHist.read(t.getID()).getOrder().getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history").isEqualTo(OrderStatus.DELIVERED);
		assertThat(shop.getShopOwner().getOrder(ord.getID()).getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history").isEqualTo(OrderStatus.DELIVERED);
		
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> shop.advanceOrder(history, transHist, 8))
		.as("check if it is possible to advance a non-existing order");
		
		
	}
	
	@Test
	public void testApplyPromotionOnProducts() throws IOException, IllegalDiscountException {
		SoldProductMockRepository soldRepo = new SoldProductMockRepository();
		List<Integer> productIds = new ArrayList<>();
		shop.applyPromotionOnProducts(soldRepo, 0, productIds);
		
		SoldProductMockFactory soldProd1 = new SoldProductMockFactory("duck", 50, "EUR", "motherducker", "food");
		SoldProductMockFactory soldProd2 = new SoldProductMockFactory("canari", 50, "EUR", "cute", "food");
		
		SoldProduct p1 = soldProd1.create();
		SoldProduct p2 = soldProd2.create();

		soldRepo.add(p1.getID(), p1);
		soldRepo.add(p2.getID(), p2);
		productIds.add(p1.getID());

		shop.applyPromotionOnProducts(soldRepo, 5, productIds);
		assertThat(soldRepo.read(p1.getID()).calculatePrice()).as("assert that the first promotion was added sucessfully").isEqualTo(45);
		
		productIds.add(p2.getID());
		shop.applyPromotionOnProducts(soldRepo, 10, productIds);
		assertThat(soldRepo.read(p1.getID()).calculatePrice()).as("assert that the first promotion was added sucessfully").isEqualTo(35);
		assertThat(soldRepo.read(p2.getID()).calculatePrice()).as("assert that the first promotion was added sucessfully").isEqualTo(40);
		
		shop.applyPromotionOnProducts(soldRepo, -100, productIds);
		assertThat(soldRepo.read(p1.getID()).calculatePrice()).as("assert that the first promotion was added sucessfully").isEqualTo(135);
		assertThat(soldRepo.read(p2.getID()).calculatePrice()).as("assert that the first promotion was added sucessfully").isEqualTo(140);
		
		 
	}
	
	@Test
	public void testGetCurrentSold() throws IOException, IllegalDiscountException {
		History history = new History();
		TransactionMockRepository transHist = new TransactionMockRepository();
		

		assertThat(shop.getCurrentSold(history, transHist)).as("assert that the shop has gained 0 euros").isEqualTo(0);
		
		ClientMockFactory cl = new ClientMockFactory("bob", "dilan", "everywhere", "777777777");
		Client c = cl.create();
		
		SoldProductMockFactory prod = new SoldProductMockFactory("duck", 50, "EUR", "motherducker", "food");
		OrderSoldProductMockFactory ord = new OrderSoldProductMockFactory("jasus");
		
		Order trueOrd = c.createOrder(ord);
		trueOrd.createProduct(prod);
		
		
		TransactionMockFactory trans = new TransactionMockFactory(trueOrd, shop.getShopOwner(), c);
		history.createTransaction(trans, transHist);
		
		assertThat(shop.getCurrentSold(history, transHist)).as("assert that the shop has gained 50 Euros").isEqualTo(50);
	}
	
	@Test
	public void testGetHistory() throws IOException {
		History history = new History();
		TransactionMockRepository transHist = new TransactionMockRepository();
		

		assertThat(shop.getHistory(history, transHist)).as("assert that the shop has an empty history").isEmpty();
		
		ClientMockFactory cl = new ClientMockFactory("bob", "dilan", "everywhere", "777777777");
		Client c = cl.create();
		
		SoldProductMockFactory prod = new SoldProductMockFactory("duck", 50, "EUR", "motherducker", "food");
		OrderSoldProductMockFactory ord = new OrderSoldProductMockFactory("jasus");
		
		Order trueOrd = c.createOrder(ord);
		trueOrd.createProduct(prod);
		
		
		TransactionMockFactory trans = new TransactionMockFactory(trueOrd, shop.getShopOwner(), c);
		Transaction t = history.createTransaction(trans, transHist);
		
		assertThat(shop.getHistory(history, transHist)).as("assert that the shop now has the correct transaction").contains(t);
	}

	@Test
	public void testPojoStandard() {
		assertPojoMethodsFor(Shop.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}

}
