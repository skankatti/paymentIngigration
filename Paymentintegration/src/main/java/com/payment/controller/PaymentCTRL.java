package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.entity.CustomerData;
import com.payment.service.PaymentSERV;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/payment")
public class PaymentCTRL {
	
	@Value("${stripe.apiKey}")
	String stripeKey;
	
	@Autowired
	PaymentSERV paymentSERV;
	
	@GetMapping("/start")
	public String index() {
		return stripeKey;
	}
	
	@PostMapping("/add-customer")
	public ResponseEntity<?> addCustomer(@RequestBody CustomerData customerData) throws StripeException{
		return new ResponseEntity<>(paymentSERV.addCustomerData(customerData),HttpStatus.OK);
	}

}
