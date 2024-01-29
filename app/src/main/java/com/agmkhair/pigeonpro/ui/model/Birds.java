package com.agmkhair.pigeonpro.ui.model;

public class Birds
{
    private String id;
    private String userId;
    private String clubId;
    private String imageEye;
    private String imageBody;
    private String type;
    private String name;
    private String ring;
    private String gender;
    private String recentRace;
    private String details;
    private String breededBy;
    private String father_ring;
    private String mother_ring;
    private String catagory;
    private String sale;




    //private String type;

    public Birds() { }


    public Birds(String id, String userId, String clubId, String imageEye, String imageBody, String type, String name, String ring, String gender, String recentRace, String details, String breededBy, String father_ring, String mother_ring, String catagory, String sale) {
        this.id = id;
        this.userId = userId;
        this.clubId = clubId;
        this.imageEye = imageEye;
        this.imageBody = imageBody;
        this.type = type;
        this.name = name;
        this.ring = ring;
        this.gender = gender;
        this.recentRace = recentRace;
        this.details = details;
        this.breededBy = breededBy;
        this.father_ring = father_ring;
        this.mother_ring = mother_ring;
        this.catagory = catagory;
        this.sale = sale;
    }


    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getImageBody() {
        return imageBody;
    }

    public void setImageBody(String imageBody) {
        this.imageBody = imageBody;
    }

    public String getImageEye() {
        return imageEye;
    }

    public void setImageEye(String imageEye) {
        this.imageEye = imageEye;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRing() {
        return ring;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRecentRace() {
        return recentRace;
    }

    public void setRecentRace(String recentRace) {
        this.recentRace = recentRace;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getBreededBy() {
        return breededBy;
    }

    public void setBreededBy(String breededBy) {
        this.breededBy = breededBy;
    }

    public String getFather_ring() {
        return father_ring;
    }

    public void setFather_ring(String father_ring) {
        this.father_ring = father_ring;
    }

    public String getMother_ring() {
        return mother_ring;
    }

    public void setMother_ring(String mother_ring) {
        this.mother_ring = mother_ring;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }
}