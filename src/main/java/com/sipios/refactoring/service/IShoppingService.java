package com.sipios.refactoring.service;

import com.sipios.refactoring.dto.Body;

public interface IShoppingService {
	
	public double computePrice(Body shoppingRequest) throws Exception;
	
	public void isPriceOverLimit(double price, String customerType) throws Exception;
}
