
package com.ziploan.team.collection.model.error;

import com.google.gson.annotations.SerializedName;

public class ErrorResponseModel {

    @SerializedName("response")
    private String mResponse;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("status_message")
    private String mStatusMessage;

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        mResponse = response;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getStatusMessage() {
        return mStatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        mStatusMessage = statusMessage;
    }

}
