package com.alma.boutique.infrastructure.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Identity;

import java.io.IOException;

/**
 *
 * @author Thomas Minier
 */
public class FactoryClient implements IFactory<Client> {

	private String firstName;
    private String lastName;
    private String address;
    private String telNumber;

    public FactoryClient(String firstName, String lastName, String address, String telNumber) {
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
