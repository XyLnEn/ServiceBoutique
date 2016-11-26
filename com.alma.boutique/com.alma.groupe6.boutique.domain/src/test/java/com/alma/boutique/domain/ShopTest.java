package com.alma.boutique.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.exceptions.TransactionNotFoundException;
import com.alma.boutique.domain.history.History;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.mocks.ExchangeRateServiceMock;
import com.alma.boutique.domain.mocks.factories.OrderMockFactory;
import com.alma.boutique.domain.mocks.factories.ProductMockFactory;
import com.alma.boutique.domain.mocks.factories.ThirdPartyMockFactory;
import com.alma.boutique.domain.mocks.factories.TransactionMockFactory;
import com.alma.boutique.domain.mocks.repositories.ThirdPartyMockRepository;
import com.alma.boutique.domain.mocks.repositories.OrderMockRepository;
import com.alma.boutique.domain.mocks.repositories.ProductMockRepository;
import com.alma.boutique.domain.mocks.repositories.TransactionMockRepository;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderStatus;
import com.alma.boutique.domain.thirdperson.ThirdParty;

import pl.pojo.tester.api.assertion.Method;

public class ShopTest {

	private Shop shop;
	private ThirdParty client;
	private ThirdParty supplier;
	private ThirdPartyMockRepository personRepo;
	
	@Before
	public void setUp() throws Exception {
		personRepo = new ThirdPartyMockRepository();
		ThirdParty shopOwner = new ThirdParty("bob",new Identity("house", "11111111"), true);
		personRepo.add(shopOwner.getID(), shopOwner);
		this.shop = new Shop(shopOwner);
		this.client = new ThirdParty("client", new Identity("some adress", "a number"), false);
		this.supplier = new ThirdParty("supplier", new Identity("nearby", "555"), true);
		this.personRepo = new ThirdPartyMockRepository();
		this.personRepo.add(this.client.getID(), this.client);
		this.personRepo.add(this.supplier.getID(), this.supplier);
		
	}
	
	@Test
	public void testBrowseStock() throws IOException {
		ProductMockRepository soldRepo = new ProductMockRepository();
		assertThat(shop.browseStock(soldRepo)).as("assert that the shop can browse an empty stock").isEmpty();
		ProductMockFactory soldFacto = new ProductMockFactory("lemon", 5, "EUR", "bitter", "fruit");
		Product prod = soldFacto.create();
		soldRepo.add(prod.getID(), prod);
		assertThat(shop.browseStock(soldRepo)).as("assert that the shop return the correct product").contains(prod);
	}
	
	@Test
	public void testBuyProduct() throws IOException, ProductNotFoundException, IllegalDiscountException, OrderNotFoundException {
		ProductMockRepository soldRepo = new ProductMockRepository();
		
		ProductMockFactory soldFacto = new ProductMockFactory("lemon", 5, "EUR", "bitter", "fruit");
		Product prod1 = soldFacto.create();

		soldFacto = new ProductMockFactory("apple", 7, "EUR", "sweet", "fruit");
		Product prod2 = soldFacto.create();
		
		OrderMockFactory soldOrder = new OrderMockFactory("bob");
		List<Integer> listeAchat = new ArrayList<>();
		Order emptyOrder = shop.buyProduct(soldRepo, this.personRepo, soldOrder, listeAchat, this.client.getID(), "EUR", new ExchangeRateServiceMock());
		
		assertThat(emptyOrder.getProducts()).as("test that the order created from the purchase is correct for an empty purchase").isEmpty();

		soldRepo.add(prod1.getID(), prod1);
		soldRepo.add(prod2.getID(), prod2);
		listeAchat.add(prod1.getID());
		listeAchat.add(prod2.getID());
		Order realOrder = shop.buyProduct(soldRepo, this.personRepo, soldOrder, listeAchat, this.client.getID(), "EUR", new ExchangeRateServiceMock());
		assertThat(realOrder.getProduct(prod1.getID())).as("test that the order is bought successfully").isEqualTo(prod1);
	}
	
