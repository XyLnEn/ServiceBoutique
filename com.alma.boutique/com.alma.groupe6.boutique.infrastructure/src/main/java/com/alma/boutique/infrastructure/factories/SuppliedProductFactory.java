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
    private int id;
    private String getProductURL;
    private WebService<SuppliedProduct> webService;

    public SuppliedProductFactory(int id, String getProductURL, WebService<SuppliedProduct> webService) {
        this.id = id;
        this.getProductURL = getProductURL;
        this.webService = webService;
    }

    @Override
    public SuppliedProduct create() throws IOException {
        String[] parameters = { String.valueOf(id) };
        return webService.read(URLFormatter.appendParameters(getProductURL, parameters));
    }
}
