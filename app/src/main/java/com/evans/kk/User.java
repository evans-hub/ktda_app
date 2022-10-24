package com.evans.kk;

public class User {
    String number,full_name,phone_number,collection,email_address,userId;

    public User() {
    }

    public User(String number, String full_name, String phone_number, String collection, String email_address, String userId) {
        this.number = number;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.collection = collection;
        this.email_address = email_address;
        this.userId = userId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
