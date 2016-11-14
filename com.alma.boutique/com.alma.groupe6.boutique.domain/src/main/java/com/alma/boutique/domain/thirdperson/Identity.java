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
		this.address = "";
		this.telNumber = "";
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Identity identity = (Identity) o;
		return address.equals(identity.address) && telNumber.equals(identity.telNumber);

	}

	@Override
	public int hashCode() {
		int result = address.hashCode();
		result = 31 * result + telNumber.hashCode();
		return result;
	}
}
