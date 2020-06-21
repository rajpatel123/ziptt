
package com.ziploan.team.webapi.model.sub_cat_;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubCatResponse {

    @SerializedName("sub_category_list")
    private List<SubCategoryList> mSubCategoryList;

    public List<SubCategoryList> getSubCategoryList() {
        return mSubCategoryList;
    }

    public void setSubCategoryList(List<SubCategoryList> subCategoryList) {
        mSubCategoryList = subCategoryList;
    }

}
