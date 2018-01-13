package com.example.xtian.cecelectionvotingsystem.getSetterItem;

/**
 * Created by Xtian on 11/19/2015.
 */
public class item_candidates {

    String candidate_id;
    String student_id;
    String position_id;
    String party_id;
    String vote_number;
    String candidate_coin;


    public String getCandidate_id() {
        return candidate_id;
    }

    public void setCandidate_id(String candidate_id) {
        this.candidate_id = candidate_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getPosition_id() {
        return position_id;
    }

    public void setPosition_id(String position_id) {
        this.position_id = position_id;
    }

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public String getVote_number() {
        return vote_number;
    }

    public void setVote_number(String vote_number) {
        this.vote_number = vote_number;
    }

    public String getCandidate_coin() {
        return candidate_coin;
    }

    public void setCandidate_coin(String candidate_coin) {
        this.candidate_coin = candidate_coin;
    }

    public item_candidates(String candidate_id, String student_id, String position_id, String party_id, String vote_number, String candidate_coin) {
        this.candidate_id=candidate_id;
        this.student_id = student_id;
        this.position_id = position_id;
        this.party_id = party_id;
        this.vote_number = vote_number;
        this.candidate_coin = candidate_coin;
    }
}
