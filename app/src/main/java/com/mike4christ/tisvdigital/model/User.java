package com.mike4christ.tisvdigital.model;

public class User {


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

    public String designation;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }



}
