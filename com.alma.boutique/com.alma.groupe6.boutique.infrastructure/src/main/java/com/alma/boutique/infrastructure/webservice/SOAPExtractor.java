package com.alma.boutique.infrastructure.webservice;

import org.apache.log4j.Logger;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class who fetch SOAP message from external webservices
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class SOAPExtractor {
    private final static Logger logger = Logger.getLogger(SOAPExtractor.class);

    private String baseUrl;

    /**
     * Constructor
     * @param baseUrl The base url of the webservice used
     */
    public SOAPExtractor(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Private method used to create a HTTP connexion pre-configured via an API
     * @param url the url of the webservice that we are trying to contact
     * @return an HTTP connexion configured for the webservice
     * @throws IOException
     */
    private HttpURLConnection setupConnection(String url) throws IOException {
        URL service = new URL(url);
        HttpURLConnection httpCon = (HttpURLConnection) service.openConnection();
        httpCon.addRequestProperty("User-Agent", "Mozilla/4.76");
        return httpCon;
    }

    /**
     * Method who fetch a SOAP message from a webservice
     * @param url The url of the service where we want to get a SOAP message
     * @return The SOAP message retrieved from the webservice
     */
    public SOAPMessage getMessage(String url) {
        SOAPMessage message = null;
        try {
            MessageFactory factory = MessageFactory.newInstance();
            HttpURLConnection httpCon = setupConnection(baseUrl + url);
            message = factory.createMessage(new MimeHeaders(), httpCon.getInputStream());
        } catch (SOAPException | IOException e) {
            logger.warn(e);
        }
        return message;
    }
}
