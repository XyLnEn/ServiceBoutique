package com.alma.boutique.domain.history;

import java.io.IOException;
import java.util.List;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.TransactionNotFoundException;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

/**
 * Class that represent the information about a shop
 * @author Lenny Lucas
 *
 */
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
	
	/**
	 * method that create a transaction and save it
	 * @param factory the factory that will create the new order
	 * @param transactionHistory the repository where the transaction will be saved
	 * @return the new transaction
	 */
	public Transaction createTransaction(IFactory<Transaction> factory, IRepository<Transaction> transactionHistory) {
		Transaction trans = null;
		try {
			trans = factory.create();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		transactionHistory.add(trans.getId(), trans);
		this.changedbalance = true;
		return trans;
	}
	
	/**
	 * method that get a transaction from a repository
	 * @param transId the id of the tranc=saction to find
	 * @param transactionHistory the repository where this method will search
	 * @return the transaction
	 * @throws TransactionNotFoundException
	 */
	public Transaction getTransaction(int transId, IRepository<Transaction> transactionHistory) throws TransactionNotFoundException {
		for (Transaction transaction : transactionHistory.browse()) {
			if (transaction.getId() == transId) {
				return transaction;
			}
		}
		throw new TransactionNotFoundException("Transaction not found");
	}
	
	/**
	 * method that delete a transaction from a repository
	 * @param trans the transaction to delete
	 * @param transactionHistory the repository where this method will delete
	 */
	public void deleteTransaction(Transaction trans, IRepository<Transaction> transactionHistory) {
		transactionHistory.delete(trans.getId());
	}
	
	/**
	 * method that calculate the current turnover of the shop.
	 * @param transactionHistory the repository of transactions
	 * @param orderList the repository of orders
	 * @param personList the repository of persons
	 * @return the current turnover
	 * @throws IllegalDiscountException if a product price is less than o after the promotion is applied
	 */
	public float getBalance(IRepository<Transaction> transactionHistory, IRepository<Order> orderList, IRepository<ThirdParty> personList) throws IllegalDiscountException {
		if(this.changedbalance) {
			int balance = 0;
			for (Transaction transaction : transactionHistory.browse()) {
				if(personList.read(transaction.getPartyId()).isSupplier()) {//expense for the shop, count negatively
					balance -= transaction.getAmount(orderList);
				} else {
					balance += transaction.getAmount(orderList);
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
