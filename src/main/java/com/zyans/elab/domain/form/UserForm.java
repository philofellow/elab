package com.zyans.elab.domain.form;

import com.zyans.elab.domain.Role;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Email;
import javax.validation.constraints.*;

public class UserForm {

    @NotEmpty @Email
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty @Size(min = 6, max = 12)
    private String password;

    @NotEmpty
    private String organization;

    @NotNull
    private Role role = Role.USER;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String toString() {
        return "Person(username: " + getUsername()
                + ", password: " + getPassword()
                + ", email: " + getEmail()
                + ", organization: " + getOrganization()
                + ", role : " + getRole() + ")";
    }
}
