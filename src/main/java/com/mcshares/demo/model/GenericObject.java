package com.mcshares.demo.model;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@FilterDefs({@FilterDef(name = "filterDeleted", defaultCondition = " BOO_DELETED = 'FALSE'")})
@Filters({@Filter(name = "filterDeleted")})
public abstract class GenericObject implements Serializable {

    @Column(name = "BOO_DELETED")
    private boolean deleted;

    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public void onUpdate() {
        this.dateUpdate = new Date();
    }

    @Transient
    public abstract String getPrimaryKey();
}
