package com.example.seatreservation;

import java.time.LocalDate;

public class CreditCard {
    private int CVV,ID;
    private String cardNumber;
    private String holderName;
    LocalDate expDate;
    CreditCard(int id,String cardNumber, int cvv, LocalDate expDate, String holderName){
        this.ID=id;
        this.cardNumber=cardNumber;
        this.CVV=cvv;
        this.expDate=expDate;
        this.holderName=holderName;
    }

    public CreditCard() {

    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public void setCVV(int CVV) {
        this.CVV = CVV;
    }
    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public int getCVV() {
        return CVV;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public String getHolderName() {
        return holderName;
    }

    @Override
    public String toString() {
        return "CreditCard" +
                "\n, cardNumber=" + cardNumber +
                "\n, CVV=" + CVV +
                "\n, expDate=" + expDate +
                "\n, holderName=" + holderName +
                '\n';
    }
    public String discreteToString() {
        return "CreditCard" +
                "\n, cardNumber=" + cardNumber +
                "\n, expDate=" + expDate +
                '\n';
    }
}
