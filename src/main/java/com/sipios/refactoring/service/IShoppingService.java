package com.sipios.refactoring.service;

import com.sipios.refactoring.dto.Body;

public interface IShoppingService {
	
	public double computePrice(Body body);
	
	public void isPriceOverLimit(double p, String type) throws Exception;
}
