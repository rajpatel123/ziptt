
package com.ziploan.team.webapi.cibil;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScannedDatum {

    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("month")
    @Expose
    private List<Month> month = null;
    @SerializedName("totalCashDeposit")
    @Expose
    private String totalCashDeposit;
    @SerializedName("totalDebit")
    @Expose
    private String totalDebit;
    @SerializedName("months_debits_array")
    @Expose
    private List<String> monthsDebitsArray = null;
    @SerializedName("data_by_perfios")
    @Expose
    private Boolean dataByPerfios;
    @SerializedName("months_credits_array")
    @Expose
    private List<String> monthsCreditsArray = null;
    @SerializedName("current_month_total_expected_emi")
    @Expose
    private String currentMonthTotalExpectedEmi;
    @SerializedName("closing_balance")
    @Expose
    private String closingBalance;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("account_type")
    @Expose
    private String accountType;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("account_holder_name")
    @Expose
    private String accountHolderName;
    @SerializedName("totalOutwBounce")
    @Expose
    private String totalOutwBounce;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("total_summary")
    @Expose
    private TotalSummary totalSummary;
    @SerializedName("total_discretionary_expenses")
    @Expose
    private String totalDiscretionaryExpenses;
    @SerializedName("bank_statement_url")
    @Expose
    private List<String> bankStatementUrl = null;
    @SerializedName("bank_type")
    @Expose
    private String bankType;
    @SerializedName("totalCredit")
    @Expose
    private String totalCredit;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public List<Month> getMonth() {
        return month;
    }

    public void setMonth(List<Month> month) {
        this.month = month;
    }

    public String getTotalCashDeposit() {
        return totalCashDeposit;
    }

    public void setTotalCashDeposit(String totalCashDeposit) {
        this.totalCashDeposit = totalCashDeposit;
    }

    public String getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(String totalDebit) {
        this.totalDebit = totalDebit;
    }

    public List<String> getMonthsDebitsArray() {
        return monthsDebitsArray;
    }

    public void setMonthsDebitsArray(List<String> monthsDebitsArray) {
        this.monthsDebitsArray = monthsDebitsArray;
    }

    public Boolean getDataByPerfios() {
        return dataByPerfios;
    }

    public void setDataByPerfios(Boolean dataByPerfios) {
        this.dataByPerfios = dataByPerfios;
    }

    public List<String> getMonthsCreditsArray() {
        return monthsCreditsArray;
    }

    public void setMonthsCreditsArray(List<String> monthsCreditsArray) {
        this.monthsCreditsArray = monthsCreditsArray;
    }

    public String getCurrentMonthTotalExpectedEmi() {
        return currentMonthTotalExpectedEmi;
    }

    public void setCurrentMonthTotalExpectedEmi(String currentMonthTotalExpectedEmi) {
        this.currentMonthTotalExpectedEmi = currentMonthTotalExpectedEmi;
    }

    public String getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(String closingBalance) {
        this.closingBalance = closingBalance;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getTotalOutwBounce() {
        return totalOutwBounce;
    }

    public void setTotalOutwBounce(String totalOutwBounce) {
        this.totalOutwBounce = totalOutwBounce;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TotalSummary getTotalSummary() {
        return totalSummary;
    }

    public void setTotalSummary(TotalSummary totalSummary) {
        this.totalSummary = totalSummary;
    }

    public String getTotalDiscretionaryExpenses() {
        return totalDiscretionaryExpenses;
    }

    public void setTotalDiscretionaryExpenses(String totalDiscretionaryExpenses) {
        this.totalDiscretionaryExpenses = totalDiscretionaryExpenses;
    }

    public List<String> getBankStatementUrl() {
        return bankStatementUrl;
    }

    public void setBankStatementUrl(List<String> bankStatementUrl) {
        this.bankStatementUrl = bankStatementUrl;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

}
