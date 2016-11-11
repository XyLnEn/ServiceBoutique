package com.alma.boutique.domain.product;

import com.alma.boutique.domain.exceptions.NotEnoughStockException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Thomas Minier
 */
public class Stock {

    /**
     * Classe utilitaire représentnat une paire d'éléments de type quelconque
     * @param <L> Le type de l'élément de gauche de la paire
     * @param <R> Le type de l'élément de droite de la paire
     */
    private class Pair<L, R> {
        private L leftElement;
        private R rightElement;

        public Pair(L leftElement, R rightElement) {
            this.leftElement = leftElement;
            this.rightElement = rightElement;
        }

        public L getLeftElement() {
            return leftElement;
        }

        public void setLeftElement(L leftElement) {
            this.leftElement = leftElement;
        }

        public R getRightElement() {
            return rightElement;
        }

        public void setRightElement(R rightElement) {
            this.rightElement = rightElement;
        }
    }

    private Map<Category, Pair<Product, Integer>> products;

    public Stock() {
        this.products = new HashMap<>();
    }

    public SoldProduct sell(Category category) throws NotEnoughStockException {
        if(!products.containsKey(category) || products.get(category).getRightElement() <= 0) {
            throw new NotEnoughStockException(String.format("There is no more products of category %s in stock", category.getName()));
        }
        Pair<Product, Integer> pair = products.get(category);
        int oldValue = pair.getRightElement();
        pair.setRightElement(oldValue - 1);
        return new SoldProduct(pair.getLeftElement());
    }

    public void supply(Product provision) {
        Category provisionCategory = provision.getCategory();
        // when the product is already in stock, increase it's quantity by one
        if(products.containsKey(provisionCategory)) {
            Pair<Product, Integer> pair = products.get(provisionCategory);
            int oldValue = pair.getRightElement();
            pair.setRightElement(oldValue + 1);
        } else {
            // add a new product to the stock
            Pair<Product, Integer> newPair = new Pair<>(provision, 1);
            products.put(provisionCategory, newPair);
        }
    }
}
