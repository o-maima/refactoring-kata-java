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

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

	private Logger logger = LoggerFactory.getLogger(ShoppingController.class);

	@Autowired
	private ShoppingService shoppingService;

	@PostMapping
	public String getPrice(@RequestBody Body b) {
		try {
			double p = shoppingService.computePrice(b);
			shoppingService.isPriceOverLimit(p, b.getType());
			return String.valueOf(p);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}