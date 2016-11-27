package com.alma.boutique.application.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Data class, used to map the post queries from the presentation to buy products
 * @author Thomas Minier
 * @author Lenny Lucas
 */
public class Purchase {

	private String deliverer;
	private List<Integer> idList;
	private String devise;
	private int personId;
	private int cardNumber;
	
	public Purchase() {
		idList = new ArrayList<>();
		
	}

	public Purchase(String deliverer, List<Integer> idList, String devise, int personId, int cardNumber) {
		this.deliverer = deliverer;
		this.idList = idList;
		this.devise = devise;
		this.personId = personId;
		this.cardNumber = cardNumber;
	}
	
	public String getDeliverer() {
		return deliverer;
	}
	
	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}
	
	public String getDevise() {
		return devise;
	}
	
	public void setDevise(String devise) {
		this.devise = devise;
	}
	
	public List<Integer> getIdList() {
		return idList;
	}
	
	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}
}
