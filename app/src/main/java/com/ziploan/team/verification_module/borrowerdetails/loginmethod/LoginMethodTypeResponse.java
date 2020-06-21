package com.ziploan.team.verification_module.borrowerdetails.loginmethod;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginMethodTypeResponse {

@SerializedName("status")
@Expose
private String status;
@SerializedName("status_message")
@Expose
private String statusMessage;
@SerializedName("response")
@Expose
private ResponseData responseData;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getStatusMessage() {
return statusMessage;
}

public void setStatusMessage(String statusMessage) {
this.statusMessage = statusMessage;
}

public ResponseData getResponseData() {
return responseData;
}

public void setResponseData(ResponseData responseData) {
this.responseData = responseData;
}

}
