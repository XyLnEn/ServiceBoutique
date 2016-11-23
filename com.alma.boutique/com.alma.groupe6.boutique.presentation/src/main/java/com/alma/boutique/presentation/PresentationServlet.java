package com.alma.boutique.presentation;

import spark.servlet.SparkApplication;

import static spark.Spark.staticFileLocation;

/**
 * Servlet simple servant les fichiers statiques de la web application
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class PresentationServlet implements SparkApplication {
    @Override
    public void init() {
        staticFileLocation("/public");
    }
}
