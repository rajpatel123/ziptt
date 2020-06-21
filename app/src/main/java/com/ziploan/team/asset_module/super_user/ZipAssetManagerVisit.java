package com.ziploan.team.asset_module.super_user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZIploan-Nitesh on 9/15/2017.
 */

public class ZipAssetManagerVisit implements Serializable{
    @SerializedName("total_number_of_visits") private String total_number_of_visits;
    @SerializedName("distance_travelled") private String distance_travelled;
    @SerializedName("date_of_visit") private String date_of_visit;
    @SerializedName("asset_manager_name") private String asset_manager_name;
    @SerializedName("first_visit_time") private String first_visit_time;
    @SerializedName("last_visit_time") private String last_visit_time;
    @SerializedName("coordinates_matched") private String coordinates_matched;

    public String getTotal_number_of_visits() {
        return total_number_of_visits;
    }

    public void setTotal_number_of_visits(String total_number_of_visits) {
        this.total_number_of_visits = total_number_of_visits;
    }

    public String getDistance_travelled() {
        return distance_travelled;
    }

    public void setDistance_travelled(String distance_travelled) {
        this.distance_travelled = distance_travelled;
    }

    public String getDate_of_visit() {
        return date_of_visit;
    }

    public void setDate_of_visit(String date_of_visit) {
        this.date_of_visit = date_of_visit;
    }

    public String getAsset_manager_name() {
        return asset_manager_name;
    }

    public void setAsset_manager_name(String asset_manager_name) {
        this.asset_manager_name = asset_manager_name;
    }

    public String getFirst_visit_time() {
        return first_visit_time;
    }

    public void setFirst_visit_time(String first_visit_time) {
        this.first_visit_time = first_visit_time;
    }

    public String getLast_visit_time() {
        return last_visit_time;
    }

    public void setLast_visit_time(String last_visit_time) {
        this.last_visit_time = last_visit_time;
    }

    public String getCoordinates_matched() {
        return coordinates_matched;
    }

    public void setCoordinates_matched(String coordinates_matched) {
        this.coordinates_matched = coordinates_matched;
    }
}
