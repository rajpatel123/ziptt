
package com.ziploan.team.collection.model.filter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterModel {

    @SerializedName("deliquency_buckets")
    @Expose
    private List<DeliquencyBucket> deliquencyBuckets = null;
    @SerializedName("available_pincodes")
    @Expose
    private List<String> availablePincodes = null;

    public List<DeliquencyBucket> getDeliquencyBuckets() {
        return deliquencyBuckets;
    }

    public void setDeliquencyBuckets(List<DeliquencyBucket> deliquencyBuckets) {
        this.deliquencyBuckets = deliquencyBuckets;
    }

    public List<String> getAvailablePincodes() {
        return availablePincodes;
    }

    public void setAvailablePincodes(List<String> availablePincodes) {
        this.availablePincodes = availablePincodes;
    }

}
