package com.alma.boutique.domain.exceptions;

public class ProductNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2177201355151606048L;

	public ProductNotFoundException(String message) {
		super(message);
	}
}
