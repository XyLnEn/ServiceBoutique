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

/**
 * Class that represent the Shop. It is the only class in the domain that has methods explicitely used outside of it.
 * @author Lenny Lucas
 *
 */
public class Shop extends Entity{

	private History shopHistory;
	
	public Shop() {
		super();
		this.shopHistory = new History();
	}


	public Shop(ThirdParty shopOwner) {
		super();
		this.shopHistory = new History();
		this.shopHistory.getAccount().setOwner(shopOwner);
		this.shopHistory.setChangedbalance(true);
	}
	
	/**
	 * Method that open the stock to get all Products according to a specifies devise
	 * @param stock the repository to explore
	 * @param devise the name of the devise asked
	 * @param currentRate the conversion exchange
	 * @return the list of all products
	 * @throws IOException 
	 */
	public List<Product> browseStock(IRepository<Product> stock, String devise, ExchangeRateService currentRate) throws IOException {
		List<Product> oldList = new ArrayList<>();
		for (Product product : stock.browse()) {
			oldList.add(updatePriceToSell(product, currentRate, devise));
		}
		
		return oldList;
	}
	
	/**
	 * Method that buy a product and create the order,save it and update the buyer
	 * @param stock where the Product are stored
	 * @param personList the list of persons containing the buyer
	 * @param orderCreator the factory that will create the Order
	 * @param productIdList a list containing the id of every products to buy
	 * @param personId the id of the buyer
	 * @param deviseUsed the devise used to buy the products
	 * @param currentRate the service to recalculate the price of the products using the currency
	 * @return the created order
	 * @throws IOException
	 * @throws OrderNotFoundException
	 * @throws IllegalDiscountException
	 */
	public Order buyProduct(IRepository<Product> stock, IRepository<ThirdParty> personList,
			IFactory<Order> orderCreator, List<Integer> productIdList, int personId,
			String deviseUsed, ExchangeRateService currentRate ) throws IllegalDiscountException, OrderNotFoundException {
		ThirdParty pers = personList.read(personId);
		Order ord = pers.createOrder(orderCreator);
		List<Product> orderList = productIdList.stream().map(stock::read).collect(Collectors.toList());
		for (Product product : orderList) {
			Price convertedPrice = new Price();
			convertedPrice.setCurrency("EUR");
			
			float newValue = currentRate.exchangeBack(product.calculatePrice(),deviseUsed);
			convertedPrice.setValue(newValue);
			
			product.setPrice(convertedPrice);
			stock.delete(product.getId());
		}
		ord.setProducts(orderList);
		pers.updateOrder(ord.getId(), ord);
		personList.edit(pers.getId(), pers);
		return ord;
	}
	
	/**
	 * Method that save a transaction
	 * @param history the shop history where to create the transaction
	 * @param transactionHistory the repository where the transaction will be saved
	 * @param newTransaction the factory that will create the transaction
	 * @return the created transaction
	 */
	public Transaction saveTransaction(History history, IRepository<Transaction> transactionHistory, 
			IFactory<Transaction> newTransaction) {
		return history.createTransaction(newTransaction, transactionHistory);
	}
	
	/**
	 * Method to buy a product from a supplier
	 * @param totalOrder the order where to create the product
	 * @param productToBuy the factory that will create the product to buy
	 * @return the bought product
	 * @throws IOException
	 */
	public Product buyProductFromSupplier(Order totalOrder, IFactory<Product> productToBuy) throws IOException {
		return totalOrder.createProduct(productToBuy);
	}
	
	/**
	 * Method that convert the price of a product into EUR
	 * @param product the product to update
	 * @param currentRate the service to recalculate the price of the product using the currency
	 * @return the updated product
	 * @throws IOException
	 */
	public Product updatePriceToStock(Product product, ExchangeRateService currentRate, String currency) throws IOException {
		Product newProd = product;
		float priceConverted = currentRate.exchange(product.getPrice().getValue(), product.getPrice().getCurrency());
		Price newPrice = new Price(priceConverted, currency);
		newProd.setPrice(newPrice);
		return newProd;
	}
	
