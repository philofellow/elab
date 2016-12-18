package com.zyans.elab.domain.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.*;

public class LoginForm {

    @NotEmpty
    private String username;

    @NotEmpty @Size(min = 6, max = 12)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "User(username: " + getUsername() + ", password: " + getPassword() + ")";
    }

}
