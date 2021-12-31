package com.mcshares.demo.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RequestDoc")
public class RequestDocVO {
    @XmlElement(name = "Doc_Date")
    String docDate;
    @XmlElement(name = "Doc_Ref")
    String docRef;
    @XmlElement(name = "Doc_Data")
    DocDataVO docDataVO;


    public String getDocDate() {
        return docDate;
    }

    public String getDocRef() {
        return docRef;
    }

    public DocDataVO getDocDataVO() {
        if (docDataVO == null) docDataVO = new DocDataVO();
        return docDataVO;
    }


}