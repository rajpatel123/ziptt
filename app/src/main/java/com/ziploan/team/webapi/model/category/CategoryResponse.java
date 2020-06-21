
package com.ziploan.team.webapi.model.category;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoryResponse {

    @SerializedName("Business_Category_List")
    private List<BusinessCategoryList> mBusinessCategoryList;
    @SerializedName("Business_Category_List_1")
    private List<String> mBusinessCategoryList1;

    public List<BusinessCategoryList> getBusinessCategoryList() {
        return mBusinessCategoryList;
    }

    public void setBusinessCategoryList(List<BusinessCategoryList> businessCategoryList) {
        mBusinessCategoryList = businessCategoryList;
    }

    public List<String> getBusinessCategoryList1() {
        return mBusinessCategoryList1;
    }

    public void setBusinessCategoryList1(List<String> businessCategoryList1) {
        mBusinessCategoryList1 = businessCategoryList1;
    }

}
