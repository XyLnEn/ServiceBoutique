package com.alma.boutique.domain.exceptions;

/**
 * 
 * @author lenny
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
