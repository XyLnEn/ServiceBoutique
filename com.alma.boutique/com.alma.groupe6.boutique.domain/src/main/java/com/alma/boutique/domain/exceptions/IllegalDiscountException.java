package com.alma.boutique.domain.exceptions;

/**
 * @author Thomas Minier
 */
public class IllegalDiscountException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5348603640237837613L;

		public IllegalDiscountException(String message) {
        super(message);
    }
}
