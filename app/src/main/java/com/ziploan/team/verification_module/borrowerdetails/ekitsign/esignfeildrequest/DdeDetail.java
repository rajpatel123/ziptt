package com.ziploan.team.verification_module.borrowerdetails.ekitsign.esignfeildrequest;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DdeDetail {

@SerializedName("name")
@Expose
private String name;
@SerializedName("applicant_type")
@Expose
private String applicantType;
@SerializedName("mother_name")
@Expose
private String motherName;
@SerializedName("weight")
@Expose
private Integer weight;
@SerializedName("hFeet")
@Expose
private Integer hFeet;
@SerializedName("hInches")
@Expose
private Integer hInches;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getApplicantType() {
return applicantType;
}

public void setApplicantType(String applicantType) {
this.applicantType = applicantType;
}

public String getMotherName() {
return motherName;
}

public void setMotherName(String motherName) {
this.motherName = motherName;
}

public Integer getWeight() {
return weight;
}

public void setWeight(Integer weight) {
this.weight = weight;
}

public Integer getHFeet() {
return hFeet;
}

public void setHFeet(Integer hFeet) {
this.hFeet = hFeet;
}

public Integer getHInches() {
return hInches;
}

public void setHInches(Integer hInches) {
this.hInches = hInches;
}

}


