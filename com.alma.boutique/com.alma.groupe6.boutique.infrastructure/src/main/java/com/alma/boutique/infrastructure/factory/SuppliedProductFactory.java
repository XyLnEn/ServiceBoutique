package com.alma.boutique.infrastructure.factory;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.product.SuppliedProduct;
import com.alma.boutique.infrastructure.webservice.WebService;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class SuppliedProductFactory implements IFactory<SuppliedProduct> {
    private WebService<SuppliedProduct> webService;

    public SuppliedProductFactory(WebService<SuppliedProduct> webService) {
        this.webService = webService;
    }

    @Override
    public SuppliedProduct create() throws IOException {
        return webService.read("api/url.com");
    }
}
