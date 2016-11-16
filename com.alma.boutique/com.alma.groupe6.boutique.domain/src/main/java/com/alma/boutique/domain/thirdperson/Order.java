package com.alma.boutique.domain.thirdperson;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;

public abstract class Order extends Entity {

	protected List<Product> products;
	protected OrderStatus orderStatus;
	protected String deliverer;

	public Order() {
		super();
	}
	

	public abstract Product createProduct(IFactory factoryProduct);
	
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
		OrderSuppliedProduct rhs = (OrderSuppliedProduct) obj;
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