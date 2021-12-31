package com.mcshares.demo;

import java.util.ArrayList;
import java.util.List;

public class ValidationResultBean {

    List<ValidationResultEnum> validationResultEnumList = new ArrayList<>();


    private int countCustomersInFile;
    private int countSuccessfulCreation;

    public List<ValidationResultEnum> getValidationResultEnumList() {
        return validationResultEnumList;
    }

    public void setValidationResultEnumList(List<ValidationResultEnum> validationResultEnumList) {
        this.validationResultEnumList = validationResultEnumList;
    }

    public int getCountCustomersInFile() {
        return countCustomersInFile;
    }

    public void setCountCustomersInFile(int countCustomersInFile) {
        this.countCustomersInFile = countCustomersInFile;
    }

    public int getCountSuccessfulCreation() {
        return countSuccessfulCreation;
    }

    public void setCountSuccessfulCreation(int countSuccessfulCreation) {
        this.countSuccessfulCreation = countSuccessfulCreation;
    }
}
