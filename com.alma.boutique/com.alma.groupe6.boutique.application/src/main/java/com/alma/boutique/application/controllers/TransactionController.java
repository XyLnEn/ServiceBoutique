package com.alma.boutique.application.controllers;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.api.IRepository;
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
import spark.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static spark.Spark.get;
import static spark.Spark.post;

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
	private ProviderCatalog supply;

    @InjectDependency(
            name = "ExchangeService",
            containerClass = RepositoryContainer.class
    )
	private FixerExchanger fixer;
	
	public TransactionController(Shop shop) {
		super(shop);
	}

    private Purchase getResults(Request req) throws IOException {
        return mapper.readValue(req.body(), Purchase.class);
    }
	
    public Transaction buy(Request req) throws IOException, IllegalDiscountException, OrderNotFoundException {
        Purchase purchase = this.getResults(req);
        String deliverer = purchase.getDeliverer();
        String devise = purchase.getDevise();
        IFactory<Order> factOrd = new OrderFactory(deliverer);
        List<Integer> idList = new ArrayList<>(purchase.getIdList());
        ThirdParty client = persons.read(purchase.getPersonId());
        Order ord = shop.buyProduct(this.stock, persons, factOrd , idList, purchase.getPersonId(), devise, fixer);

        return shop.saveTransaction(shop.getShopHistory(), this.transactions, new TransactionFactory(ord.getID(), shop.getShopOwner().getID(), client.getID()));
    }
	

	public Transaction resupply(Request req) throws IOException, IllegalDiscountException {
		Purchase purchase = this.getResults(req);
		String deliverer = purchase.getDeliverer();
        String devise = purchase.getDevise();
        IFactory<Order> factOrd = new OrderFactory(deliverer);
        List<Integer> idList = new ArrayList<>(purchase.getIdList());
        List<IFactory<Product>> productList = new ArrayList<>();

        for (Integer id : idList) {
            productList.addAll(supply.browse().stream().filter(product -> product.getID() == id).map(product -> new ProductFactory(product.getName(), product.getPrice().getValue(), product.getPrice().getCurrency(), product.getDescription(), product.getCategory().getName())).collect(Collectors.toList()));
        }
        Order ord = shop.restock(this.stock, productList, factOrd, devise, fixer);

        ThirdParty supplier = persons.read(purchase.getPersonId());
        return shop.saveTransaction(shop.getShopHistory(), this.transactions, new TransactionFactory(ord.getID(), shop.getShopOwner().getID(), supplier.getID()));
	}
	
	@Override
	public void init() {
        get("/transaction/all", (req, resp) -> transactions.browse(), this::toJson);

        get("/transaction/:id", (req, resp) -> transactions.read(Integer.parseInt(req.params(":id"))), this::toJson);

        post("/transaction/new/sale", (req, resp) -> this.buy(req), this::toJson);

        post("/transaction/new/resupply", (req, resp) -> this.resupply(req), this::toJson);

	}

}
