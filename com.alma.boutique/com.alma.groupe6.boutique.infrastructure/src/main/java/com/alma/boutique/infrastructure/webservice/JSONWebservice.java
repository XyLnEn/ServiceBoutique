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
 * JSONWebservice représente un WebService utilisant du JSON
 * @author Thomas Minier
 */
public class JSONWebservice<T> implements WebService<T> {
    private static final Logger logger = Logger.getLogger(JSONWebservice.class);

    private String baseURL;
    private ObjectMapper mapper;
    private Class<T> referenceClass;

    public JSONWebservice(String baseURL, Class<T> referenceClass) {
        this.baseURL = baseURL;
        mapper = new ObjectMapper();
        this.referenceClass = referenceClass;
    }

    /**
     * Méthode privée utilisée pour créer une connexion HTTP pré-configurée avec une API
     * @param url L'url du webserice avec lequel on veut ouvrir une connexion
     * @return Une connexion HTTP correctement configurée avec le webservice
     * @throws IOException
     */
    private HttpURLConnection setupConnection(String url) throws IOException {
        URL service = new URL(url);
        HttpURLConnection httpCon = (HttpURLConnection) service.openConnection();
        httpCon.addRequestProperty("User-Agent", "Mozilla/4.76");
        return httpCon;
    }

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
