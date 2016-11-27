package com.alma.boutique.application.controllers;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.application.injection.InjectDependency;
import com.alma.boutique.application.injection.RepositoryContainer;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.product.Category;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.infrastructure.services.FixerExchanger;

import static spark.Spark.get;

/**
 * A controller that expose the products of the shop
 * @author Thomas Minier
 * @author Lenny Lucas
 */
public class CatalogController extends ShopController {
    @InjectDependency(
            name = "ProductRepository",
            containerClass = RepositoryContainer.class
    )
    private IRepository<Product> soldProducts;
    
    @InjectDependency(
        name = "ExchangeService",
        containerClass = RepositoryContainer.class
		)
		private FixerExchanger fixer;

    public CatalogController(Shop shop) {
        super(shop);
    }
    
    public void populate() {
    	Product p = new Product("test", new Price(1, "EUR"), "a test product", new Category("test"));
    	soldProducts.add(p.getId(), p);
    	p = new Product("test", new Price(2, "EUR"), "a test product", new Category("test"));
    	soldProducts.add(p.getId(), p);
    }

    @Override
    public void init() {
      // route used to see all the products
    get("/products/all", (req, resp) -> soldProducts.browse(), this::toJson);
    // route used to see all the products
    get("/products/all/:id", (req, resp) -> shop.browseStock(soldProducts, req.params(":id"), fixer), this::toJson);
      	// route used to see a product in particular
      get("/products/:id", (req, resp) -> soldProducts.read(Integer.parseInt(req.params(":id"))), this::toJson);
    }
}
