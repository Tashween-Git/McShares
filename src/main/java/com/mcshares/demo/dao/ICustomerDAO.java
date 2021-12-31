package com.mcshares.demo.dao;

import com.mcshares.demo.dto.CustomerRecordDTO;
import com.mcshares.demo.model.Customer;

import java.util.List;


public interface ICustomerDAO extends IGenericDAO<Customer> {

    Customer findByCustomerRef(String customerRef);

    List<CustomerRecordDTO> retrieveRecord();

    Customer findCustomerByName(String customerName);
}
