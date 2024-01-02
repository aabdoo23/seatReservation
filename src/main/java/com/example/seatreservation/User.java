package com.example.seatreservation;

public class User {
    private int ID;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private int card;

    User(){}
    public User(int id, String name, String email, String password, String phoneNumber) {
        this.ID = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
    public User(int id, String name, String email, String password, String phoneNumber, int CreditCardID) {
        this.ID = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        card=CreditCardID;
    }

    public int getID() {
        return ID;
    }

    public int getCard() {
        return card;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name;
    }
}
