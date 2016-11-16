package com.alma.boutique.infrastructure.services;

import com.alma.boutique.api.services.BrowseSuppliesService;
import com.alma.boutique.domain.product.SuppliedProduct;
import com.alma.boutique.infrastructure.webservice.WebService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Minier
 */
public class ProviderCatalog implements BrowseSuppliesService<SuppliedProduct> {
    private String browseURL;
    private WebService<SuppliedProduct> webService;

    public ProviderCatalog(String browseURL, WebService<SuppliedProduct> webService) {
        this.browseURL = browseURL;
        this.webService = webService;
    }

    @Override
    public List<SuppliedProduct> browse() {
        List<SuppliedProduct> catalog = new ArrayList<>();
        try {
            List<SuppliedProduct> products = webService.browse(browseURL);
            catalog.addAll(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return catalog;
    }
}
