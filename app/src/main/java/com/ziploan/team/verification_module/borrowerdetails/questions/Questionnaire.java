
package com.ziploan.team.verification_module.borrowerdetails.questions;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Questionnaire {

    @SerializedName("is_mandatory")
    @Expose
    private boolean isMandatory;
    @SerializedName("ques")
    @Expose
    private String ques;
    @SerializedName("is_sub_ques")
    @Expose
    private Integer isSubQues;
    @SerializedName("display")
    @Expose
    private String display;
    @SerializedName("ans")
    @Expose
    private List<String> ans = null;

    @SerializedName("ansObj")
    @Expose
    private List<An> ansObj = null;

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public Integer getIsSubQues() {
        return isSubQues;
    }

    public void setIsSubQues(Integer isSubQues) {
        this.isSubQues = isSubQues;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public List<String> getAns() {
        return ans;
    }

    public void setAns(List<String> ans) {
        this.ans = ans;
    }

    public List<An> getAnsObj() {
        return ansObj;
    }

    public void setAnsObj(List<An> ansObj) {
        this.ansObj = ansObj;
    }

    public boolean getIsMendatory() {
        return isMandatory;
    }

    public void setIsMendatory(boolean isMendatory) {
        this.isMandatory = isMendatory;
    }
}
