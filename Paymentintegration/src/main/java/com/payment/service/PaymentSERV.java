package com.payment.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.payment.entity.CustomerData;
import com.payment.repo.CustomerDataREPO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

@Service
public class PaymentSERV {
	
	@Value("${stripe.apiKey}")
	String stripeKey;
	
	@Autowired
	CustomerDataREPO customerDataREPO;
	
	public CustomerData addCustomerData(CustomerData customerData) throws StripeException {
		
		CustomerData detailsCustomerData=customerDataREPO.save(customerData);
		Stripe.apiKey = stripeKey;

		Map<String, Object> params = new HashMap<>();
		params.put("id", detailsCustomerData.getId());
		params.put("name", detailsCustomerData.getName());
		params.put("email", detailsCustomerData.getEmail());

		Customer customer = Customer.create(params);
		return detailsCustomerData;
	}

}
