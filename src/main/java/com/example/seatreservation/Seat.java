package com.example.seatreservation;

public class Seat {
    private int ID;
    private boolean Booked;
    private int row, column;
    private double seatPrice;
    private int hallID;

    Seat(){
    }
    Seat(int id, boolean isBooked, int row, int col, double price, int hallId){
        this.ID=id;
        this.column=col;
        this.row=row;
        this.Booked = isBooked;
        this.hallID=hallId;
        this.seatPrice=price;
    }

    public int getID() {
        return ID;
    }

    public int getHallID() {
        return hallID;
    }

    public double getSeatPrice() {
        return seatPrice;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setBooked(boolean booked) {
        Booked = booked;
    }

    public boolean isBooked() {
        return Booked;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setSeatPrice(double seatPrice) {
        this.seatPrice = seatPrice;
    }
}
