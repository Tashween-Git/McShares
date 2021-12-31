package com.mcshares.demo.dao.impl;

import com.mcshares.demo.dao.IShareDetailsDAO;
import com.mcshares.demo.model.SharesDetails;
import org.springframework.stereotype.Repository;

@Repository
public class ShareDetailsDAOImpl extends GenericDAOImpl<SharesDetails> implements IShareDetailsDAO {

    public SharesDetails updateAndRecalculateBalance(SharesDetails sharesDetails) {
        sharesDetails.reCalculateBalance();
        return super.update(sharesDetails);
    }
}
