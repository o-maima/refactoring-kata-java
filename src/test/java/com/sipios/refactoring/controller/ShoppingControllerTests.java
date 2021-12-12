package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.dto.Body;
import com.sipios.refactoring.dto.Item;
import com.sipios.refactoring.service.ShoppingService;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

class ShoppingControllerTests extends UnitTest {
	
	@Mock
	private ShoppingService shoppingService;

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Body(new Item[] {}, "STANDARD_CUSTOMER"))
        );
    }
    
    @Test
    void should_return_price_for_standard_customer() throws Exception {
    	Item[] items = {new Item("TSHIRT", 1), new Item("JACKET", 1), new Item("DRESS", 1)};
    	Body body = new Body(items, "STANDARD_CUSTOMER");
    	Mockito.doReturn(180.0).when(shoppingService).computePrice(body);
    	String price = controller.getPrice(body);
    	Assertions.assertEquals("180.0", price);
    }
    
    @Test
    void should_throw_ResponseStatusException_when_price_is_too_high_for_standard_customer() throws Exception {
    	Item[] items = {new Item("TSHIRT", 1), new Item("JACKET", 10), new Item("DRESS", 1)};
    	Body body = new Body(items, "STANDARD_CUSTOMER");
    	Mockito.doReturn(210.0).when(shoppingService).computePrice(body);
    	Mockito.doThrow(new Exception("Price is too high for standard customer")).when(shoppingService).isPriceOverLimit(210.0, body.getType());
    	ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
    		controller.getPrice(body);
		});
		Assertions.assertTrue(exception.getMessage().contains("is too high"));
    }
    
    @Test
    void should_return_price_for_premium_customer() throws Exception {
    	Item[] items = {new Item("TSHIRT", 2), new Item("JACKET", 2), new Item("DRESS", 1)};
    	Body body = new Body(items, "PREMIUM_CUSTOMER");
    	Mockito.doReturn(279.0).when(shoppingService).computePrice(body);
    	String price = controller.getPrice(body);
    	Assertions.assertEquals("279.0", price);
    }
    
    @Test
    void should_throw_ResponseStatusException_when_price_is_too_high_for_premium_customer() throws Exception {
    	Item[] items = {new Item("TSHIRT", 1), new Item("JACKET", 10), new Item("DRESS", 100)};
    	Body body = new Body(items, "PREMIUM_CUSTOMER");
    	Mockito.doReturn(810.0).when(shoppingService).computePrice(body);
    	Mockito.doThrow(new Exception("Price is too high for premium customer")).when(shoppingService).isPriceOverLimit(810.0, body.getType());
    	ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
    		controller.getPrice(body);
		});
		Assertions.assertTrue(exception.getMessage().contains("is too high"));
    }
    
    @Test
    void should_return_price_for_platinum_customer() throws Exception {
    	Item[] items = {new Item("TSHIRT", 2), new Item("JACKET", 3), new Item("DRESS", 3)};
    	Body body = new Body(items, "PLATINUM_CUSTOMER");
    	Mockito.doReturn(255.0).when(shoppingService).computePrice(body);
    	String price = controller.getPrice(body);
    	Assertions.assertEquals("255.0", price);
    }
    
    @Test
    void should_throw_responsestatusexception_when_price_is_too_high_for_platinum_customer() throws Exception {
    	Item[] items = {new Item("TSHIRT", 1), new Item("JACKET", 100), new Item("DRESS", 100)};
    	Body body = new Body(items, "PLATINUM_CUSTOMER");
    	Mockito.doReturn(2001.0).when(shoppingService).computePrice(body);
    	Mockito.doThrow(new Exception("Price is too high for platinum customer")).when(shoppingService).isPriceOverLimit(2001.0, body.getType());
    	ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
    		controller.getPrice(body);
		});
		Assertions.assertTrue(exception.getMessage().contains("is too high"));
    }
}
