package com.mcshares.demo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_SHARES_DETAILS")
public class SharesDetails extends GenericObject implements Serializable {

    @Column(name = "NUM_SHARES")
    int numShares;
    @Column(name = "SHARE_PRICE")
    Double sharePrice;
    @Column(name = "BALANCE")
    Double balance;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_SHARES_DETAILS")
    private int sharesDetailsId;

    public int getSharesDetailsId() {
        return sharesDetailsId;
    }

    public void setSharesDetailsId(int sharesDetailsId) {
        this.sharesDetailsId = sharesDetailsId;
    }

    public int getNumShares() {
        return numShares;
    }

    public void setNumShares(int numShares) {
        this.numShares = numShares;
    }

    public Double getSharePrice() {
        return sharePrice;
    }

    public void setSharePrice(Double sharePrice) {
        this.sharePrice = sharePrice;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String getPrimaryKey() {
        return "ID_SHARES_DETAILS";
    }

    public void reCalculateBalance() {
        this.setBalance(getNumShares() * getSharePrice());
    }

    @Override
    public String toString() {
        return "SharesDetails{" +
                "sharesDetailsId=" + sharesDetailsId +
                ", numShares=" + numShares +
                ", sharePrice=" + sharePrice +
                ", balance=" + balance +
                '}';
    }
}