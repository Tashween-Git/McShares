package com.mcshares.demo.vo;

import javax.xml.bind.annotation.XmlElement;

public class DataItemCustomerVO {
    @XmlElement(name = "Customer_Type")
    String customerType;
    @XmlElement(name = "Date_Incorp")
    String dateIncorp;
    @XmlElement(name = "Date_Of_Birth")
    String dateOfBirth;
    @XmlElement(name = "IsMulti")
    String isMulti;
    @XmlElement(name = "Registration_No")
    String registrationNo;
    @XmlElement(name = "customer_id")
    String customerId;
    @XmlElement(name = "Mailing_Address")
    MailingAddressVO mailingAddressVO;
    @XmlElement(name = "Contact_Details")
    ContactDetailsVO contactDetailsVO;
    @XmlElement(name = "Shares_Details")
    SharesDetailsVO sharesDetailsVO;


    public String getCustomerType() {
        return customerType;
    }

    public String getDateIncorp() {
        return dateIncorp;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getIsMulti() {
        return isMulti;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public String getCustomerId() {
        return customerId;
    }


    public MailingAddressVO getMailingAddressVO() {
        if (mailingAddressVO == null) mailingAddressVO = new MailingAddressVO();
        return mailingAddressVO;
    }


    public ContactDetailsVO getContactDetailsVO() {
        if (contactDetailsVO == null) contactDetailsVO = new ContactDetailsVO();
        return contactDetailsVO;
    }

    public SharesDetailsVO getSharesDetailsVO() {
        if (sharesDetailsVO == null) sharesDetailsVO = new SharesDetailsVO();
        return sharesDetailsVO;
    }


}