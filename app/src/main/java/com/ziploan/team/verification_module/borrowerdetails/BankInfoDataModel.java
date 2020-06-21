package com.ziploan.team.verification_module.borrowerdetails;

import java.util.List;

public class BankInfoDataModel {

    private List<BankInfoModel> loan_details;

    public List<BankInfoModel> getLoan_details() {
        return loan_details;
    }

    public void setLoan_details(List<BankInfoModel> loan_details) {
        this.loan_details = loan_details;
    }
}
