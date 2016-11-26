package com.alma.boutique.application.controllers;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.application.data.Purchase;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import com.alma.boutique.infrastructure.factories.OrderFactory;
import com.alma.boutique.infrastructure.factories.ProductFactory;
import com.alma.boutique.infrastructure.factories.TransactionFactory;
import com.alma.boutique.infrastructure.services.FixerExchanger;
import com.alma.boutique.infrastructure.services.ProviderCatalog;
import com.fasterxml.jackson.databind.JsonMappingException;

import spark.Request;

public class TransactionController extends ShopController {

	private IRepository<Transaction> transactions;
	private IRepository<Order> orderHistory;
	private IRepository<Product> stock;
	private ProviderCatalog supply;
	private IRepository<ThirdParty> persons;
	private FixerExchanger fixer;
	
	
	
	public TransactionController(Shop shop, IRepository<Transaction> transactions, IRepository<Order> orderHistory,
			IRepository<Product> stock, ProviderCatalog supply, IRepository<ThirdParty> persons, FixerExchanger fixer) {
		super(shop);
		this.orderHistory = orderHistory;
		this.transactions = transactions;
		this.stock = stock;
		this.supply = supply;
		this.fixer = fixer;
		this.persons = persons;
	}
	
  protected Purchase getResults(Request req) throws JsonMappingException, IOException {
  	return (Purchase) mapper.readValue(req.body(), Purchase.class);
  }
	
	public void buy(Request req) throws IOException, IllegalDiscountException {
		Purchase purchase = this.getResults(req);
		String deliverer = purchase.getDeliverer();
  	String devise = purchase.getDevise();
  	IFactory<Order> factOrd = new OrderFactory(deliverer);
  	List<Integer> idList = new ArrayList<>(purchase.getIdList());
  	ThirdParty client = persons.read(purchase.getPersonId());
		Order ord = shop.buyProduct(this.stock, persons, factOrd , idList, purchase.getPersonId(), devise, fixer);
		orderHistory.add(ord.getID(), ord);
  	shop.saveTransaction(shop.getShopHistory(), this.transactions, new TransactionFactory(ord.getID(), shop.getShopOwner().getID(), client.getID()));
	
	}
	

	public void resupply(Request req) throws IOException, IllegalDiscountException {
		Purchase purchase = this.getResults(req);
		
		String deliverer = purchase.getDeliverer();
  	String devise = purchase.getDevise();
  	IFactory<Order> factOrd = new OrderFactory(deliverer);
  	List<Integer> idList = new ArrayList<>(purchase.getIdList());
  	List<IFactory<Product>> productList = new ArrayList<>();
  	
  	for (Integer id : idList) {
  		for (Product product : supply.browse()) {
				if(product.getID() == id) {
					productList.add(new ProductFactory(product.getName(), product.getPrice().getValue(), product.getPrice().getCurrency(), product.getDescription(), product.getCategory().getName()));
				}
			}
		}
  	Order ord = shop.restock(this.stock, productList, factOrd, devise, fixer);
  	
  	System.out.println("ok");
  	ThirdParty supplier = persons.read(purchase.getPersonId());
  	Transaction t = shop.saveTransaction(shop.getShopHistory(), this.transactions, new TransactionFactory(ord.getID(), shop.getShopOwner().getID(), supplier.getID()));
  	System.out.println(orderHistory.read(t.getOrderId()).getDeliverer());
	}
	
	@Override
	public void init() {
//			shop.buyProduct(stock, orderCreator, productIdList, deviseUsed, currentRate)
    get("/transaction/all", (req, resp) -> transactions.browse(), this::toJson);
    get("/transaction/:id", (req, resp) -> transactions.read(Integer.parseInt(req.params(":id"))), this::toJson);
    post("/transaction/new/sale", (req, resp) -> {
    	this.buy(req);
    	return req.body();
    }, this::toJson);
    post("/transaction/new/resupply", (req, resp) -> {
    	this.resupply(req);
    	return req.body();
    }, this::toJson);

	}

}
