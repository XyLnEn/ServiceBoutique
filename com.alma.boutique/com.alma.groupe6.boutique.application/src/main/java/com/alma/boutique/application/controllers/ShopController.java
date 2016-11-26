package com.alma.boutique.application.controllers;

import com.alma.boutique.domain.Shop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract class that represent a controller exposing one part of the services of the shop
 * as a REST API
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public abstract class ShopController {
    protected Shop shop;
    protected ObjectMapper mapper;

    /**
     * Constructor
     * @param shop the shop
     */
    public ShopController(Shop shop) {
        this.shop = shop;
        mapper = new ObjectMapper();
    }

    /**
     * method that initialize the controller
     */
    public abstract void init();

    /**
     * method that map an object into a JSON
     * @param entity the object to serialize
     * @return the objext as a JSON
     */
    protected String toJson(Object entity) throws JsonProcessingException {
        return mapper.writeValueAsString(entity);
    }

		public ObjectMapper getMapper() {
			return mapper;
		}
    
    
}
