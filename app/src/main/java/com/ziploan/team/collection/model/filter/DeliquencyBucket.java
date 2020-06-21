
package com.ziploan.team.collection.model.filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliquencyBucket {

    @SerializedName("bucket_category")
    @Expose
    private String bucketCategory;
    @SerializedName("start_range")
    @Expose
    private Integer startRange;
    @SerializedName("end_range")
    @Expose
    private Integer endRange;

    private boolean selected;

    public String getBucketCategory() {
        return bucketCategory;
    }

    public void setBucketCategory(String bucketCategory) {
        this.bucketCategory = bucketCategory;
    }

    public Integer getStartRange() {
        return startRange;
    }

    public void setStartRange(Integer startRange) {
        this.startRange = startRange;
    }

    public Integer getEndRange() {
        return endRange;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setEndRange(Integer endRange) {
        this.endRange = endRange;
    }

}
