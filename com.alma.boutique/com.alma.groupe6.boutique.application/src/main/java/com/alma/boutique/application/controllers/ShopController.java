package com.alma.boutique.application.controllers;

import com.alma.boutique.domain.Shop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe abstraite représentant un controlleur quelconque offrant une partie des services d'une boutique
 * sous la forme d'une API REST
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public abstract class ShopController {
    protected Shop shop;
    private ObjectMapper mapper;

    /**
     * Constructeur
     * @param shop L'instance de boutique utilisée comme point d'entrée par le controlleur
     */
    public ShopController(Shop shop) {
        this.shop = shop;
        mapper = new ObjectMapper();
    }

    /**
     * Méthode initialisant le controlleur
     */
    public abstract void init();

    /**
     * Méthode utilitaire renvoyant un objet sous forme de string JSON
     * @param entity L'objet à serializer en JSON
     * @return L'objet sous forme d'une string JSON
     */
    protected String toJson(Object entity) throws JsonProcessingException {
        return mapper.writeValueAsString(entity);
    }
}
