package com.alma.boutique.domain;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.history.Account;
import com.alma.boutique.domain.history.History;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.product.SoldProduct;
import com.alma.boutique.domain.product.SuppliedProduct;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.OrderSuppliedProduct;
import com.alma.boutique.domain.thirdperson.ShopOwner;
import com.alma.boutique.domain.thirdperson.Supplier;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class Shop extends Entity{

	private History shopHistory;
	private ThirdParty shopOwner;
	private IFactory<Supplier> factorySupplier;
	private IFactory<Client> factoryClient;
	private IFactory<OrderSuppliedProduct> factoryOrder;
	private IFactory<SuppliedProduct> factorySuppliedProduct;
	private IFactory<SoldProduct> factorySoldProduct;
	private IFactory<Transaction> factoryTransaction;
	private IRepository<Supplier> repositorySupplier;
	private IRepository<Client> repositoryClient;
	private IRepository<ShopOwner> repositoryShopOwner;
	private IRepository<OrderSuppliedProduct> repositoryOrder;
	private IRepository<SoldProduct> repositorySoldProduct;
	private IRepository<Transaction> repositoryTransaction;
	
	
	public Shop(IFactory<Supplier> factorySupplier, IFactory<Client> factoryClient, IFactory<OrderSuppliedProduct> factoryOrder,
	    IFactory<SuppliedProduct> factorySuppliedProduct, IFactory<SoldProduct> factorySoldProduct,
	    IFactory<Transaction> factoryTransaction, IRepository<Supplier> repositorySupplier,
	    IRepository<Client> repositoryClient, IRepository<ShopOwner> repositoryShopOwner,
	    IRepository<OrderSuppliedProduct> repositoryOrder, IRepository<SoldProduct> repositorySoldProduct, 
	    IRepository<Transaction> repositoryTransaction) {
		super();
		this.factorySupplier = factorySupplier;
		this.factoryClient = factoryClient;
		this.factoryOrder = factoryOrder;
		this.factorySuppliedProduct = factorySuppliedProduct;
		this.factorySoldProduct = factorySoldProduct;
		this.factoryTransaction = factoryTransaction;
		this.repositorySupplier = repositorySupplier;
		this.repositoryClient = repositoryClient;
		this.repositoryShopOwner = repositoryShopOwner;
		this.repositoryOrder = repositoryOrder;
		this.repositorySoldProduct = repositorySoldProduct;
		this.repositoryTransaction = repositoryTransaction;
		this.shopOwner = this.repositoryShopOwner.browse().get(0);
		this.shopHistory = new History();
		this.shopHistory.getTransactionHistory().addAll(repositoryTransaction.browse());
		this.shopHistory.getAccount().setOwner(this.shopOwner);
		this.shopHistory.setChangedbalance(true);
	}
	
	
	
	

	

	
}
