package com.alma.creditcard;

/**
 * Service effectuant la validation de cartes bleues
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class CreditCardValidator {

    /**
     * Méthode validant un numéro de carte bleue
     * @param number le numéro de carte à valider
     * @return Le message de validation
     */
    public String validate(int number) {
        String response = "false";
        if(number > 0) {
            response = "true";
        }
        return response;
    }
}
