
package com.ziploan.team.collection.model.bank_names;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("bank_display_name")
    private String mBankDisplayName;
    @SerializedName("bank_code")
    private String mBankCode;

    public String getBankDisplayName() {
        return mBankDisplayName;
    }

    public void setBankDisplayName(String bankDisplayName) {
        mBankDisplayName = bankDisplayName;
    }

    public String getBankCode() {
        return mBankCode;
    }

    public void setBankCode(String bankName) {
        mBankCode = bankName;
    }

    @Override
    public String toString() {
        return mBankDisplayName;
    }

}
