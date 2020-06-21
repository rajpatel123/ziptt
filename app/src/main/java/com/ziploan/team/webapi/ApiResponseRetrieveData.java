package com.ziploan.team.webapi;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 2/3/2017.
 */
public class ApiResponseRetrieveData {
    @SerializedName("result") public LoanRequestDataResponse result;

    public LoanRequestDataResponse getResult() {
        return result;
    }

    public void setResult(LoanRequestDataResponse result) {
        this.result = result;
    }
}
