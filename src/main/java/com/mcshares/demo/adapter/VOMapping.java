package com.mcshares.demo.adapter;

import com.mcshares.demo.CustomerTypeEnum;
import com.mcshares.demo.McSharesConstants;
import com.mcshares.demo.model.ContactDetails;
import com.mcshares.demo.model.Customer;
import com.mcshares.demo.model.MailingAddress;
import com.mcshares.demo.model.SharesDetails;
import com.mcshares.demo.utils.DateUtils;
import com.mcshares.demo.utils.ValidationUtils;
import com.mcshares.demo.vo.ContactDetailsVO;
import com.mcshares.demo.vo.DataItemCustomerVO;
import com.mcshares.demo.vo.MailingAddressVO;
import com.mcshares.demo.vo.SharesDetailsVO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class VOMapping {

    @Resource
    private ValidationUtils validationUtils;

    @Resource
    private DateUtils dateUtils;

    public Customer fromVO(DataItemCustomerVO dataItemCustomerVO) throws Exception {
        Customer customer = new Customer();
        customer.setCustomerRef(dataItemCustomerVO.getCustomerId());
        customer.setCustomerType(dataItemCustomerVO.getCustomerType());
        customer.setDob(dateUtils.fromString(dataItemCustomerVO.getDateOfBirth(), McSharesConstants.DATE_FORMAT_DD_MM_YYYY));
        customer.setDateIncorp(dateUtils.fromString(dataItemCustomerVO.getDateIncorp(), McSharesConstants.DATE_FORMAT_DD_MM_YYYY));
        customer.setRegistrationNo(dataItemCustomerVO.getRegistrationNo());

        MailingAddressVO mailingAddressVO = dataItemCustomerVO.getMailingAddressVO();
        MailingAddress mailingAddress = new MailingAddress();
        mailingAddress.setAddressLine1(mailingAddressVO.getAddressLine1());
        mailingAddress.setAddressLine2(mailingAddressVO.getAddressLine2());
        mailingAddress.setTownCity(mailingAddressVO.getTownCity());
        mailingAddress.setCountry(mailingAddressVO.getCountry());
        customer.setMailingAddress(mailingAddress);

        ContactDetailsVO contactDetailsVO = dataItemCustomerVO.getContactDetailsVO();
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setContactName(contactDetailsVO.getContactName());
        contactDetails.setContactNumber(contactDetailsVO.getContactNumber());
        customer.setContactDetails(contactDetails);

        SharesDetailsVO sharesDetailsVO = dataItemCustomerVO.getSharesDetailsVO();
        SharesDetails sharesDetails = new SharesDetails();
        sharesDetails.setNumShares(sharesDetailsVO.getNumShares());
        sharesDetails.setSharePrice(sharesDetailsVO.getSharePrice());
        sharesDetails.setBalance(sharesDetailsVO.getNumShares() * sharesDetailsVO.getSharePrice());
        customer.setSharesDetails(sharesDetails);


        return customer;
    }

    public SharesDetails fromVO(Customer customer, SharesDetailsVO sharesDetailsVO) {
        SharesDetails sharesDetails = customer.getSharesDetails();
        boolean isShareDetailsValid = validationUtils.validateNumberOfShares(sharesDetailsVO.getNumShares(), null) && validationUtils.validateSharePrice(sharesDetailsVO.getSharePrice(), null);

        if (!isShareDetailsValid) {
            return null;
        }

        if (!CustomerTypeEnum.CORPORATE.name().equalsIgnoreCase(customer.getCustomerType())) {
            sharesDetails.setNumShares(sharesDetailsVO.getNumShares());
        }
        sharesDetails.setSharePrice(sharesDetailsVO.getSharePrice());

        return sharesDetails;
    }
}
