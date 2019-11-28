package edu.icesi.sportapp.model.entity;

public class EventSportRequest {

    public static final String ACCEPTED = "Aceptado";
    public static final String PENDING = "Pendiente";
    public static final String REJECTED = "Rechazado";

    private String uid;
    private String userID;
    private String eventID;
    private String status;

    public EventSportRequest() {
    }

    public EventSportRequest(String uid, String userID, String eventID, String status) {
        this.uid = uid;
        this.userID = userID;
        this.eventID = eventID;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
