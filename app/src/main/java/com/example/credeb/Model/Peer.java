package com.example.credeb.Model;

public class Peer {
    String amount;
    String lender_accountNo;
    String lender_bank;
    String lender_ifsc;
    String lender_name;
    String lender_userID;
    String rec_accountNo;
    String rec_bank;
    String rec_ifsc;
    String return_date;
    String status;
    String userID;
    String id;
    String bounce;

    public Peer() {
    }

    public Peer(String bounce, String amount, String lender_accountNo, String lender_bank, String lender_ifsc, String lender_name, String lender_userID, String rec_accountNo, String rec_bank, String rec_ifsc, String return_date, String status, String userID, String id) {
        this.amount = amount;
        this.lender_accountNo = lender_accountNo;
        this.lender_bank = lender_bank;
        this.lender_ifsc = lender_ifsc;
        this.lender_name = lender_name;
        this.lender_userID = lender_userID;
        this.rec_accountNo = rec_accountNo;
        this.rec_bank = rec_bank;
        this.rec_ifsc = rec_ifsc;
        this.return_date = return_date;
        this.status = status;
        this.userID = userID;
        this.id = id;
        this.bounce = bounce;
    }

    public String getBounce() {
        return bounce;
    }

    public void setBounce(String bounce) {
        this.bounce = bounce;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLender_accountNo() {
        return lender_accountNo;
    }

    public void setLender_accountNo(String lender_accountNo) {
        this.lender_accountNo = lender_accountNo;
    }

    public String getLender_bank() {
        return lender_bank;
    }

    public void setLender_bank(String lender_bank) {
        this.lender_bank = lender_bank;
    }

    public String getLender_ifsc() {
        return lender_ifsc;
    }

    public void setLender_ifsc(String lender_ifsc) {
        this.lender_ifsc = lender_ifsc;
    }

    public String getLender_name() {
        return lender_name;
    }

    public void setLender_name(String lender_name) {
        this.lender_name = lender_name;
    }

    public String getRec_accountNo() {
        return rec_accountNo;
    }

    public void setRec_accountNo(String rec_accountNo) {
        this.rec_accountNo = rec_accountNo;
    }

    public String getRec_bank() {
        return rec_bank;
    }

    public void setRec_bank(String rec_bank) {
        this.rec_bank = rec_bank;
    }

    public String getRec_ifsc() {
        return rec_ifsc;
    }

    public void setRec_ifsc(String rec_ifsc) {
        this.rec_ifsc = rec_ifsc;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLender_userID() {
        return lender_userID;
    }

    public void setLender_userID(String lender_userID) {
        this.lender_userID = lender_userID;
    }
}
