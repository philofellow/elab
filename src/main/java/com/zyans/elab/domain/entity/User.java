package com.zyans.elab.domain.entity;

import com.zyans.elab.domain.Role;

import javax.persistence.*;

@Entity
@Table(name = "lab_user")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // todo use hash instead of plain text
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "organization", nullable = false)
    private String organization;

    @Column(name = "role", nullable = false)
    private String role = Role.USER;

    public long getId() {
        return id;
    }


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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Person(username: " + getUsername()
                + ", password: " + getPassword()
                + ", email: " + getEmail()
                + ", organization: " + getOrganization()
                + ", role : " + getRole() + ")";
    }
}
