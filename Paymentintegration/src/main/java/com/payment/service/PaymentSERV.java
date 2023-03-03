package com.payment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.payment.entity.CustomerData;
import com.payment.repo.CustomerDataREPO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;

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
	
	public String addCustomerDataToStripe (CustomerData customerData) throws StripeException  {
		CustomerData detailsCustomerData=customerDataREPO.save(customerData);
		Stripe.apiKey = stripeKey;
		Map<String, Object> params = new HashMap<>();
		params.put("id", detailsCustomerData.getId());
		params.put("name", detailsCustomerData.getName());
		params.put("email", detailsCustomerData.getEmail());

		Customer customer = Customer.create(params);
		return "success";
		
	}
	
public List<CustomerData> getAllCustomer() throws StripeException {
		
		Stripe.apiKey=stripeKey;
		
		
		Map<String, Object> parms= new HashMap<>();
		parms.put("limit", 3);
		
		CustomerCollection customerCollection= Customer.list(parms);
		List<CustomerData> allCustomerList= new ArrayList<CustomerData>();
		for (int i = 0; i < customerCollection.getData().size(); i++) {
			CustomerData customerData=new CustomerData();
			customerData.setId(customerCollection.getData().get(i).getId());
			customerData.setName(customerCollection.getData().get(i).getName());
			customerData.setEmail(customerCollection.getData().get(i).getEmail());
			allCustomerList.add(customerData);
		}
		
		return allCustomerList;
	}
}
