
package com.ziploan.team.webapi.model.category;

import com.google.gson.annotations.SerializedName;

public class BusinessCategoryList {

    @SerializedName("business_category_id")
    private String mBusinessCategoryId;
    @SerializedName("business_category_name")
    private String mBusinessCategoryName;

    public String getBusinessCategoryId() {
        return mBusinessCategoryId;
    }

    public void setBusinessCategoryId(String businessCategoryId) {
        mBusinessCategoryId = businessCategoryId;
    }

    public String getBusinessCategoryName() {
        return mBusinessCategoryName;
    }

    public void setBusinessCategoryName(String businessCategoryName) {
        mBusinessCategoryName = businessCategoryName;
    }

}
