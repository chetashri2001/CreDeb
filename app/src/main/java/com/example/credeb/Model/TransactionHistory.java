package com.example.credeb.Model;

public class TransactionHistory {
    private String Amount;
    private String Date_and_Time;
    private String Type_of_transaction;
    private String user1, user2, user2_ifsc,user1_ifsc;

    public TransactionHistory() {
    }

    public TransactionHistory(String amount, String date_and_Time, String type_of_transaction, String user1, String user2, String user2_ifsc, String user1_ifsc) {
        Amount = amount;
        Date_and_Time = date_and_Time;
        Type_of_transaction = type_of_transaction;
        this.user1 = user1;
        this.user2 = user2;
        this.user2_ifsc = user2_ifsc;
        this.user1_ifsc = user1_ifsc;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDate_and_Time() {
        return Date_and_Time;
    }

    public void setDate_and_Time(String date_and_Time) {
        Date_and_Time = date_and_Time;
    }

    public String getType_of_transaction() {
        return Type_of_transaction;
    }

    public void setType_of_transaction(String type_of_transaction) {
        Type_of_transaction = type_of_transaction;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getUser2_ifsc() {
        return user2_ifsc;
    }

    public void setUser2_ifsc(String user2_ifsc) {
        this.user2_ifsc = user2_ifsc;
    }

    public String getUser1_ifsc() {
        return user1_ifsc;
    }

    public void setUser1_ifsc(String user1_ifsc) {
        this.user1_ifsc = user1_ifsc;
    }
}
