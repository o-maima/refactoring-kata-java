package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.web.server.ResponseStatusException;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Body(new Item[] {}, "STANDARD_CUSTOMER"))
        );
    }
    
    @Test
    void should_return_price_for_standard_customer() {
    	Item[] items = {new Item("TSHIRT", 1), new Item("JACKET", 1), new Item("DRESS", 1)};
    	Body body = new Body(items, "STANDARD_CUSTOMER");
    	String price = controller.getPrice(body);
    	Assertions.assertEquals("180.0", price);
    }
    
    @Test
    void should_throw_ResponseStatusException_when_price_is_too_high_for_standard_customer() {
    	Item[] items = {new Item("TSHIRT", 1), new Item("JACKET", 10), new Item("DRESS", 1)};
    	Body body = new Body(items, "STANDARD_CUSTOMER");
    	ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
    		controller.getPrice(body);
		});
		Assertions.assertTrue(exception.getMessage().contains("is too high for standard customer"));
    }
    
    @Test
    void should_return_price_for_premium_customer() {
    	Item[] items = {new Item("TSHIRT", 2), new Item("JACKET", 2), new Item("DRESS", 1)};
    	Body body = new Body(items, "PREMIUM_CUSTOMER");
    	String price = controller.getPrice(body);
    	Assertions.assertEquals("279.0", price);
    }
    
    @Test
    void should_throw_ResponseStatusException_when_price_is_too_high_for_premium_customer() {
    	Item[] items = {new Item("TSHIRT", 1), new Item("JACKET", 10), new Item("DRESS", 100)};
    	Body body = new Body(items, "PREMIUM_CUSTOMER");
    	ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
    		controller.getPrice(body);
		});
		Assertions.assertTrue(exception.getMessage().contains("is too high for premium customer"));
    }
    
    @Test
    void should_return_price_for_platinum_customer() {
    	Item[] items = {new Item("TSHIRT", 2), new Item("JACKET", 3), new Item("DRESS", 3)};
    	Body body = new Body(items, "PLATINUM_CUSTOMER");
    	String price = controller.getPrice(body);
    	Assertions.assertEquals("255.0", price);
    }
    
    @Test
    void should_throw_responsestatusexception_when_price_is_too_high_for_platinum_customer() {
    	Item[] items = {new Item("TSHIRT", 1), new Item("JACKET", 100), new Item("DRESS", 100)};
    	Body body = new Body(items, "PLATINUM_CUSTOMER");
    	ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
    		controller.getPrice(body);
		});
		Assertions.assertTrue(exception.getMessage().contains("is too high for platinum customer"));
    }
}
