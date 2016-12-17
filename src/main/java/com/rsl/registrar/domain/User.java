package com.rsl.registrar.domain;

/**
 * Created by akaizer on 11/8/16.
 */
public class User extends AbstractUser {
    private Long id;

    private String passwordHash;

    private String apikey;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    @Override
    public String toString() {
        String returnStr = "User{id=" + id + ", apikey='" + apikey +'"';
        if (getEmail() != null) returnStr += ", email='" + getEmail().replaceFirst("@.*", "@***") +"'";
        if (passwordHash != null) returnStr += ", passwordHash='" + passwordHash;
        if (getName() != null) returnStr +=  ", name ='" + getName();
        if (getOrganization() != null) returnStr += ", organization ='" + getOrganization();
        if (getPhone() != null) returnStr += ", phone ='" + getPhone();
        if (getFax() != null) returnStr += ", fax ='" + getFax();
        if (getAddressline1() != null) returnStr += ", addressline1 ='" + getAddressline1();
        if (getAddressline2() != null) returnStr += ", addressline2 ='" + getAddressline2();
        if (getAddressline3() != null) returnStr += ", addressline3 ='" + getAddressline3();
        if (getCity() != null) returnStr += ", city ='" + getCity();
        if (getStateprovince() != null) returnStr += ", state/province ='" + getStateprovince();
        if (getPostalcode() != null) returnStr += ", postalcode ='" + getPostalcode();
        if (getCountrycode() != null) returnStr += ", countrycode ='" + getCountrycode();
        returnStr = "}";
        return returnStr;
    }
}
