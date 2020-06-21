
package com.ziploan.team.collection.model.emi;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmiBreakupModel {

    @SerializedName("loan_application_number")
    @Expose
    private String loanApplicationNumber;
    @SerializedName("overdue_amount")
    @Expose
    private List<OverdueAmount> overdueAmount = null;
    @SerializedName("total_amount")
    @Expose
    private Double totalAmount;

    public String getLoanApplicationNumber() {
        return loanApplicationNumber;
    }

    public void setLoanApplicationNumber(String loanApplicationNumber) {
        this.loanApplicationNumber = loanApplicationNumber;
    }

    public List<OverdueAmount> getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(List<OverdueAmount> overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

}



