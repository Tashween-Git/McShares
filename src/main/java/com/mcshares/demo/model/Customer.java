package com.mcshares.demo.model;

import com.mcshares.demo.McSharesConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_CUSTOMERS")
public class Customer extends GenericObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CUSTOMER", updatable = false)
    private int customerId;

    @Column(name = "CUSTOMER_REF")
    private String customerRef;

    @Column(name = "CUSTOMER_TYPE")
    private String customerType;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_BIRTH")
    private Date dob;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_INCORP")
    private Date dateIncorp;

    @Column(name = "NO_REGISTRATION")
    private String registrationNo;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_MAILING_ADDRESS")
    private MailingAddress mailingAddress;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CONTACT_DETAILS")
    private ContactDetails contactDetails;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_SHARES_DETAILS")
    private SharesDetails sharesDetails;


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDateIncorp() {
        return dateIncorp;
    }

    public void setDateIncorp(Date dateIncorp) {
        this.dateIncorp = dateIncorp;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public MailingAddress getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(MailingAddress mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public SharesDetails getSharesDetails() {
        return sharesDetails;
    }

    public void setSharesDetails(SharesDetails sharesDetails) {
        this.sharesDetails = sharesDetails;
    }

    @Override
    @Transient
    public String getPrimaryKey() {
        return "ID_CUSTOMER";
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerRef='" + customerRef + '\'' +
                ", customerType='" + customerType + '\'' +
                ", dob=" + dob +
                ", dateIncorp=" + dateIncorp +
                ", registrationNo='" + registrationNo + '\'' +
                ", mailingAddress=" + mailingAddress +
                ", contactDetails=" + contactDetails +
                ", sharesDetails=" + sharesDetails +
                '}';
    }

    public String toStringForCSV() {
        return String.join(McSharesConstants.CUSTOM_DELIMETER,
                String.valueOf(customerId),
                String.valueOf(customerId),
                String.valueOf(customerRef),
                String.valueOf(customerType),
                String.valueOf(dob),
                String.valueOf(dateIncorp),
                String.valueOf(registrationNo),
                String.valueOf(mailingAddress.getAddressLine1()),
                String.valueOf(mailingAddress.getAddressLine2()),
                String.valueOf(mailingAddress.getCountry()),
                String.valueOf(mailingAddress.getTownCity()),
                String.valueOf(contactDetails.getContactName()),
                String.valueOf(contactDetails.getContactNumber()),
                String.valueOf(sharesDetails.getNumShares()),
                String.valueOf(sharesDetails.getSharePrice()),
                String.valueOf(sharesDetails.getBalance())
        );
    }
}
