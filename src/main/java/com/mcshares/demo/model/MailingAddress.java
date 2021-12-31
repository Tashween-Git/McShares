package com.mcshares.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_MAILING_ADDRESS")
public class MailingAddress extends GenericObject implements Serializable {

    @Column(name = "TXT_ADDRESS_1")
    String addressLine1;
    @Column(name = "TXT_ADDRESS_2")
    String addressLine2;
    @Column(name = "TXT_TOWN_CITY")
    String townCity;
    @Column(name = "TXT_COUNTRY")
    String country;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_MAILING_ADDRESS")
    private int mailingAddressId;

    public int getMailingAddressId() {
        return mailingAddressId;
    }

    public void setMailingAddressId(int mailingAddressId) {
        this.mailingAddressId = mailingAddressId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getTownCity() {
        return townCity;
    }

    public void setTownCity(String townCity) {
        this.townCity = townCity;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getPrimaryKey() {
        return "ID_MAILING_ADDRESS";
    }

    @Override
    public String toString() {
        return "MailingAddress{" +
                "mailingAddressId=" + mailingAddressId +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", townCity='" + townCity + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}