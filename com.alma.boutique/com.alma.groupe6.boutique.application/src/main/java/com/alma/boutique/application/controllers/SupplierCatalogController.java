package com.alma.boutique.application.controllers;

import com.alma.boutique.application.injection.InjectDependency;
import com.alma.boutique.application.injection.RepositoryContainer;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.infrastructure.services.ProviderCatalog;

import static spark.Spark.get;

/**
 * @author Thomas Minier
 */
public class SupplierCatalogController extends ShopController {
    @InjectDependency(
            name = "ProviderCatalog",
            containerClass = RepositoryContainer.class
    )
    private ProviderCatalog suppliedProducts;

    public SupplierCatalogController(Shop shop) {
        super(shop);
    }

    @Override
    public void init() {
        // route used to see all the products from the supplier
      get("/supplier/catalog/all", (req, resp) -> suppliedProducts.browse(), this::toJson);
      
      //get("/supplier/catalog/:id", (req, resp) -> suppliedProducts.read(Integer.parseInt(req.params(":id"))), this::toJson);
    }
}
