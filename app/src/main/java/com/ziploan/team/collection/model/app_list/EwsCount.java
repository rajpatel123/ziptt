
package com.ziploan.team.collection.model.app_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EwsCount {

    @SerializedName("app_status")
    private List<AppStatus> mAppStatus;
    @SerializedName("cibil_triggers_generated_in_last_two_months")
    private List<CibilTriggersGeneratedInLastTwoMonth> mCibilTriggersGeneratedInLastTwoMonths;
    @SerializedName("repayment")
    private List<Repayment> mRepayment;

    public List<AppStatus> getAppStatus() {
        return mAppStatus;
    }

    public void setAppStatus(List<AppStatus> appStatus) {
        mAppStatus = appStatus;
    }

    public List<CibilTriggersGeneratedInLastTwoMonth> getCibilTriggersGeneratedInLastTwoMonths() {
        return mCibilTriggersGeneratedInLastTwoMonths;
    }

    public void setCibilTriggersGeneratedInLastTwoMonths(List<CibilTriggersGeneratedInLastTwoMonth> cibilTriggersGeneratedInLastTwoMonths) {
        mCibilTriggersGeneratedInLastTwoMonths = cibilTriggersGeneratedInLastTwoMonths;
    }

    public List<Repayment> getRepayment() {
        return mRepayment;
    }

    public void setRepayment(List<Repayment> repayment) {
        mRepayment = repayment;
    }

}
