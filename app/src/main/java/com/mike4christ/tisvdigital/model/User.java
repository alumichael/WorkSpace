package com.mike4christ.tisvdigital.model;

public class User {
    public String designation;
    public String email;
    public String firebaseToken;
    public String firstname;
    public String lastname;
    public String link;
    public String password;
    public String phone_num;

    public User(String firstname, String lastname, String designation, String phone_num, String email, String password, String link, String firebaseToken) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.designation = designation;
        this.phone_num = phone_num;
        this.email = email;
        this.password = password;
        this.link = link;
        this.firebaseToken = firebaseToken;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone_num() {
        return this.phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFirebaseToken() {
        return this.firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
}
