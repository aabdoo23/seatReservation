package com.example.seatreservation;

import java.time.LocalDateTime;
import java.util.*;

public class Hall {
    private int ID;
    private int rows,columns;
    private String name;
    private Map<LocalDateTime, Boolean> slots=new HashMap<>();


    public void markSlotAsBooked(LocalDateTime dateTime) {
        slots.put(dateTime, true);
    }

    public void markSlotAsAvailable(LocalDateTime dateTime) {
        slots.put(dateTime, false);
    }
    public boolean isSlotBooked(LocalDateTime dateTime) {
        return slots.getOrDefault(dateTime, false);
    }

    Hall(int ID,String name,int rows,int columns){
        this.ID=ID;
        this.name=name;
        this.rows=rows;
        this.columns=columns;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return ID+", "+name;
    }
}
