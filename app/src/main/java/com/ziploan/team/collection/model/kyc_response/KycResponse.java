
package com.ziploan.team.collection.model.kyc_response;

import com.google.gson.annotations.SerializedName;

public class KycResponse {

    @SerializedName("response")
    private Response mResponse;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("status_message")
    private String mStatusMessage;

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
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
