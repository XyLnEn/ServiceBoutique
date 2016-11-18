package com.alma.boutique.application;

import com.alma.boutique.application.controllers.GreetingController;

import static spark.Spark.after;

/**
 *
 * @author Thomas Minier
 */
public class Application {
    public static void main(String[] args) {
        // use a filter to convert all request into JSON
        after((req, res) -> res.type("application/json"));

        // create all the REST controller
        GreetingController controller = new GreetingController();
    }
}
