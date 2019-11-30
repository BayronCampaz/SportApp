package edu.icesi.sportapp.model.entity;

import com.google.android.gms.maps.model.LatLng;

public class EventSport {

    public static final String ACTIVE = "Activo";
    public static final String ENDED = "Terminado";


    private String uid;
    private String ownerID;
    private int photo;
    private String name;
    private String description;
    private int numberPeople;
    private double price;
    private String sport;
    private long date;
   // private LatLng latLng;
    private String status;
    private double latitude;
    private double longitude;
    private String address;


    public EventSport() {
    }

    public EventSport(String uid, String ownerID, int photo, String name, String description, int numberPeople, double price, String sport, long date, double latitude,double longitude,String address, String status) {
        this.uid = uid;
        this.ownerID = ownerID;
        this.photo = photo;
        this.name = name;
        this.description = description;
        this.numberPeople = numberPeople;
        this.price = price;
        this.sport = sport;
        this.date = date;
       // this.latLng = latLng;
        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
     */



    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}