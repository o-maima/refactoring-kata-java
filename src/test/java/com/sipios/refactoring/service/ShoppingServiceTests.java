package com.sipios.refactoring.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.configuration.ApplicationProperties;
import com.sipios.refactoring.dto.Body;
import com.sipios.refactoring.dto.Item;

public class ShoppingServiceTests extends UnitTest {
	
	private Map<String, Double> customerDiscounts = initializeDiscountsMap();
	private Map<String, Double> prices = initializePricesMap();
	
	@Mock
	private ApplicationProperties applicationProperties;
	
	@InjectMocks
	private ShoppingService shoppingService;
	
	@Test
    void should_compute_price_for_standard_customer() throws Exception {
    	Item[] items = {new Item("TSHIRT", 1), new Item("JACKET", 1), new Item("DRESS", 1)};
    	Body body = new Body(items, "STANDARD_CUSTOMER");
    	Mockito.when(applicationProperties.getCustomerDiscounts()).thenReturn(customerDiscounts);
		Mockito.when(applicationProperties.getPrices()).thenReturn(prices);
		Mockito.when(applicationProperties.getZoneId()).thenReturn("Europe/Paris");
    	double price = shoppingService.computePrice(body);
    	Assertions.assertEquals(180.0, price);
    }
	
	@Test
    void should_compute_price_for_premium_customer() throws Exception {
    	Item[] items = {new Item("TSHIRT", 2), new Item("JACKET", 2), new Item("DRESS", 1)};
    	Body body = new Body(items, "PREMIUM_CUSTOMER");
    	Mockito.when(applicationProperties.getCustomerDiscounts()).thenReturn(customerDiscounts);
		Mockito.when(applicationProperties.getPrices()).thenReturn(prices);
		Mockito.when(applicationProperties.getZoneId()).thenReturn("Europe/Paris");
    	double price = shoppingService.computePrice(body);
    	Assertions.assertEquals(279.0, price);
    }
	
	@Test
    void should_compute_price_for_platinum_customer() throws Exception {
    	Item[] items = {new Item("TSHIRT", 2), new Item("JACKET", 3), new Item("DRESS", 3)};
    	Body body = new Body(items, "PLATINUM_CUSTOMER");
    	Mockito.when(applicationProperties.getCustomerDiscounts()).thenReturn(customerDiscounts);
		Mockito.when(applicationProperties.getPrices()).thenReturn(prices);
		Mockito.when(applicationProperties.getZoneId()).thenReturn("Europe/Paris");
    	double price = shoppingService.computePrice(body);
    	Assertions.assertEquals(255.0, price);
    }
	
	@Test
    void should_throw_Exception_when_customer_type_is_unknown() throws Exception {
    	Item[] items = {new Item("TSHIRT", 2), new Item("JACKET", 3), new Item("DRESS", 3)};
    	Body body = new Body(items, "HELLO");
    	Mockito.when(applicationProperties.getCustomerDiscounts()).thenReturn(customerDiscounts);
    	Exception exception = assertThrows(Exception.class, () -> {
    		shoppingService.computePrice(body);
		});
		Assertions.assertTrue(exception.getMessage().contains("Unknown customer type"));
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Double> initializeDiscountsMap() {
		Map<String, Double> customerDiscounts = new HashMap();
		customerDiscounts.put("STANDARD_CUSTOMER", (double) 1);
		customerDiscounts.put("PREMIUM_CUSTOMER", (double) 0.9);
		customerDiscounts.put("PLATINUM_CUSTOMER", (double) 0.5);
		return customerDiscounts;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Double> initializePricesMap() {
		Map<String, Double> prices = new HashMap();
		prices.put("TSHIRT", (double) 30);
		prices.put("DRESS", (double) 50);
		prices.put("JACKET", (double) 100);
		return prices;
	}
	
}
