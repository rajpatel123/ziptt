package com.ziploan.team.asset_module.ews;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EwsResponse implements Serializable {
    @SerializedName("feature")
    private String feature;
    @SerializedName("value")
    private String value;
    @SerializedName("score")
    private String score;


    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
