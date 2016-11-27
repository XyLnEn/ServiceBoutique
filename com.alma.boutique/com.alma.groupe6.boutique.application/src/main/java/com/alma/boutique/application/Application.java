package com.alma.boutique.application;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.application.controllers.*;
import com.alma.boutique.application.injection.Injector;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.history.Account;
import com.alma.boutique.domain.history.History;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import com.alma.boutique.infrastructure.database.MongoDBStore;
import com.alma.boutique.infrastructure.repositories.ThirdPartyRepository;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

/**
 * Principal class to launch the application layer
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class.getName());

    private Application() {
    	
    }
    /**
     *
     * @param origin
     * @param methods
     * @param headers
     */
    private static void enableCORS(final String origin, final String methods, final String headers) {
        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }

    /**
     *
     */
    private static void enableJSON() {
        after((req, res) -> res.type("application/json"));
    }
    
    public static void main(String[] args) {
        if(args.length  == 0) {
            logger.error("The application must be run with the path to the MongoDB config file as first argument");
            System.exit(1);
        }
        enableCORS("*", "*", "*");
        enableJSON();

        List<ShopController> controllers = new ArrayList<>();
        Shop shop = new Shop();
        History shopHistory = new History(new Account(new ThirdParty()));
        shop.setShopHistory(shopHistory);

        MongoDBStore.setConfigFile(args[0]);
        IRepository<ThirdParty> clientRepo = null;
        try {
	        clientRepo = new ThirdPartyRepository(MongoDBStore.getInstance());
	        shop.getShopHistory().getAccount().setOwner(clientRepo.read(-1114086729));
        } catch (IOException e) {
            logger.error(e);
        }

        // create and register the REST controllers
        ShopController catalogController = new CatalogController(shop);
        controllers.add(catalogController); //stock management

        ShopController supplierCatalogController = new SupplierCatalogController(shop);
        controllers.add(supplierCatalogController); //supplier stock management

        TransactionController transCont = new TransactionController(shop);
        controllers.add(transCont); //transaction management

        PersonController persCont = new PersonController(shop);
        controllers.add(persCont); //client management
        
        OrderController ordCont = new OrderController(shop);
        controllers.add(ordCont); //order management

        // inject dependencies in controllers
        controllers.forEach(Injector::injectAttributes);

        // initialize all registered controllers
        controllers.forEach(ShopController::init);

        // launch the server
        init();
    }
}
