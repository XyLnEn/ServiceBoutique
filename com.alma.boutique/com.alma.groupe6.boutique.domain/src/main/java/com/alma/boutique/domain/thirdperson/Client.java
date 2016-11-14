package com.alma.boutique.domain.thirdperson;

import com.alma.boutique.domain.factories.FactorySoldProduct;

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
		this.setFactoryProd(new FactorySoldProduct());
		this.firstName = "";
		this.lastName = "";
		this.info = new Identity();
	}
	
	public Client(String firstName, String lastName, Identity info) {
		super();
		this.setFactoryProd(new FactorySoldProduct());
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
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return firstName.equals(client.firstName) && lastName.equals(client.lastName) && info.equals(client.info);
    }

	@Override
	public int hashCode() {
		int result = firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + info.hashCode();
		return result;
	}
}
