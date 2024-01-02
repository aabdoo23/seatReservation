package com.example.seatreservation;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

public class Ticket {
    private int ID;
    private LocalDateTime issueTime;
    private int userId;
    private int partyId;
    private int seatId;

    public Ticket(){}
    public Ticket(int reservationID, LocalDateTime issueTime,  int user, int party, int seat) {
        this.ID= reservationID;
        this.issueTime=issueTime;
        this.userId = user;
        this.partyId=party;
        this.seatId=seat;
    }

    public int getID() {
        return ID;
    }


    public int getPartyId() {
        return partyId;
    }

    public int getSeatId() {
        return seatId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getIssueTime() {
        return issueTime;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setIssueTime(LocalDateTime issueTime) {
        this.issueTime = issueTime;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getPrice() {
        return DB.getSeatById(seatId).getSeatPrice();
    }

    @Override
    public String toString() {
        return DB.getUserByID(userId).getName()+", "+DB.getMovieByTicketId(ID).getMovieName();
    }
}