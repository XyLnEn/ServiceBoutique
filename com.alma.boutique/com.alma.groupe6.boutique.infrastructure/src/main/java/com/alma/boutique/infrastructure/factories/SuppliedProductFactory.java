package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.Product;
import com.alma.boutique.infrastructure.webservice.URLFormatter;
import com.alma.boutique.infrastructure.webservice.WebService;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class SuppliedProductFactory implements IFactory<Product> {
    private int id;
    private String getProductURL;
    private WebService<Product> webService;

    public SuppliedProductFactory(int id, String getProductURL, WebService<Product> webService) {
        this.id = id;
        this.getProductURL = getProductURL;
        this.webService = webService;
    }

    @Override
    public Product create() throws IOException {
        String[] parameters = { String.valueOf(id) };
        return webService.read(URLFormatter.appendParameters(getProductURL, parameters));
    }
}