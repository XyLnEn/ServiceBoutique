package com.alma.boutique.domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.api.services.ExchangeRateService;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.history.History;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class Shop extends Entity{

	private History shopHistory;
	private ThirdParty shopOwner;
	
	public Shop() {
		super();
		this.shopOwner = new ThirdParty();
		this.shopHistory = new History();
	}


	public Shop(IRepository<ThirdParty> repositoryShopOwner) {
		super();
		this.shopOwner = repositoryShopOwner.browse().get(0);
		this.shopHistory = new History();
		this.shopHistory.getAccount().setOwner(this.shopOwner);
		this.shopHistory.setChangedbalance(true);
	}
	
	public List<Product> browseStock(IRepository<Product> stock) {
		return stock.browse();
	}
	
	public Order buyProduct(IRepository<Product> stock, IRepository<ThirdParty> personList,
			IFactory<Order> orderCreator, List<Integer> productIdList, int personId,
			String deviseUsed, ExchangeRateService currentRate ) throws IOException, IllegalDiscountException, OrderNotFoundException {
		ThirdParty pers = personList.read(personId);
		Order ord = pers.createOrder(orderCreator);
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
		pers.updateOrder(ord.getID(), ord);
		personList.edit(pers.getID(), pers);
		return ord;
	}
	
	public Transaction saveTransaction(History history, IRepository<Transaction> transactionHistory, 
			IFactory<Transaction> newTransaction) {
		return history.createTransaction(newTransaction, transactionHistory);
	}
	
	
	public Product buyProductFromSupplier(Order totalOrder, IFactory<Product> productToBuy) throws IOException {
		return totalOrder.createProduct(productToBuy);
	}
	
	public Order restock(IRepository<Product> stock, IRepository<ThirdParty> personList, 
			List<IFactory<Product>> productList, IFactory<Order> orderCreator, 
			int supplierId, String deviseUsed, ExchangeRateService currentRate) throws IOException, OrderNotFoundException {
		ThirdParty supplier = personList.read(supplierId); 
		Order restockOrder = supplier.createOrder(orderCreator);
		List<Product> orderList = new ArrayList<>();
		for (IFactory<Product> product : productList) {
			Product newProd = buyProductFromSupplier(restockOrder, product);
			orderList.add(newProd);
			stock.add(newProd.getID(), newProd);
		}
		restockOrder.setProducts(orderList);
		supplier.updateOrder(restockOrder.getID(), restockOrder);
		personList.edit(supplier.getID(), supplier);
		return restockOrder;
	}
	
	public void advanceOrder( History history, IRepository<Transaction> transactionHistory, IRepository<Order> orderList,  int ordId) throws OrderNotFoundException {
		history.AdvanceOrder(ordId, transactionHistory, orderList);
	}
	
	public void applyProductPromotion(IRepository<Product> stock,int prodId, float discount) {
		Product prod = stock.read(prodId);
		prod.addDiscount(discount);
		stock.edit(prodId, prod);
	}
	
	public void applyPromotionOnProducts(IRepository<Product> stock, float promo, List<Integer> productIds) {
		for (Integer productId : productIds) {
			applyProductPromotion(stock, productId, promo);
		}
	}
	
	public float getCurrentSold(History hist, IRepository<Transaction> transactionHistory, 
			IRepository<Order> orderList, IRepository<ThirdParty> personList) throws IllegalDiscountException {
		return hist.getBalance(transactionHistory, orderList, personList);
	}
	
	public List<Transaction> getHistory(History hist, IRepository<Transaction> transactionHistory) {
		return hist.getHistory(transactionHistory);
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
	
}
