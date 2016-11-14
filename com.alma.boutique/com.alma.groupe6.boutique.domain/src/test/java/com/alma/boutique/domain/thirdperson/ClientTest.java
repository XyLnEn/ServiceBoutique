package com.alma.boutique.domain.thirdperson;

import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.factories.FactoryClient;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static org.assertj.core.api.Assertions.*;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class ClientTest {
	
	

	@Test
	public void testCreateOrder() {
		FactoryClient factory = new FactoryClient();
		Client cli = factory.make("bob", "lemon", "Somewhere over the rainbow", "555-5555");
		assertThat(cli.getOrderHistory()).as("Check if the client is created with an empty order list").isEmpty();
		Order ord = cli.createOrder("USP");
		try {
			assertThat(cli.getOrder(ord)).as("check if the order was created correctly").isEqualTo(ord);
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testGetOrder() {
		FactoryClient factory = new FactoryClient();
		Client cli = factory.make("bob", "lemon", "Somewhere over the rainbow", "555-5555");
		Order ord = cli.createOrder("USP");
		try {
			assertThat(cli.getOrder(ord)).as("Check if %s %s has the order", cli.getFirstName(), cli.getLastName()).isEqualTo(ord);
		} catch (OrderNotFoundException e) {
			fail(e.getMessage());
		}
		Order notExistingOrd = cli.getFactoryOrd().make("jesus", cli.getFactoryProd());
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> cli.getOrder(notExistingOrd))
			.as("check if %s %s can react when he is asked to provide an order he doesn't have", cli.getFirstName(), cli.getLastName());
	}

	@Test
	public void testUpdateOrder() {
		FactoryClient factory = new FactoryClient();
		Client cli = factory.make("bob", "lemon", "Somewhere over the rainbow", "555-5555");
		Order oldOrder = cli.createOrder("VERT");
		try {
			assertThat(cli.getOrder(oldOrder).getDeliverer()).as("check that the initial order has the right deliverer").isEqualTo("VERT");
			Order newOrd = cli.getFactoryOrd().make("JAUNE", cli.getFactoryProd());
			cli.updateOrder(oldOrder, newOrd);
			assertThat(cli.getOrder(oldOrder).getDeliverer()).as("check that the initial order has the right deliverer").isEqualTo("JAUNE");
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Order notExistingOrd = cli.getFactoryOrd().make("jesus", cli.getFactoryProd());
		Order notPossibleOrder = cli.getFactoryOrd().make("CRIST", cli.getFactoryProd());
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> cli.updateOrder(notExistingOrd, notPossibleOrder))
		.as("check if %s %s can react when he is asked to update an order he doesn't have", cli.getFirstName(), cli.getLastName());
		
	}

	@Test
	public void testDeleteOrder() {
		FactoryClient factory = new FactoryClient();
		Client cli = factory.make("bob", "lemon", "Somewhere over the rainbow", "555-5555");
		Order oldOrder = cli.createOrder("VERT");
		try {
			assertThat(cli.getOrder(oldOrder).getDeliverer()).as("check the presence of the initial order").isEqualTo("VERT");
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cli.deleteOrder(oldOrder);
		assertThat(cli.getOrderHistory()).as("check that the history list is empty").isEmpty();
	}

	@Test
	public void testPojoStandard() {
		assertPojoMethodsFor(Client.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}

}
