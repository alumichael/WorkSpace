package com.mike4christ.tisvdigital.events;

public class PushNotificationEvent {
    private String email;
    private String fcmToken;
    private String firstname;
    private String lastname;
    private String link;
    private String message;
    private String title;

    public PushNotificationEvent(String title, String message, String firstname, String lastname, String email, String link, String fcmToken) {
        this.title = title;
        this.message = message;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.link = link;
        this.fcmToken = fcmToken;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getFcmToken() {
        return this.fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
