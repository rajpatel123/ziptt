package com.ziploan.team.webapi;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.webapi.model.sub_cat_.BusinessCategoryDatum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 24-04-2018.
 */

public class BusinessCategoryResponse implements Serializable {
    @SerializedName("Business_Category_List")
    private ArrayList<BusinessCategory> Business_Category_List;

    @SerializedName("Business_Category_Data")
    private ArrayList<BusinessCategoryDatum> mBusinessCategoryData;



    public ArrayList<BusinessCategory> getBusiness_Category_List() {
        return Business_Category_List;
    }

    public void setBusiness_Category_List(ArrayList<BusinessCategory> business_Category_List) {
        Business_Category_List = business_Category_List;
    }

    public ArrayList<BusinessCategoryDatum> getBusinessCategoryData() {
        return mBusinessCategoryData;
    }

    public void setBusinessCategoryData(ArrayList<BusinessCategoryDatum> businessCategoryData) {
        mBusinessCategoryData = businessCategoryData;
    }

}
