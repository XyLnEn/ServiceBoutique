package com.alma.boutique.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.api.services.BrowseSuppliesService;
import com.alma.boutique.api.services.ExchangeRateService;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.history.History;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.product.SoldProduct;
import com.alma.boutique.domain.product.SuppliedProduct;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderSoldProduct;
import com.alma.boutique.domain.thirdperson.OrderSuppliedProduct;
import com.alma.boutique.domain.thirdperson.ShopOwner;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class Shop extends Entity{

	private History shopHistory;
	private ThirdParty shopOwner;
	
	public Shop() {
		super();
		this.shopOwner = new ShopOwner();
		this.shopHistory = new History();
	}


	public Shop(IRepository<ShopOwner> repositoryShopOwner) {
		super();
		this.shopOwner = repositoryShopOwner.browse().get(0);
		this.shopHistory = new History();
		this.shopHistory.getAccount().setOwner(this.shopOwner);
		this.shopHistory.setChangedbalance(true);
	}
	
	public List<SoldProduct> browseStock(IRepository<SoldProduct> stock) {
		return stock.browse();
	}
	
	public Order buyProduct(IRepository<SoldProduct> stock, 
			IFactory<OrderSoldProduct> orderCreator, List<Integer> productIdList, 
			String deviseUsed, ExchangeRateService currentRate ) throws IOException, IllegalDiscountException {
		
		Order ord = orderCreator.create();
		List<Product> orderList = productIdList.stream().map(stock::read).collect(Collectors.toList());
		for (Product product : orderList) {
			Price convertedPrice = new Price();
			convertedPrice.setCurrency("EUR");
			
			float newValue = currentRate.exchange(product.calculatePrice(),product.getPrice().getCurrency());
			convertedPrice.setValue(newValue);
			
			product.setPrice(convertedPrice);
			stock.delete(product.getID());
		}
		ord.setProducts(orderList);
		return ord;
	}
	
	public Transaction saveTransaction(History history, IRepository<Transaction> transactionHistory, 
			IFactory<Transaction> newTransaction) {
		return history.createTransaction(newTransaction, transactionHistory);
	}
	
	
	public void buyProductFromSupplier(OrderSuppliedProduct totalOrder, IFactory<SuppliedProduct> productToBuy) throws IOException {
		totalOrder.createProduct(productToBuy);
	}
	
	public Order restock(IRepository<SoldProduct> stock, List<IFactory<SuppliedProduct>> productList, 
			IFactory<OrderSuppliedProduct> orderCreator, String deviseUsed, ExchangeRateService currentRate) throws IOException {
		OrderSuppliedProduct restockOrder = orderCreator.create();
		for (IFactory<SuppliedProduct> product : productList) {
			buyProductFromSupplier(restockOrder, product);
		}
		return restockOrder;
	}
	
	public void advanceOrder(int ordId, History history, IRepository<Transaction> transactionHistory, ThirdParty person) throws OrderNotFoundException {
		history.AdvanceOrder(ordId, transactionHistory);
		person.getOrder(ordId).advanceState();
	}
	
	public void addProductPromotion(int prodId, IRepository<SoldProduct> stock, float discount) {
		SoldProduct prod = stock.read(prodId);
		prod.addDiscount(discount);
		stock.edit(prodId, prod);
	}

	public void supProductPromotion(int prodId, IRepository<SoldProduct> stock, float discount) {
		SoldProduct prod = stock.read(prodId);
		prod.addDiscount(-discount);
		stock.edit(prodId, prod);
	}
	
	//TODO ProductPromotion on multiple products
	
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
	
}
