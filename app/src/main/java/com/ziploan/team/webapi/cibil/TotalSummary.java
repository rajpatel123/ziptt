
package com.ziploan.team.webapi.cibil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalSummary {

    @SerializedName("cash_deposit")
    @Expose
    private Integer cashDeposit;
    @SerializedName("emi_amount")
    @Expose
    private Integer emiAmount;
    @SerializedName("no_outward_bounces")
    @Expose
    private Integer noOutwardBounces;
    @SerializedName("credit_card_payment")
    @Expose
    private Integer creditCardPayment;
    @SerializedName("no_credit_transactions")
    @Expose
    private Integer noCreditTransactions;
    @SerializedName("credit")
    @Expose
    private Integer credit;
    @SerializedName("opening_balance")
    @Expose
    private Integer openingBalance;

    public Integer getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(Integer cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public Integer getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(Integer emiAmount) {
        this.emiAmount = emiAmount;
    }

    public Integer getNoOutwardBounces() {
        return noOutwardBounces;
    }

    public void setNoOutwardBounces(Integer noOutwardBounces) {
        this.noOutwardBounces = noOutwardBounces;
    }

    public Integer getCreditCardPayment() {
        return creditCardPayment;
    }

    public void setCreditCardPayment(Integer creditCardPayment) {
        this.creditCardPayment = creditCardPayment;
    }

    public Integer getNoCreditTransactions() {
        return noCreditTransactions;
    }

    public void setNoCreditTransactions(Integer noCreditTransactions) {
        this.noCreditTransactions = noCreditTransactions;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Integer getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Integer openingBalance) {
        this.openingBalance = openingBalance;
    }

}
