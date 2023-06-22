package com.dju.demo.helpers;

public class InviteLinkData {
    private final int userID;
    private final int travelID;

    public int getUserID() {
        return userID;
    }

    public int getTravelID() {
        return travelID;
    }

    public InviteLinkData(int userID, int travelID) {
        this.userID = userID;
        this.travelID = travelID;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!this.getClass().equals(obj.getClass())) {
            return false;
        }

        InviteLinkData item = (InviteLinkData) obj;
        return item.getUserID() == userID && item.getTravelID() == travelID;
    }
}
