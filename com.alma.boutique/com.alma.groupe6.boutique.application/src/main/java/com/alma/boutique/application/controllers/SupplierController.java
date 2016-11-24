package com.alma.boutique.application.controllers;

import static spark.Spark.get;


import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.thirdperson.Supplier;

public class SupplierController extends ShopController {

	private IRepository<Supplier> suppliers;
	
	public SupplierController(Shop shop, IRepository<Supplier> suppliers) {
		super(shop);
		this.suppliers = suppliers;
	}

	@Override
	public void init() {

    get("/supplier/all", (req, resp) -> suppliers.browse(), this::toJson);
    
    get("/supplier/:id", (req, resp) -> suppliers.read(Integer.parseInt(req.params(":id"))), this::toJson);

	}

}
