package com.ziploan.team.verification_module.borrowerdetails.ekitsign.esignfeildrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NomineeDetail {

@SerializedName("name")
@Expose
private String name;
@SerializedName("applicant_type")
@Expose
private String applicantType;
@SerializedName("nominee_name")
@Expose
private String nomineeName;
@SerializedName("nominee_dob")
@Expose
private String nomineeDob;
@SerializedName("nominee_gender")
@Expose
private String nomineeGender;
@SerializedName("nominee_mobile")
@Expose
private String nomineeContact;
@SerializedName("nominee_relationship")
@Expose
private String nomineeRelationship;

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

public String getNomineeName() {
return nomineeName;
}

public void setNomineeName(String nomineeName) {
this.nomineeName = nomineeName;
}

public String getNomineeDob() {
return nomineeDob;
}

public void setNomineeDob(String nomineeDob) {
this.nomineeDob = nomineeDob;
}

public String getNomineeGender() {
return nomineeGender;
}

public void setNomineeGender(String nomineeGender) {
this.nomineeGender = nomineeGender;
}

public String getNomineeContact() {
return nomineeContact;
}

public void setNomineeContact(String nomineeContact) {
this.nomineeContact = nomineeContact;
}

public String getNomineeRelationship() {
return nomineeRelationship;
}

public void setNomineeRelationship(String nomineeRelationship) {
this.nomineeRelationship = nomineeRelationship;
}

}
