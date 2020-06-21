
package com.ziploan.team.webapi.QuestionAnswers;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionAnswersModel {

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
