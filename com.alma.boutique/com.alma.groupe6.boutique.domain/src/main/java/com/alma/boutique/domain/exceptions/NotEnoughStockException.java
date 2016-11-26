package com.alma.boutique.domain.exceptions;

/**
 * Exception thrown when there is not enough Product in stock
 * @author Thomas Minier
 */
public class NotEnoughStockException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8328107401739986105L;

		public NotEnoughStockException(String message) {
        super(message);
    }
}
