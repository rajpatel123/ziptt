
package com.ziploan.team.asset_module.rv;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AllRvResponse {

    @SerializedName("business_place_photos")
    private List<String> mBusinessPlacePhotos;
    @SerializedName("bv")
    private String mBv;
    @SerializedName("rv")
    private List<Rv> mRv;

    public List<String> getBusinessPlacePhotos() {
        return mBusinessPlacePhotos;
    }

    public void setBusinessPlacePhotos(List<String> businessPlacePhotos) {
        mBusinessPlacePhotos = businessPlacePhotos;
    }

    public String getBv() {
        return mBv;
    }

    public void setBv(String bv) {
        mBv = bv;
    }

    public List<Rv> getRv() {
        return mRv;
    }

    public void setRv(List<Rv> rv) {
        mRv = rv;
    }

}
