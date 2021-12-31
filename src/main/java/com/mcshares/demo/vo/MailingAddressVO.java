package com.mcshares.demo.vo;

import javax.xml.bind.annotation.XmlElement;

public class MailingAddressVO {
    @XmlElement(name = "Address_Line1")
    String addressLine1;
    @XmlElement(name = "Address_Line2")
    String addressLine2;
    @XmlElement(name = "Country")
    String country;
    @XmlElement(name = "Town_City")
    String townCity;


    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCountry() {
        return country;
    }

    public String getTownCity() {
        return townCity;
    }


}