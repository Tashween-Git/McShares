package com.mcshares.demo.controller;

import com.mcshares.demo.adapter.VOMapping;
import com.mcshares.demo.model.Customer;
import com.mcshares.demo.model.SharesDetails;
import com.mcshares.demo.service.CustomerService;
import com.mcshares.demo.utils.ValidationUtils;
import com.mcshares.demo.vo.SharesDetailsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;

/**
 * Controller that exposes APIs for customer operations
 * for e.g retrieving all customers saved in DB
 * updating specific record
 */
@Controller
@RequestMapping("/mcshares")
public class CustomerServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceController.class);

    @Resource
    private CustomerService customerService;

    @Resource
    private ValidationUtils validationUtils;

    @Resource
    private VOMapping voMapping;


    /**
     * API to either
     * Retrieve all customers if customerRef not added in REST CALL
     * or Retrieve one customer by the customerRef
     *
     * @param customerRef
     * @return 200 OK
     * notFound 404 if no customer found for customerRef input
     */
    @GetMapping({"/retrieveAllCustomers", "/retrieveAllCustomers/{customerRef}"})
    @ResponseBody
    @Transactional
    public ResponseEntity retrieveAllCustomers(@PathVariable(name = "customerRef", required = false) String customerRef) {
        LOGGER.info("START retrieveAllCustomers for customerRef : {}", customerRef);
        ResponseEntity responseEntity;
        if (StringUtils.isEmpty(customerRef)) {
            LOGGER.info("customerRef is empty, proceeding to retrieveAll");
            responseEntity = ResponseEntity.ok(customerService.retrieveAll());
        } else {
            LOGGER.info("Proceeding to find By CustomerRef for customerRef : {}", customerRef);
            Customer customer = customerService.findByCustomerRef(customerRef);
            if (customer != null) {
                responseEntity = ResponseEntity.ok(customer);
            } else {
                LOGGER.info("No customer found customerRef : {}", customerRef);
                responseEntity = ResponseEntity.notFound().build();
            }
        }

        LOGGER.info("END retrieveAllCustomers for customerRef : {}", customerRef);
        return responseEntity;
    }

    /**
     * API to retrieve the following customer information
     * ID
     * Customer Name
     * Date Birth/Incorporated
     * Customer Type
     * Number of Shares
     * Share Price Per unit
     * Balance (Number of Shares * Share Price per unit)
     *
     * @return 200 OK
     */
    @GetMapping("/retrieveRecord")
    @ResponseBody
    @Transactional
    public ResponseEntity retrieveRecord() {

        LOGGER.info("START retrieveRecord");
        ResponseEntity responseEntity;

        responseEntity = ResponseEntity.ok(customerService.retrieveRecord());

        LOGGER.info("END retrieveRecord");

        return responseEntity;
    }

    /**
     * API to update specific record for a customerRef for e.g SharesDetails
     *
     * @param customerRef
     * @param sharesDetailsVO
     * @return 200 OK if sharesDetails updated correctly for customerRef
     * notFound 404 if no customer found for customerRef
     * badRequest 403 if sharesDetails data incorrect or failed any validation
     */
    @PutMapping("/updateRecord/{customerRef}")
    @ResponseBody
    @Transactional
    public ResponseEntity updateRecord(@PathVariable(name = "customerRef") String customerRef, @RequestBody SharesDetailsVO sharesDetailsVO) {
        LOGGER.info("START updateRecord for customerRef: {}, sharesDetailsVO: {}", customerRef, sharesDetailsVO);
        ResponseEntity responseEntity;

        Customer customer = customerService.findByCustomerRef(customerRef);

        if (customer == null) {
            LOGGER.info("No customer found for customerRef : {}", customerRef);
            responseEntity = ResponseEntity.notFound().build();
        } else {
            SharesDetails sharesDetails = voMapping.fromVO(customer, sharesDetailsVO);
            if (sharesDetails == null) {
                LOGGER.info("Share details could not be mapped Please verify share details data. sharesDetailsVO: {}", sharesDetailsVO);
                responseEntity = ResponseEntity.badRequest().body("Please verify share details data");
            } else {
                responseEntity = ResponseEntity.ok(customerService.updateShareDetailsRecord(sharesDetails));
            }
        }

        LOGGER.info("END updateRecord for customerRef: {}, sharesDetailsVO: {}", customerRef, sharesDetailsVO);

        return responseEntity;
    }

    /**
     * API to retrieve a specific customer record by the customer name
     *
     * @param customerName
     * @return
     */
    @GetMapping("/retrieveCustomerByName/{customerName}")
    @ResponseBody
    @Transactional
    public ResponseEntity retrieveCustomerByName(@PathVariable(name = "customerName") String customerName) {
        LOGGER.info("START retrieveCustomerByName for customerName: {}", customerName);
        ResponseEntity responseEntity;

        Customer customer = customerService.retrieveCustomerByName(customerName);

        if (customer == null) {
            LOGGER.info("No customer found for customerName : {}", customerName);
            responseEntity = ResponseEntity.notFound().build();
        } else {
            LOGGER.info("Customer found for customerName : {}, customer: {}", customerName, customer);
            responseEntity = ResponseEntity.ok(customer);
        }

        LOGGER.info("END retrieveCustomerByName for customerName: {}", customerName);
        return responseEntity;
    }


    /**
     * API to generate and download customers records in a CSV file
     *
     * @return 200 OK if CSV file has been generated
     * 500 Internal Server error if any exception during csv file generation
     */
    @GetMapping("/downloadDataAsCSV")
    @ResponseBody
    @Transactional
    public ResponseEntity downloadDataAsCSV() {
        LOGGER.info("START downloadDataAsCSV");
        ResponseEntity responseEntity;

        File csvFile = customerService.downloadRecordAsCSV();
        if (csvFile != null) {
            responseEntity = ResponseEntity.ok(customerService.downloadRecordAsCSV());
        } else {
            responseEntity = ResponseEntity.internalServerError().body("An exception occurred during csv file generation");
        }

        LOGGER.info("END downloadDataAsCSV");

        return responseEntity;
    }
}
