package edu.icesi.sportapp.model.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class EventSport {


    private int photo;
    private String name;
    private String description;
    private int numberPeople;
    private double price;
    private String sport;
    private Date date;
    //private Calendar starthour;
    //private double locationLat;
    //private double locationLong;

    public EventSport() {
    }

    public EventSport(int photo, String name, String description, int numberPeople, double price, String sport, Date date) {
        this.photo = photo;
        this.name = name;
        this.description = description;
        this.numberPeople = numberPeople;
        this.price = price;
        this.sport = sport;
        this.date = date;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}