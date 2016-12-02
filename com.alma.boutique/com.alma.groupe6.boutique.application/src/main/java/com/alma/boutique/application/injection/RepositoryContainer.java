package com.alma.boutique.application.injection;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.api.services.BrowseSuppliesService;
import com.alma.boutique.api.services.CreditCardValidation;
import com.alma.boutique.api.services.ExchangeRateService;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import com.alma.boutique.infrastructure.conversion.FluffyProduct;
import com.alma.boutique.infrastructure.database.Database;
import com.alma.boutique.infrastructure.database.MongoDBStore;
import com.alma.boutique.infrastructure.repositories.OrderRepository;
import com.alma.boutique.infrastructure.repositories.ProductRepository;
import com.alma.boutique.infrastructure.repositories.ThirdPartyRepository;
import com.alma.boutique.infrastructure.repositories.TransactionRepository;
import com.alma.boutique.infrastructure.services.CBValidator;
import com.alma.boutique.infrastructure.services.FixerExchangeRates;
import com.alma.boutique.infrastructure.services.FixerExchanger;
import com.alma.boutique.infrastructure.services.FluffyProviderCatalog;
import com.alma.boutique.infrastructure.webservice.JSONWebservice;
import com.alma.boutique.infrastructure.webservice.WebService;

import java.io.IOException;

/**
 * Injection container for the MondoDB base repositories
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class RepositoryContainer implements InjectionContainer {
    private String supplierURL;
    private String catalogURL;
    private WebService<FluffyProduct> supplierWebService;
    private WebService<FixerExchangeRates> fixerWebService;
    private Database database;

    public RepositoryContainer() throws IOException {
        database = MongoDBStore.getInstance();
        supplierURL = "https://fluffy-stock.herokuapp.com/api/product";
        catalogURL = "s";
        supplierWebService = new JSONWebservice<>(supplierURL, FluffyProduct.class);
        fixerWebService = new JSONWebservice<>("http://api.fixer.io", FixerExchangeRates.class);
    }

    /**
     * Method used to inject a dependency to OrderRepository
     * @return An instance of an implementation of a repository of Orders
     */
    @Override
    public IRepository<Order> getOrderRepository() {
        return new OrderRepository(database);
    }

    /**
     * Method used to inject a dependency to ProductRepository
     * @return An instance of an implementation of a repository of Products
     */
    @Override
    public IRepository<Product> getProductRepository() {
        return new ProductRepository(database);
    }

    /**
     * Method used to inject a dependency to ThirdPartyRepository
     * @return An instance of an implementation of a repository of Third Party
     */
    @Override
    public IRepository<ThirdParty> getThirdPartyRepository() {
        return new ThirdPartyRepository(database);
    }

    /**
     * Method used to inject a dependency to TransactionRepository
     * @return An instance of an implementation of a repository of Transactions
     */
    @Override
    public IRepository<Transaction> getTransactionRepository() {
        return new TransactionRepository(database);
    }

    /**
     * Method used to inject a dependency to BrowseSuppliesService
     * @return An instance of an implementation of BrowseSuppliesService
     */
    @Override
    public BrowseSuppliesService<Product> getProviderCatalog() {
        return new FluffyProviderCatalog(catalogURL, supplierWebService);
    }

    /**
     * Method used to inject a dependency to ExchangeRateService
     * @return An instance of an implementation of ExchangeRateService
     */
    @Override
    public ExchangeRateService getExchangeService() {
        return new FixerExchanger("/latest", fixerWebService);
    }

    /**
     * Method used to inject a dependency to CreditCardValidation
     * @return An instance of an implementation of CreditCardValidation
     */
    @Override
    public CreditCardValidation getValidationService() {
        return new CBValidator("http://localhost:8080/validate");
    }
}
