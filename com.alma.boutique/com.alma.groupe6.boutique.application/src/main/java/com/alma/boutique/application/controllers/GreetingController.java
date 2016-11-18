package com.alma.boutique.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import static spark.Spark.*;

/**
 * Exemple canonique pour la création d'un controller REST
 * @author Thomas Minier
 */
public class GreetingController {
    private ObjectMapper mapper = new ObjectMapper();
    private static class Greeting {
        private String message;

        public Greeting(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    public GreetingController() {
        Greeting greeting = new Greeting("hello world");
        get("/hello", (req, res) -> mapper.writeValueAsString(greeting));
    }
}
