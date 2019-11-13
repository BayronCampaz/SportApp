package edu.icesi.sportapp.model.entity;

public class User {
    private String uid;
    private String name;
    private String email;
    private String password;
    private String cellphone;
    private String favoriteSport;

    public User(String uid, String name, String email, String password, String cellphone, String favoriteSport) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cellphone = cellphone;
        this.favoriteSport = favoriteSport;
    }

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getFavoriteSport() {
        return favoriteSport;
    }

    public void setFavoriteSport(String favoriteSport) {
        this.favoriteSport = favoriteSport;
    }
}
