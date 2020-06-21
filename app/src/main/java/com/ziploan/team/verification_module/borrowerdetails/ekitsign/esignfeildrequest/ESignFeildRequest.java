package com.ziploan.team.verification_module.borrowerdetails.ekitsign.esignfeildrequest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ESignFeildRequest {

@SerializedName("dde_details")
@Expose
private List<DdeDetail> ddeDetails = null;
@SerializedName("nominee_details")
@Expose
private List<NomineeDetail> nomineeDetails = null;
@SerializedName("drf_details")
@Expose
private DrfDetails drfDetails;
@SerializedName("stamp_paper_details")
@Expose
private StampPaperDetails stampPaperDetails;

public List<DdeDetail> getDdeDetails() {
return ddeDetails;
}

public void setDdeDetails(List<DdeDetail> ddeDetails) {
this.ddeDetails = ddeDetails;
}

public List<NomineeDetail> getNomineeDetails() {
return nomineeDetails;
}

public void setNomineeDetails(List<NomineeDetail> nomineeDetails) {
this.nomineeDetails = nomineeDetails;
}

public DrfDetails getDrfDetails() {
return drfDetails;
}

public void setDrfDetails(DrfDetails drfDetails) {
this.drfDetails = drfDetails;
}

public StampPaperDetails getStampPaperDetails() {
return stampPaperDetails;
}

public void setStampPaperDetails(StampPaperDetails stampPaperDetails) {
this.stampPaperDetails = stampPaperDetails;
}

}