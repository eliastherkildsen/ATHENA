package org.apollo.template.Model;

public class Email {

    private int emailID;
    private String email;

    public Email(String email) {
        this.email = email;
    }

    public Email(int emailID, String email) {
        this.emailID = emailID;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public int getEmailID() {
        return emailID;
    }
}
