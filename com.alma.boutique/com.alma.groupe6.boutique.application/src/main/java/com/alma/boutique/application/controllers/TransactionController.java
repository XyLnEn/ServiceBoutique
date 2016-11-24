package com.alma.boutique.application.controllers;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.api.services.ExchangeRateService;
import com.alma.boutique.application.data.Purchase;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.SoldProduct;
import com.alma.boutique.domain.product.SuppliedProduct;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderSoldProduct;
import com.alma.boutique.domain.thirdperson.OrderSuppliedProduct;
import com.alma.boutique.domain.thirdperson.Supplier;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import com.alma.boutique.infrastructure.factories.OrderSoldProductFactory;
import com.alma.boutique.infrastructure.factories.OrderSuppliedProductFactory;
import com.alma.boutique.infrastructure.factories.SuppliedProductFactory;
import com.alma.boutique.infrastructure.factories.TransactionFactory;
import com.alma.boutique.infrastructure.services.FixerExchanger;
import com.alma.boutique.infrastructure.services.ProviderCatalog;
import com.fasterxml.jackson.databind.JsonMappingException;

import spark.Request;

public class TransactionController extends ShopController {

	private IRepository<Transaction> transactions;
	private IRepository<SoldProduct> stock;
	private ProviderCatalog supply;
	private IRepository<Client> persons;
	private IRepository<Supplier> suppliers;
	private FixerExchanger fixer;
	
	
	
	public TransactionController(Shop shop, IRepository<Transaction> transactions, 
			IRepository<SoldProduct> stock, ProviderCatalog supply, IRepository<Client> persons, IRepository<Supplier> suppliers, FixerExchanger fixer) {
		super(shop);
		this.transactions = transactions;
		this.stock = stock;
		this.supply = supply;
		this.fixer = fixer;
		this.persons = persons;
		this.suppliers = suppliers;
	}
	
  protected Purchase getResults(Request req) throws JsonMappingException, IOException {
  	return (Purchase) mapper.readValue(req.body(), Purchase.class);
  }
	
	public void buy(Request req) throws IOException, IllegalDiscountException {
		Purchase purchase = this.getResults(req);
		String deliverer = purchase.getDeliverer();
  	String devise = purchase.getDevise();
  	IFactory<OrderSoldProduct> factOrd = new OrderSoldProductFactory(deliverer);
  	List<Integer> idList = new ArrayList<>(purchase.getIdList());
  	Order ord = shop.buyProduct(this.stock, factOrd , idList, devise, fixer);
  	System.out.println("ok");
  	ThirdParty client = persons.read(purchase.getPerson());
  	Transaction t = shop.saveTransaction(shop.getShopHistory(), this.transactions, new TransactionFactory(ord, shop.getShopOwner(), client));
  	System.out.println(t.getOrder().getDeliverer());
	}
	

	public void resupply(Request req) throws IOException, IllegalDiscountException {
		Purchase purchase = this.getResults(req);
		
		String deliverer = purchase.getDeliverer();
  	String devise = purchase.getDevise();
  	IFactory<OrderSuppliedProduct> factOrd = new OrderSuppliedProductFactory(deliverer);
  	List<Integer> idList = new ArrayList<>(purchase.getIdList());
  	List<IFactory<SuppliedProduct>> productList = new ArrayList<>();
  	for (Integer id : idList) {
			productList.add(new SuppliedProductFactory(id, null, null));
		}
  	Order ord = shop.restock(this.stock, productList, factOrd, devise, fixer);
  	
  	System.out.println("ok");
  	ThirdParty supplier = persons.read(purchase.getPerson());
  	Transaction t = shop.saveTransaction(shop.getShopHistory(), this.transactions, new TransactionFactory(ord, supplier, shop.getShopOwner()));
  	System.out.println(t.getOrder().getDeliverer());
	}
	
	@Override
	public void init() {
//			shop.buyProduct(stock, orderCreator, productIdList, deviseUsed, currentRate)
    get("/Transaction/all", (req, resp) -> transactions.browse(), this::toJson);
    get("/Transaction/:id", (req, resp) -> transactions.read(Integer.parseInt(req.params(":id"))), this::toJson);
    post("/Transaction/new/sale", (req, resp) -> {
    	this.buy(req);
    	return req.body();
    }, this::toJson);
    post("/Transaction/new/resupply", (req, resp) -> {
    	this.resupply(req);
    	return req.body();
    }, this::toJson);

	}

}
