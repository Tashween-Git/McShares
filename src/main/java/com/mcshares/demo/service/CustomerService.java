package com.mcshares.demo.service;

import com.mcshares.demo.McSharesConstants;
import com.mcshares.demo.dao.ICustomerDAO;
import com.mcshares.demo.dao.IShareDetailsDAO;
import com.mcshares.demo.dto.CustomerRecordDTO;
import com.mcshares.demo.model.Customer;
import com.mcshares.demo.model.SharesDetails;
import com.mcshares.demo.utils.CSVUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("customerService")
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    @Resource
    private ICustomerDAO customerDAO;
    @Resource
    private IShareDetailsDAO shareDetailsDAO;
    @Resource
    private CSVUtils csvUtils;

    @Transactional
    public Customer addCustomer(Customer customer) {
        return customerDAO.create(customer);
    }

    @Transactional
    public List<Customer> retrieveAll() {
        return customerDAO.findAll();
    }

    @Transactional
    public Customer findByCustomerRef(String customerRef) {
        return customerDAO.findByCustomerRef(customerRef);
    }

    @Transactional
    public List<CustomerRecordDTO> retrieveRecord() {
        return customerDAO.retrieveRecord();
    }

    public SharesDetails updateShareDetailsRecord(SharesDetails sharesDetails) {
        return shareDetailsDAO.updateAndRecalculateBalance(sharesDetails);
    }

    public Customer retrieveCustomerByName(String customerName) {
        return customerDAO.findCustomerByName(customerName);
    }

    public File downloadRecordAsCSV() {

        List<Customer> customer = customerDAO.findAll();

        List<String> customerCSVString = new ArrayList<>();
        customer.stream().forEach(cust -> customerCSVString.add(cust.toStringForCSV()));

        String[] headers = new String[]{"customer_id", "Customer_Type", "Date_Of_Birth", "Date_Incorp", "Registration_No", "Address_Line1", "Address_Line2", "Town_City", "Country",
                "Contact_Name", "Contact_Number", "Num_Shares", "Share_Price"};

        try {
            return csvUtils.createCSVFile(generateFileNameForCustomerRecord(), headers, customerCSVString);
        } catch (IOException e) {
            LOGGER.error("Exception occurred ", e);
            return null;
        }

    }

    private String generateFileNameForCustomerRecord() {
        return "CUSTOMER_RECORDS" + McSharesConstants.UNDERSCORE + new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date()) + McSharesConstants.CSV_EXTENSION;
    }
}
