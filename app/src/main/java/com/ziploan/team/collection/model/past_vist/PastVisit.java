
package com.ziploan.team.collection.model.past_vist;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PastVisit {

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


    public String getAmountCollected() {
        if (mode.equalsIgnoreCase("cash")) {
            return "Amount Collected by Cash";
        } else if (mode.equalsIgnoreCase("Cheque")) {
            return "Amount Collected by Cheque";
        }
        return "";
    }

    public String getReferenceNumber() {

        if (mode.equalsIgnoreCase("cash")) {
            return "Cheque No - " + referenceNumber;
        } else if (mode.equalsIgnoreCase("Cheque")) {
            return "Reference No - " + referenceNumber;
        }
        return "";
    }

    public String getAmount() {
        if (mode.equalsIgnoreCase("cash")) {
            return "Cheque Amount - Rs." + amount;
        } else if (mode.equalsIgnoreCase("Cheque")) {
            return "Amount - Rs." + amount;
        }
        return "";
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

    @Override
    public String toString() {
        return "Visit Date - " + dateOfVisit;
    }
//    CASH = 0
//    NEFT = 3
//    RTGS = 4
//    NETBANKING = 5
//    IMPS = 6
//    CHEQUE = 7
//    FUNDS_TRANSFER = 9
//    OTHER = 10
}
