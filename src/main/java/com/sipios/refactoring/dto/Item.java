package com.sipios.refactoring.dto;

public class Item {

	private String type;
	private int nb;

	public Item() {
	}

	public Item(String type, int nb) {
		this.type = type;
		this.nb = nb;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNb() {
		return nb;
	}

	public void setNb(int nb) {
		this.nb = nb;
	}
	
}
