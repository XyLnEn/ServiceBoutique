package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Identity;

import java.io.IOException;

/**
 * Classe fabrique créant des objets de type Client
 * @author Lenny Lucas
 * @author Thomas Minier
 */
public class ClientFactory implements IFactory<Client> {

	private String firstName;
    private String lastName;
    private String address;
    private String telNumber;

    /**
     * Constructeur
     * @param firstName Le prénom du client
     * @param lastName Le nom de famille du client
     * @param address L'adresse du client
     * @param telNumber le numéro de téléphone du client
     */
    public ClientFactory(String firstName, String lastName, String address, String telNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telNumber = telNumber;
    }

    @Override
	public Client create() throws IOException {
		Identity info = new Identity(address, telNumber);
		return new Client(firstName, lastName, info);
	}
}
