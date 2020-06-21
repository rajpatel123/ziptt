
package com.ziploan.team.collection.model.app_list;

import com.google.gson.annotations.SerializedName;

public class CibilTriggersGeneratedInLastTwoMonth {

    @SerializedName("trigger_msg")
    private String mTriggerMsg;
    @SerializedName("trigger_name")
    private String mTriggerName;
    @SerializedName("trigger_time")
    private String mTriggerTime;

    public String getTriggerMsg() {
        return mTriggerMsg;
    }

    public void setTriggerMsg(String triggerMsg) {
        mTriggerMsg = triggerMsg;
    }

    public String getTriggerName() {
        return mTriggerName;
    }

    public void setTriggerName(String triggerName) {
        mTriggerName = triggerName;
    }

    public String getTriggerTime() {
        return mTriggerTime;
    }

    public void setTriggerTime(String triggerTime) {
        mTriggerTime = triggerTime;
    }

}
