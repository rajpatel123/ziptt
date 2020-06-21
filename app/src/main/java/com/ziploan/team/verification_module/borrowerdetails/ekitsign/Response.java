package com.ziploan.team.verification_module.borrowerdetails.ekitsign;

import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("esign_docs")
    @Expose
    private List<EsignDoc> esignDocs = null;
    @SerializedName("lender_name")
    @Expose
    private String lenderName;

    public List<EsignDoc> getEsignDocs() {
        return esignDocs;
    }

    public void setEsignDocs(List<EsignDoc> esignDocs) {
        this.esignDocs = esignDocs;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

}