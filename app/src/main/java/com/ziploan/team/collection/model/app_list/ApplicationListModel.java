
package com.ziploan.team.collection.model.app_list;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ApplicationListModel {

    @SerializedName("results")
    private List<Result> mResults;
    @SerializedName("total")
    private Long mTotal;

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

}
