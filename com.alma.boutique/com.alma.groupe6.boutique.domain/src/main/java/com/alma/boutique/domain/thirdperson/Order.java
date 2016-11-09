package com.alma.boutique.domain.thirdperson;

import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;
/**
 * 
 * @author lenny
 *
 */
public class Order extends Entity {

	private List<Product> products;
	private OrderStatus orderStatus;
	private String deliverer;
	
	public Order(OrderStatus orderStatus, String deliverer) {
		super();
		this.products = new ArrayList<>();
		this.orderStatus = orderStatus;
		this.deliverer = deliverer;
	}
	
	public Product createProduct() {
		//creation of the Product with the factory and addition to the products
		return null;
		//how do they determine if we want to create which type of product?
	}
	
	public Product getProduct(Product prod) throws ProductNotFoundException {
		for (Product product : products) {
			if (product.sameIdentityAs(prod)){
				return product;
			}
		}
		throw new ProductNotFoundException("Product not found");//in case the order doesn't exist
	}
	
	public void updateProduct(Product prod) {
		for (Product product : products) {
			if (product.sameIdentityAs(prod)){
//				product.updateProduct(prod);
			}
		}
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
			calculatedPrice += stockProduct.getPrice();
		}
		return calculatedPrice;
	}
	public String getDeliverer() {
		return deliverer;
	}
	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}
	
}
