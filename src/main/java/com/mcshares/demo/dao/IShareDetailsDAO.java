package com.mcshares.demo.dao;

import com.mcshares.demo.model.SharesDetails;


public interface IShareDetailsDAO extends IGenericDAO<SharesDetails> {

    public SharesDetails updateAndRecalculateBalance(SharesDetails sharesDetails);

}