	@Test
	public void testSaveTransaction() throws IOException, TransactionNotFoundException, IllegalDiscountException, OrderNotFoundException {
		ProductMockRepository soldRepo = new ProductMockRepository();
		
		ProductMockFactory soldFacto = new ProductMockFactory("lemon", 5, "EUR", "bitter", "fruit");
		Product prod1 = soldFacto.create();

		soldFacto = new ProductMockFactory("apple", 7, "EUR", "sweet", "fruit");
		Product prod2 = soldFacto.create();
		
		OrderMockFactory soldOrder = new OrderMockFactory("bob");
		List<Integer> listeAchat = new ArrayList<>();
		Order emptyOrder = shop.buyProduct(soldRepo, this.personRepo, soldOrder, listeAchat, this.client.getID(), "EUR", new ExchangeRateServiceMock());
		
		assertThat(emptyOrder.getProducts()).as("test that the order created from the purchase is correct for an empty purchase").isEmpty();
		
		soldRepo.add(prod1.getID(), prod1);
		soldRepo.add(prod2.getID(), prod2);
		listeAchat.add(prod1.getID());
		listeAchat.add(prod2.getID());
		Order ordToSave = shop.buyProduct(soldRepo, this.personRepo, soldOrder, listeAchat, this.client.getID(), "EUR", new ExchangeRateServiceMock());
		
		ThirdPartyMockFactory clientFacto = new ThirdPartyMockFactory("Remi", "somewhere", "888888", false);
		ThirdParty mockClient = clientFacto.create();
		
		TransactionMockRepository transRepo = new TransactionMockRepository();
		
		TransactionMockFactory transToSave = new TransactionMockFactory(ordToSave.getID(), shop.getShopHistory().getAccount().getOwner().getID(), mockClient.getID());
		Transaction generatedTrans = shop.saveTransaction(shop.getShopHistory(), transRepo, transToSave);
		assertThat(shop.getShopHistory().getTransaction(generatedTrans.getID(), transRepo)).as("check that the transaction was added successfully").isEqualTo(generatedTrans);
		
	}
	
	@Test
	public void testRestock() throws IOException, OrderNotFoundException {
		ProductMockRepository soldRepo = new ProductMockRepository();
		
		ProductMockFactory suppProd1 = new ProductMockFactory("duck", 1, "EUR", "motherducker", "food");
		ProductMockFactory suppProd2 = new ProductMockFactory("canari", 1, "EUR", "cute", "food");
		
		OrderMockFactory ordSupp = new OrderMockFactory("the poste");
		
		List<IFactory<Product>> testList = new ArrayList<>();
		
		Order ord = shop.restock(soldRepo, personRepo, testList, ordSupp, this.supplier.getID(), "EUR", new ExchangeRateServiceMock());
		assertThat(ord.getDeliverer()).as("test that we can restock 0 items").isEqualTo("the poste");
		
		ordSupp = new OrderMockFactory("the true poste");
		
		testList.add(suppProd1);
		testList.add(suppProd2);
		
		ord = shop.restock(soldRepo, personRepo, testList, ordSupp, this.supplier.getID(), "EUR", new ExchangeRateServiceMock());
		assertThat(ord.getProducts().size()).as("test that the restock is working for a non-empty list of products").isEqualTo(2);
		
	}
	
