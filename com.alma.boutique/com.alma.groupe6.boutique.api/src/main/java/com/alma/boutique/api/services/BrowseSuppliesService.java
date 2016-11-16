package com.alma.boutique.api.services;

import java.util.List;

/**
 * Service qui gère la consultation des produits d'un fournisseur
 * @author Thomas Minier
 */
@FunctionalInterface
public interface BrowseSuppliesService<T> {
    List<T> browse();
}
