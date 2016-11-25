package com.alma.boutique.infrastructure.services;

import com.alma.boutique.api.services.BrowseSuppliesService;
import com.alma.boutique.domain.product.Product;
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
public class ProviderCatalog implements BrowseSuppliesService<Product> {
    private String browseURL;
    private WebService<Product> webService;

    /**
     * Constructeur
     * @param browseURL L'url du fournisseur pour accéder à son catalogue
     * @param webService Le web service utilisé pour consommer les services du fournisseur
     */
    public ProviderCatalog(String browseURL, WebService<Product> webService) {
        this.browseURL = browseURL;
        this.webService = webService;
    }

    /**
     * Méthode permettant de récupérer le catalgue du fournisseur
     * @return La liste des objets fournis par le fournisseur
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
