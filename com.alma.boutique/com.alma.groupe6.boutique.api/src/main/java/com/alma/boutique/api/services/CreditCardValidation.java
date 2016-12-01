package com.alma.boutique.api.services;

/**
 * Service used to validate a credit card number
 * @author Lenny Lucas
 * @author Thomas Minier
 */
@FunctionalInterface
public interface CreditCardValidation {
    boolean validate(int number);
}
