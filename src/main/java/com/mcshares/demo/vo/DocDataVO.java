package com.mcshares.demo.vo;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class DocDataVO {
    @XmlElement(name = "DataItem_Customer")
    List<DataItemCustomerVO> dataItemCustomerVOList;

    public List<DataItemCustomerVO> getDataItemCustomerVOList() {
        if (dataItemCustomerVOList == null)
            dataItemCustomerVOList = new ArrayList<>();
        return dataItemCustomerVOList;
    }

}