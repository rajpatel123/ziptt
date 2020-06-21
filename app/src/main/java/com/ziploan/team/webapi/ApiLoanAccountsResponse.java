package com.ziploan.team.webapi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ZIploan-Nitesh on 8/23/2017.
 */

public class ApiLoanAccountsResponse {
    @SerializedName("results") public ArrayList<Loan> loans;
    @SerializedName("total") public int total_record;

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    public int getTotal_record() {
        return total_record;
    }

    public void setTotal_record(int total_record) {
        this.total_record = total_record;
    }
}
