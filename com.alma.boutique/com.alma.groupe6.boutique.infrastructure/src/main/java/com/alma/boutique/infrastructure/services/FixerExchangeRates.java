package com.alma.boutique.infrastructure.services;

import java.util.HashMap;
import java.util.Map;

/**
 * JavaBean uved as a container for the exchange rate from fixer.io
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class FixerExchangeRates {
    private String base;
    private String date;
    private Map<String, String> rates;

    /**
     * Constructor
     */
    public FixerExchangeRates() {
        rates = new HashMap<>();
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, String> getRates() {
        return rates;
    }

    public void setRates(Map<String, String> rates) {
        this.rates = rates;
    }

    public float getRate(String currency) {
        if(rates.containsKey(currency)) {
            return Float.valueOf(rates.get(currency));
        }
        return -1;
    }
}
