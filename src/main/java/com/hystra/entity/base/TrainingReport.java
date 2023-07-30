package com.hystra.entity.base;


import javax.persistence.*;

/**
 * @author Syed Muhammad Hassan
 * 7th June, 2023
 */

@Entity
@Table(name="TrainingReport")
public class TrainingReport {
    @Id
    @Column(name="id")
    private int id;
    @Column(name = "trainerType")
    private String trainerType;
    @Column(name = "trainingTheme")
    private String trainingTheme;
    @Column(name = "trainingVenue")
    private String trainingVenue;
    @Column(name = "fromDate")
    private String fromDate;
    @Column(name = "toDate")
    private String toDate;
    @Column(name = "participantName")
    private String participantName;
    @Column(name = "gender")
    private String gender;
    @Column(name = "designation")
    private String designation;
    @Column(name = "district")
    private String district;
    @Column(name = "tehsil")
    private String tehsil;
    @Column(name = "facilityName")
    private String facilityName;
    @Column(name = "contactNumber")
    private String contactNumber;
    @Column(name = "nationalIDCard")
    private String nationalIDCard;
    @Column(name = "preScore")
    private String preScore;
    @Column(name = "postScore")
    private String postScore;
    @Column(name = "improvement")
    private String improvement;
    @Column(name = "organizedBy")
    private String organizedBy;

    public String getTrainerType() {
        return trainerType;
    }

    public void setTrainerType(String trainerType) {
        this.trainerType = trainerType;
    }

    public String getTrainingTheme() {
        return trainingTheme;
    }

    public void setTrainingTheme(String trainingTheme) {
        this.trainingTheme = trainingTheme;
    }

    public String getTrainingVenue() {
        return trainingVenue;
    }

    public void setTrainingVenue(String trainingVenue) {
        this.trainingVenue = trainingVenue;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getNationalIDCard() {
        return nationalIDCard;
    }

    public void setNationalIDCard(String nationalIDCard) {
        this.nationalIDCard = nationalIDCard;
    }

    public String getPreScore() {
        return preScore;
    }

    public void setPreScore(String preScore) {
        this.preScore = preScore;
    }

    public String getPostScore() {
        return postScore;
    }

    public void setPostScore(String postScore) {
        this.postScore = postScore;
    }

    public String getOrganizedBy() {
        return organizedBy;
    }

    public void setOrganizedBy(String organizedBy) {
        this.organizedBy = organizedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImprovement() {
        return improvement;
    }

    public void setImprovement(String improvement) {
        this.improvement = improvement;
    }
}
