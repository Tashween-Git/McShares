package com.mcshares.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_ERROR_LOG")
public class ErrorLog extends GenericObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ERROR_LOG", updatable = false)
    private int errorLogId;

    @Column(name = "CUSTOMER_REF")
    private String customerRef;

    @Column(name = "TXT_ERROR_DESCRIPTION")
    private String errorDescription;

    @Column(name = "TXT_FILENAME")
    private String filename;

    public int getErrorLogId() {
        return errorLogId;
    }

    public void setErrorLogId(int errorLogId) {
        this.errorLogId = errorLogId;
    }

    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String getPrimaryKey() {
        return "ID_ERROR_LOG";
    }
}
