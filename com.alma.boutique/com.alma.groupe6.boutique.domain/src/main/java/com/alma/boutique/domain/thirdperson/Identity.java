package com.alma.boutique.domain.thirdperson;

public class Identity {

	private final String address;
	private final String telNumber;
	
	public Identity(String address, String telNumber) {
		this.address = address;
		this.telNumber = telNumber;
	}

	public String getAddress() {
		return address;
	}

	public String getTelNumber() {
		return telNumber;
	}

	
	
}
