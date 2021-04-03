package com.example.credeb.Model;

public class TransactionHistory {
    private String Amount;
    private String Date_and_Time;
    private String Receiver;
    private String Receiver_IFSC;
    private String Type_of_transaction;
    private String Sender;
    private String Sender_IFSC;

    public TransactionHistory() {
    }

    public TransactionHistory(String Amount, String Date_and_Time, String Receiver, String Receiver_IFSC, String Type_of_transaction, String Sender, String Sender_IFSC) {
        this.Amount = Amount;
        this.Date_and_Time = Date_and_Time;
        this.Receiver = Receiver;
        this.Receiver_IFSC = Receiver_IFSC;
        this.Type_of_transaction= Type_of_transaction;
        this.Sender= Sender;
        this.Sender_IFSC= Sender_IFSC;
    }

    public String getAmount() {
        return Amount;
    }

    public void setFname(String Amount) {
        this.Amount = Amount;
    }

    public String getDate_and_Time() {
        return Date_and_Time;
    }

    public void setDate_and_Time(String Date_and_Time) {
        this.Date_and_Time = Date_and_Time;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String Receiver) {
        this.Receiver = Receiver;
    }

    public String getReceiver_IFSC() {
        return Receiver_IFSC;
    }

    public void setSender_IFSC(String Sender_IFSC) {
        this.Sender_IFSC = Sender_IFSC;
    }
    public String getSender_IFSC() {
        return Sender_IFSC;
    }

    public void setReceiver_IFSC(String Receiver_IFSC) {
        this.Receiver_IFSC = Receiver_IFSC;
    }
    public String getType_of_transaction() {
        return Type_of_transaction;
    }

    public void setType_of_transaction(String Type_of_transaction) {
        this.Type_of_transaction = Type_of_transaction;
    }
    public String getSender() {
        return Sender;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }
}
