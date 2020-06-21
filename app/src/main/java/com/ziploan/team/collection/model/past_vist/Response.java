
package com.ziploan.team.collection.model.past_vist;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class Response {

    @SerializedName("date_of_visit")
    @Expose
    private String dateOfVisit;
    @SerializedName("person_met")
    @Expose
    private String personMet;
    @SerializedName("comment")
    @Expose
    private String comments;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("mode")
    @Expose
    private String mode;

    @SerializedName("reference_number")
    @Expose
    private String referenceNumber;

    @SerializedName("bank_name")
    @Expose
    private String bankName;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("invoice_number")
    @Expose
    private String mInvoiceNumber;

    @SerializedName("lat") private float lat;
    @SerializedName("lon") private float lng;

    public String getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getPersonMet() {
        return personMet;
    }

    public void setPersonMet(String personMet) {
        this.personMet = personMet;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getmInvoiceNumber() {
        return mInvoiceNumber;
    }

    public void setmInvoiceNumber(String mInvoiceNumber) {
        this.mInvoiceNumber = mInvoiceNumber;
    }

    public String getAmountCollected() {
        if(!TextUtils.isEmpty(mode)) {
            return "Amount Collected by - " + mode;
        }
        return "";
    }

    public String getReferenceNumber() {
        if(!TextUtils.isEmpty(mode)) {
            if(!TextUtils.isEmpty(referenceNumber)) {
                if (mode.toUpperCase().contains("CHEQUE")) {
                    return "Cheque No - " + referenceNumber;
                } else {
                    return "Reference No - " + referenceNumber;
                }
            }
        }
        return "";
    }

    public String getInviceNumber() {
        if (!TextUtils.isEmpty(mode)) {
            if (!TextUtils.isEmpty(mInvoiceNumber)) {
                return "Invoice No. - " + mInvoiceNumber;
            }
        }
        return "";
    }

    public String getAmount() {
        if(!TextUtils.isEmpty(mode)) {
            if(!TextUtils.isEmpty(amount)){
                Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                String formattedAmount = ((NumberFormat) format).format(Double.parseDouble(amount));
                if (mode.toUpperCase().contains("CHEQUE")) {
                    return "Cheque Amount - " + formattedAmount;
                } else {
                    return "Amount - " + formattedAmount;
                }
            }
        }
        return "";
    }

    public String getFormattedBankName() {
        if (!TextUtils.isEmpty(bankName)) {

            return "Bank Name - " + bankName;
        }
        return "";
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public boolean getWholeLayoutVisibility() {
        return !TextUtils.isEmpty(personMet);
    }

    public boolean getBankNameLayoutVisibility() {
        return !TextUtils.isEmpty(bankName);
    }

    public boolean getAmountLayoutVisibility() {
        boolean cango = false;
        if(!TextUtils.isEmpty(mode)){
            cango = (!mode.equalsIgnoreCase("no payment"));
        }
        return cango;
    }

    public boolean getReferenceLayoutVisibility() {
       return !TextUtils.isEmpty(referenceNumber);
    }

    public boolean getInvoiceLayoutVisibility() {
        return !TextUtils.isEmpty(mInvoiceNumber);
    }

    public boolean getCommentLayoutVisibility() {
        return !TextUtils.isEmpty(comments);
    }

    public boolean getMobileLayoutVisibility() {
        return !TextUtils.isEmpty(mobile);
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Visit Date - " + dateOfVisit;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
