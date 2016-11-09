package com.alma.boutique.domain.exceptions;

/**
 * @author Thomas Minier
 */
public class NotEnoughStockException extends Exception {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
