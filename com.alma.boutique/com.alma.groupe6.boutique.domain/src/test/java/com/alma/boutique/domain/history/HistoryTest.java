package com.alma.boutique.domain.history;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.TransactionNotFoundException;
import com.alma.boutique.domain.mocks.factories.OrderMockFactory;
import com.alma.boutique.domain.mocks.factories.ProductMockFactory;
import com.alma.boutique.domain.mocks.factories.ThirdPartyMockFactory;
import com.alma.boutique.domain.mocks.factories.TransactionMockFactory;
import com.alma.boutique.domain.mocks.repositories.OrderMockRepository;
import com.alma.boutique.domain.mocks.repositories.ThirdPartyMockRepository;
import com.alma.boutique.domain.mocks.repositories.TransactionMockRepository;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

import org.junit.Before;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class HistoryTest {

	private TransactionMockRepository repoTrans;
	private ThirdPartyMockRepository repoThirdParty;
	private OrderMockRepository repoOrder;

	private OrderMockFactory supProdFac;
	
	private ThirdParty cli1;
	private ThirdParty cli2;
	private ThirdParty supp1;
	private ThirdParty shop;

	@Before
	public void setUp() throws Exception {
		repoThirdParty = new ThirdPartyMockRepository();
		ThirdPartyMockFactory factPerson = new ThirdPartyMockFactory("client", "somewhere", "555-5555", false);
		cli1 = factPerson.create();
		repoThirdParty.add(cli1.getId(), cli1);
		
		factPerson = new ThirdPartyMockFactory("Supplier", "there", "123-4567", true);
		supp1 = factPerson.create();
		repoThirdParty.add(supp1.getId(), supp1);
		
		factPerson = new ThirdPartyMockFactory("shop", "here", "123-4567", false);
		shop = factPerson.create();
		repoThirdParty.add(shop.getId(), shop);
		
		factPerson = new ThirdPartyMockFactory("client2", "rue de la soif", "555-5557", false);
		cli2 = factPerson.create();
		repoThirdParty.add(cli2.getId(), cli2);
	}
	
	@Test
	public void testCreateTransaction() throws IOException {
		Account account = new Account(this.shop);
		History hist = new History(account);
		repoTrans = new TransactionMockRepository();
		assertThat(hist.getHistory(repoTrans)).as("assert that the history is created empty").isEmpty();
		
		supProdFac = new OrderMockFactory("Bob");
		Order ord = shop.createOrder(supProdFac);
		
		TransactionMockFactory transFacto = new TransactionMockFactory(ord.getId(), shop.getId(), supp1.getId());
		Transaction transSupBou = transFacto.create();
		
		repoTrans.add(transSupBou.getId(), transSupBou);
		assertThat(hist.getHistory(repoTrans)).as("assert that the Transaction was added successfully").contains(transSupBou);
		
	}
	
	@Test
	public void testGetTransaction() throws IOException, TransactionNotFoundException {
		Account account = new Account(this.shop);
		History hist = new History(account);
		
		repoTrans = new TransactionMockRepository();
		
		assertThat(hist.getHistory(repoTrans)).as("assert that the history is created empty").isEmpty();
		
		supProdFac = new OrderMockFactory("Bob");
		Order ord = shop.createOrder(supProdFac);
		
		ProductMockFactory prod = new ProductMockFactory("duck", 1000000, "EUR", "A realy charismatic duck", "weapon");
		ord.createProduct(prod);
		
		TransactionMockFactory transFacto = new TransactionMockFactory(ord.getId(), shop.getId(), supp1.getId());
		Transaction trans = hist.createTransaction(transFacto, repoTrans);
		assertThat(hist.getTransaction(trans.getId(), repoTrans)).as("assert that the transaction was added successfully").isEqualTo(trans);
		
		
		supProdFac = new OrderMockFactory("Remi");
		ord = cli2.createOrder(supProdFac);
		
		ProductMockFactory prodSold = new ProductMockFactory("duck", 1000000, "EUR", "A realy charismatic duck", "weapon");
		ord.createProduct(prodSold);
		
		transFacto = new TransactionMockFactory(ord.getId(), shop.getId(), cli2.getId());
		Transaction nonExistentTransaction = transFacto.create();
		assertThatExceptionOfType(TransactionNotFoundException.class).isThrownBy(() -> hist.getTransaction(nonExistentTransaction.getId(), repoTrans))
		.as("check if the history can react when he is asked to get a Transaction he doesn't have");
	}
	
	

	@Test
	public void testDeleteTransaction() throws IOException {
		Account account = new Account(this.shop);
		History hist = new History(account);
		repoTrans = new TransactionMockRepository();
		assertThat(hist.getHistory(repoTrans)).as("assert that the history is created empty").isEmpty();
		
		supProdFac = new OrderMockFactory("Bob");
		Order ord = shop.createOrder(supProdFac);
		
		TransactionMockFactory transFacto = new TransactionMockFactory(ord.getId(), shop.getId(), supp1.getId());
		Transaction transSupBou = transFacto.create();
		
		repoTrans.add(transSupBou.getId(), transSupBou);
		assertThat(hist.getHistory(repoTrans)).as("assert that the Transaction was added successfully").contains(transSupBou);
		hist.deleteTransaction(transSupBou, repoTrans);
		assertThat(hist.getHistory(repoTrans)).as("assert that the transaction was removed successfully").doesNotContain(transSupBou);
	}

	@Test
	public void testGetBalance() throws IOException, IllegalDiscountException {
		Account account = new Account(this.shop);
		History hist = new History(account);
		
		repoTrans = new TransactionMockRepository();
		assertThat(hist.getHistory(repoTrans)).as("assert that the history is created empty").isEmpty();
		
		repoOrder = new OrderMockRepository();
		supProdFac = new OrderMockFactory("Bob");
		Order ord = shop.createOrder(supProdFac);
		
		ProductMockFactory prod = new ProductMockFactory("duck", 5, "EUR", "A realy charismatic duck", "weapon");
		ord.createProduct(prod);

		repoOrder.add(ord.getId(), ord);
		
		TransactionMockFactory transFacto = new TransactionMockFactory(ord.getId(),shop.getId(), supp1.getId());
		
		Transaction transSupBou = hist.createTransaction(transFacto, repoTrans);
		assertThat(hist.getHistory(repoTrans)).as("assert that the Transaction was added successfully").contains(transSupBou);
		
		assertThat(hist.getBalance(repoTrans,repoOrder,repoThirdParty)).as("assert that the current balance is correct").isEqualTo(-5);
		
		supProdFac = new OrderMockFactory("Opal");
		ord = cli1.createOrder(supProdFac);
		
		ProductMockFactory soldProd = new ProductMockFactory("duck", 5, "EUR", "A realy charismatic duck", "weapon");
		ord.createProduct(soldProd);

		repoOrder.add(ord.getId(), ord);
		
		transFacto = new TransactionMockFactory(ord.getId(), shop.getId(), cli1.getId());
		hist.createTransaction(transFacto, repoTrans);
		
		assertThat(hist.getBalance(repoTrans,repoOrder,repoThirdParty)).as("assert that the current balance is correct").isEqualTo(0);
		assertThat(hist.getBalance(repoTrans,repoOrder,repoThirdParty)).as("assert that the current balance is not changed").isEqualTo(0);
		
	}

	@Test
	public void testPojoStandard() {
		assertPojoMethodsFor(History.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}

}
