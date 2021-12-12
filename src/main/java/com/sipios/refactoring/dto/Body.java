package com.sipios.refactoring.dto;

import java.util.Arrays;

public class Body {

	private Item[] items;
	private String type;

	public Body(Item[] items, String type) {
		this.items = items;
		this.type = type;
	}

	public Body() {
	}

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Body [items=" + Arrays.toString(items) + ", type=" + type + "]";
	}
	
}
