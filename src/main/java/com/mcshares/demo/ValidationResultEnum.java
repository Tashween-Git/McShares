package com.mcshares.demo;

import org.springframework.http.HttpStatus;

public enum ValidationResultEnum {

    SUCCESS(HttpStatus.OK, "Upload Successful"),
    PARTIAL_SUCCESS(HttpStatus.OK, "Upload partially successful"),
    INVALID_EXTENSION(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid file extension, Please provide an XML file"),
    INVALID_AGE(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid age, Please verify XML file"),
    INVALID_NUMBER_OF_SHARES(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid number of shares, Please verify XML file"),
    INVALID_SHARE_PRICE(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid share price, Please verify XML file");

    private HttpStatus httpStatus;

    private String message;

    ValidationResultEnum(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

}
