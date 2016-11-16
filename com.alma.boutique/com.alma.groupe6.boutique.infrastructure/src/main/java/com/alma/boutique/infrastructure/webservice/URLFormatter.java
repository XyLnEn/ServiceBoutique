package com.alma.boutique.infrastructure.webservice;

/**
 * @author Thomas Minier
 */
public class URLFormatter {
    /**
     * Méthode utilitaire qui ajoute des paramètres à une URL
     * @param url
     * @param parameters
     * @return
     */
    public static String appendParameters(String url, String[] parameters) {
        String finalURL = url;
        // remove the last char if t's '/'
        if(finalURL.charAt(url.length() - 1) == '/') {
            finalURL = finalURL.substring(0, url.length() - 1);
        }

        // concat parameter to the url
        for(String parameter : parameters) {
            finalURL += "/" + parameter;
        }
        return finalURL;
    }
}
