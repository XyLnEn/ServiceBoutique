package com.alma.boutique.domain.exceptions;

/**
 * 
 * @author lenny
 *
 */
public class OrderNotFoundException extends Exception {
	public OrderNotFoundException(String message) {
		super(message);
	}
}
