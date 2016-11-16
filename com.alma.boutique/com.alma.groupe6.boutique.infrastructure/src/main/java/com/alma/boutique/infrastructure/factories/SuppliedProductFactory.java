package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.SuppliedProduct;
import com.alma.boutique.infrastructure.webservice.URLFormatter;
import com.alma.boutique.infrastructure.webservice.WebService;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class SuppliedProductFactory implements IFactory<SuppliedProduct> {
    private String name;
    private String getProductURL;
    private String browseProductsURL;
    private WebService<SuppliedProduct> webService;

    public SuppliedProductFactory(String name, String getProductURL, String browseProductsURL, WebService<SuppliedProduct> webService) {
        this.name = name;
        this.getProductURL = getProductURL;
        this.browseProductsURL = browseProductsURL;
        this.webService = webService;
    }

    @Override
    public SuppliedProduct create() throws IOException {
        String[] parameters = { name };
        return webService.read(URLFormatter.appendParameters(getProductURL, parameters));
    }
}
