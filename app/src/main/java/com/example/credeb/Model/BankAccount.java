package com.example.credeb.Model;
// BANK1 DATABASE
public class BankAccount {

    private String account_No;
    private String account_balance;
    private String account_type;
    private String cust_id;
    private String id;

    public BankAccount() {
    }

    public BankAccount(String account_No, String account_balance, String account_type, String cust_id, String id) {
        this.account_No = account_No;
        this.account_balance = account_balance;
        this.account_type = account_type;
        this.cust_id = cust_id;
        this.id = id;
    }

    public String getAccount_No() {
        return account_No;
    }

    public void setAccount_No(String account_No) {
        this.account_No = account_No;
    }

    public String getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(String account_balance) {
        this.account_balance = account_balance;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getCust_id()
    {
        return cust_id;
    }

    public void setCust_id(String cust_id)
    {
        this.cust_id = cust_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
