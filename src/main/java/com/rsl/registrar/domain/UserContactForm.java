package com.rsl.registrar.domain;

public class UserContactForm extends AbstractUser {

    // id will be generate on backend
    private String id = null;

    private String registrantid = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistrantid() {
        return registrantid;
    }

    public void setRegistrantid(String registrantid) {
        this.registrantid = registrantid;
    }

    public String toString() {
        return "Person(name: " + getName()
                + ", id: " + (id != null ? getId() : "n/a")
                + ", registrantid: " + (registrantid != null ? getRegistrantid() : "n/a")
                + ", addressline1: " + getAddressline1()
                + ", addressline2: " + getAddressline2()
                + ", addressline3: " + getAddressline3()
                + ", city: " + getCity()
                + ", state/province: " + getStateprovince()
                + ", postalcode: " + getPostalcode()
                + ", countrycode: " + getCountrycode()
                + ", organization: " + getOrganization()
                + ", phone: " + getPhone() + 'x' + getPhoneext()
                + ", fax: " + getFax() + 'x' + getFaxext()
                + ", email: " + getEmail() + ")";
    }

}
