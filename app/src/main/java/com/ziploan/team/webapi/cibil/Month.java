
package com.ziploan.team.webapi.cibil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Month {

    @SerializedName("cash_deposit")
    @Expose
    private String cashDeposit;
    @SerializedName("emi_amount")
    @Expose
    private String emiAmount;
    @SerializedName("no_outward_bounces")
    @Expose
    private String noOutwardBounces;
    @SerializedName("credit_card_payment")
    @Expose
    private String creditCardPayment;
    @SerializedName("no_credit_transactions")
    @Expose
    private String noCreditTransactions;
    @SerializedName("discretionary_expense")
    @Expose
    private String discretionaryExpense;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("monthName")
    @Expose
    private String monthName;
    @SerializedName("debit")
    @Expose
    private String debit;
    @SerializedName("opening_balance")
    @Expose
    private String openingBalance;

    public String getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(String cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public String getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(String emiAmount) {
        this.emiAmount = emiAmount;
    }

    public String getNoOutwardBounces() {
        return noOutwardBounces;
    }

    public void setNoOutwardBounces(String noOutwardBounces) {
        this.noOutwardBounces = noOutwardBounces;
    }

    public String getCreditCardPayment() {
        return creditCardPayment;
    }

    public void setCreditCardPayment(String creditCardPayment) {
        this.creditCardPayment = creditCardPayment;
    }

    public String getNoCreditTransactions() {
        return noCreditTransactions;
    }

    public void setNoCreditTransactions(String noCreditTransactions) {
        this.noCreditTransactions = noCreditTransactions;
    }

    public String getDiscretionaryExpense() {
        return discretionaryExpense;
    }

    public void setDiscretionaryExpense(String discretionaryExpense) {
        this.discretionaryExpense = discretionaryExpense;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

}
