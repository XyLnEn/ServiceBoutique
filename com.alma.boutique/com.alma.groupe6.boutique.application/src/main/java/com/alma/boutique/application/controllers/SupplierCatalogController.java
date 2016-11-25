package com.alma.boutique.application.controllers;

import com.alma.boutique.domain.Shop;
import com.alma.boutique.infrastructure.services.ProviderCatalog;

import static spark.Spark.*;

/**
 * @author Thomas Minier
 */
public class SupplierCatalogController extends ShopController {
    private ProviderCatalog suppliedProducts;

    public SupplierCatalogController(Shop shop, ProviderCatalog suppliedProducts) {
        super(shop);
        this.suppliedProducts = suppliedProducts;
    }

    @Override
    public void init() {
        // route used to see all the products
      get("/supplier/catalog/all", (req, resp) -> suppliedProducts.browse(), this::toJson);
      
      //get("/supplier/catalog/:id", (req, resp) -> suppliedProducts.read(Integer.parseInt(req.params(":id"))), this::toJson);
    }
}
