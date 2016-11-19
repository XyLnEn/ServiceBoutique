package com.alma.boutique.application;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.application.controllers.CatalogController;
import com.alma.boutique.application.controllers.ShopController;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.product.SoldProduct;
import com.alma.boutique.infrastructure.database.MongoDBStore;
import com.alma.boutique.infrastructure.repositories.SoldProductRepository;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.after;
import static spark.Spark.init;

/**
 * Classe principale pour lancer le layer application
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        if(args.length  == 0) {
            logger.error("The application must be run with the path to the MongoDB config file as first argument");
            System.exit(1);
        }

        // use a filter to convert all requests into JSON
        after((req, res) -> res.type("application/json"));

        List<ShopController> controllers = new ArrayList<>();
        Shop shop = new Shop();
        try {
            MongoDBStore.setConfigFile(args[0]);
            IRepository<SoldProduct> soldProductRepo = new SoldProductRepository(MongoDBStore.getInstance());

            // create and register the REST controllers
            ShopController catalogController = new CatalogController(shop, soldProductRepo);
            controllers.add(catalogController);

            // initialize all registered controllers
            controllers.forEach(ShopController::init);

            // launch the server
            init();
        } catch (IOException e) {
            logger.error(e);
        }

    }
}
