package com.mcshares.demo.dao.impl;

import com.mcshares.demo.dao.IErrorLogDAO;
import com.mcshares.demo.model.ErrorLog;
import org.springframework.stereotype.Repository;

@Repository
public class ErrorLogDAOImpl extends GenericDAOImpl<ErrorLog> implements IErrorLogDAO {

}
