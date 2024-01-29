package com.agmkhair.pigeonpro.ui.model;

public class Member {

    private String id;
    private String clubId;
    private String image;
    private String name;
    private String LoftName;
    private String number;
    private String address;
    private String city;
    private String email;
    private String coin;
    private String lastUpdateTime;

    public Member() {

    }

    public Member(String id, String clubId, String image, String name, String loftName, String number, String address, String city, String email, String coin, String lastUpdateTime) {
        this.id = id;
        this.clubId = clubId;
        this.image = image;
        this.name = name;
        LoftName = loftName;
        this.number = number;
        this.address = address;
        this.city = city;
        this.email = email;
        this.coin = coin;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLoftName() {
        return LoftName;
    }

    public void setLoftName(String loftName) {
        LoftName = loftName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
