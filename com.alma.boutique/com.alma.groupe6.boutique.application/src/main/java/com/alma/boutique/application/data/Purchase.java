package com.alma.boutique.application.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alma.boutique.domain.thirdperson.Client;
import com.alma.boutique.domain.thirdperson.ThirdParty;

public class Purchase {

	private String deliverer;
	private List<Integer> idList;
	private String devise;
	private int personId;
	
	public Purchase() {
		idList = new ArrayList<>();
		
	}

	public Purchase(String deliverer, ArrayList<Integer> idList, String devise, int personId) {
		this.deliverer = deliverer;
		this.idList = idList;
		this.devise = devise;
		this.personId = personId;
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

	public int getPerson() {
		return personId;
	}

	public void setPerson(int personId) {
		this.personId = personId;
	}
	
	
}
