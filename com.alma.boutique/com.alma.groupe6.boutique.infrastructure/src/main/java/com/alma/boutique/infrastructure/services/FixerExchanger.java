package com.alma.boutique.infrastructure.services;

import com.alma.boutique.api.services.ExchangeRateService;
import com.alma.boutique.infrastructure.webservice.WebService;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Service de conversion de monnaie qui utilise le service web fixer.io
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class FixerExchanger implements ExchangeRateService {
    private static final Logger logger = Logger.getLogger(FixerExchanger.class);

    private String dateUrl;
    private WebService<FixerExchangeRates> webService;

    /**
     * Constructeur
     * @param dateUrl La date pour la version désirée des taux de change (format 'yyyy-mm-dd' ou 'latest' pour les derniers)
     * @param webService Le web service utilisée pour consommer les services de fixer.io
     */
    public FixerExchanger(String dateUrl, WebService<FixerExchangeRates> webService) {
        this.dateUrl = dateUrl;
        this.webService = webService;
    }

    /**
     * Méthode qui effectue la conversion d'une monnaie selon les taux de change désirés
     * @param value La valeur à convertir
     * @param currency La monnaie correspondant à la valeur ('EUR', 'USD, etc)
     * @return La valeur convertie en euro (EUR)
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
}
