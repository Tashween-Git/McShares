package com.mcshares.demo.dao.impl;

import com.mcshares.demo.dao.ICustomerDAO;
import com.mcshares.demo.dto.CustomerRecordDTO;
import com.mcshares.demo.model.Customer;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl extends GenericDAOImpl<Customer> implements ICustomerDAO {

    @Override
    public Customer findByCustomerRef(String customerRef) {

        Query query = getFilteredCurrentSession().createQuery("SELECT c FROM Customer c WHERE c.customerRef = :customerRef ");
        query.setParameter("customerRef", customerRef);
        return (Customer) query.uniqueResult();
    }

    @Override
    public List<CustomerRecordDTO> retrieveRecord() {
        String queryString = "SELECT " +
                " c.customerRef as id, " +
                " c.contactDetails.contactName as customerName, " +
                " (case when c.dob != null then c.dob else dateIncorp END) as dateBirthOrIncorporated, " +
//                " c.dateIncorp as dateIncorporated, " +
                " c.customerType as customerType, " +
                " c.sharesDetails.numShares as numberOfShares, " +
                " c.sharesDetails.sharePrice as sharePrice, " +
                " c.sharesDetails.balance as balance " +
                " FROM Customer c ";
        Query query = getFilteredCurrentSession().createQuery(queryString).setResultTransformer(Transformers.aliasToBean(CustomerRecordDTO.class));
        return (List<CustomerRecordDTO>) query.list();
    }

    public Customer findCustomerByName(String customerName) {
        Query query = getFilteredCurrentSession().createQuery("SELECT c FROM Customer c WHERE LOWER(c.contactDetails.contactName) LIKE LOWER(:customerName) ");
        query.setParameter("customerName", "%" + customerName + "%");
        return (Customer) query.uniqueResult();

    }
}
