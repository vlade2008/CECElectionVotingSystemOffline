package com.example.xtian.cecelectionvotingsystem.getSetterItem;

/**
 * Created by Xtian on 11/19/2015.
 */
public class item_views_candidates {

    String student_ID;
    String student_Name;
    String student_Course;
    String position_ID;
    String position_Name;
    String party_ID;
    String party_Name;
    String vote_Number;
    String candidate_coin;


    public String getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(String student_ID) {
        this.student_ID = student_ID;
    }

    public String getStudent_Name() {
        return student_Name;
    }

    public void setStudent_Name(String student_Name) {
        this.student_Name = student_Name;
    }

    public String getStudent_Course() {
        return student_Course;
    }

    public void setStudent_Course(String student_Course) {
        this.student_Course = student_Course;
    }

    public String getPosition_ID() {
        return position_ID;
    }

    public void setPosition_ID(String position_ID) {
        this.position_ID = position_ID;
    }

    public String getPosition_Name() {
        return position_Name;
    }

    public void setPosition_Name(String position_Name) {
        this.position_Name = position_Name;
    }

    public String getParty_ID() {
        return party_ID;
    }

    public void setParty_ID(String party_ID) {
        this.party_ID = party_ID;
    }

    public String getParty_Name() {
        return party_Name;
    }

    public void setParty_Name(String party_Name) {
        this.party_Name = party_Name;
    }

    public String getVote_Number() {
        return vote_Number;
    }

    public void setVote_Number(String vote_Number) {
        this.vote_Number = vote_Number;
    }

    public String getCandidate_coin() {
        return candidate_coin;
    }

    public void setCandidate_coin(String candidate_coin) {
        this.candidate_coin = candidate_coin;
    }

    public item_views_candidates(String student_id, String student_name, String student_course, String position_id, String position_name, String party_id, String party_name, String vote_number, String candidate_coin) {
        this.student_ID = student_id;
        this.student_Name = student_name;
        this.student_Course = student_course;
        this.position_ID = position_id;
        this.position_Name = position_name;
        this.party_ID = party_id;
        this.party_Name = party_name;
        this.vote_Number = vote_number;
        this.candidate_coin = candidate_coin;
    }
}
