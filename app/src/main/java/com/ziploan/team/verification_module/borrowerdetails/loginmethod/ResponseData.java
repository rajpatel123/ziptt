package com.ziploan.team.verification_module.borrowerdetails.loginmethod;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseData {

@SerializedName("data")
@Expose
private String data;

public String getData() {
return data;
}

public void setData(String data) {
this.data = data;
}

}