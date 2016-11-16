package com.alma.boutique.domain.thirdperson;

import java.io.IOException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.alma.boutique.api.IFactory;

/**
 * 
 * @author lenny
 *
 */
public class Client extends ThirdParty {	
	
	private String firstName;
	private String lastName;
	private Identity info;
	
	public Client() {
		super();
		this.firstName = "";
		this.lastName = "";
		this.info = new Identity();
	}
	
	public Client(String firstName, String lastName, Identity info) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.info = info;
	}
	
	public Order createOrder(IFactory factoryOrd) {
		OrderSoldProduct newOrd = null;
		try {
			newOrd = (OrderSoldProduct) factoryOrd.create();
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		this.getOrderHistory().add(newOrd);
		return newOrd;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setInfo(Identity info) {
		this.info = info;
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
		Client rhs = (Client) obj;
		return new EqualsBuilder()
				.appendSuper(super.equals(obj))
				.append(this.firstName, rhs.firstName)
				.append(this.lastName, rhs.lastName)
				.append(this.info, rhs.info)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(firstName)
				.append(lastName)
				.append(info)
				.toHashCode();
	}
}
