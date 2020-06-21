
package com.ziploan.team.asset_module.ews.res;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("count")
    private Long mCount;
    @SerializedName("next")
    private Object mNext;
    @SerializedName("previous")
    private Object mPrevious;
    @SerializedName("results")
    private List<Result> mResults;

    public Long getCount() {
        return mCount;
    }

    public void setCount(Long count) {
        mCount = count;
    }

    public Object getNext() {
        return mNext;
    }

    public void setNext(Object next) {
        mNext = next;
    }

    public Object getPrevious() {
        return mPrevious;
    }

    public void setPrevious(Object previous) {
        mPrevious = previous;
    }

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

}
