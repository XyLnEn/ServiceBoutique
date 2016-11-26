package com.alma.boutique.application.controllers;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.application.injection.InjectDependency;
import com.alma.boutique.application.injection.RepositoryContainer;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.thirdperson.ThirdParty;

import static spark.Spark.get;

public class PersonController extends ShopController {
	@InjectDependency(
			name = "ThirdPartyRepository",
			containerClass = RepositoryContainer.class
	)
	private IRepository<ThirdParty> persons;
	
	public PersonController(Shop shop) {
		super(shop);
	}

	@Override
	public void init() {
			// route used to see all the persons
    get("/person/all", (req, resp) -> persons.browse(), this::toJson);
			// route used to see a person in particular
    get("/person/:id", (req, resp) -> persons.read(Integer.parseInt(req.params(":id"))), this::toJson);

	}

}
