package com.sipios.refactoring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.dto.Body;
import com.sipios.refactoring.dto.Item;

public class ShoppingServiceTests extends UnitTest {
	
	@InjectMocks
	private ShoppingService shoppingService;
	
	@Test
    void should_compute_price_for_standard_customer() {
    	Item[] items = {new Item("TSHIRT", 1), new Item("JACKET", 1), new Item("DRESS", 1)};
    	Body body = new Body(items, "STANDARD_CUSTOMER");
    	double price = shoppingService.computePrice(body);
    	Assertions.assertEquals(180.0, price);
    }
	
	@Test
    void should_compute_price_for_premium_customer() {
    	Item[] items = {new Item("TSHIRT", 2), new Item("JACKET", 2), new Item("DRESS", 1)};
    	Body body = new Body(items, "PREMIUM_CUSTOMER");
    	double price = shoppingService.computePrice(body);
    	Assertions.assertEquals(279.0, price);
    }
	
	@Test
    void should_return_price_for_platinum_customer() {
    	Item[] items = {new Item("TSHIRT", 2), new Item("JACKET", 3), new Item("DRESS", 3)};
    	Body body = new Body(items, "PLATINUM_CUSTOMER");
    	double price = shoppingService.computePrice(body);
    	Assertions.assertEquals(255.0, price);
    }

}
