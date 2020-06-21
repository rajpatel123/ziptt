
package com.ziploan.team.verification_module.borrowerdetails.questions;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewQuestionModel {

    @SerializedName("questionnaire")
    @Expose
    private List<Questionnaire> questionnaire = null;

    public List<Questionnaire> getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(List<Questionnaire> questionnaire) {
        this.questionnaire = questionnaire;
    }

}
