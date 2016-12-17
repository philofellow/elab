package com.rsl.registrar.domain;

/**
 * This class handles the binding and setting of the signupAndEdit.html page by
 * containing the necessary user information to be set/validated.
 *
 * Created by akaizer on 11/4/16.
 */
public class UserCreateForm extends AbstractUser {

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFromUser(User user) {
        setName(user.getName());
        setEmail(user.getEmail());
        setPassword(user.getPasswordHash());
        setPhone(user.getPhone());
        setFax(user.getFax());
        setOrganization(user.getOrganization());
        setAddressline1(user.getAddressline1());
        setAddressline2(user.getAddressline2());
        setAddressline3(user.getAddressline3());
        setCity(user.getCity());
        setStateprovince(user.getStateprovince());
        setPostalcode(user.getPostalcode());
        setCountrycode(user.getCountrycode());
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
                + ", phone: " + getPhone()
                + ", fax: " + getFax()
                + ", email: " + getEmail()
                + ", password: " + getPassword() + ")";
    }
}
