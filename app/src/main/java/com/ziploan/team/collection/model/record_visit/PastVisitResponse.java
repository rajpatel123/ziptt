package com.ziploan.team.collection.model.record_visit;

import com.google.gson.annotations.SerializedName;

public class PastVisitResponse {
    @SerializedName("status")
    private String mStatus;
    @SerializedName("status_message")
    private String mStatusMessage;
    @SerializedName("response")
    private Response mResponse;


    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmStatusMessage() {
        return mStatusMessage;
    }

    public void setmStatusMessage(String mStatusMessage) {
        this.mStatusMessage = mStatusMessage;
    }

    public Response getmResponse() {
        return mResponse;
    }

    public void setmLoanApplicationNumber(Response response) {
        this.mResponse = response;
    }
}
