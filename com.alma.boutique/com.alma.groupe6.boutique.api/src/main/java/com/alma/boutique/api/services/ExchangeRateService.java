package com.alma.boutique.api.services;

/**
 * Service used to exchange currencies
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public interface ExchangeRateService {
    float exchange(float value, String currency);
    float exchangeBack(float value, String currency);
}
