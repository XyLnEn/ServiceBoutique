package com.alma.boutique.domain.history;

import java.io.IOException;
import java.util.List;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.exceptions.TransactionNotFoundException;
import com.alma.boutique.domain.shared.Entity;

public class History extends Entity {

	private boolean changedbalance;
	private Account account;
	
	public History() {
		super();
		this.changedbalance = false;
		this.account = new Account();
	}
	
	public History(Account account) {
		super();
		this.changedbalance = false;
		this.account = account;
	}
	
	public Transaction createTransaction(IFactory<Transaction> factory, IRepository<Transaction> transactionHistory) {
		Transaction trans = null;
		try {
			trans = factory.create();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		transactionHistory.add(trans.getID(), trans);
		this.changedbalance = true;
		return trans;
	}
	
	public Transaction getTransaction(Transaction trans, IRepository<Transaction> transactionHistory) throws TransactionNotFoundException {
		for (Transaction transaction : transactionHistory.browse()) {
			if (transaction.sameIdentityAs(trans)) {
				return transaction;
			}
		}
		throw new TransactionNotFoundException("Transaction not found");
	}
	
	public void deleteTransaction(Transaction trans, IRepository<Transaction> transactionHistory) {
		transactionHistory.delete(trans.getID());
	}
	
	
	public float getBalance(IRepository<Transaction> transactionHistory) {
		if(this.changedbalance) {
			int balance = 0;
			for (Transaction transaction : transactionHistory.browse()) {
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
	
	public List<Transaction> getHistory(IRepository<Transaction> repositoryTrans) {
		return repositoryTrans.browse();
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

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}
