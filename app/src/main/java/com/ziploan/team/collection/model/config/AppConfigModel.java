
package com.ziploan.team.collection.model.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppConfigModel {

    @SerializedName("system_info")
    @Expose
    private SystemInfo systemInfo;

    public SystemInfo getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

}
