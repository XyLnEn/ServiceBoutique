package com.alma.boutique.domain;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.history.History;
import com.alma.boutique.domain.history.Transaction;
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
		this.shopHistory.getAccount().setOwner(this.shopOwner);
		this.shopHistory.setChangedbalance(true);
	}


	public History getShopHistory() {
		return shopHistory;
	}


	public void setShopHistory(History shopHistory) {
		this.shopHistory = shopHistory;
	}


	public ThirdParty getShopOwner() {
		return shopOwner;
	}


	public void setShopOwner(ThirdParty shopOwner) {
		this.shopOwner = shopOwner;
	}


	public IFactory<Supplier> getFactorySupplier() {
		return factorySupplier;
	}


	public void setFactorySupplier(IFactory<Supplier> factorySupplier) {
		this.factorySupplier = factorySupplier;
	}


	public IFactory<Client> getFactoryClient() {
		return factoryClient;
	}


	public void setFactoryClient(IFactory<Client> factoryClient) {
		this.factoryClient = factoryClient;
	}


	public IFactory<OrderSuppliedProduct> getFactoryOrder() {
		return factoryOrder;
	}


	public void setFactoryOrder(IFactory<OrderSuppliedProduct> factoryOrder) {
		this.factoryOrder = factoryOrder;
	}


	public IFactory<SuppliedProduct> getFactorySuppliedProduct() {
		return factorySuppliedProduct;
	}


	public void setFactorySuppliedProduct(IFactory<SuppliedProduct> factorySuppliedProduct) {
		this.factorySuppliedProduct = factorySuppliedProduct;
	}


	public IFactory<SoldProduct> getFactorySoldProduct() {
		return factorySoldProduct;
	}


	public void setFactorySoldProduct(IFactory<SoldProduct> factorySoldProduct) {
		this.factorySoldProduct = factorySoldProduct;
	}


	public IFactory<Transaction> getFactoryTransaction() {
		return factoryTransaction;
	}


	public void setFactoryTransaction(IFactory<Transaction> factoryTransaction) {
		this.factoryTransaction = factoryTransaction;
	}


	public IRepository<Supplier> getRepositorySupplier() {
		return repositorySupplier;
	}


	public void setRepositorySupplier(IRepository<Supplier> repositorySupplier) {
		this.repositorySupplier = repositorySupplier;
	}


	public IRepository<Client> getRepositoryClient() {
		return repositoryClient;
	}


	public void setRepositoryClient(IRepository<Client> repositoryClient) {
		this.repositoryClient = repositoryClient;
	}


	public IRepository<ShopOwner> getRepositoryShopOwner() {
		return repositoryShopOwner;
	}


	public void setRepositoryShopOwner(IRepository<ShopOwner> repositoryShopOwner) {
		this.repositoryShopOwner = repositoryShopOwner;
	}


	public IRepository<OrderSuppliedProduct> getRepositoryOrder() {
		return repositoryOrder;
	}


	public void setRepositoryOrder(IRepository<OrderSuppliedProduct> repositoryOrder) {
		this.repositoryOrder = repositoryOrder;
	}


	public IRepository<SoldProduct> getRepositorySoldProduct() {
		return repositorySoldProduct;
	}


	public void setRepositorySoldProduct(IRepository<SoldProduct> repositorySoldProduct) {
		this.repositorySoldProduct = repositorySoldProduct;
	}


	public IRepository<Transaction> getRepositoryTransaction() {
		return repositoryTransaction;
	}


	public void setRepositoryTransaction(IRepository<Transaction> repositoryTransaction) {
		this.repositoryTransaction = repositoryTransaction;
	}
	
	
	
	

	

	
}
