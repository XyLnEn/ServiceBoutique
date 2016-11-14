package com.alma.boutique.domain.thirdperson;
/**
 * 
 * @author lenny
 *
 */
public class Identity {

	private String address;
	private String telNumber;
	
	public Identity() {
		//empty for serialisation
	}
	
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

	public void setAddress(String address) {
		this.address = address;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	
	
	
}
