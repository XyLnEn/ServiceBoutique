package com.alma.boutique.domain.thirdperson;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.mocks.factories.OrderMockFactory;
import com.alma.boutique.domain.mocks.factories.ProductMockFactory;
import com.alma.boutique.domain.product.Product;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static org.assertj.core.api.Assertions.*;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import java.io.IOException;

public class OrderTest {

	@Test
	public void testUpdateOrder() throws IOException {
		
		OrderMockFactory factoryOrd = new OrderMockFactory("USP");
		Order ord = factoryOrd.create();
		assertThat(ord.getDeliverer()).as("check the initial deliverer").isEqualTo("USP");

		factoryOrd = new OrderMockFactory("DPS");
		Order newDeliverer = factoryOrd.create();
		ord.updateOrder(newDeliverer);
		assertThat(ord.getDeliverer()).as("check the update of the deliverer").isEqualTo("DPS");
		
		factoryOrd = new OrderMockFactory("DPS");
		Order newStatus = factoryOrd.create();
		newStatus.advanceState();
		ord.updateOrder(newStatus);
		assertThat(ord.getOrderStatus()).as("check the update of the state of the order").isEqualTo(OrderStatus.TRAVELING);

		factoryOrd = new OrderMockFactory("DPS");
		Order newProd = factoryOrd.create();
		ProductMockFactory factoProd = new ProductMockFactory("DAB", 5, "EUR", "On 'em", "lol");
		newProd.createProduct(factoProd);
		ord.updateOrder(newProd);
		assertThat(ord.getProducts().get(0).getDescription()).as("check the update on the Product list").isEqualTo("On 'em");
		
	}

	@Test
	public void testGetTotalPrice() throws Exception {
		
		OrderMockFactory factoryOrd = new OrderMockFactory("DPS");
		
		Order ord = factoryOrd.create();
		assertThat(ord.TotalPrice()).as("test with the initial price").isEqualTo(0);
		
		ProductMockFactory factoProd = new ProductMockFactory("DAB", 5, "EUR", "On 'em", "lol");
		Product prod = ord.createProduct(factoProd);
		assertThat(ord.TotalPrice()).as("test with a non-empty Product list").isEqualTo(5);
		
		ord.getProduct(prod.getID()).addDiscount(25);
		assertThatExceptionOfType(IllegalDiscountException.class).isThrownBy(() -> ord.TotalPrice())
		.as("check if the order can react when he is asked to get an order with a product that has a discount too high");
		
	}
	
	@Test
	public void testCreateProduct() throws IOException {
		OrderMockFactory factoryOrd = new OrderMockFactory("DPS");
		Order ord = factoryOrd.create();
		assertThat(ord.getProducts()).as("check that the order is created empty").isEmpty();
		
		ProductMockFactory factoProd = new ProductMockFactory("duck", 1, "EUR", "a very charismatic duck", "weapon");
		Product prod = ord.createProduct(factoProd);
		assertThat(ord.getProducts()).as("check that the product was added successfully").containsExactly(prod);
	}
	
	@Test
	public void testGetProduct() throws IOException, ProductNotFoundException {
		OrderMockFactory factoryOrd = new OrderMockFactory("DPS");
		Order ord = factoryOrd.create();
		ProductMockFactory factoProd = new ProductMockFactory("duck", 1, "EUR", "a very charismatic duck", "weapon");
		Product prod = ord.createProduct(factoProd);
		Product prodNotAdded = new ProductMockFactory("gun", 1, "EUR", "a very charismatic gun", "food").create();
		assertThat(ord.getProduct(prod.getID())).as("check that the order return the right product").isEqualTo(prod);
		
		assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> ord.getProduct(prodNotAdded.getID()))
		.as("check if the order can react when he is asked to get an order he doesn't have");
		
	}
	
	@Test
	public void testUpdateProduct() throws IOException, ProductNotFoundException {
		OrderMockFactory factoryOrd = new OrderMockFactory("DPS");
		Order ord = factoryOrd.create();
		ProductMockFactory factoProd = new ProductMockFactory("duck", 1, "EUR", "a very charismatic duck", "weapon");
		Product prod = ord.createProduct(factoProd);
		factoProd = new ProductMockFactory("gun", 2, "EUR", "I could use this", "food");
		Product prodNew = factoProd.create();
		ord.updateProduct(prod.getID(), prodNew);
		assertThat(ord.getProduct(prod.getID()).getName()).as("check that the order's name was updated successfully").isEqualTo(prodNew.getName());
		
		assertThat(ord.getProduct(prod.getID()).getPrice()).as("check that the order's price was updated successfully").isEqualTo(prodNew.getPrice());
		
		assertThat(ord.getProduct(prod.getID()).getDescription()).as("check that the order's description was updated successfully").isEqualTo(prodNew.getDescription());
		
		assertThat(ord.getProduct(prod.getID()).getCategory().getName()).as("check that the order's category was updated successfully").isEqualTo(prodNew.getCategory().getName());
		
		assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> ord.getProduct(new ProductMockFactory("NAN", 10000, "EUR", "Not a number", "number").create().getID()))
		.as("check if the order can react when he is asked to update an order he doesn't have");
	}
	
	@Test
	public void testDeleteProduct() throws IOException, ProductNotFoundException {
		OrderMockFactory factoryOrd = new OrderMockFactory("DPS");
		Order ord = factoryOrd.create();
		
		ProductMockFactory factoProd = new ProductMockFactory("duck", 1, "EUR", "a very charismatic duck", "weapon");
		Product prod = ord.createProduct(factoProd);
		
		assertThat(ord.getProduct(prod.getID())).as("check that the order is not empty").isEqualTo(prod);
		
		ord.deleteProduct(prod);
		assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> ord.getProduct(prod.getID()))
		.as("check if the order has indeed deleted the product");
	}
	
	@Test
	public void testAdvanceState() throws IOException {
		OrderMockFactory factoryOrd = new OrderMockFactory("DPS");
		Order ord = factoryOrd.create();
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

    @Test
    public void testPojoStandard() {
        assertPojoMethodsFor(Order.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
    }
}
