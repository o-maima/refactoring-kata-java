package com.sipios.refactoring.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

	private String zoneId;
	private Map<String, Double> customerDiscounts;
	private Map<String, Double> prices;
	private Map<String, Double> itemDiscounts;
	private Map<String, Double> customerPriceLimits;

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public Map<String, Double> getCustomerDiscounts() {
		return customerDiscounts;
	}

	public void setCustomerDiscounts(Map<String, Double> customerDiscounts) {
		this.customerDiscounts = customerDiscounts;
	}

	public Map<String, Double> getPrices() {
		return prices;
	}

	public void setPrices(Map<String, Double> prices) {
		this.prices = prices;
	}

	public Map<String, Double> getItemDiscounts() {
		return itemDiscounts;
	}

	public void setItemDiscounts(Map<String, Double> itemDiscounts) {
		this.itemDiscounts = itemDiscounts;
	}

	public Map<String, Double> getCustomerPriceLimits() {
		return customerPriceLimits;
	}

	public void setCustomerPriceLimits(Map<String, Double> customerPriceLimits) {
		this.customerPriceLimits = customerPriceLimits;
	}

}
