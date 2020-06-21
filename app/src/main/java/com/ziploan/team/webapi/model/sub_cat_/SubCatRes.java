
package com.ziploan.team.webapi.model.sub_cat_;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubCatRes {

    @SerializedName("Business_Category_Data")
    private List<BusinessCategoryDatum> mBusinessCategoryData;

    public List<BusinessCategoryDatum> getBusinessCategoryData() {
        return mBusinessCategoryData;
    }

    public void setBusinessCategoryData(List<BusinessCategoryDatum> businessCategoryData) {
        mBusinessCategoryData = businessCategoryData;
    }

}
