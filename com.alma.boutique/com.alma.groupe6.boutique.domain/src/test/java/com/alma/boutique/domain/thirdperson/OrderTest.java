package com.alma.boutique.domain.thirdperson;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.product.SoldProduct;

public class OrderTest {

	@Test
	public void testUpdateOrder() {
		List<Product> list = new ArrayList<Product>();
		Order ord = new Order(list, OrderStatus.ORDERED, "USP");
		assertThat(ord.getDeliverer()).as("check the initial deliverer").isEqualTo("USP");
		

		Order newDeliverer = new Order(list, OrderStatus.ORDERED, "DPS");
		ord.updateOrder(newDeliverer);
		assertThat(ord.getDeliverer()).as("check the update of the deliverer").isEqualTo("DPS");
		
		Order newStatus = new Order(list, OrderStatus.TRAVELING, "DPS");
		ord.updateOrder(newStatus);
		assertThat(ord.getOrderStatus()).as("check the update of the state of the order").isEqualTo(OrderStatus.TRAVELING);
		
		List<Product> list2 = new ArrayList<Product>();
		list2.add(new SoldProduct("DAB", 5, "On 'em", 500));
		Order newList = new Order(list2, OrderStatus.TRAVELING, "DPS");
		ord.updateOrder(newList);
		assertThat(ord.getProducts()).as("check the update on the Product list").isEqualTo(list2);
		
	}

	@Test
	public void testgetTotalPrice() {
		List<Product> list = new ArrayList<Product>();
		Order ord = new Order(list, OrderStatus.ORDERED, "USP");
		assertThat(ord.getTotalPrice()).as("test with the initial price").isEqualTo(0);
		
		List<Product> list2 = new ArrayList<Product>();
		list2.add(new SoldProduct("DAB", 5, "On 'em", 500));
		Order newList = new Order(list2, OrderStatus.TRAVELING, "DPS");
		assertThat(newList.getTotalPrice()).as("test with a non-empty Product list").isEqualTo(5);
		
	}
}
