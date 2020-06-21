
package com.ziploan.team.webapi.model.sub_cat_;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusinessCategoryDatum implements Serializable {

    @SerializedName("business_category_name")
    private String mBusinessCategoryName;
    @SerializedName("sub_category_name")
    private String mSubCategoryName;

    public String getBusinessCategoryName() {
        return mBusinessCategoryName;
    }

    public void setBusinessCategoryName(String businessCategoryName) {
        mBusinessCategoryName = businessCategoryName;
    }

    public String getSubCategoryName() {
        return mSubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        mSubCategoryName = subCategoryName;
    }

    public BusinessCategoryDatum(String mBusinessCategoryName, String mSubCategoryName) {
        this.mBusinessCategoryName = mBusinessCategoryName;
        this.mSubCategoryName = mSubCategoryName;
    }
}
