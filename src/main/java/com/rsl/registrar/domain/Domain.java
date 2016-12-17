package com.rsl.registrar.domain;

/**
 * Created by akaizer on 11/15/16.
 */
public class Domain {

    private Long id;

    private String domainName;

    private Long registrantId;

    private String createdDate;

    private String updatedDate;

    private String expiredDate;

    public Long getId() {
        return id;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public Long getRegistrantId() {
        return registrantId;
    }

    public void setRegistrantId(Long registrantId) {
        this.registrantId = registrantId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }


    @Override
    public String toString() {
        return "Domain{" +
                "id=" + id +
                ", domain_name='" + domainName +
                ", registrant_id='" + registrantId +
                ", created_date='" + createdDate +
                ", updated_date='" + updatedDate +
                ", expired_date='" + expiredDate +
                '}';
    }
}
