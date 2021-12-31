package com.mcshares.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;

public class SessionDAOHelper {

    @Resource
    protected SessionFactory sessionFactory;

    public void flush() {
        this.sessionFactory.getCurrentSession().flush();
    }

    public void clear() {
        this.sessionFactory.getCurrentSession().clear();
    }

    /**
     * method to return the current hibernate session WITH FILTERS ENABLED for e.g DELETED FILTER
     *
     * @return
     */
    public Session getFilteredCurrentSession() {
        Session result = this.sessionFactory.getCurrentSession();
        result.enableFilter("filterDeleted");

        return result;
    }
}
