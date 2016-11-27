package com.alma.boutique.infrastructure.services;

import com.alma.boutique.api.services.CreditCardValidation;
import com.alma.boutique.infrastructure.webservice.SOAPExtractor;
import org.apache.log4j.Logger;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.text.MessageFormat;
import java.util.Iterator;

/**
 * Implementation of the credit card validation service using an external SOAP webservice
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class CBValidator implements CreditCardValidation {
    private static final Logger logger = Logger.getLogger(CBValidator.class);

    private SOAPExtractor extractor;

    /**
     * Constructor
     * @param webserviceUrl the url of the webservice used to perform the validation
     */
    public CBValidator(String webserviceUrl) {
        extractor = new SOAPExtractor(webserviceUrl);
    }

    /**
     * Method that use an external webservice to validate a credit card
     * @param number The number of the credit card to validate
     * @return True if the credit card is valid, False otherwise
     */
    @Override
    public boolean validate(int number) {
        boolean validation = false;
        SOAPMessage message = extractor.getMessage(MessageFormat.format("?number={0}", String.valueOf(number)));
        try {
            SOAPBody body = message.getSOAPBody();
            SOAPElement response = (SOAPElement) body.getChildElements().next();
            Iterator responseChildElements = response.getChildElements();
            while(responseChildElements.hasNext()) {
                SOAPElement current = (SOAPElement) responseChildElements.next();
                if("creditcard:validationValue".equals(current.getNodeName())) {
                    validation = Boolean.valueOf(current.getTextContent());
                }
            }
        } catch (SOAPException e) {
            logger.warn(e);
        }
        return validation;
    }
}
