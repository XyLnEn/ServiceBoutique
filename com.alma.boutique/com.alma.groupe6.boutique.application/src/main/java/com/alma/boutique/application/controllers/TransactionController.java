package com.alma.boutique.application.controllers;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
import com.alma.boutique.api.services.CreditCardValidation;
import com.alma.boutique.application.data.Purchase;
import com.alma.boutique.application.injection.InjectDependency;
import com.alma.boutique.application.injection.RepositoryContainer;
import com.alma.boutique.domain.Shop;
import com.alma.boutique.domain.exceptions.IllegalDiscountException;
import com.alma.boutique.domain.exceptions.OrderNotFoundException;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import com.alma.boutique.infrastructure.factories.OrderFactory;
import com.alma.boutique.infrastructure.factories.ProductFactory;
import com.alma.boutique.infrastructure.factories.TransactionFactory;
import com.alma.boutique.infrastructure.services.FixerExchanger;
import com.alma.boutique.infrastructure.services.ProviderCatalog;
import com.alma.boutique.infrastructure.services.ProviderCatalogThabo;

import spark.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * A controller that expose every transactions made at the shop. It also expose the methods to buy products and resupply
 * @author Thomas Minier
 * @author Lenny Lucas
 */
public class TransactionController extends ShopController {

	@InjectDependency(
			name = "TransactionRepository",
			containerClass = RepositoryContainer.class
	)
	private IRepository<Transaction> transactions;

    @InjectDependency(
            name = "OrderRepository",
            containerClass = RepositoryContainer.class
    )
	private IRepository<Order> orderHistory;

    @InjectDependency(
            name = "ProductRepository",
            containerClass = RepositoryContainer.class
    )
	private IRepository<Product> stock;

    @InjectDependency(
            name = "ThirdPartyRepository",
            containerClass = RepositoryContainer.class
    )
    private IRepository<ThirdParty> persons;

    @InjectDependency(
            name = "ProviderCatalog",
            containerClass = RepositoryContainer.class
    )
	private ProviderCatalogThabo supply;

    @InjectDependency(
            name = "ExchangeService",
            containerClass = RepositoryContainer.class
    )
	private FixerExchanger fixer;

    @InjectDependency(
            name = "ValidationService",
            containerClass = RepositoryContainer.class
    )
	private CreditCardValidation cbValidator;
	
	public TransactionController(Shop shop) {
		super(shop);
	}

  private Purchase getResults(Request req) throws IOException {
  	return mapper.readValue(req.body(), Purchase.class);
  }

  /**
    * method that inject the repositories from the infra into the domain to buy a set of products
    * the request should have the following fields :
    * "deliverer" : the name of the deliverer,
    * "devise" : the devise name,
    * "idList" : [the list of all the ids of the products to buy]
    * "personId" : the Id of the ThirdParty that is buying the products
    * "cardNumber : the number of the credit card used in the transaction
    *
    * @param req the request
    * @return the completed transaction
    * @throws IOException
    * @throws OrderNotFoundException
    * @throws IllegalDiscountException
   */
	public Transaction buy(Request req) throws IOException, IllegalDiscountException, OrderNotFoundException {
        Transaction newTransaction = null;
		Purchase purchase = this.getResults(req);
        // first of all, validate the credit card number
        if(cbValidator.validate(purchase.getCardNumber())) {
            String deliverer = purchase.getDeliverer();
            String devise = purchase.getDevise();
            IFactory<Order> factOrd = new OrderFactory(deliverer);
            List<Integer> idList = new ArrayList<>(purchase.getIdList());
            ThirdParty client = persons.read(purchase.getPersonId());
            Order ord = shop.buyProduct(this.stock, persons, factOrd , idList, purchase.getPersonId(), devise, fixer);
            orderHistory.add(ord.getId(), ord);

            newTransaction = shop.saveTransaction(shop.getShopHistory(), this.transactions, new TransactionFactory(ord.getId(), shop.getShopHistory().getAccount().getOwner().getId(), client.getId()));
        }
        return newTransaction;
	}
	
	/**
    * method that inject the repositories from the infra into the domain to resupply the stock
    * the request should have the following fields :
    * "deliverer" : the name of the deliverer,
    * "devise" : the devise name,
    * "idList" : [the list of all the ids of the products to buy]
    * "personId" : the Id of the ThirdParty that is supplying the products
    * "cardNumber : the number of the credit card used in the transaction
    *
    * @param req the request
    * @return the completed transaction
	 * @throws IllegalDiscountException
	 * @throws OrderNotFoundException
	 * @throws IOException
	 */
	public Transaction resupply(Request req) throws IOException, IllegalDiscountException, OrderNotFoundException {
        Transaction newTransaction = null;
		Purchase purchase = this.getResults(req);
        // first of all, validate the credit card number
        if(cbValidator.validate(purchase.getCardNumber())) {
            String deliverer = purchase.getDeliverer();
            String devise = purchase.getDevise();
            IFactory<Order> factOrd = new OrderFactory(deliverer);
            List<Integer> idList = new ArrayList<>(purchase.getIdList());
            List<IFactory<Product>> productList = new ArrayList<>();

            for (Integer id : idList) {
                productList.addAll(supply.browse().stream().filter(product -> product.getId() == id).map(product -> new ProductFactory(product.getName(), product.getPrice().getValue(), product.getPrice().getCurrency(), product.getDescription(), product.getCategory().getName())).collect(Collectors.toList()));
            }
            Order ord = shop.restock(this.stock, persons, productList, factOrd, purchase.getPersonId(), devise, fixer);

            ThirdParty supplier = persons.read(purchase.getPersonId());
            newTransaction = shop.saveTransaction(shop.getShopHistory(), this.transactions, new TransactionFactory(ord.getId(), shop.getShopHistory().getAccount().getOwner().getId(), supplier.getId()));
        }
        return newTransaction;
	}
	
	@Override
	public void init() {
			// route used to see all the transactions
		get("/transaction/all", (req, resp) -> transactions.browse(), this::toJson);
			// route used to see a transaction in particular
		get("/transaction/:id", (req, resp) -> transactions.read(Integer.parseInt(req.params(":id"))), this::toJson);
			// route used to allow a client to buy products
		post("/transaction/new/sale", (req, resp) -> this.buy(req), this::toJson);
			// route used to buy products from a supplier
		post("/transaction/new/resupply", (req, resp) -> this.resupply(req), this::toJson);
	}

}
