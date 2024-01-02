package com.example.seatreservation;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.LinkedList;

public class Movie {
    private int ID,screenTime;
    private String movieName,description,genre;
    private Image img;

    private LocalDate releaseDate;
    Movie(){}
    Movie(int id, int st, String mn, String dc, Image img, LocalDate rd, String genre){
        this.ID=id;
        this.screenTime=st;
        this.movieName=mn;
        this.description=dc;
        this.img=img;
        this.releaseDate=rd;
        this.genre=genre;

    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setScreenTime(int screenTime) {
        this.screenTime = screenTime;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public int getID() {
        return ID;
    }

    public Image getImg() {
        return img;
    }

    public int getScreenTime() {
        return screenTime;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public String getMovieName() {
        return movieName;
    }

    @Override
    public String toString() {
        return movieName;
    }
}