	/**
	 * Method that convert the price of a product from EUR to the desired devise
	 * @param product the product to update
	 * @param currentRate the service to recalculate the price of the product using the currency
	 * @return the updated product
	 * @throws IOException
	 */
	public Product updatePriceToSell(Product product, ExchangeRateService currentRate, String currency) throws IOException {
		Product newProd = product;
		float priceConverted = currentRate.exchangeBack(product.getPrice().getValue(), currency);
		Price newPrice = new Price(priceConverted, currency);
		newProd.setPrice(newPrice);
		return newProd;
	}
	
	/**
	 * Method to buy products from a supplier and put them into the shop's stock. The supplier is updated 
	 * @param stock where to put the bought products
	 * @param personList the list of persons containing the supplier
	 * @param productList the list of every factories needed to create the bought products
	 * @param orderCreator the factory that will create the Order
	 * @param supplierId the id of the supplier to update
	 * @param deviseUsed the devise used to buy the products
	 * @param currentRate the service to recalculate the price of the products using the currency
	 * @return the created Order
	 * @throws IOException
	 * @throws OrderNotFoundException
	 */
	public Order restock(IRepository<Product> stock, IRepository<ThirdParty> personList, 
			List<Product> productList, IFactory<Order> orderCreator, 
			int supplierId, String deviseUsed, ExchangeRateService currentRate) throws IOException, OrderNotFoundException {
		ThirdParty supplier = personList.read(supplierId); 
		Order restockOrder = supplier.createOrder(orderCreator);
		List<Product> orderList = new ArrayList<>();
		for (Product product : productList) {
			
			Product newProd = updatePriceToStock(product, currentRate, "EUR");
			orderList.add(newProd);
			stock.add(newProd.getId(), newProd);
		}
		restockOrder.setProducts(orderList);
		supplier.updateOrder(restockOrder.getId(), restockOrder);
		personList.edit(supplier.getId(), supplier);
		return restockOrder;
	}
	
	/**
	 * Method that advance the state of an order
     * @param orderList the repository
     * @param ordId the id of the order to advance
	 * @throws OrderNotFoundException
	 */
	public void advanceOrder(IRepository<Order> orderList, int ordId) throws OrderNotFoundException{
		try {
			orderList.read(ordId).advanceState();
		} catch (NullPointerException e) {
			log.warn(e.getMessage(), e);
			throw new OrderNotFoundException("Order not found");
		}
	}
	
	/**
	 * Method that apply a promotion on a product
	 * @param stock where the products are stored
	 * @param prodId the id of the product to update
	 * @param discount the discount to apply
	 */
	public void applyProductPromotion(IRepository<Product> stock,int prodId, float discount) {
		Product prod = stock.read(prodId);
		prod.addDiscount(discount);
		stock.edit(prodId, prod);
	}
	
	/**
	 * Method that apply a promotion on a list of products
	 * @param stock where the products are stored
	 * @param promo the promotion to apply
	 * @param productIds the list of products which we want to promotion to be applied
	 */
	public void applyPromotionOnProducts(IRepository<Product> stock, float promo, List<Integer> productIds) {
		for (Integer productId : productIds) {
			applyProductPromotion(stock, productId, promo);
		}
	}
	
	/**
	 * Method that calculate the turnover of the shop
	 * @param transactionHistory the list of transactions done in the shop
	 * @param orderList the list of orders made in the shop
	 * @param personList the list of persons 
	 * @return the turnover of the shop
	 * @throws IllegalDiscountException
	 */
	public float getCurrentSold(IRepository<Transaction> transactionHistory, 
			IRepository<Order> orderList, IRepository<ThirdParty> personList) throws IllegalDiscountException {
		return shopHistory.getBalance(transactionHistory, orderList, personList);
	}
	
	public List<Transaction> getHistory(IRepository<Transaction> transactionHistory) {
		return shopHistory.getHistory(transactionHistory);
	}
	
	public History getShopHistory() {
		return shopHistory;
	}


	public void setShopHistory(History shopHistory) {
		this.shopHistory = shopHistory;
	}
	
}
