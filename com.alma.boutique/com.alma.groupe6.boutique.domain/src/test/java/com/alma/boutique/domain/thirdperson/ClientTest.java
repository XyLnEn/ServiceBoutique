package com.alma.boutique.domain.thirdperson;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Test;

import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.exceptions.ProductNotFoundException;
import com.alma.boutique.domain.product.Product;

public class ClientTest {

	@Test
	public void testCreateOrder() {
		
	}

	@Test
	public void testGetOrder() {
		Client cli = new Client("bob", "lemon", new Identity("Somewhere over the rainbow", "555-5555"));
		ArrayList<Product> products = new ArrayList<>();
		Order ord = cli.createOrder(products, OrderStatus.ORDERED, "USP");
		try {
			assertThat(cli.getOrder(ord)).as("Check if %s %s has the order", cli.getFirstName(), cli.getLastName()).isEqualTo(ord);
		} catch (OrderNotFoundException e) {
			fail(e.getMessage());
		}
		Order notExistingOrd = new Order(OrderStatus.ORDERED, "jesus");
		assertThatExceptionOfType(OrderNotFoundException.class).isThrownBy(() -> cli.getOrder(notExistingOrd))
			.as("check if %s %s can react when he is asked to provide an order he doesn't have", cli.getFirstName(), cli.getLastName());
	}

	@Test
	public void testUpdateOrder() {
		
	}

	@Test
	public void testDeleteOrder() {
		
	}

}
