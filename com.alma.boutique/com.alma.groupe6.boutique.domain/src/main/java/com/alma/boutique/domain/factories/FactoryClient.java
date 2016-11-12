package com.alma.boutique.domain.factories;

import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.Identity;

public class FactoryClient {

	public Client make(String firstName, String lastName, String address, String telNumber) {
		Identity info = new Identity(address, telNumber);
		return new Client(firstName, lastName, info);
	}
	
}
