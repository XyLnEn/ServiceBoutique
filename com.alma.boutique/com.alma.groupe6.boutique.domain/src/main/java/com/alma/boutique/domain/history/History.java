package com.alma.boutique.domain.history;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.exceptions.TransactionNotFoundException;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class History extends Entity {

	private boolean changedbalance;
	private List<Transaction> transactionHistory;
	private Account account;
	
	public History() {
		super();
		this.changedbalance = false;
		this.transactionHistory = new ArrayList<>();
		this.account = new Account();
	}
	
	public History(IFactory<Transaction> factory, Account account) {
		super();
		this.changedbalance = false;
		this.transactionHistory = new ArrayList<>();
		this.account = account;
	}

	public Transaction buy(IFactory<Transaction> factoryTrans, List<Product> list, ThirdParty from, ThirdParty to, String deliverer) throws OrderNotFoundException {
		Order ord1 = from.createOrder(deliverer);
		Order ord2 = to.createOrder(deliverer);
		for (Product product : list) {
			ord1.createProduct(product.getName(), product.getPrice().getValue(), product.getPrice().getCurrency(), product.getDescription(), product.getCategory().getName());
			ord2.createProduct(product.getName(), product.getPrice().getValue(), product.getPrice().getCurrency(), product.getDescription(), product.getCategory().getName());
		}
		from.updateOrder(ord1, ord1);
		to.updateOrder(ord2, ord2);
		return createTransaction(factoryTrans);
	}
	
	public Transaction createTransaction(IFactory<Transaction> factory) {
		Transaction trans = null;
		try {
			trans = factory.create();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public Account getAccount() {
		return account;
	}

	public boolean balanceHasChanged() {
		return changedbalance;
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
