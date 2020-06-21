
package com.ziploan.team.webapi.cibil;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CibilLiveDatum {

    @SerializedName("datereportedcertified")
    @Expose
    private Datereportedcertified datereportedcertified;
    @SerializedName("account_type")
    @Expose
    private String accountType;
    @SerializedName("highcreditsanctionedamount")
    @Expose
    private String highcreditsanctionedamount;
    @SerializedName("dateopeneddisbursed")
    @Expose
    private Dateopeneddisbursed dateopeneddisbursed;
    @SerializedName("emi")
    @Expose
    private String emi;
    @SerializedName("emi_flag")
    @Expose
    private String emiFlag;
    @SerializedName("currentbalance")
    @Expose
    private String currentbalance;
    @SerializedName("loan_type")
    @Expose
    private String loanType;
    @SerializedName("dateoflastpayment")
    @Expose
    private String dateoflastpayment;

    public Datereportedcertified getDatereportedcertified() {
        return datereportedcertified;
    }

    public void setDatereportedcertified(Datereportedcertified datereportedcertified) {
        this.datereportedcertified = datereportedcertified;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getHighcreditsanctionedamount() {
        return highcreditsanctionedamount;
    }

    public void setHighcreditsanctionedamount(String highcreditsanctionedamount) {
        this.highcreditsanctionedamount = highcreditsanctionedamount;
    }

    public Dateopeneddisbursed getDateopeneddisbursed() {
        return dateopeneddisbursed;
    }

    public void setDateopeneddisbursed(Dateopeneddisbursed dateopeneddisbursed) {
        this.dateopeneddisbursed = dateopeneddisbursed;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getEmiFlag() {
        return emiFlag;
    }

    public void setEmiFlag(String emiFlag) {
        this.emiFlag = emiFlag;
    }

    public String getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(String currentbalance) {
        this.currentbalance = currentbalance;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getDateoflastpayment() {
        return dateoflastpayment;
    }

    public void setDateoflastpayment(String dateoflastpayment) {
        this.dateoflastpayment = dateoflastpayment;
    }

    public String getEmiFromatted() {
        if (!TextUtils.isEmpty(emi)) {
            Double emi_int = Double.parseDouble(emi);
            NumberFormat nf = new DecimalFormat("#.#");
            return nf.format(emi_int);
        }
        return "";
    }
}
