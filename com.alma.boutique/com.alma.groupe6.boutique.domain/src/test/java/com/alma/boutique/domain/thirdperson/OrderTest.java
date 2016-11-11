package com.alma.boutique.domain.thirdperson;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.alma.boutique.domain.product.Category;
import org.junit.Test;

import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.product.SoldProduct;

public class OrderTest {

	@Test
	public void testUpdateOrder() {
		Order ord = new Order(OrderStatus.ORDERED, "USP");
		assertThat(ord.getDeliverer()).as("check the initial deliverer").isEqualTo("USP");

		Order newDeliverer = new Order(OrderStatus.ORDERED, "DPS");
		ord.updateOrder(newDeliverer);
		assertThat(ord.getDeliverer()).as("check the update of the deliverer").isEqualTo("DPS");
		
		Order newStatus = new Order(OrderStatus.TRAVELING, "DPS");
		ord.updateOrder(newStatus);
		assertThat(ord.getOrderStatus()).as("check the update of the state of the order").isEqualTo(OrderStatus.TRAVELING);
		
		List<Product> list2 = new ArrayList<Product>();
		Category category = new Category("category");
		Product prod = new SoldProduct("DAB", 5, "On 'em", category);
		list2.add(prod);
		ord.createProduct();//not functioning
		//assertThat(ord.getProducts()).as("check the update on the Product list").isEqualTo(list2);
		
	}

	@Test
	public void testgetTotalPrice() {
		List<Product> list = new ArrayList<Product>();
		Order ord = new Order(OrderStatus.ORDERED, "USP");
		assertThat(ord.getTotalPrice()).as("test with the initial price").isEqualTo(0);
		
		List<Product> list2 = new ArrayList<Product>();
		Category category = new Category("category");
		list2.add(new SoldProduct("DAB", 5, "On 'em", category));
		Order newList = new Order(OrderStatus.TRAVELING, "DPS");
//		assertThat(newList.getTotalPrice()).as("test with a non-empty Product list").isEqualTo(5);
		
	}
}
