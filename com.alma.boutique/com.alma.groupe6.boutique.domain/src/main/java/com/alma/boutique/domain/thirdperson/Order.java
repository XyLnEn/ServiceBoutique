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
/**
 * Class that represent am order: it contains the name of the deliverer, the list of products bought and the state of the order
 * @author Lenny Lucas
 *
 */
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
	
	
	/**
	 * create a product
	 * @param factoryProd the factory that will supply the product
	 * @return the created product
	 */
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
	
	/**
	 * get the product if it is in the order
	 * @param prodId the unique Id of the product
	 * @return the product 
	 * @throws ProductNotFoundException
	 */
	public Product getProduct(int prodId) throws ProductNotFoundException {
		for (Product product : products) {
			if (product.getId() == prodId){
				return product;
			}
		}
		throw new ProductNotFoundException("Product not found");//in case the order doesn't exist
	}

	/**
	 * update an existing product 
	 * @param oldProdId the ID of the product to update
	 * @param newProd the patron that will be used to update
	 * @throws ProductNotFoundException
	 */
	public void updateProduct(int oldProdId, Product newProd) throws ProductNotFoundException {
		for (Product product : products) {
			if (product.getId() == oldProdId){
				product.updateProduct(newProd);
				return;
			}
		}
		throw new ProductNotFoundException("Product not found");//in case the order doesn't exist
	}

	/**
	 * delete a product 
	 * @param prod the product to delete
	 */
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
	
	/**
	 * method that calculate the total price of the order
	 * @return the total price of the order
	 * @throws IllegalDiscountException 
	 */
	public float totalPrice() throws IllegalDiscountException {
		float calculatedPrice = 0;
		for (Product stockProduct : products) {
			calculatedPrice += stockProduct.calculatePrice();
		}
		return calculatedPrice;
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