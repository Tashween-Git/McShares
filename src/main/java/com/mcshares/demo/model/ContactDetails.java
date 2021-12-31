package com.mcshares.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_CONTACT_DETAILS")
public class ContactDetails extends GenericObject implements Serializable {
    @Column(name = "CONTACT_NAME")
    String contactName;
    @Column(name = "CONTACT_NUMBER")
    String contactNumber;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CONTACT_DETAILS")
    private int contactDetailsId;

    public int getContactDetailsId() {
        return contactDetailsId;
    }

    public void setContactDetailsId(int contactDetailsId) {
        this.contactDetailsId = contactDetailsId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String getPrimaryKey() {
        return "ID_CONTACT_DETAILS";
    }

    @Override
    public String toString() {
        return "ContactDetails{" +
                "contactDetailsId=" + contactDetailsId +
                ", contactName='" + contactName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}