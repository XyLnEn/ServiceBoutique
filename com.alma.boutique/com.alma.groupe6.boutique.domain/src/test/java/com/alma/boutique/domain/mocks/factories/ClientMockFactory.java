package com.alma.boutique.domain.mocks.factories;

import com.alma.boutique.api.IFactory;
import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Identity;

import java.io.IOException;

/**
 * @author Thomas Minier
 */
public class ClientMockFactory implements IFactory<Client> {
    private String firstName;
    private String lastName;
    private String address;
    private String telNumber;

    public ClientMockFactory(String firstName, String lastName, String address, String telNumber) {
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
