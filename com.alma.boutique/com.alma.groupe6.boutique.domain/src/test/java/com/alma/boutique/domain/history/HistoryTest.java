package com.alma.boutique.domain.history;

import com.alma.boutique.domain.exceptions.TransactionNotFoundException;
import com.alma.boutique.domain.mocks.factories.ClientMockFactory;
import com.alma.boutique.domain.mocks.factories.OrderSoldProductMockFactory;
import com.alma.boutique.domain.mocks.factories.OrderSuppliedProductMockFactory;
import com.alma.boutique.domain.mocks.factories.ShopMockFactory;
import com.alma.boutique.domain.mocks.factories.SoldProductMockFactory;
import com.alma.boutique.domain.mocks.factories.SuppliedProductMockFactory;
import com.alma.boutique.domain.mocks.factories.SupplierMockFactory;
import com.alma.boutique.domain.mocks.factories.TransactionMockFactory;
import com.alma.boutique.domain.mocks.repositories.TransactionMockRepository;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ShopOwner;
import com.alma.boutique.domain.thirdperson.Supplier;
import org.junit.Before;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class HistoryTest {

	
	private ClientMockFactory factClient;
	private SupplierMockFactory factSupp;
	private ShopMockFactory factShop;
	private Client cli1;
	private Client cli2;
	private Supplier supp1;
	private ShopOwner shop;

	@Before
	public void setUp() throws Exception {
		factClient = new ClientMockFactory("a", "client", "somewhere", "555-5555");
		factSupp = new SupplierMockFactory("fo", "mar", "123-4567");
		factShop = new ShopMockFactory("fo", "mar", "123-4567");
		cli1 = factClient.create();
		factClient = new ClientMockFactory("another", "client", "nowhere", "555-5557");
		cli2 = factClient.create();
		supp1 = factSupp.create();
		shop = factShop.create();
	}
	
	@Test
	public void testCreateTransaction() throws IOException {
		Account account = new Account(this.shop);
		History hist = new History(account);
		TransactionMockRepository repoTrans = new TransactionMockRepository();
		assertThat(hist.getHistory(repoTrans)).as("assert that the history is created empty").isEmpty();
		
		OrderSuppliedProductMockFactory supProdFac = new OrderSuppliedProductMockFactory("Bob");
		Order ord = shop.createOrder(supProdFac);
		
		TransactionMockFactory transFacto = new TransactionMockFactory(ord, supp1, shop);
		Transaction transSupBou = transFacto.create();
		
		repoTrans.add(transSupBou.getID(), transSupBou);
		assertThat(hist.getHistory(repoTrans)).as("assert that the Transaction was added successfully").contains(transSupBou);
		
	}
	
	@Test
	public void testGetTransaction() throws IOException, TransactionNotFoundException {
		Account account = new Account(this.shop);
		History hist = new History(account);
		
		TransactionMockRepository repoTrans = new TransactionMockRepository();
		
		assertThat(hist.getHistory(repoTrans)).as("assert that the history is created empty").isEmpty();
		
		OrderSuppliedProductMockFactory supProdFac = new OrderSuppliedProductMockFactory("Bob");
		Order ord = shop.createOrder(supProdFac);
		
		SuppliedProductMockFactory prod = new SuppliedProductMockFactory("duck", 1000000, "EUR", "A realy charismatic duck", "weapon");
		ord.createProduct(prod);
		
		TransactionMockFactory transFacto = new TransactionMockFactory(ord, supp1, shop);
		Transaction trans = hist.createTransaction(transFacto, repoTrans);
		assertThat(hist.getTransaction(trans.getID(), repoTrans)).as("assert that the transaction was added successfully").isEqualTo(trans);
		
		
		
		OrderSoldProductMockFactory soldProdFac = new OrderSoldProductMockFactory("Remi");
		ord = cli2.createOrder(soldProdFac);
		
		SoldProductMockFactory prodSold = new SoldProductMockFactory("duck", 1000000, "EUR", "A realy charismatic duck", "weapon");
		ord.createProduct(prodSold);
		
		transFacto = new TransactionMockFactory(ord, shop, cli2);
		Transaction nonExistentTransaction = transFacto.create();
		assertThatExceptionOfType(TransactionNotFoundException.class).isThrownBy(() -> hist.getTransaction(nonExistentTransaction.getID(), repoTrans))
		.as("check if the history can react when he is asked to get a Transaction he doesn't have");
	}
	
	

	@Test
	public void testDeleteTransaction() throws IOException {
		Account account = new Account(this.shop);
		History hist = new History(account);
		TransactionMockRepository repoTrans = new TransactionMockRepository();
		assertThat(hist.getHistory(repoTrans)).as("assert that the history is created empty").isEmpty();
		
		OrderSuppliedProductMockFactory supProdFac = new OrderSuppliedProductMockFactory("Bob");
		Order ord = shop.createOrder(supProdFac);
		
		TransactionMockFactory transFacto = new TransactionMockFactory(ord, supp1, shop);
		Transaction transSupBou = transFacto.create();
		
		repoTrans.add(transSupBou.getID(), transSupBou);
		assertThat(hist.getHistory(repoTrans)).as("assert that the Transaction was added successfully").contains(transSupBou);
		hist.deleteTransaction(transSupBou, repoTrans);
		assertThat(hist.getHistory(repoTrans)).as("assert that the transaction was removed successfully").doesNotContain(transSupBou);
	}

	@Test
	public void testGetBalance() throws IOException {
		Account account = new Account(this.shop);
		History hist = new History(account);
		
		TransactionMockRepository repoTrans = new TransactionMockRepository();
		assertThat(hist.getHistory(repoTrans)).as("assert that the history is created empty").isEmpty();
		
		OrderSuppliedProductMockFactory supProdFac = new OrderSuppliedProductMockFactory("Bob");
		Order ord = shop.createOrder(supProdFac);
		
		SuppliedProductMockFactory prod = new SuppliedProductMockFactory("duck", 5, "EUR", "A realy charismatic duck", "weapon");
		ord.createProduct(prod);
		
		TransactionMockFactory transFacto = new TransactionMockFactory(ord, supp1, shop);
		
		Transaction transSupBou = hist.createTransaction(transFacto, repoTrans);
		assertThat(hist.getHistory(repoTrans)).as("assert that the Transaction was added successfully").contains(transSupBou);
		
		assertThat(hist.getBalance(repoTrans)).as("assert that the current balance is correct").isEqualTo(-5);
		
		OrderSoldProductMockFactory soldProdFac = new OrderSoldProductMockFactory("Opal");
		ord = cli1.createOrder(soldProdFac);
		
		SoldProductMockFactory soldProd = new SoldProductMockFactory("duck", 5, "EUR", "A realy charismatic duck", "weapon");
		ord.createProduct(soldProd);
		
		transFacto = new TransactionMockFactory(ord, shop, cli1);
		hist.createTransaction(transFacto, repoTrans);
		
		assertThat(hist.getBalance(repoTrans)).as("assert that the current balance is correct").isEqualTo(0);
		assertThat(hist.getBalance(repoTrans)).as("assert that the current balance is not changed").isEqualTo(0);
		
	}

	@Test
	public void testPojoStandard() {
		assertPojoMethodsFor(History.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}

}
