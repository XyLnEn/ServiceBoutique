package com.alma.boutique.infrastructure.services;

import com.alma.boutique.api.services.BrowseSuppliesService;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.domain.shared.Entity;
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
public class ProviderCatalog implements BrowseSuppliesService<Product> {
    private String browseURL;
    private WebService<Product> webService;

    /**
     * Constructor
     * @param browseURL the URL where the supplier catalog is accessible
     * @param webService the webservice used to consume the supplier's service
     */
    public ProviderCatalog(String browseURL, WebService<Product> webService) {
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
            List<Product> products = webService.browse(browseURL);
            catalog.addAll(products);
        } catch (IOException e) {
        	LoggerFactory.getLogger(Entity.class).warn(e.getMessage(),e);
        }
        return catalog;
    }
}
