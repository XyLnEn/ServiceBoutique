package com.alma.boutique.application.controllers;

import static spark.Spark.get;


import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class PersonController extends ShopController {

	private IRepository<ThirdParty> persons;
	
	public PersonController(Shop shop, IRepository<ThirdParty> persons) {
		super(shop);
		this.persons = persons;
	}

	@Override
	public void init() {

    get("/person/all", (req, resp) -> persons.browse(), this::toJson);
    
    get("/person/:id", (req, resp) -> persons.read(Integer.parseInt(req.params(":id"))), this::toJson);

	}

}
