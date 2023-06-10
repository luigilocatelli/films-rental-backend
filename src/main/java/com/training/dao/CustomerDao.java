package com.training.dao;

import com.training.models.Customer;

public interface CustomerDao {

    public Customer getById(Long customerId);
}
