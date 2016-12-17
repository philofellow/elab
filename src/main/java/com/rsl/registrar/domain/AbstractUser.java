package com.rsl.registrar.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by akaizer on 11/10/16.
 */
public class AbstractUser {

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    // todo put this constraint in backend too
    @NotEmpty @Pattern(regexp = "\\+\\d+\\.\\d+")
    private String phone;

    @NotEmpty @Pattern(regexp = "\\d+")
    private String phoneext;

    @NotEmpty @Pattern(regexp = "\\+\\d+\\.\\d+")
    private String fax;

    @NotEmpty @Pattern(regexp = "\\d+")
    private String faxext;

    @NotEmpty
    private String organization;

    @NotEmpty
    private String addressline1;

    private String addressline2;

    private String addressline3;

    @NotEmpty
    private String city;

    @NotEmpty
    private String stateprovince;

    @NotEmpty
    private String postalcode;

    @NotEmpty
    private String countrycode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneext() {
        return phoneext;
    }

    public void setPhoneext(String phoneext) {
        this.phoneext = phoneext;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFaxext() {
        return faxext;
    }

    public void setFaxext(String faxext) {
        this.faxext = faxext;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getAddressline3() {
        return addressline3;
    }

    public void setAddressline3(String addressline3) {
        this.addressline3 = addressline3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateprovince() {
        return stateprovince;
    }

    public void setStateprovince(String stateprovince) {
        this.stateprovince = stateprovince;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String toString() {
        return "Person(name: " + getName()
                + ", addressline1: " + getAddressline1()
                + ", addressline2: " + getAddressline2()
                + ", addressline3: " + getAddressline3()
                + ", city: " + getCity()
                + ", state/province: " + getStateprovince()
                + ", postalcode: " + getPostalcode()
                + ", countrycode: " + getCountrycode()
                + ", organization: " + getOrganization()
                + ", phone: " + getPhone() + "-" + getPhoneext()
                + ", fax: " + getFax()
                + ", email: " + getEmail() + ")";
    }


}
