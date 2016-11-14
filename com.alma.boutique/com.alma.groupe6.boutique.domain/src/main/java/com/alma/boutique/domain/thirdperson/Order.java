package com.alma.boutique.domain.thirdperson;

import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.domain.factories.FactoryProduct;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * @author lenny
 *
 */
public class Order extends Entity {

	private List<Product> products;
	private OrderStatus orderStatus;
	private String deliverer;
	
	private FactoryProduct factoryProd;
	
	public Order(){
		super();
		this.products = new ArrayList<>();
		this.orderStatus = OrderStatus.ORDERED;
		this.deliverer = "";
		this.factoryProd = null;
	}
	
	public Order(OrderStatus orderStatus, String deliverer, FactoryProduct factory) {
		super();
		this.products = new ArrayList<>();
		this.orderStatus = orderStatus;
		this.deliverer = deliverer;
		this.factoryProd = factory;
	}
	
	public Product createProduct(String name, float price, String currency, String description,  String categoryName) {
		Product prod = this.factoryProd.make(name, price, currency, description, categoryName);
		products.add(prod);
		return prod;
	}
	
	public Product getProduct(Product prod) throws ProductNotFoundException {
		for (Product product : products) {
			if (product.sameIdentityAs(prod)){
				return product;
			}
		}
		throw new ProductNotFoundException("Product not found");//in case the order doesn't exist
	}
	
	public void updateProduct(Product oldProd, Product newProd) throws ProductNotFoundException {
		for (Product product : products) {
			if (product.sameIdentityAs(oldProd)){
				product.updateProduct(newProd);
				return;
			}
		}
		throw new ProductNotFoundException("Product not found");//in case the order doesn't exist
	}
	
	public void deleteProduct(Product prod) {
		products.remove(prod);
	}
	
	/**
	 * update the order with new values
	 * @param ord the new value
	 */
	public void updateOrder(Order ord) {
		this.products = ord.getProducts();
		this.orderStatus = ord.getOrderStatus();
		this.deliverer = ord.getDeliverer();
	}
	
	public void advanceState(){
		switch (this.orderStatus) {
		case ORDERED:
			this.orderStatus = OrderStatus.TRAVELING;
			break;

		case TRAVELING:
			this.orderStatus = OrderStatus.ARRIVED;
			break;
			
		case ARRIVED:
			this.orderStatus = OrderStatus.DELIVERED;
			break;
			
		default:
			break;
		}
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * method that calculate the total price of the order
	 * @return the total price of the order
	 */
	public float getTotalPrice() {//calculated
		float calculatedPrice = 0;
		for (Product stockProduct : products) {
			calculatedPrice += stockProduct.getPrice().getValue();
		}
		return calculatedPrice;
	}
	public String getDeliverer() {
		return deliverer;
	}
	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}
	public FactoryProduct getFactoryProd() {
		return factoryProd;
	}
	public void setFactoryProd(FactoryProduct factoryProd) {
		this.factoryProd = factoryProd;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Order rhs = (Order) obj;
		return new EqualsBuilder()
				.append(this.products, rhs.products)
				.append(this.orderStatus, rhs.orderStatus)
				.append(this.deliverer, rhs.deliverer)
				.append(this.factoryProd, rhs.factoryProd)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(products)
				.append(orderStatus)
				.append(deliverer)
				.append(factoryProd)
				.toHashCode();
	}
}
