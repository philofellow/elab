package com.zyans.elab.domain.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.*;

public class LoginForm {

    @NotEmpty
    private String email;

    @NotEmpty @Size(min = 6, max = 12)
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
        return "User(email: " + getEmail() + ", password: " + getPassword() + ")";
    }

}
