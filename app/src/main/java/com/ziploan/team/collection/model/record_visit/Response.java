package com.ziploan.team.collection.model.record_visit;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("loan_application_number")
    private String mLoanApplicationNumber;

    @SerializedName("invoice_number")
    private String mInvoiceNumber;

    public String getmLoanApplicationNumber() {
        return mLoanApplicationNumber;
    }

    public void setmLoanApplicationNumber(String mLoanApplicationNumber) {
        this.mLoanApplicationNumber = mLoanApplicationNumber;
    }

    public String getmInvoiceNumber() {
        return mInvoiceNumber;
    }

    public void setmInvoiceNumber(String mInvoiceNumber) {
        this.mInvoiceNumber = mInvoiceNumber;
    }
}
