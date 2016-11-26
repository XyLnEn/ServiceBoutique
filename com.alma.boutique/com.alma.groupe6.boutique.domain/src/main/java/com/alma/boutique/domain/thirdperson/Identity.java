package com.alma.boutique.domain.thirdperson;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Object value class to represent the Identity of a person
 * @author Lenny Lucas
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
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Identity rhs = (Identity) obj;
		return new EqualsBuilder()
				.append(this.address, rhs.address)
				.append(this.telNumber, rhs.telNumber)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(address)
				.append(telNumber)
				.toHashCode();
	}
}
