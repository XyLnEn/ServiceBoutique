package com.alma.boutique.application;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.application.controllers.CatalogController;
import com.alma.boutique.application.controllers.ShopController;
import com.alma.boutique.application.controllers.TransactionController;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.history.Account;
import com.alma.boutique.domain.history.History;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.Category;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.product.SoldProduct;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderSoldProduct;
import com.alma.boutique.domain.thirdperson.OrderStatus;
import com.alma.boutique.domain.thirdperson.ShopOwner;
import com.alma.boutique.infrastructure.database.MongoDBStore;
import com.alma.boutique.infrastructure.repositories.ClientRepository;
import com.alma.boutique.infrastructure.repositories.SoldProductRepository;
import com.alma.boutique.infrastructure.repositories.TransactionRepository;
import com.alma.boutique.infrastructure.services.FixerExchangeRates;
import com.alma.boutique.infrastructure.services.FixerExchanger;
import com.alma.boutique.infrastructure.webservice.JSONWebservice;
import com.alma.boutique.infrastructure.webservice.WebService;

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

    public static void affRepoTransaction(IRepository<Transaction> repo, Shop shop) {
    	System.out.println("avant : " + repo.browse().toString());
    	
      Client c = new Client("bill", "nie", new Identity("aaaa", "888"));
      
      Order r = new OrderSoldProduct(OrderStatus.ORDERED, "bob");
      
      List<Product> list = new ArrayList<>();
      list.add(new SoldProduct("bob", new Price(10, "EUR"), "maybe", new Category("prod")));
      r.setProducts(list);
      
      Transaction t = new Transaction(r,shop.getShopOwner(),c);
      System.out.println(t.toString());
      repo.add(t.getID(), t);
      
      System.out.println("apres : " + repo.browse().toString());
    }
    
    public static void affRepoClient(IRepository<Client> repo) {
    	System.out.println("avant : " + repo.browse().toString());
      Client c = new Client("bill", "nie", new Identity("aaaa", "888"));
      repo.add(c.getID(), c);
      System.out.println("apres : " + repo.browse().toString());
    }
    
    public static void main(String[] args) {
        if(args.length  == 0) {
            logger.error("The application must be run with the path to the MongoDB config file as first argument");
            System.exit(1);
        }

        // use a filter to convert all requests into JSON
        after((req, res) -> res.type("application/json"));

        List<ShopController> controllers = new ArrayList<>();
        Shop shop = new Shop();
        shop.setShopOwner(new ShopOwner("Remi", new Identity("here", "123456")));
        History shopHistory = new History(new Account(shop.getShopOwner()));
        shop.setShopHistory(shopHistory);
        try {
            MongoDBStore.setConfigFile(args[0]);
            IRepository<SoldProduct> soldProductRepo = new SoldProductRepository(MongoDBStore.getInstance());
            SoldProduct p = new SoldProduct("test", new Price(100, "EUR"), "ok", new Category("product"));
            
            soldProductRepo.add(p.getID(), p);
            
            // create and register the REST controllers
            ShopController catalogController = new CatalogController(shop, soldProductRepo);
            controllers.add(catalogController);
            
            IRepository<Transaction> transactionRepo = new TransactionRepository(MongoDBStore.getInstance());
            Application.affRepoTransaction(transactionRepo, shop);
            
            
            WebService<FixerExchangeRates> webService = new JSONWebservice<>("http://api.fixer.io", FixerExchangeRates.class);
            FixerExchanger fix = new FixerExchanger("/2016-11-16", webService);
            
            TransactionController ordCont = new TransactionController(shop, transactionRepo, soldProductRepo,fix);
            controllers.add(ordCont);
            // initialize all registered controllers
            controllers.forEach(ShopController::init);

            // launch the server
            init();
        } catch (IOException e) {
            logger.error(e);
        }

    }
}
