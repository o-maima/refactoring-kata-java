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
			double customerDiscount = customerDiscounts.get(customerType);
			double price = 0;

			// Compute total amount
			if (isNotSalePeriod()) {
				for (int i = 0; i < shoppingRequest.getItems().length; i++) {
					Item it = shoppingRequest.getItems()[i];
					price += prices.get(it.getType()) * it.getNb() * customerDiscount;
				}
			} else {
				for (int i = 0; i < shoppingRequest.getItems().length; i++) {
					Item it = shoppingRequest.getItems()[i];
					double itemDiscount = itemDiscounts.get(it.getType());
					price += prices.get(it.getType()) * it.getNb() * itemDiscount * customerDiscount;
				}
			}
			LOGGER.debug("Computed price: {}", price);
			return price;

		} else {
			throw new Exception("Unknown customer type");
		}
	}
	
	/**
	 * Checks if price is higher than configured price for a certain customer type
	 */
	@Override
	public void checkPriceLimit(double price, String customerType) throws Exception {
		Map<String, Double> customerPriceLimits = applicationProperties.getCustomerPriceLimits();
		if(price > customerPriceLimits.get(customerType)) {
			throw new Exception("Price (" + price + ") is too high for " + customerType + "");
		}
	}
	
	private boolean isNotSalePeriod() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(applicationProperties.getZoneId()));
		cal.setTime(date);
		return !(cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5 && cal.get(Calendar.MONTH) == 5)
				&& !(cal.get(Calendar.DAY_OF_MONTH) < 15 && cal.get(Calendar.DAY_OF_MONTH) > 5 && cal.get(Calendar.MONTH) == 0);
	}

}
