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
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class JSONWebservice<T> implements WebService<T> {
    private static final Logger logger = Logger.getLogger(JSONWebservice.class);

    private String baseURL;
    private ObjectMapper mapper;
    private Class<T> referenceClass;

    /**
     * Constructeur
     * @param baseURL L'url de base du webservice JSON
     * @param referenceClass La classe des objets fournis par le web service
     */
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

    /**
     * Méthode qui renvoie un objet récupéré depuis une url d'un service offert par le webservice
     * @param url L'url vers laquelle on souhaite faire un appel GET
     * @return L'objet servie par l'urtl passée en paramètre
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
     * Méthode qui renvoie une liste d'objets récupérée depuis une url d'un service offert par le webservice
     * @param url L'url vers laquelle on souhaite faire un appel GET
     * @return La liste d'objets servie par l'url passée en paramètre
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
