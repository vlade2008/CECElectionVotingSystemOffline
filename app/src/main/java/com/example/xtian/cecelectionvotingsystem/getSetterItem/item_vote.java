package com.example.xtian.cecelectionvotingsystem.getSetterItem;

/**
 * Created by Xtian on 11/19/2015.
 */
public class item_vote {

    String studentID;
    String presidentID;
    String VicepresidentID;
    String secretaryID;
    String tresureID;
    String auditorID;
    String proFirstID;
    String proSecondID;
    String firstyearID;
    String secondyearID;
    String thirdyearID;
    String fourthyearID;
    String voteflag;


    public String getPresidentID() {
        return presidentID;
    }

    public void setPresidentID(String presidentID) {
        this.presidentID = presidentID;
    }

    public String getVicepresidentID() {
        return VicepresidentID;
    }

    public void setVicepresidentID(String vicepresidentID) {
        VicepresidentID = vicepresidentID;
    }

    public String getSecretaryID() {
        return secretaryID;
    }

    public void setSecretaryID(String secretaryID) {
        this.secretaryID = secretaryID;
    }

    public String getTresureID() {
        return tresureID;
    }

    public void setTresureID(String tresureID) {
        this.tresureID = tresureID;
    }

    public String getAuditorID() {
        return auditorID;
    }

    public void setAuditorID(String auditorID) {
        this.auditorID = auditorID;
    }

    public String getProFirstID() {
        return proFirstID;
    }

    public void setProFirstID(String proFirstID) {
        this.proFirstID = proFirstID;
    }

    public String getProSecondID() {
        return proSecondID;
    }

    public void setProSecondID(String proSecondID) {
        this.proSecondID = proSecondID;
    }

    public String getFirstyearID() {
        return firstyearID;
    }

    public void setFirstyearID(String firstyearID) {
        this.firstyearID = firstyearID;
    }

    public String getSecondyearID() {
        return secondyearID;
    }

    public void setSecondyearID(String secondyearID) {
        this.secondyearID = secondyearID;
    }

    public String getThirdyearID() {
        return thirdyearID;
    }

    public void setThirdyearID(String thirdyearID) {
        this.thirdyearID = thirdyearID;
    }

    public String getFourthyearID() {
        return fourthyearID;
    }

    public void setFourthyearID(String fourthyearID) {
        this.fourthyearID = fourthyearID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getVoteflag() {
        return voteflag;
    }

    public void setVoteflag(String voteflag) {
        this.voteflag = voteflag;
    }

    public item_vote(String studentID,String presidentID, String vicepresidentID, String secretaryID, String tresureID, String auditorID, String proFirstID, String proSecondID, String firstyearID, String secondyearID, String thirdyearID, String fourthyearID,String voteflag) {
        this.studentID = studentID;

        this.presidentID = presidentID;
        this.VicepresidentID = vicepresidentID;
        this.secretaryID = secretaryID;
        this.tresureID = tresureID;
        this.auditorID = auditorID;
        this.proFirstID = proFirstID;
        this.proSecondID = proSecondID;
        this.firstyearID = firstyearID;
        this.secondyearID = secondyearID;
        this.thirdyearID = thirdyearID;
        this.fourthyearID = fourthyearID;
        this.voteflag = voteflag;


    }
}
