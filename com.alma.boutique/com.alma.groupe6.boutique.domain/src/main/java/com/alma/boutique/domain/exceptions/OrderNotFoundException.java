package com.alma.boutique.domain.exceptions;

/**
 * Exception thrown when an Order is not found
 * @author Lenny Lucas
 *
 */
public class OrderNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8650360405322850875L;

	public OrderNotFoundException(String message) {
		super(message);
	}
}
