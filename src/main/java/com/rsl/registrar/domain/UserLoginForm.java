package com.rsl.registrar.domain;

/**
 * This class handles the binding and setting of the login.html page by
 * containing the necessary user information to be validated to log the user in.
 *
 * Created by akaizer on 11/4/16.
 */
public class UserLoginForm {
    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "Person(Email: " + email + ", Password: " + password + ")";
    }
}
