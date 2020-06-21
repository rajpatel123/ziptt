
package com.ziploan.team.collection.model.error;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("refrence_number")
    private List<String> mRefrenceNumber;

    public List<String> getRefrenceNumber() {
        return mRefrenceNumber;
    }

    public void setRefrenceNumber(List<String> refrenceNumber) {
        mRefrenceNumber = refrenceNumber;
    }

}
