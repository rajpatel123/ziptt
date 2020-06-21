
package com.ziploan.team.collection.model.kyc_response;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class FileDataApplicant {

    @SerializedName("applicant_type")
    private Long mApplicantType;
    @SerializedName("category")
    private String mCategory;
    @SerializedName("file_url")
    private String mFileUrl;
    @SerializedName("orig_file_name")
    private String mOrigFileName;
    @SerializedName("sub_category")
    private Object mSubCategory;

    public Long getApplicantType() {
        return mApplicantType;
    }

    public void setApplicantType(Long applicantType) {
        mApplicantType = applicantType;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getFileUrl() {
        return mFileUrl;
    }

    public void setFileUrl(String fileUrl) {
        mFileUrl = fileUrl;
    }

    public String getOrigFileName() {
        return mOrigFileName;
    }

    public void setOrigFileName(String origFileName) {
        mOrigFileName = origFileName;
    }

    public Object getSubCategory() {
        return mSubCategory;
    }

    public void setSubCategory(Object subCategory) {
        mSubCategory = subCategory;
    }

}
