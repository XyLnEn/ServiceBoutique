package com.alma.boutique.infrastructure.services;

import com.alma.boutique.api.services.BrowseSuppliesService;
import com.alma.boutique.domain.product.SuppliedProduct;
import com.alma.boutique.domain.shared.Entity;
import com.alma.boutique.infrastructure.webservice.WebService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

/**
 * Classe implémentant le service permettant de consulter le catalogue d'un fournisseur
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class ProviderCatalog implements BrowseSuppliesService<SuppliedProduct> {
    private String browseURL;
    private WebService<SuppliedProduct> webService;

    /**
     * Constructeur
     * @param browseURL L'url du fournisseur pour accéder à son catalogue
     * @param webService Le web service utilisé pour consommer les services du fournisseur
     */
    public ProviderCatalog(String browseURL, WebService<SuppliedProduct> webService) {
        this.browseURL = browseURL;
        this.webService = webService;
    }

    /**
     * Méthode permettant de récupérer le catalgue du fournisseur
     * @return La liste des objets fournis par le fournisseur
     */
    @Override
    public List<SuppliedProduct> browse() {
        List<SuppliedProduct> catalog = new ArrayList<>();
        try {
            List<SuppliedProduct> products = webService.browse(browseURL);
            catalog.addAll(products);
        } catch (IOException e) {
        	LoggerFactory.getLogger(Entity.class).warn(e.getMessage(),e);
        }
        return catalog;
    }
}
