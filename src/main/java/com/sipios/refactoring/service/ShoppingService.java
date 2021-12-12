package com.sipios.refactoring.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sipios.refactoring.configuration.ApplicationProperties;
import com.sipios.refactoring.controller.ShoppingController;
import com.sipios.refactoring.dto.Body;
import com.sipios.refactoring.dto.Item;

@Service
public class ShoppingService implements IShoppingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingService.class);

	@Autowired
	private ApplicationProperties applicationProperties;

	@Override
	public double computePrice(Body shoppingRequest) throws Exception {
		Map<String, Double> customerDiscounts = applicationProperties.getCustomerDiscounts();
		String customerType = shoppingRequest.getType();
		
		//Check existence of provided customer type
		if (customerDiscounts.containsKey(customerType)) {
			Map<String, Double> prices = applicationProperties.getPrices();
			Map<String, Double> itemDiscounts = applicationProperties.getItemDiscounts();
			double price = 0;
			double discount = customerDiscounts.get(customerType);

			// Compute total amount depending on the types and quantity of product and
			if (isNotSalePeriod()) {
				for (int i = 0; i < shoppingRequest.getItems().length; i++) {
					Item it = shoppingRequest.getItems()[i];
					price += prices.get(it.getType()) * it.getNb() * discount;
				}
			} else {
				for (int i = 0; i < shoppingRequest.getItems().length; i++) {
					Item it = shoppingRequest.getItems()[i];
					price += prices.get(it.getType()) * it.getNb() * itemDiscounts.get(it.getType()) * discount;
				}
			}
			return price;

		} else {
			throw new Exception("Unknown customer type");
		}
	}

	@Override
	public void isPriceOverLimit(double price, String customerType) throws Exception {
		if (customerType.equals("STANDARD_CUSTOMER")) {
			if (price > 200) {
				throw new Exception("Price (" + price + ") is too high for standard customer");
			}
		} else if (customerType.equals("PREMIUM_CUSTOMER")) {
			if (price > 800) {
				throw new Exception("Price (" + price + ") is too high for premium customer");
			}
		} else if (customerType.equals("PLATINUM_CUSTOMER")) {
			if (price > 2000) {
				throw new Exception("Price (" + price + ") is too high for platinum customer");
			}
		} else {
			if (price > 200) {
				throw new Exception("Price (" + price + ") is too high for standard customer");
			}
		}
	}

	private boolean isNotSalePeriod() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		cal.setTime(date);
		return !(cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5 && cal.get(Calendar.MONTH) == 5)
				&& !(cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5 && cal.get(Calendar.MONTH) == 0);
	}

}
