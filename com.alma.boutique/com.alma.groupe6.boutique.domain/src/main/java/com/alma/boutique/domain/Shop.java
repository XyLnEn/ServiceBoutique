package com.alma.boutique.domain;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.history.Account;
import com.alma.boutique.domain.history.History;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ShopOwner;
import com.alma.boutique.domain.thirdperson.Supplier;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class Shop extends Entity{

	private History shopHistory;
	private ThirdParty shopOwner;
	private IFactory<Supplier> factorySupplier;
	private IFactory<Client> factoryClient;
	private IFactory<Order> factoryOrder;
	private IFactory<Product> factoryProduct;
	private IRepository repository;
	
	public Shop(IFactory<Supplier> factorySupplier, IFactory<Client> factoryClient,
	    IFactory<Order> factoryOrder, IFactory<Product> factoryProduct, IRepository repository) {
		super();
		this.factorySupplier = factorySupplier;
		this.factoryClient = factoryClient;
		this.factoryOrder = factoryOrder;
		this.factoryProduct = factoryProduct;
		this.repository = repository;
		//this.shopOwner = new ShopOwner(shopName, shopId);
	}
	

	
}
