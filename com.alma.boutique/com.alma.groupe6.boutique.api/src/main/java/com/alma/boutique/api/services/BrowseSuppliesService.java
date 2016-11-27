package com.alma.boutique.api.services;

import java.util.List;

/**
 * Service used to display the catalog of a supplier
 * @author Lenny Lucas
 * @author Thomas Minier
 */
@FunctionalInterface
public interface BrowseSuppliesService<T> {
    List<T> browse();
}
