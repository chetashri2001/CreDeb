package com.example.credeb.Model;

public class BankUserDetails {

        private String address;
        private String cust_ID;
        private String emailID;
        private String fname;
        private String id;
        private String ifscCode;
        private String lname;
        private String mobile;

        public BankUserDetails() {
        }

        public BankUserDetails(String address, String cust_ID, String emailID, String fname, String id, String ifscCode, String lname, String mobile) {
            this.address = address;
            this.cust_ID = cust_ID;
            this.emailID = emailID;
            this.id = id;
            this.fname=fname;
            this.ifscCode=ifscCode;
            this.lname=lname;
            this.mobile=mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String Balance) {
            this.address = address;
        }

        public String getCust_ID() {
            return cust_ID;
        }

        public void setCust_ID(String cust_ID) {
            this.cust_ID = cust_ID;
        }

        public String getEmailID() {
            return emailID;
        }

        public void setEmailID(String b_name) {
            this.emailID = emailID;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String branch) {
            this.fname = fname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIfscCode() {
            return ifscCode;
        }

        public void setIfscCode(String ifsc) {
            this.ifscCode = ifscCode;
        }
        public String getLname() {
            return lname;
        }

        public void setLname(String userID) {
            this.lname = lname;
        }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String userID) {
        this.mobile = mobile;
    }

}
