package com.agmkhair.pigeonpro.ui.model;

public class Club
{

    private String  clubId;
    private String  name;
    private String  address;
    private String  phoneNumber;
    private String  clubTeg;


    public Club() {
    }

    public Club(String clubId, String name, String address, String phoneNumber, String clubTeg)
    {
        this.clubId = clubId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.clubTeg = clubTeg;
    }

    public String getClubTeg() {
        return clubTeg;
    }

    public void setClubTeg(String clubTeg) {
        this.clubTeg = clubTeg;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
