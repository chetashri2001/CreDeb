package com.example.credeb.Model;
// APP DATABASE
public class UserBankDetails {

    private String Balance;
    private String account_no;
    private String b_name;
    private String branch;
    private String id;
    private String ifsc;
    private String userID;

    public UserBankDetails() {
    }

    public UserBankDetails(String Balance, String account_no, String b_name, String branch, String id, String ifsc, String userID) {
        this.Balance = Balance;
        this.account_no = account_no;
        this.b_name = b_name;
        this.branch = branch;
        this.id = id;
        this.ifsc=ifsc;
        this.userID=userID;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
