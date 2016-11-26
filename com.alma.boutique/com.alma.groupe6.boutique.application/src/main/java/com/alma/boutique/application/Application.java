package com.alma.boutique.application;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.application.controllers.CatalogController;
import com.alma.boutique.application.controllers.PersonController;
import com.alma.boutique.application.controllers.ShopController;
import com.alma.boutique.application.controllers.SupplierCatalogController;
import com.alma.boutique.application.controllers.TransactionController;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.history.Account;
import com.alma.boutique.domain.history.History;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.Category;
import com.alma.boutique.domain.product.Price;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.Identity;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.OrderStatus;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import com.alma.boutique.infrastructure.database.MongoDBStore;
import com.alma.boutique.infrastructure.repositories.OrderRepository;
import com.alma.boutique.infrastructure.repositories.ProductRepository;
import com.alma.boutique.infrastructure.repositories.ThirdPartyRepository;
import com.alma.boutique.infrastructure.repositories.TransactionRepository;
import com.alma.boutique.infrastructure.services.FixerExchangeRates;
import com.alma.boutique.infrastructure.services.FixerExchanger;
import com.alma.boutique.infrastructure.services.ProviderCatalog;
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
    
    private static String pathToSupplier = null;
    
    
    public static void populateClients(IRepository<ThirdParty> clientRepo) {
			ThirdParty part = new ThirdParty("Beta", new Identity("maga", "111112"), false);
			clientRepo.add(part.getID(), part);
    }
    
    public static void populateProducts(IRepository<Product> productRepo) {
			Product p = new Product("prod", new Price(10, "EUR"), "a test product", new Category("test"));
			productRepo.add(p.getID(), p);
    }
    
    public static void populateDB(IRepository<Product> productRepo, IRepository<ThirdParty> clientRepo, 
    		IRepository<Transaction> transactionRepo, IRepository<Order> orderHistory) {
//    	populateClients(clientRepo);
    	populateProducts(productRepo);
			
    }
    
    
    
    
    
    public static void affRepoTransaction(IRepository<Transaction> repo, Shop shop) {
    	System.out.println("avant : " + repo.browse().toString());
    	
      ThirdParty c = new ThirdParty("bill", new Identity("aaaa", "888"), false);
      
      Order r = new Order(OrderStatus.ORDERED, "bob");
      
      List<Product> list = new ArrayList<>();
      list.add(new Product("bob", new Price(10, "EUR"), "maybe", new Category("prod")));
      r.setProducts(list);
      
      Transaction t = new Transaction(r.getID() ,shop.getShopOwner().getID() ,c.getID());
      System.out.println(t.toString());
      repo.add(t.getID(), t);
      
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
        shop.setShopOwner(new ThirdParty());
        History shopHistory = new History(new Account(shop.getShopOwner()));
        shop.setShopHistory(shopHistory);
        

        MongoDBStore.setConfigFile(args[0]);
        IRepository<Product> productRepo = null;
        IRepository<ThirdParty> clientRepo = null;
        IRepository<Transaction> transactionRepo = null;
        IRepository<Order> orderHistory = null;
				try {
					productRepo = new ProductRepository(MongoDBStore.getInstance());
					
	        clientRepo = new ThirdPartyRepository(MongoDBStore.getInstance());
	        shop.setShopOwner(clientRepo.read(-1114086729));
	        
	        transactionRepo = new TransactionRepository(MongoDBStore.getInstance());
	        orderHistory = new OrderRepository(MongoDBStore.getInstance());
				} catch (IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
				populateDB(productRepo, clientRepo, transactionRepo, orderHistory);
				
        // create and register the REST controllers
				ShopController catalogController = new CatalogController(shop, productRepo);
				controllers.add(catalogController);//stock management
				
				
				ProviderCatalog suppliedProductRepo = new ProviderCatalog(pathToSupplier, new JSONWebservice<Product>(pathToSupplier, Product.class));//how to browse the current state of the Supplier stock?
				ShopController supplierCatalogController = new SupplierCatalogController(shop, suppliedProductRepo);
				controllers.add(supplierCatalogController);//supplier stock management
				
				
				WebService<FixerExchangeRates> webService = new JSONWebservice<>("http://api.fixer.io", FixerExchangeRates.class);
				FixerExchanger fix = new FixerExchanger("/2016-11-16", webService);
				
				TransactionController ordCont = new TransactionController(shop, transactionRepo, orderHistory, productRepo, suppliedProductRepo, clientRepo, fix);
				controllers.add(ordCont);//transaction management
				
				
				PersonController persCont = new PersonController(shop, clientRepo);
				controllers.add(persCont);//client management
				
				// initialize all registered controllers
				controllers.forEach(ShopController::init);

				// launch the server
				init();
				
    }
}
