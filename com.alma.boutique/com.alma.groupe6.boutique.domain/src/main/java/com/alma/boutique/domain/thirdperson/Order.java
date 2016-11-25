package com.alma.boutique.domain.thirdperson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;

public class Order extends Entity {

	protected List<Product> products;
	protected OrderStatus orderStatus;
	protected String deliverer;

	public Order() {
		super();
		this.products = new ArrayList<>();
	}
	
	public Order(OrderStatus orderStatus, String deliverer) {
		super();
		this.products = new ArrayList<>();
		this.orderStatus = orderStatus;
		this.deliverer = deliverer;
	}
	
	
	
	public Product createProduct(IFactory<Product> factoryProd) {
		Product prod = null;
		try {
			prod = factoryProd.create();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		products.add(prod);
		return prod;
	}
	
	public Product getProduct(int prodId) throws ProductNotFoundException {
		for (Product product : products) {
			if (product.getID() == prodId){
				return product;
			}
		}
		throw new ProductNotFoundException("Product not found");//in case the order doesn't exist
	}

	public void updateProduct(int oldProdId, Product newProd) throws ProductNotFoundException {
		for (Product product : products) {
			if (product.getID() == oldProdId){
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

	public void advanceState() {
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
	 * @throws IllegalDiscountException 
	 */
	public float getTotalPrice() throws IllegalDiscountException {
		float calculatedPrice = 0;
		for (Product stockProduct : products) {
			calculatedPrice += stockProduct.calculatePrice();
		}
		return calculatedPrice;
	}

	public String getDeliverer() {
		return deliverer;
	}

	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
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
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(products)
				.append(orderStatus)
				.append(deliverer)
				.toHashCode();
	}

}