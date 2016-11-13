package com.alma.boutique.domain.exceptions;

public class TransactionNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2177201355151606048L;

	public TransactionNotFoundException(String message) {
		super(message);
	}
}
