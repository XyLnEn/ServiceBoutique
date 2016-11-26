package com.alma.boutique.infrastructure.webservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * JSONWebservice representing a webservice using JSON
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class JSONWebservice<T> implements WebService<T> {
    private static final Logger logger = Logger.getLogger(JSONWebservice.class);

    private String baseURL;
    private ObjectMapper mapper;
    private Class<T> referenceClass;

    /**
     * Constructor
     * @param baseURL the base url of the JSON webservice
     * @param referenceClass the class of the object supplied by the supplier
     */
    public JSONWebservice(String baseURL, Class<T> referenceClass) {
        this.baseURL = baseURL;
        mapper = new ObjectMapper();
        this.referenceClass = referenceClass;
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
     * Method that return an object read from an url of a service supplied by the webservice
     * @param url the url to which we want to do a GET
     * @return the object served by the url
     * @throws IOException
     */
    @Override
    public T read(String url) throws IOException {
        T value = null;
        try {
            HttpURLConnection httpCon = setupConnection(baseURL + url);
            value = mapper.readValue(httpCon.getInputStream(), referenceClass);
        } catch (MalformedURLException e) {
           logger.error(e.getMessage());
        }
        return value;
    }

    /**
     * Method that get a list of object from an url supplied by the webservice
     * @param url the url to which we want to do a GET
     * @return the list of objects from the url
     * @throws IOException
     */
    @Override
    public List<T> browse(String url) throws IOException {
        List<T> elements = new ArrayList<>();
        try {
            HttpURLConnection httpCon = setupConnection(baseURL + url);
            elements.addAll(mapper.readValue(httpCon.getInputStream(), mapper.getTypeFactory().constructCollectionType(List.class, referenceClass)));
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
        return elements;
    }
}
