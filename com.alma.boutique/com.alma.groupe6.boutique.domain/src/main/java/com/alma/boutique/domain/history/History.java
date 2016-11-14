package com.alma.boutique.domain.history;

import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.exceptions.TransactionNotFoundException;
import com.alma.boutique.domain.factories.FactoryTransaction;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class History extends Entity {

	private FactoryTransaction factory;
	private boolean changedbalance;
	private List<Transaction> transactionHistory;
	private Account account;
	
	public History() {
		super();
	}
	
	public History(FactoryTransaction factory, Account account) {
		super();
		this.factory = factory;
		this.changedbalance = false;
		this.transactionHistory = new ArrayList<>();
		this.account = account;
	}

	public Transaction buy(List<Product> list, ThirdParty from, ThirdParty to, String deliverer) throws OrderNotFoundException {
		Order ord1 = from.createOrder(deliverer);
		Order ord2 = to.createOrder(deliverer);
		for (Product product : list) {
			ord1.createProduct(product.getName(), product.getPrice().getValue(), product.getPrice().getCurrency(), product.getDescription(), product.getCategory().getName());
			ord2.createProduct(product.getName(), product.getPrice().getValue(), product.getPrice().getCurrency(), product.getDescription(), product.getCategory().getName());
		}
		from.updateOrder(ord1, ord1);
		to.updateOrder(ord2, ord2);
		return createTransaction(ord1, from, to);
	}
	
	public Transaction createTransaction(Order ord, ThirdParty from, ThirdParty to) {
		Transaction trans = factory.make(ord, from, to);
		this.transactionHistory.add(trans);
		this.changedbalance = true;
		return trans;
	}
	
	public Transaction getTransaction(Transaction trans) throws TransactionNotFoundException {
		for (Transaction transaction : transactionHistory) {
			if (transaction.sameIdentityAs(trans)) {
				return transaction;
			}
		}
		throw new TransactionNotFoundException("Transaction not found");
	}
	
	public void deleteTransaction(Transaction trans) {
		transactionHistory.remove(trans);
	}
	
	
	public float getBalance() {
		if(this.changedbalance) {
			int balance = 0;
			for (Transaction transaction : transactionHistory) {
				if(transaction.getTo().sameIdentityAs(this.getAccount().getOwner())) {//expense for the shop, count negatively
					balance -= transaction.getAmount();
				} else {
					balance += transaction.getAmount();
				}
			}
			this.changedbalance = false;
			this.getAccount().setCurrentBalance(balance);
		}
		return account.getCurrentBalance();
	}
	
	public List<Transaction> getHistory() {
		return transactionHistory;
	}
	
	public Account getAccount() {
		return account;
	}

	public boolean balanceHasChanged() {
		return changedbalance;
	}

	public FactoryTransaction getFactory() {
		return factory;
	}

	public void setFactory(FactoryTransaction factory) {
		this.factory = factory;
	}

	public boolean isChangedbalance() {
		return changedbalance;
	}

	public void setChangedbalance(boolean changedbalance) {
		this.changedbalance = changedbalance;
	}

	public List<Transaction> getTransactionHistory() {
		return transactionHistory;
	}

	public void setTransactionHistory(List<Transaction> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}
