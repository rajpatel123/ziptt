package com.ziploan.team.verification_module.borrowerdetails.ekitsign.esignfeildrequest;

import com.google.gson.annotations.SerializedName;
                import com.google.gson.annotations.Expose;


public class DrfDetails {

@SerializedName("micr_code")
@Expose
private String micrCode;
@SerializedName("ifsc_code")
@Expose
private String ifscCode;

public String getMicrCode() {
return micrCode;
}

public void setMicrCode(String micrCode) {
this.micrCode = micrCode;
}

public String getIfscCode() {
return ifscCode;
}

public void setIfscCode(String ifscCode) {
this.ifscCode = ifscCode;
}

}