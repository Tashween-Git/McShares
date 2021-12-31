package com.mcshares.demo.vo;

import javax.xml.bind.annotation.XmlElement;

public class SharesDetailsVO {
    @XmlElement(name = "Num_Shares")
    int numShares;
    @XmlElement(name = "Share_Price")
    Double sharePrice;


    public int getNumShares() {
        return numShares;
    }

    public Double getSharePrice() {
        return sharePrice;
    }

}