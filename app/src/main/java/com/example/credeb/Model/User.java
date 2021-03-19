package com.example.credeb.Model;

public class User {
    private String fname;
    private String lname;
    private String mobile;
    private String email_ID;
    private String id;

    public User() {
    }

    public User(String fname, String lname, String mobile, String email_ID, String id) {
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.email_ID = email_ID;
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail_ID() {
        return email_ID;
    }

    public void setEmail_ID(String email_ID) {
        this.email_ID = email_ID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
