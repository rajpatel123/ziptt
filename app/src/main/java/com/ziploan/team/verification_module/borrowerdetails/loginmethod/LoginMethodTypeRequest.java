package com.ziploan.team.verification_module.borrowerdetails.loginmethod;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginMethodTypeRequest {

@SerializedName("email")
@Expose
private String email;


@SerializedName("platform")
@Expose
private String platform="app";


public LoginMethodTypeRequest(String email,String platform){
    this.email= email;
    this.platform=platform;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

}