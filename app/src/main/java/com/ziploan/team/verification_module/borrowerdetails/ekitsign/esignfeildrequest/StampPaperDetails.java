package com.ziploan.team.verification_module.borrowerdetails.ekitsign.esignfeildrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StampPaperDetails {

@SerializedName("stamp_no")
@Expose
private String stampNo;

public String getStampNo() {
return stampNo;
}

public void setStampNo(String stampNo) {
this.stampNo = stampNo;
}

}