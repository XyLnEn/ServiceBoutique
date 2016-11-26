package com.alma.boutique.application.injection;

import com.alma.boutique.api.IRepository;
import com.alma.boutique.api.services.BrowseSuppliesService;
import com.alma.boutique.api.services.ExchangeRateService;
import com.alma.boutique.domain.history.Transaction;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.thirdperson.Order;
import com.alma.boutique.domain.thirdperson.ThirdParty;

/**
 * Interface representing a container usable to inject dependencies
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public interface InjectionContainer {
    IRepository<Order> getOrderRepository();
    IRepository<Product> getProductRepository();
    IRepository<ThirdParty> getThirdPartyRepository();
    IRepository<Transaction> getTransactionRepository();
    BrowseSuppliesService<Product> getProviderCatalog();
    ExchangeRateService getExchangeService();
}
