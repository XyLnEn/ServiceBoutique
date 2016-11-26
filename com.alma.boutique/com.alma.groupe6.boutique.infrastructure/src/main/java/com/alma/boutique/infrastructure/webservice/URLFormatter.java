package com.alma.boutique.infrastructure.webservice;

/**
 * Utility class supplying methods to format urls
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class URLFormatter {

    private URLFormatter() {
    }

    /**
     * Method the add parameters to a url
     * @param url the base url
     * @param parameters the parameters to add
     * @return the new url
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
