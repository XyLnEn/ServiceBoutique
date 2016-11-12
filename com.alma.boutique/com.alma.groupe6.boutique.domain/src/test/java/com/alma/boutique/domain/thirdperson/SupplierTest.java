package com.alma.boutique.domain.thirdperson;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.fail;

import org.junit.Test;

import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.factories.FactorySupplier;

public class SupplierTest {

	@Test
	public void testCreateOrder() {
		FactorySupplier factory = new FactorySupplier();
		Supplier supp = factory.make("Google", "Somewhere over the rainbow", "555-5555");
		assertThat(supp.getOrderHistory()).as("Check if the Supplier is created with an empty order list").isEmpty();
		Order ord = supp.createOrder("USP");
		try {
			assertThat(supp.getOrder(ord)).as("check if the order was created correctly").isEqualTo(ord);
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testGetOrder() {
		FactorySupplier factory = new FactorySupplier();
		Supplier supp = factory.make("Google", "Somewhere over the rainbow", "555-5555");
		Order ord = supp.createOrder("USP");
		try {
			assertThat(supp.getOrder(ord)).as("Check if %s has the order", supp.getSupplierName()).isEqualTo(ord);
		} catch (OrderNotFoundException e) {
			fail(e.getMessage());
		}
		Order notExistingOrd = supp.getFactoryOrd().make("jesus", supp.getFactoryProd());
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> supp.getOrder(notExistingOrd))
			.as("check if %s %s can react when he is asked to provide an order he doesn't have", supp.getSupplierName());
	}

	@Test
	public void testUpdateOrder() {
		FactorySupplier factory = new FactorySupplier();
		Supplier supp = factory.make("Google", "Somewhere over the rainbow", "555-5555");
		Order oldOrder = supp.createOrder("VERT");
		try {
			assertThat(supp.getOrder(oldOrder).getDeliverer()).as("check that the initial order has the right deliverer").isEqualTo("VERT");

			Order newOrd = supp.getFactoryOrd().make("JAUNE", supp.getFactoryProd());
			supp.updateOrder(oldOrder, newOrd);
			
			assertThat(supp.getOrder(oldOrder).getDeliverer()).as("check that the initial order has the right deliverer").isEqualTo("JAUNE");
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Order notExistingOrd = supp.getFactoryOrd().make("jesus", supp.getFactoryProd());
		Order notLaicOrd = supp.getFactoryOrd().make("CRIST", supp.getFactoryProd());
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> supp.updateOrder(notExistingOrd, notLaicOrd))
			.as("check if %s %s can react when he is asked to provide an order he doesn't have", supp.getSupplierName());
		
	}

	@Test
	public void testDeleteOrder() {
		FactorySupplier factory = new FactorySupplier();
		Supplier supp = factory.make("Google", "Somewhere over the rainbow", "555-5555");
		Order oldOrder = supp.createOrder("VERT");
		try {
			assertThat(supp.getOrder(oldOrder).getDeliverer()).as("check the presence of the initial order").isEqualTo("VERT");
		} catch (OrderNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		supp.deleteOrder(oldOrder);
		assertThat(supp.getOrderHistory()).as("check that the history list is empty").isEmpty();
	}
}
