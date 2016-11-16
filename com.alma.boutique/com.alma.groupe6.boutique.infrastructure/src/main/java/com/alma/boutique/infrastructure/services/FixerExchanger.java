package com.alma.boutique.infrastructure.services;

import com.alma.boutique.api.services.ExchangeRateService;
import com.alma.boutique.infrastructure.webservice.WebService;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Service de conversion de monnaire qui utilise le service web fixer.io
 * @author Thomas Minier
 */
public class FixerExchanger implements ExchangeRateService {
    private static final Logger logger = Logger.getLogger(FixerExchanger.class);

    private static final String fixerURL = "/latest";
    private WebService<FixerExchangeRates> webService;

    public FixerExchanger(WebService<FixerExchangeRates> webService) {
        this.webService = webService;
    }

    @Override
    public float exchange(float value, String currency) {
        // returns the value if the currency is euro
        if("EUR".equals(currency)) {
            return value;
        }
        float newValue = -1;
        try {
            FixerExchangeRates rates = webService.read(fixerURL);
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
}
