package com.payment.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.payment.entity.CustomerData;
import com.payment.service.PaymentSERV;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;

@Controller
public class PaymentCTRL {

	@Value("${stripe.apiKey}")
	String stripeKey;

	@Autowired
	PaymentSERV paymentSERV;

	@RequestMapping("/index")
	public String index(Model model) throws StripeException {
		Stripe.apiKey = stripeKey;

		Map<String, Object> parms = new HashMap<>();
		parms.put("limit", 3);

		CustomerCollection customerCollection = Customer.list(parms);
		List<CustomerData> allCustomerList = new ArrayList<CustomerData>();
		for (int i = 0; i < customerCollection.getData().size(); i++) {
			CustomerData customerData = new CustomerData();
			customerData.setId(customerCollection.getData().get(i).getId());
			customerData.setName(customerCollection.getData().get(i).getName());
			customerData.setEmail(customerCollection.getData().get(i).getEmail());
			allCustomerList.add(customerData);
		}
		model.addAttribute("customers", allCustomerList);
		return "index";
	}

	@RequestMapping("/createCustomer")
	public String createCustomer(CustomerData customerData) {
		return "create-customer";
	}

	@RequestMapping("/add-customer-to-striper")
	public String addCustomerToStriper(CustomerData customerData) throws StripeException {
		return paymentSERV.addCustomerDataToStripe(customerData);
	}

	@RequestMapping("/get-all-customer")
	public ResponseEntity<?> getAllCustomerData() throws StripeException {
		return new ResponseEntity<>(paymentSERV.getAllCustomer(), HttpStatus.OK);
	}

}
