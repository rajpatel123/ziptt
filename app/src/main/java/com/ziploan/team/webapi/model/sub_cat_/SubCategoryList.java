
package com.ziploan.team.webapi.model.sub_cat_;

import com.google.gson.annotations.SerializedName;

public class SubCategoryList {

    @SerializedName("margin")
    private Double mMargin;
    @SerializedName("root_business_category_id")
    private String mRootBusinessCategoryId;
    @SerializedName("sub_category")
    private String mSubCategory;

    public Double getMargin() {
        return mMargin;
    }

    public void setMargin(Double margin) {
        mMargin = margin;
    }

    public String getRootBusinessCategoryId() {
        return mRootBusinessCategoryId;
    }

    public void setRootBusinessCategoryId(String rootBusinessCategoryId) {
        mRootBusinessCategoryId = rootBusinessCategoryId;
    }

    public String getSubCategory() {
        return mSubCategory;
    }

    public void setSubCategory(String subCategory) {
        mSubCategory = subCategory;
    }

    public SubCategoryList(Double mMargin, String mRootBusinessCategoryId, String mSubCategory) {
        this.mMargin = mMargin;
        this.mRootBusinessCategoryId = mRootBusinessCategoryId;
        this.mSubCategory = mSubCategory;
    }
}
