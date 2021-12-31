package com.mcshares.demo.controller;

import com.mcshares.demo.ValidationResultBean;
import com.mcshares.demo.ValidationResultEnum;
import com.mcshares.demo.service.CustomerService;
import com.mcshares.demo.utils.XmlProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * Controller that exposes APIs for customer operations
 * for e.g retrieving all customers saved in DB
 * updating specific record
 */
@Controller
@RequestMapping("/mcshares")
public class FileServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceController.class);

    @Resource
    private XmlProcessor xmlProcessor;

    @Resource
    private CustomerService customerService;


    /**
     * API to upload an XML file and save customer records if valid
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {

        LOGGER.info("START uploadFile for file: {}", file.getOriginalFilename());

        ResponseEntity responseEntity = null;

        if (file.isEmpty()) {
            LOGGER.warn("File is empty");
            return ResponseEntity.ok("File is empty, please upload a valid file");
        }

        ValidationResultBean validationResultBean = xmlProcessor.processFile(file);

        StringBuilder bodyStringbuilder = new StringBuilder().append(file.getOriginalFilename())
                .append(" - ")
                .append(ValidationResultEnum.SUCCESS.getMessage())
                .append(" - ")
                .append(validationResultBean.getCountSuccessfulCreation())
                .append("/")
                .append(validationResultBean.getCountCustomersInFile())
                .append(" saved succesfully.");

        if (validationResultBean.getValidationResultEnumList().isEmpty() && validationResultBean.getCountCustomersInFile() == validationResultBean.getCountSuccessfulCreation()) {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(bodyStringbuilder.toString());
        } else if (!validationResultBean.getValidationResultEnumList().isEmpty()) {
            ValidationResultEnum validationResultEnum = validationResultBean.getValidationResultEnumList().stream().findFirst().get();
            responseEntity = ResponseEntity.status(validationResultEnum.getHttpStatus()).body(bodyStringbuilder.toString());
        }

        LOGGER.info("END uploadFile for file: {}", file.getOriginalFilename());

        return responseEntity;
    }

}
