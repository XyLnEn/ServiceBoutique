package com.alma.boutique.application.controllers;

import static spark.Spark.get;


import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.thirdperson.Client;

public class PersonController extends ShopController {

	private IRepository<Client> clients;
	
	public PersonController(Shop shop, IRepository<Client> clients) {
		super(shop);
		this.clients = clients;
	}

	@Override
	public void init() {

    get("/person/all", (req, resp) -> clients.browse(), this::toJson);
    
    get("/person/:id", (req, resp) -> clients.read(Integer.parseInt(req.params(":id"))), this::toJson);

	}

}
