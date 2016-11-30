package com.alma.boutique.infrastructure.services;

import com.alma.boutique.api.services.BrowseSuppliesService;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.infrastructure.conversion.ThaboProduct;
import com.alma.boutique.infrastructure.webservice.WebService;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing the service to consult the catalog of a supplier
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class ProviderCatalogThabo implements BrowseSuppliesService<Product> {
    private String browseURL;
    private WebService<ThaboProduct> webService;

    /**
     * Constructor
     * @param browseURL the URL where the supplier catalog is accessible
     * @param webService the webservice used to consume the supplier's service
     */
    public ProviderCatalogThabo(String browseURL, WebService<ThaboProduct> webService) {
        this.browseURL = browseURL;
        this.webService = webService;
    }

    /**
     * Method used to get the entire catalog of a supplier
     * @return the list of all the products that the supplier is selling
     */
    @Override
    public List<Product> browse() {
        List<Product> catalog = new ArrayList<>();
        try {
            List<ThaboProduct> products = webService.browse(browseURL);
            for (ThaboProduct thaboProduct : products) {
              catalog.add(thaboProduct.translate());
						}
        } catch (IOException e) {
        	LoggerFactory.getLogger(Entity.class).warn(e.getMessage(),e);
        }
        return catalog;
    }
}
