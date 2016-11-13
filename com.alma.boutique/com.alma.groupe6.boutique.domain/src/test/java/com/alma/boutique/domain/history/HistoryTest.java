package com.alma.boutique.domain.history;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.exceptions.TransactionNotFoundException;
import com.alma.boutique.domain.factories.FactoryClient;
import com.alma.boutique.domain.factories.FactoryOrder;
import com.alma.boutique.domain.factories.FactoryShop;
import com.alma.boutique.domain.factories.FactorySoldProduct;
import com.alma.boutique.domain.factories.FactorySuppliedProduct;
import com.alma.boutique.domain.factories.FactorySupplier;
import com.alma.boutique.domain.factories.FactoryTransaction;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.FactoryProduct;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.Shop;
import com.alma.boutique.domain.thirdperson.Supplier;

public class HistoryTest {

	
	private FactoryClient factClient;
	private FactorySupplier factSupp;
	private FactoryShop factShop;
	private FactoryTransaction factTrans;
	private FactoryProduct factProd;
	private Client cli1;
	private Client cli2;
	private Supplier supp1;
	private Shop shop;

	@Before
	public void setUp() throws Exception {
		factClient = new FactoryClient();
		factSupp = new FactorySupplier();
		factShop = new FactoryShop();
		factTrans = new FactoryTransaction();
		cli1 = factClient.make("a", "client", "somewhere", "555-5555");
		cli2 = factClient.make("another", "client", "nowhere", "555-5557");
		supp1 = factSupp.make("fo", "mar", "123-4567");
		shop = factShop.make("Tomy & lenny", "everywhere", "777-7777");
	}

	@Test
	public void testAcheter() {
		Account account = new Account(this.shop);
		History hist = new History(factTrans, account);
		assertThat(hist.getHistory()).as("assert that the history is created empty").isEmpty();
		
		
		this.factProd = new FactorySuppliedProduct();
		List<Product> list = new ArrayList<>();
		Product p1 = factProd.make("duck", 5, "A realy charismatic duck", "weapon");
		list.add(p1);
		Transaction trans;
		try {
			trans = hist.Acheter(list, supp1, shop, "USP");
			assertThat(hist.getHistory()).as("assert that the transaction was added successfully").contains(trans);
			assertThat(hist.getHistory().get(0).getFrom()).as("check that the supplier is correct").isEqualTo(supp1);
			assertThat(hist.getHistory().get(0).getTo()).as("chech that the receiver is correct").isEqualTo(shop);
			assertThat(hist.getHistory().get(0).getOrder().getDeliverer()).as("check that the order is correct").isEqualTo("USP");
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTransaction() {
		Account account = new Account(this.shop);
		History hist = new History(factTrans, account);
		assertThat(hist.getHistory()).as("assert that the history is created empty").isEmpty();
		
		
		this.factProd = new FactorySuppliedProduct();
		List<Product> list = new ArrayList<>();
		Product p1 = factProd.make("duck", 1000000, "A realy charismatic duck", "weapon");
		list.add(p1);
		Transaction trans;
		try {
			trans = hist.Acheter(list, supp1, shop, "USP");
			assertThat(hist.getTransaction(trans)).as("assert that the transaction was added successfully").isEqualTo(trans);
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FactoryOrder factoOrd = new FactoryOrder();
		Order nonExistingOrder = factoOrd.make("Nobody", factProd);
		Transaction nonExistentTransaction = factTrans.make(nonExistingOrder, shop, cli2);
		assertThatExceptionOfType(TransactionNotFoundException.class).isThrownBy(() -> hist.getTransaction(nonExistentTransaction))
		.as("check if the history can react when he is asked to get a Transaction he doesn't have");
	}

	@Test
	public void testDeleteTransaction() {
		Account account = new Account(this.shop);
		History hist = new History(factTrans, account);
		assertThat(hist.getHistory()).as("assert that the history is created empty").isEmpty();
		
		
		this.factProd = new FactorySuppliedProduct();
		List<Product> list = new ArrayList<>();
		Product p1 = factProd.make("duck", 5, "A realy charismatic duck", "weapon");
		list.add(p1);
		Transaction trans;
		try {
			trans = hist.Acheter(list, supp1, shop, "USP");
			assertThat(hist.getHistory()).as("assert that the transaction was added successfully").contains(trans);
			hist.deleteTransaction(trans);
			assertThat(hist.getHistory()).as("assert that the transaction was removed successfully").doesNotContain(trans);
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetBalance() {
		Account account = new Account(this.shop);
		History hist = new History(factTrans, account);
		this.factProd = new FactorySuppliedProduct();
		List<Product> list = new ArrayList<>();
		Product p1 = factProd.make("duck", 5, "A realy charismatic duck", "weapon");
		list.add(p1);
		try {
			hist.Acheter(list, supp1, shop, "USP");
			assertThat(hist.getBalance()).as("assert that the current balance is correct").isEqualTo(-5);
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.factProd = new FactorySoldProduct();
		List<Product> list2 = new ArrayList<>();
		Product p2 = factProd.make("duck", 5, "A realy charismatic duck", "weapon");
		list2.add(p2);
		try {
			hist.Acheter(list2, shop, cli1, "USP");
			assertThat(hist.getBalance()).as("assert that the current balance is correct").isEqualTo(0);
			assertThat(hist.getBalance()).as("assert that the current balance is not changed").isEqualTo(0);
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