	@Test
	public void testAdvanceOrder() throws IOException, OrderNotFoundException {
		History history = new History();
		TransactionMockRepository transHist = new TransactionMockRepository();
		ProductMockFactory suppProd1 = new ProductMockFactory("duck", 1, "EUR", "motherducker", "food");
		ProductMockFactory suppProd2 = new ProductMockFactory("canari", 1, "EUR", "cute", "food");
		
		OrderMockFactory ordSupp = new OrderMockFactory("the poste");
		Order ord = shop.getShopHistory().getAccount().getOwner().createOrder(ordSupp);
		ord.createProduct(suppProd1);
		ord.createProduct(suppProd2);
		
		OrderMockRepository ordRepo = new OrderMockRepository();
		ordRepo.add(ord.getID(), ord);
		
		ThirdPartyMockFactory factoSupp = new ThirdPartyMockFactory("LIDL", "somewhere", "00000000", true);
		TransactionMockFactory transFact = new TransactionMockFactory(ord.getID(), shop.getShopHistory().getAccount().getOwner().getID(), factoSupp.create().getID());
		
		Transaction t = history.createTransaction(transFact, transHist);
		assertThat(ord.getOrderStatus()).as("assert that the order is created is the base state").isEqualTo(OrderStatus.ORDERED);
		
		
		shop.advanceOrder(ordRepo, ord.getID());
		assertThat(ordRepo.read(transHist.read(t.getID()).getOrderId()).getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history").isEqualTo(OrderStatus.TRAVELING);
		assertThat(shop.getShopHistory().getAccount().getOwner().getOrder(ord.getID()).getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history").isEqualTo(OrderStatus.TRAVELING);

		shop.advanceOrder(ordRepo, ord.getID());
		assertThat(ordRepo.read(transHist.read(t.getID()).getOrderId()).getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history the second time").isEqualTo(OrderStatus.ARRIVED);
		assertThat(shop.getShopHistory().getAccount().getOwner().getOrder(ord.getID()).getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history the second time").isEqualTo(OrderStatus.ARRIVED);

		shop.advanceOrder(ordRepo, ord.getID());
		assertThat(ordRepo.read(transHist.read(t.getID()).getOrderId()).getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history").isEqualTo(OrderStatus.DELIVERED);
		assertThat(shop.getShopHistory().getAccount().getOwner().getOrder(ord.getID()).getOrderStatus()).as("assert that the order was advanced sucessfully in the transaction history").isEqualTo(OrderStatus.DELIVERED);
		
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> shop.advanceOrder(ordRepo, -1))
		.as("check if it is possible to advance a non-existing order");
		
		
	}
	
	@Test
	public void testApplyPromotionOnProducts() throws IOException, IllegalDiscountException {
		ProductMockRepository soldRepo = new ProductMockRepository();
		List<Integer> productIds = new ArrayList<>();
		shop.applyPromotionOnProducts(soldRepo, 0, productIds);
		
		ProductMockFactory soldProd1 = new ProductMockFactory("duck", 50, "EUR", "motherducker", "food");
		ProductMockFactory soldProd2 = new ProductMockFactory("canari", 50, "EUR", "cute", "food");
		
		Product p1 = soldProd1.create();
		Product p2 = soldProd2.create();

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
	public void testGetCurrentSold() throws IOException, IllegalDiscountException, OrderNotFoundException {
		TransactionMockRepository transHist = new TransactionMockRepository();
		
		ThirdPartyMockRepository personRepo = new ThirdPartyMockRepository();
		ThirdPartyMockFactory cl = new ThirdPartyMockFactory("bob", "everywhere", "777777777", false);
		ThirdParty c = cl.create();
		personRepo.add(c.getID(), c);
		
		OrderMockRepository orderRepo = new OrderMockRepository();
		
		assertThat(shop.getCurrentSold(transHist, orderRepo, personRepo)).as("assert that the shop has gained 0 euros").isEqualTo(0);
		
		ProductMockFactory prod = new ProductMockFactory("duck", 50, "EUR", "motherducker", "food");
		OrderMockFactory ord = new OrderMockFactory("jasus");
		
		Order trueOrd = c.createOrder(ord);
		
		trueOrd.createProduct(prod);
		orderRepo.add(trueOrd.getID(), trueOrd);
		
		c.updateOrder(trueOrd.getID(), trueOrd);
		
		personRepo.edit(c.getID(), c);
		
		TransactionMockFactory trans = new TransactionMockFactory(trueOrd.getID(), shop.getShopHistory().getAccount().getOwner().getID(), c.getID());
		Transaction t = shop.getShopHistory().createTransaction(trans, transHist);
		transHist.add(t.getID(), t);
		
		assertThat(shop.getCurrentSold(transHist, orderRepo, personRepo)).as("assert that the shop has gained 50 Euros").isEqualTo(50);
	}
	
	@Test
	public void testGetHistory() throws IOException {
		History history = new History();
		TransactionMockRepository transHist = new TransactionMockRepository();
		

		assertThat(shop.getHistory(transHist)).as("assert that the shop has an empty history").isEmpty();
		
		ThirdPartyMockFactory cl = new ThirdPartyMockFactory("bob", "everywhere", "777777777", false);
		ThirdParty c = cl.create();
		
		ProductMockFactory prod = new ProductMockFactory("duck", 50, "EUR", "motherducker", "food");
		OrderMockFactory ord = new OrderMockFactory("jasus");
		
		Order trueOrd = c.createOrder(ord);
		trueOrd.createProduct(prod);
		
		
		TransactionMockFactory trans = new TransactionMockFactory(trueOrd.getID(), shop.getShopHistory().getAccount().getOwner().getID(), c.getID());
		Transaction t = history.createTransaction(trans, transHist);
		
		assertThat(shop.getHistory(transHist)).as("assert that the shop now has the correct transaction").contains(t);
	}

	@Test
	public void testPojoStandard() {
		assertPojoMethodsFor(Shop.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}

}
