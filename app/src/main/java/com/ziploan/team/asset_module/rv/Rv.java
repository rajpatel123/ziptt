
package com.ziploan.team.asset_module.rv;

import com.google.gson.annotations.SerializedName;

public class Rv {

    @SerializedName("applicant")
    private String mApplicant;
    @SerializedName("url")
    private String mUrl;

    public Rv(String mApplicant, String mUrl) {
        this.mApplicant = mApplicant;
        this.mUrl = mUrl;
    }

    public String getApplicant() {
        return mApplicant;
    }

    public void setApplicant(String applicant) {
        mApplicant = applicant;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
