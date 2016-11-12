package com.alma.boutique.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Minier
 */
public class ExternalService {
    private String baseURL;
    private ObjectMapper mapper;

    public ExternalService(String baseURL) {
        this.baseURL = baseURL;
        mapper = new ObjectMapper();
    }

    public <C> C get(String url, Class<C> targetClass) throws IOException {
        Object value = null;
        try {
            URL service = new URL(baseURL + url);
            HttpURLConnection httpcon = (HttpURLConnection) service.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
            value = mapper.readValue(httpcon.getInputStream(), targetClass);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return (C) value;
    }

    public <C> List<C> getList(String url, Class<C> targetClass) throws IOException {
        List<C> elements = new ArrayList<>();
        try {
            URL service = new URL(baseURL + url);
            HttpURLConnection httpcon = (HttpURLConnection) service.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
            elements.addAll(mapper.readValue(httpcon.getInputStream(), mapper.getTypeFactory().constructCollectionType(List.class, targetClass)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return elements;
    }
}
