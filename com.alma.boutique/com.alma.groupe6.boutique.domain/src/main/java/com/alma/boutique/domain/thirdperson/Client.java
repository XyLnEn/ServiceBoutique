package com.alma.boutique.domain.thirdperson;

/**
 * 
 * @author lenny
 *
 */
public class Client extends ThirdParty {	
	
	private final String firstName;
	private final String lastName;
	private final Identity info;
	

	public Client(String firstName, String lastName, Identity info) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.info = info;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Identity getInfo() {
		return info;
	}

}
