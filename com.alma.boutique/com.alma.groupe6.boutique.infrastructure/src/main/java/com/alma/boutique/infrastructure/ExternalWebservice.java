package com.alma.boutique.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Minier
 */
public class ExternalWebservice {
    private static final Logger logger = Logger.getLogger(ExternalWebservice.class);

    private String baseURL;
    private ObjectMapper mapper;

    public ExternalWebservice(String baseURL) {
        this.baseURL = baseURL;
        mapper = new ObjectMapper();
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

    public <C> C get(String url, Class<C> targetClass) throws IOException {
        Object value = null;
        try {
            HttpURLConnection httpCon = setupConnection(baseURL + url);
            value = mapper.readValue(httpCon.getInputStream(), targetClass);
        } catch (MalformedURLException e) {
           logger.error(e.getMessage());
        }
        return (C) value;
    }

    public <C> List<C> getList(String url, Class<C> targetClass) throws IOException {
        List<C> elements = new ArrayList<>();
        try {
            HttpURLConnection httpCon = setupConnection(baseURL + url);
            elements.addAll(mapper.readValue(httpCon.getInputStream(), mapper.getTypeFactory().constructCollectionType(List.class, targetClass)));
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
        return elements;
    }
}
