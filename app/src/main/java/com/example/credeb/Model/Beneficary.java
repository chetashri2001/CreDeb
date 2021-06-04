package com.example.credeb.Model;

public class Beneficary {
    String account_no, bank_name, cust_ID, ifsc, name, userID;

    public Beneficary() {
    }

    public Beneficary(String account_no, String bank_name, String cust_ID, String ifsc, String name, String userID) {
        this.account_no = account_no;
        this.bank_name = bank_name;
        this.cust_ID = cust_ID;
        this.ifsc = ifsc;
        this.name = name;
        this.userID = userID;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCust_ID() {
        return cust_ID;
    }

    public void setCust_ID(String cust_ID) {
        this.cust_ID = cust_ID;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
