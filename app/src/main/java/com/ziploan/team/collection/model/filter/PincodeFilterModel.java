package com.ziploan.team.collection.model.filter;

public class PincodeFilterModel {
    private String pincode;
    public boolean selected;

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
