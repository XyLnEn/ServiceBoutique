package com.alma.boutique.application.controllers;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.product.SoldProduct;

import static spark.Spark.*;

/**
 * @author Thomas Minier
 */
public class CatalogController extends ShopController {
    private IRepository<SoldProduct> soldProducts;

    public CatalogController(Shop shop, IRepository<SoldProduct> soldProducts) {
        super(shop);
        this.soldProducts = soldProducts;
    }

    @Override
    public void init() {
        // route used to see all the products
        get("/products/all", (req, resp) -> soldProducts.browse(), this::toJson);
    }
}
