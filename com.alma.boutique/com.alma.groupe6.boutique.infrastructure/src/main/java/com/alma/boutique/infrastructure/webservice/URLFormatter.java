package com.alma.boutique.infrastructure.webservice;

/**
 * Classe utilitaire fournissant des méthodes pour le formattage d'urls
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class URLFormatter {

    private URLFormatter() {
    }

    /**
     * Méthode utilitaire qui ajoute des paramètres à une URL
     * @param url L'url à utiliser
     * @param parameters les paramètres à ajouter
     * @return L'url avec ses paramètres
     */
    public static String appendParameters(String url, String[] parameters) {
        StringBuilder finalURL = new StringBuilder();
        // remove the last char if t's '/'
        if(url.charAt(url.length() - 1) == '/') {
            finalURL.append(url.substring(0, url.length() - 1));
        } else {
            finalURL.append(url);
        }

        // concat parameter to the url
        for(String parameter : parameters) {
            finalURL.append("/").append(parameter);
        }
        return finalURL.toString();
    }
}
