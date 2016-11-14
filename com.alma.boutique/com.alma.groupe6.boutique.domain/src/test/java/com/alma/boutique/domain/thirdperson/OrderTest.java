package com.alma.boutique.domain.thirdperson;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.factories.FactoryOrder;
import com.alma.boutique.domain.factories.FactorySoldProduct;
import com.alma.boutique.domain.product.Product;

public class OrderTest {

	@Test
	public void testUpdateOrder() {
		
		FactoryOrder factoryOrd = new FactoryOrder();
		FactorySoldProduct factoryProd = new FactorySoldProduct();
		
		Order ord = factoryOrd.make("USP", factoryProd);
		assertThat(ord.getDeliverer()).as("check the initial deliverer").isEqualTo("USP");

		Order newDeliverer = factoryOrd.make("DPS", factoryProd);
		ord.updateOrder(newDeliverer);
		assertThat(ord.getDeliverer()).as("check the update of the deliverer").isEqualTo("DPS");
		
		Order newStatus = factoryOrd.make("DPS", factoryProd);
		newStatus.advanceState();
		ord.updateOrder(newStatus);
		assertThat(ord.getOrderStatus()).as("check the update of the state of the order").isEqualTo(OrderStatus.TRAVELING);

		Order newProd = factoryOrd.make("DPS", factoryProd);
		newProd.createProduct("DAB", 5, "EUR", "On 'em", "lol");
		ord.updateOrder(newProd);
		assertThat(ord.getProducts().get(0).getDescription()).as("check the update on the Product list").isEqualTo("On 'em");
		
	}

	@Test
	public void testGetTotalPrice() {
		
		FactoryOrder factoryOrd = new FactoryOrder();
		FactorySoldProduct factoryProd = new FactorySoldProduct();
		
		Order ord = factoryOrd.make("DPS", factoryProd);
		assertThat(ord.getTotalPrice()).as("test with the initial price").isEqualTo(0);
		
		ord.createProduct("DAB", 5, "EUR", "On 'em", "lol");
		assertThat(ord.getTotalPrice()).as("test with a non-empty Product list").isEqualTo(5);
		
	}
	
	@Test
	public void testCreateProduct() {
		FactoryOrder factoryOrd = new FactoryOrder();
		FactorySoldProduct factoryProd = new FactorySoldProduct();
		Order ord = factoryOrd.make("DPS", factoryProd);
		assertThat(ord.getProducts()).as("check that the order is created empty").isEmpty();
		Product prod = ord.createProduct("duck", 1, "EUR", "a very charismatic duck", "weapon");
		assertThat(ord.getProducts()).as("check that the product was added successfully").containsExactly(prod);
	}
	
	@Test
	public void testGetProduct() {
		FactoryOrder factoryOrd = new FactoryOrder();
		FactorySoldProduct factoryProd = new FactorySoldProduct();
		Order ord = factoryOrd.make("DPS", factoryProd);
		
		Product prod = ord.createProduct("duck", 1, "EUR", "a very charismatic duck", "weapon");
		Product prodNotAdded = ord.getFactoryProd().make("gun", 1, "EUR", "a very charismatic gun", "food");
		try {
			assertThat(ord.getProduct(prod)).as("check that the order return the right product").isEqualTo(prod);
		} catch (ProductNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> ord.getProduct(prodNotAdded))
		.as("check if the order can react when he is asked to get an order he doesn't have");
		
	}
	
	@Test
	public void testUpdateProduct() {
		FactoryOrder factoryOrd = new FactoryOrder();
		FactorySoldProduct factoryProd = new FactorySoldProduct();
		Order ord = factoryOrd.make("DPS", factoryProd);
		
		Product prod = ord.createProduct("duck", 1, "EUR", "a very charismatic duck", "weapon");
		Product prodNew = ord.getFactoryProd().make("gun", 2, "EUR", "I could use this", "food");
		try {
			ord.updateProduct(prod, prodNew);
			assertThat(ord.getProduct(prod).getName()).as("check that the order's name was updated successfully").isEqualTo(prodNew.getName());
			assertThat(ord.getProduct(prod).getPrice()).as("check that the order's price was updated successfully").isEqualTo(prodNew.getPrice());
			assertThat(ord.getProduct(prod).getDescription()).as("check that the order's description was updated successfully").isEqualTo(prodNew.getDescription());
			assertThat(ord.getProduct(prod).getCategory().getName()).as("check that the order's category was updated successfully").isEqualTo(prodNew.getCategory().getName());
		} catch (ProductNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		
		assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> ord.getProduct(factoryProd.make("NAN", 10000, "EUR", "Not a number", "number")))
		.as("check if the order can react when he is asked to update an order he doesn't have");
	}
	
	@Test
	public void testDeleteProduct() {
		FactoryOrder factoryOrd = new FactoryOrder();
		FactorySoldProduct factoryProd = new FactorySoldProduct();
		Order ord = factoryOrd.make("DPS", factoryProd);
		
		Product prod = ord.createProduct("duck", 1, "EUR", "a very charismatic duck", "weapon");
		try {
			assertThat(ord.getProduct(prod)).as("check that the order is not empty").isEqualTo(prod);
			ord.deleteProduct(prod);
		} catch (ProductNotFoundException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> ord.getProduct(prod))
		.as("check if the order has indeed deleted the product");
	}
	
	@Test
	public void testAdvanceState() {
		FactoryOrder factoryOrd = new FactoryOrder();
		FactorySoldProduct factoryProd = new FactorySoldProduct();
		Order ord = factoryOrd.make("DPS", factoryProd);
		assertThat(ord.getOrderStatus()).as("check the initial value of the state of the order is correct").isEqualTo(OrderStatus.ORDERED);
		ord.advanceState();
		assertThat(ord.getOrderStatus()).as("check the next value of the state is correct").isEqualTo(OrderStatus.TRAVELING);
		ord.advanceState();
		assertThat(ord.getOrderStatus()).as("check the next value of the state is correct").isEqualTo(OrderStatus.ARRIVED);
		ord.advanceState();
		assertThat(ord.getOrderStatus()).as("check the next value of the state is correct").isEqualTo(OrderStatus.DELIVERED);
		ord.advanceState();
		assertThat(ord.getOrderStatus()).as("check the value of the state doesn't go beyond the last state").isEqualTo(OrderStatus.DELIVERED);
	}
}
