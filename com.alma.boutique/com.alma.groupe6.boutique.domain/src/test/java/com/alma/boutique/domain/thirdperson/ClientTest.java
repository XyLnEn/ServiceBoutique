package com.alma.boutique.domain.thirdperson;

import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.mocks.factories.ClientMockFactory;
import com.alma.boutique.domain.mocks.factories.OrderSoldProductMockFactory;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static org.assertj.core.api.Assertions.*;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import java.io.IOException;

public class ClientTest {
	
	

	@Test
	public void testCreateOrder() {
		ClientMockFactory factory = new ClientMockFactory("bob", "lemon", "Somewhere over the rainbow", "555-5555");
		Client cli = null;
		try {
			cli = factory.create();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assertThat(cli.getOrderHistory()).as("Check if the client is created with an empty order list").isEmpty();
		Order ord = cli.createOrder(new OrderSoldProductMockFactory("USP"));
		try {
			assertThat(cli.getOrder(ord.getID())).as("check if the order was created correctly").isEqualTo(ord);
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testGetOrder() throws IOException, OrderNotFoundException {
		ClientMockFactory factory = new ClientMockFactory("bob", "lemon", "Somewhere over the rainbow", "555-5555");
		Client cli = factory.create();
		assertThat(cli.getOrderHistory()).as("Check if the client is created with an empty order list").isEmpty();
		
		Order ord = cli.createOrder(new OrderSoldProductMockFactory("USP"));
		assertThat(cli.getOrder(ord.getID())).as("Check if %s %s has the order", cli.getFirstName(), cli.getLastName()).isEqualTo(ord);
		
		Order notExistingOrd = new OrderSoldProductMockFactory("jesus").create();
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> cli.getOrder(notExistingOrd.getID()))
			.as("check if %s %s can react when he is asked to provide an order he doesn't have", cli.getFirstName(), cli.getLastName());
	}

	@Test
	public void testUpdateOrder() throws IOException, OrderNotFoundException {
		ClientMockFactory factory = new ClientMockFactory("bob", "lemon", "Somewhere over the rainbow", "555-5555");
		Client cli = factory.create();
		Order oldOrder = cli.createOrder(new OrderSoldProductMockFactory("VERT"));
		assertThat(cli.getOrder(oldOrder.getID()).getDeliverer()).as("check that the initial order has the right deliverer").isEqualTo("VERT");
		
		Order newOrd = cli.createOrder(new OrderSoldProductMockFactory("JAUNE"));
		cli.updateOrder(oldOrder.getID(), newOrd);
		assertThat(cli.getOrder(oldOrder.getID()).getDeliverer()).as("check that the initial order has the right deliverer").isEqualTo("JAUNE");
		
		Order notExistingOrd = new OrderSoldProductMockFactory("jesus").create();
		Order notPossibleOrder = new OrderSoldProductMockFactory("crust").create();
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> cli.updateOrder(notExistingOrd.getID(), notPossibleOrder))
		.as("check if %s %s can react when he is asked to update an order he doesn't have", cli.getFirstName(), cli.getLastName());
		
	}

	@Test
	public void testDeleteOrder() throws IOException, OrderNotFoundException {
		ClientMockFactory factory = new ClientMockFactory("bob", "lemon", "Somewhere over the rainbow", "555-5555");
		Client cli = factory.create();
		Order oldOrder = cli.createOrder(new OrderSoldProductMockFactory("VERT"));
		assertThat(cli.getOrder(oldOrder.getID()).getDeliverer()).as("check that the initial order has the right deliverer").isEqualTo("VERT");
		
		cli.deleteOrder(oldOrder);
		assertThat(cli.getOrderHistory()).as("check that the history list is empty").isEmpty();
	}

	@Test
	public void testPojoStandard() {
		assertPojoMethodsFor(Client.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}

}
