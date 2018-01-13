package com.example.xtian.cecelectionvotingsystem.getSetterItem;

/**
 * Created by Xtian on 11/19/2015.
 */
public class item_position {

    String positionID;
    String positionName;


    public String getPositionID() {
        return positionID;
    }

    public void setPositionID(String positionID) {
        this.positionID = positionID;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public item_position(String positionID, String positionName) {
        this.positionID = positionID;
        this.positionName = positionName;

    }
}
