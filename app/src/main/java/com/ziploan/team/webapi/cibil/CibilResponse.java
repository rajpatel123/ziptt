
package com.ziploan.team.webapi.cibil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CibilResponse{

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
