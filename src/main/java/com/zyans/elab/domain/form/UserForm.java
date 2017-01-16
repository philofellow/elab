package com.zyans.elab.domain.form;

import com.zyans.elab.domain.Role;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.*;

public class UserForm {

    @NotEmpty @Email
    private String email;

    @NotEmpty
    private String name;

    @NotEmpty @Size(min = 6, max = 12)
    private String password;

    @NotEmpty
    private String organization;

    @NotNull
    private String role = Role.USER;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toString() {
        return "Person(name: " + getName()
                + ", password: " + getPassword()
                + ", email: " + getEmail()
                + ", organization: " + getOrganization()
                + ", role : " + getRole() + ")";
    }
}
