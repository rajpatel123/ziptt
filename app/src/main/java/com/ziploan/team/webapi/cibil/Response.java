
package com.ziploan.team.webapi.cibil;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("req_cibil_data")
    @Expose
    private List<ReqCibilDatum> reqCibilData = null;
    @SerializedName("scanned_data")
    @Expose
    private List<ScannedDatum> scannedData = null;

    public List<ReqCibilDatum> getReqCibilData() {
        return reqCibilData;
    }

    public void setReqCibilData(List<ReqCibilDatum> reqCibilData) {
        this.reqCibilData = reqCibilData;
    }

    public List<ScannedDatum> getScannedData() {
        return scannedData;
    }

    public void setScannedData(List<ScannedDatum> scannedData) {
        this.scannedData = scannedData;
    }

}
