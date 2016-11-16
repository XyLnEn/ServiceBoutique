package com.alma.boutique.domain.thirdperson;

import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.mocks.factories.OrderSuppliedProductMockFactory;
import com.alma.boutique.domain.mocks.factories.SupplierMockFactory;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static org.assertj.core.api.Assertions.*;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import java.io.IOException;

public class SupplierTest {

	@Test
	public void testCreateOrder() throws IOException, OrderNotFoundException {
		SupplierMockFactory factorySupplier = new SupplierMockFactory("Google", "Somewhere over the rainbow", "555-5555");
		Supplier supp = factorySupplier.create();
		assertThat(supp.getOrderHistory()).as("Check if the Supplier is created with an empty order list").isEmpty();
		
		OrderSuppliedProductMockFactory factoOrdSup = new OrderSuppliedProductMockFactory("USP");
		Order ord = supp.createOrder(factoOrdSup);
		assertThat(supp.getOrder(ord)).as("check if the order was created correctly").isEqualTo(ord);
		
	}

	@Test
	public void testGetOrder() throws IOException, OrderNotFoundException {
		SupplierMockFactory factorySupplier = new SupplierMockFactory("Google", "Somewhere over the rainbow", "555-5555");
		Supplier supp = factorySupplier.create();
		assertThat(supp.getOrderHistory()).as("Check if the Supplier is created with an empty order list").isEmpty();
		
		OrderSuppliedProductMockFactory factoOrdSup = new OrderSuppliedProductMockFactory("USP");
		Order ord = supp.createOrder(factoOrdSup);
		assertThat(supp.getOrder(ord)).as("Check if %s has the order", supp.getSupplierName()).isEqualTo(ord);

		factoOrdSup = new OrderSuppliedProductMockFactory("jesus");
		Order notExistingOrd = factoOrdSup.create();
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> supp.getOrder(notExistingOrd))
			.as("check if %s %s can react when he is asked to provide an order he doesn't have", supp.getSupplierName());
	}

	@Test
	public void testUpdateOrder() throws OrderNotFoundException, IOException {
		SupplierMockFactory factorySupplier = new SupplierMockFactory("Google", "Somewhere over the rainbow", "555-5555");
		Supplier supp = factorySupplier.create();
		OrderSuppliedProductMockFactory factoOrdSup = new OrderSuppliedProductMockFactory("VERT");
		Order oldOrder = supp.createOrder(factoOrdSup);
		assertThat(supp.getOrder(oldOrder).getDeliverer()).as("check that the initial order has the right deliverer").isEqualTo("VERT");

		factoOrdSup = new OrderSuppliedProductMockFactory("JAUNE");
		Order newOrd = supp.createOrder(factoOrdSup);
		supp.updateOrder(oldOrder, newOrd);
		assertThat(supp.getOrder(oldOrder).getDeliverer()).as("check that the initial order has the right deliverer").isEqualTo("JAUNE");
		
		factoOrdSup = new OrderSuppliedProductMockFactory("jesus");
		Order notExistingOrd = factoOrdSup.create();
		factoOrdSup = new OrderSuppliedProductMockFactory("CHRIST");
		Order notLaicOrd = factoOrdSup.create();
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> supp.updateOrder(notExistingOrd, notLaicOrd))
			.as("check if %s %s can react when he is asked to provide an order he doesn't have", supp.getSupplierName());
		
	}

	@Test
	public void testDeleteOrder() throws IOException, OrderNotFoundException {
		SupplierMockFactory factorySupplier = new SupplierMockFactory("Google", "Somewhere over the rainbow", "555-5555");
		Supplier supp = factorySupplier.create();
		OrderSuppliedProductMockFactory factoOrdSup = new OrderSuppliedProductMockFactory("VERT");
		Order oldOrder = supp.createOrder(factoOrdSup);
		assertThat(supp.getOrder(oldOrder).getDeliverer()).as("check the presence of the initial order").isEqualTo("VERT");
		supp.deleteOrder(oldOrder);
		assertThat(supp.getOrderHistory()).as("check that the history list is empty").isEmpty();
	}

	@Test
	public void testPojoStandard() {
		assertPojoMethodsFor(Supplier.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
	}
}
