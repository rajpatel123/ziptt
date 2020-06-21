
package com.ziploan.team.collection.model.emi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverdueAmount {

    @SerializedName("total_amount_outstanding")
    @Expose
    private String totalAmountOutstanding;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("bouncing_component")
    @Expose
    private String bouncingComponent;
    @SerializedName("principal_component")
    @Expose
    private String principalComponent;
    @SerializedName("emi")
    @Expose
    private String emi;
    @SerializedName("interest_component")
    @Expose
    private String interestComponent;
    @SerializedName("overdue_charges")
    @Expose
    private String overdueCharges;
    @SerializedName("penal_charges")
    @Expose
    private String penalCharges;


    public String getTotalAmountOutstanding() {
        return totalAmountOutstanding;
    }

    public void setTotalAmountOutstanding(String totalAmountOutstanding) {
        this.totalAmountOutstanding = totalAmountOutstanding;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBouncingComponent() {
        return bouncingComponent;
    }

    public void setBouncingComponent(String bouncingComponent) {
        this.bouncingComponent = bouncingComponent;
    }

    public String getPrincipalComponent() {
        return principalComponent;
    }

    public void setPrincipalComponent(String principalComponent) {
        this.principalComponent = principalComponent;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getInterestComponent() {
        return interestComponent;
    }

    public void setInterestComponent(String interestComponent) {
        this.interestComponent = interestComponent;
    }

    public String getOverdueCharges() {
        return overdueCharges;
    }

    public void setOverdueCharges(String overdueCharges) {
        this.overdueCharges = overdueCharges;
    }

    public String getPenalCharges() {
        return penalCharges;
    }

    public void setPenalCharges(String penalCharges) {
        this.penalCharges = penalCharges;
    }

    public String getOverdueText() {
        return "Now Amount Outstanding as on" + dueDate + " = " + totalAmountOutstanding;
    }

    public String getBreakupEmiText() {
        return "Breakup of Due Date " + dueDate;
    }

    @Override
    public String toString() {
        return "EMI Pending Due Date : " + dueDate;
    }
}
