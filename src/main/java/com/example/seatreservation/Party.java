package com.example.seatreservation;

import java.time.LocalDateTime;

public class Party {
    private int ID;
    private LocalDateTime startTime,endTime;
    private int movieID;
    private int hallID;
    int numberOfBookedSeats;
    Party(){}
    Party(int id,LocalDateTime st, LocalDateTime et, int movieId, int hallId, int numberOfBookedSeats){
        this.ID=id;
        this.startTime=st;
        this.endTime=et;
        this.hallID=hallId;
        this.movieID=movieId;
        this.numberOfBookedSeats=numberOfBookedSeats;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setHallID(int hallID) {
        this.hallID = hallID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public void setNumberOfBookedSeats(int numberOfBookedSeats) {
        this.numberOfBookedSeats = numberOfBookedSeats;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getNumberOfBookedSeats() {
        return numberOfBookedSeats;
    }

    public int getHallID() {
        return hallID;
    }

    public int getID() {
        return ID;
    }

    public int getMovieID() {
        return movieID;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "Party{" +
                "ID=" + ID +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", movieID=" + movieID +
                ", hallID=" + hallID +
                ", numberOfBookedSeats=" + numberOfBookedSeats +
                '}';
    }
}
