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
	public double computePrice(Body b) throws Exception {
		double p = 0;
		double d;
		
		// Compute discount for customer
		if (b.getType().equals("STANDARD_CUSTOMER")) {
			d = 1;
		} else if (b.getType().equals("PREMIUM_CUSTOMER")) {
			d = 0.9;
		} else if (b.getType().equals("PLATINUM_CUSTOMER")) {
			d = 0.5;
		} else {
			throw new Exception("Unknown customer type");
		}

		// Compute total amount depending on the types and quantity of product and
		// if we are in winter or summer discounts periods
		if (isNotSalePeriod()) {
			for (int i = 0; i < b.getItems().length; i++) {
				Item it = b.getItems()[i];

				if (it.getType().equals("TSHIRT")) {
					p += 30 * it.getNb() * d;
				} else if (it.getType().equals("DRESS")) {
					p += 50 * it.getNb() * d;
				} else if (it.getType().equals("JACKET")) {
					p += 100 * it.getNb() * d;
				}
			}
		} else {
			for (int i = 0; i < b.getItems().length; i++) {
				Item it = b.getItems()[i];

				if (it.getType().equals("TSHIRT")) {
					p += 30 * it.getNb() * d;
				} else if (it.getType().equals("DRESS")) {
					p += 50 * it.getNb() * 0.8 * d;
				} else if (it.getType().equals("JACKET")) {
					p += 100 * it.getNb() * 0.9 * d;
				}
			}
		}
		return p;
	}

	@Override
	public void isPriceOverLimit(double p, String type) throws Exception {
		if (type.equals("STANDARD_CUSTOMER")) {
			if (p > 200) {
				throw new Exception("Price (" + p + ") is too high for standard customer");
			}
		} else if (type.equals("PREMIUM_CUSTOMER")) {
			if (p > 800) {
				throw new Exception("Price (" + p + ") is too high for premium customer");
			}
		} else if (type.equals("PLATINUM_CUSTOMER")) {
			if (p > 2000) {
				throw new Exception("Price (" + p + ") is too high for platinum customer");
			}
		} else {
			if (p > 200) {
				throw new Exception("Price (" + p + ") is too high for standard customer");
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
