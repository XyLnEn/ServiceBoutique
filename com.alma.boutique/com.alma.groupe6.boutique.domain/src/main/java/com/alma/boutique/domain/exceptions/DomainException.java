package com.alma.boutique.domain.exceptions;

/**
 * @author Thomas Minier
 */
public abstract class DomainException extends Exception {
    public DomainException(String message) {
        super(message);
    }
}
