package com.sipios.refactoring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sipios.refactoring.dto.Body;
import com.sipios.refactoring.service.ShoppingService;
import com.sipios.refactoring.utils.JsonUtils;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingController.class);

	@Autowired
	private ShoppingService shoppingService;

	@PostMapping
	public String getPrice(@RequestBody Body shoppingRequest) {
		LOGGER.debug("New incoming request: {}", JsonUtils.objectToJsonString(shoppingRequest));
		try {
			double price = shoppingService.computePrice(shoppingRequest);
			shoppingService.checkPriceLimit(price, shoppingRequest.getType());
			return String.valueOf(price);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}