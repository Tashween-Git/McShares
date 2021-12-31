package com.mcshares.demo.vo;

import javax.xml.bind.annotation.XmlElement;

public class ContactDetailsVO {
    @XmlElement(name = "Contact_Name")
    String contactName;
    @XmlElement(name = "Contact_Number")
    String contactNumber;

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }


}