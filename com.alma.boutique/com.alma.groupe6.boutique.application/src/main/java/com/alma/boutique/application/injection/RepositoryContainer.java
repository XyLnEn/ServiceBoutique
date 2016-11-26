package com.alma.boutique.application.injection;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.api.services.BrowseSuppliesService;
import com.alma.boutique.api.services.ExchangeRateService;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;
import com.alma.boutique.infrastructure.database.Database;
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

import java.io.IOException;

/**
 * Conteneur d'injection pour les repository liés à la base MongoDB
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class RepositoryContainer implements InjectionContainer {
    private String supplierURL;
    private String catalogURL;
    private WebService<Product> supplierWebService;
    private WebService<FixerExchangeRates> fixerWebService;
    private Database database;

    public RepositoryContainer() throws IOException {
        database = MongoDBStore.getInstance();
        supplierURL = "";
        catalogURL = "";
        supplierWebService = new JSONWebservice<>(supplierURL, Product.class);
        fixerWebService = new JSONWebservice<>("http://api.fixer.io", FixerExchangeRates.class);
    }

    @Override
    public IRepository<Order> getOrderRepository() {
        return new OrderRepository(database);
    }

    @Override
    public IRepository<Product> getProductRepository() {
        return new ProductRepository(database);
    }

    @Override
    public IRepository<ThirdParty> getThirdPartyRepository() {
        return new ThirdPartyRepository(database);
    }

    @Override
    public IRepository<Transaction> getTransactionRepository() {
        return new TransactionRepository(database);
    }

    @Override
    public BrowseSuppliesService<Product> getProviderCatalog() {
        return new ProviderCatalog(catalogURL, supplierWebService);
    }

    @Override
    public ExchangeRateService getExchangeService() {
        return new FixerExchanger("/lastest", fixerWebService);
    }
}
