
package com.ziploan.team.collection.model.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SystemInfo {

    @SerializedName("min_supported_version")
    @Expose
    private Integer minSupportedVersion;
    @SerializedName("current_app_version")
    @Expose
    private Integer currentAppVersion;
    @SerializedName("upgrade_message")
    @Expose
    private String upgradeMessage;
    @SerializedName("force_upgrade")
    @Expose
    private Integer forceUpgrade;
    @SerializedName("refresh_bank")
    @Expose
    private Integer refreshBank;


    public Integer getMinSupportedVersion() {
        return minSupportedVersion;
    }

    public void setMinSupportedVersion(Integer minSupportedVersion) {
        this.minSupportedVersion = minSupportedVersion;
    }

    public Integer getCurrentAppVersion() {
        return currentAppVersion;
    }

    public void setCurrentAppVersion(Integer currentAppVersion) {
        this.currentAppVersion = currentAppVersion;
    }

    public Integer getForceUpgrade() {
        return forceUpgrade;
    }

    public void setForceUpgrade(Integer forceUpgrade) {
        this.forceUpgrade = forceUpgrade;
    }

    public String getUpgradeMessage() {
        return upgradeMessage;
    }

    public void setUpgradeMessage(String upgradeMessage) {
        this.upgradeMessage = upgradeMessage;
    }

    public Integer getRefreshBank() {
        return refreshBank;
    }

    public void setRefreshBank(Integer refreshBank) {
        this.refreshBank = refreshBank;
    }
}
