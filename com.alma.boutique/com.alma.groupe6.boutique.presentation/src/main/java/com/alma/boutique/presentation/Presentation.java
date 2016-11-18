package com.alma.boutique.presentation;

import static spark.Spark.*;

/**
 * Simple controlleur servant simplement les fichiers de la webapp, réalisée en javascript avec Vue.js
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class Presentation {
	public static void main(String args[]) {
        staticFileLocation("/public");
        init();
	}
}
