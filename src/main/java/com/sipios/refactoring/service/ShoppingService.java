package com.sipios.refactoring.service;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sipios.refactoring.dto.Body;
import com.sipios.refactoring.dto.Item;

@Service
public class ShoppingService implements IShoppingService {

	@Override
	public double computePrice(Body shoppingRequest) throws Exception {
		double price = 0;
		double discount;
		
		// Compute discount for customer
		if (shoppingRequest.getType().equals("STANDARD_CUSTOMER")) {
			discount = 1;
		} else if (shoppingRequest.getType().equals("PREMIUM_CUSTOMER")) {
			discount = 0.9;
		} else if (shoppingRequest.getType().equals("PLATINUM_CUSTOMER")) {
			discount = 0.5;
		} else {
			throw new Exception("Unknown customer type");
		}

		// Compute total amount depending on the types and quantity of product and
		// if we are in winter or summer discounts periods
		if (isNotSalePeriod()) {
			for (int i = 0; i < shoppingRequest.getItems().length; i++) {
				Item it = shoppingRequest.getItems()[i];

				if (it.getType().equals("TSHIRT")) {
					price += 30 * it.getNb() * discount;
				} else if (it.getType().equals("DRESS")) {
					price += 50 * it.getNb() * discount;
				} else if (it.getType().equals("JACKET")) {
					price += 100 * it.getNb() * discount;
				}
			}
		} else {
			for (int i = 0; i < shoppingRequest.getItems().length; i++) {
				Item it = shoppingRequest.getItems()[i];

				if (it.getType().equals("TSHIRT")) {
					price += 30 * it.getNb() * discount;
				} else if (it.getType().equals("DRESS")) {
					price += 50 * it.getNb() * 0.8 * discount;
				} else if (it.getType().equals("JACKET")) {
					price += 100 * it.getNb() * 0.9 * discount;
				}
			}
		}
		return price;
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
