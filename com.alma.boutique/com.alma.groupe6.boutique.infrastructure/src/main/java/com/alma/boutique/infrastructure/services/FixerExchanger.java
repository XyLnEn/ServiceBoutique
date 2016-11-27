package com.alma.boutique.infrastructure.services;

import com.alma.boutique.api.services.ExchangeRateService;
import com.alma.boutique.infrastructure.webservice.WebService;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Exchange rate service using the web service fixer.io
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class FixerExchanger implements ExchangeRateService {
    private static final Logger logger = Logger.getLogger(FixerExchanger.class);

    private String dateUrl;
    private WebService<FixerExchangeRates> webService;

    /**
     * Constructor
     * @param dateUrl the date for which the exchange rate is required (format 'yyyy-mm-dd' or 'latest' for the last)
     * @param webServicethe web service used to consume the services of fixer.io
     */
    public FixerExchanger(String dateUrl, WebService<FixerExchangeRates> webService) {
        this.dateUrl = dateUrl;
        this.webService = webService;
    }

    /**
     * Method that exchange a value in a currency into our currency according to the desired exchange rate
     * @param value the value to exchange
     * @param currency the corresponding currency ('EUR', 'USD, etc)
     * @return the value converted into euros (EUR)
     */
    @Override
    public float exchange(float value, String currency) {
        // returns the value if the currency is euro
        if("EUR".equals(currency)) {
            return value;
        }
        float newValue = -1;
        try {
            FixerExchangeRates rates = webService.read(dateUrl);
            // find the currency and use it's rate to convert to EUR
            float rate = rates.getRate(currency);
            if(Float.compare(rate, -1) != 0) {
             newValue = value * rate;
            }
        } catch (IOException e) {
            logger.warn(e);
        }
        return newValue;
    }
    
    /**
     * Method that exchange a value in EUR into a new currency to the desired exchange rate
     * @param value the value to exchange
     * @param currency the corresponding currency ('EUR', 'USD, etc)
     * @return the value converted into the currency
     */
    @Override
    public float exchangeBack(float value, String currency) {
        // returns the value if the currency is euro
    		if("EUR".equals(currency)) {
    			return value;
    		}
        float newValue = -1;
        try {
            FixerExchangeRates rates = webService.read(dateUrl);
            // find the currency and use it's rate to convert to EUR
            float rate = rates.getRate(currency);
            if(Float.compare(rate, -1) != 0) {
             newValue = value / rate;
            }
        } catch (IOException e) {
            logger.warn(e);
        }
        return newValue;
    }
}
