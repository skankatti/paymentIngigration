package com.payment.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.entity.CustomerData;

public interface CustomerDataREPO extends JpaRepository<CustomerData, Integer> {

}
