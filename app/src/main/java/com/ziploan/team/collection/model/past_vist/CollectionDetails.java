
package com.ziploan.team.collection.model.past_vist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectionDetails {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("mode")
    @Expose
    private Integer mode;
    @SerializedName("denominations")
    @Expose
    private Denominations denominations;
    @SerializedName("reference_number")
    @Expose
    private String referenceNumber;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Denominations getDenominations() {
        return denominations;
    }

    public void setDenominations(Denominations denominations) {
        this.denominations = denominations;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

}
