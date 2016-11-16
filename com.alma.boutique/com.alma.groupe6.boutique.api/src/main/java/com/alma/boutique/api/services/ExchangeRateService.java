package com.alma.boutique.api.services;

/**
 * Service qui gère la conversion de monnaie
 * @author Thomas Minier
 */
@FunctionalInterface
public interface ExchangeRateService {
    float exchange(float value, String currency);
}
