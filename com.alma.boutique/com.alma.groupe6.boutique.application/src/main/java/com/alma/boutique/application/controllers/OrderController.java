package com.alma.boutique.application.controllers;

import static spark.Spark.get;


import com.alma.boutique.api.IRepository;
import com.alma.boutique.application.injection.InjectDependency;
import com.alma.boutique.application.injection.RepositoryContainer;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.thirdperson.Order;

public class OrderController extends ShopController {

	@InjectDependency(
      name = "OrderRepository",
      containerClass = RepositoryContainer.class
	)
	private IRepository<Order> orderList;
	
	public OrderController(Shop shop) {
		super(shop);
	}
	
	@Override
	public void init() {
			// route used to see all the order
    get("/order/all", (req, resp) -> orderList.browse(), this::toJson);
			// route used to see an order in particular
    get("/order/:id", (req, resp) -> orderList.read(Integer.parseInt(req.params(":id"))), this::toJson);

	}

}
