package com.ziploan.team.verification_module.borrowerdetails;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ziploan.team.MultiSelectSpinner;
import com.ziploan.team.R;
import com.ziploan.team.databinding.BankInfoLayoutBinding;
import com.ziploan.team.databinding.FragmentQuestionsBinding;
import com.ziploan.team.databinding.ItemReferenceBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.JsonLocalDataFetcher;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.base.BaseFragment;
import com.ziploan.team.verification_module.borrowerdetails.questions.An;
import com.ziploan.team.verification_module.borrowerdetails.questions.NewQuestionModel;
import com.ziploan.team.verification_module.borrowerdetails.questions.Val;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.QuestionAnswers.QuestionAnswersModel;
import com.ziploan.team.webapi.QuestionAnswers.Questionnaire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 2/4/2017.
 */
public class QuestionariesFragment extends BaseFragment implements View.OnClickListener {

    private FragmentQuestionsBinding binding;
    private ArrayList<ZiploanQuestion> questions;
    private ArrayList<ZiploanQuestion> saved_questions = new ArrayList<>();
    private List<com.ziploan.team.verification_module.borrowerdetails.questions.Questionnaire> newquestions;
    private HashMap<String, Integer> arrSelectedQuestions = new HashMap<>();
    private boolean isNeedToDisable;
    private List<Questionnaire> questionnaire;
    private List<BankInfoModel> bankInfoModels;
    private static final String TEXT_AREA = "textarea";
    private static final String SINGLE_SELECT = "singleSelect";
    private static final String MULTI_SELECT = "multiselect";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuestionsBinding.inflate(inflater, container, false);
        questions = getQuestionsFromBundle(getArguments());
        bankInfoModels = getArguments().getParcelableArrayList(AppConstant.Key.EXTRA_BANK_INFO);
        setListeners();
        //loadData();
        loadNewData();
//        if (isNeedToDisable) {
//            binding.rlQuesAns.setVisibility(View.GONE);
//            if (questions == null || questions.size() == 0) {
//                binding.labelQuestionaries.setVisibility(View.GONE);
//            } else {
//                fillQuesntionsIfAny(questions);
//            }
//        } else {
//            fillQuesntionsIfAny(questions);
//            showAllQuestions();
//        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imgAddQuestion.setOnClickListener(this);
        if(bankInfoModels != null && bankInfoModels.size() > 0){
            showBankInfo();
        } else {
            addReferenceBox(new BankInfoModel("","",""));
        }
    }

    private ArrayList<ZiploanQuestion> getQuestionsFromBundle(Bundle bundle) {
        ArrayList<ZiploanQuestion> arrQuestions = new ArrayList<>();
        if (bundle != null && bundle.containsKey(AppConstant.Key.EXTRA_QUESTIONS)) {
            arrQuestions = bundle.getParcelableArrayList(AppConstant.Key.EXTRA_QUESTIONS);
        }
        if (bundle != null && bundle.containsKey(AppConstant.Key.IS_NEED_TO_DISABLE)) {
            isNeedToDisable = bundle.getBoolean(AppConstant.Key.IS_NEED_TO_DISABLE);
        }
        return arrQuestions;
    }

    private void fillQuesntionsIfAny(List<ZiploanQuestion> questions) {
        if (questions != null && questions.size() > 0) {
            for (ZiploanQuestion question : questions) {
                addQuestionItem(question, false);
            }
        }
    }

    private void showAllQuestions() {
        ArrayList<String> arrQuestions = JsonLocalDataFetcher.fetchQuestionaries(mContext);
        for (String str : arrQuestions) {
            if (!arrSelectedQuestions.containsKey(str)) {
                addQuestionItem(new ZiploanQuestion(str, ""), false);
            }
        }

    }

    private void setListeners() {
        binding.tvBack.setOnClickListener(this);
        binding.tvContinue.setOnClickListener(this);
        binding.tvAddMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:

                break;
            case R.id.tv_continue:

                break;

            case R.id.img_add_question:
                showCustomQuestionDialog();
                break;
            case R.id.tv_add_more:
                addReferenceBox(new BankInfoModel("", "", ""));
                break;
        }
    }

    private void addQuestionItem(ZiploanQuestion itemQuestion, boolean isCustomQuestion) {
        FrameLayout item = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.item_question, binding.llQuestionItem, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        item.setLayoutParams(params);
        TextView tvQuestion = ((TextView) item.findViewById(R.id.tv_question));
        tvQuestion.setTag((this.getId() + new Random().nextInt(9999)));
        tvQuestion.setText((binding.llQuestionItem.getChildCount() + 1) + ". " + itemQuestion.getQuestion());

//        Spinner spinner = item.findViewById(R.id.answer_spinner);
//        spinner.setId(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
//        spinner.setVisibility(View.VISIBLE);
//
//        List<String> dats  = new ArrayList<>();
//        dats.add(getString(R.string.select_anser));
//        dats.add(itemQuestion.getAnswer());
//        populateSpinner(spinner,dats);


        EditText etAnswer = ((EditText) item.findViewById(R.id.et_answer));
        etAnswer.setVisibility(View.VISIBLE);
        etAnswer.setId(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
        etAnswer.setText(itemQuestion.getAnswer());
        if (isCustomQuestion) {
            binding.llQuestionItem.addView(item, 0);
            arrangeNumbering();
        } else {
            binding.llQuestionItem.addView(item);
        }
        ImageView ivClose = (ImageView) item.findViewById(R.id.iv_delete);
        ivClose.setTag(itemQuestion.getQuestion().trim());
        arrSelectedQuestions.put(itemQuestion.getQuestion().trim(), binding.llQuestionItem.getChildCount() - 1);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (arrSelectedQuestions!=null){
                    String question = String.valueOf(view.getTag());
                    int position = arrSelectedQuestions.get(question);
                    try {
                        binding.llQuestionItem.removeViewAt(position);
                        arrSelectedQuestions.remove(question);
                        normalizeMap(arrSelectedQuestions, position);
                        arrangeNumbering();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        if (isNeedToDisable) {
            enableField(etAnswer, !isNeedToDisable);
            ivClose.setVisibility(View.GONE);
        }
    }

    private void populateSpinner(Spinner spinner, Questionnaire data) {
        try {
            if (data != null) {
                List<String> list = data.getAns();
                list.add(0, getString(R.string.select_anser));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(spinner.getContext(),
                        android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                for (int ii = 0; ii < questions.size(); ii++) {
                    if (data.getQues().equalsIgnoreCase(questions.get(ii).getQuestion())) {
                        for (int j = 0; j < data.getAns().size(); j++) {
                            if (data.getAns().get(j).equalsIgnoreCase(questions.get(ii).getAnswer())) {
                                spinner.setSelection(j);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void arrangeNumbering() {
        for (int i = 1; i <= binding.llQuestionItem.getChildCount(); i++) {
            View viewTemp = binding.llQuestionItem.getChildAt(i - 1);
            String currentString;
            TextView tvQuestion = ((TextView) viewTemp.findViewById(R.id.tv_question));
            Spinner spinner = viewTemp.findViewById(R.id.answer_spinner);
            if (spinner != null && spinner.getSelectedItem() != null
                    && !TextUtils.isEmpty(spinner.getSelectedItem().toString())) {
                currentString = spinner.getSelectedItem().toString();
            } else {
                currentString = tvQuestion.getText().toString().trim();
            }
            tvQuestion.setText(i + "" + currentString.substring(currentString.indexOf(".")));
        }
    }

    private void normalizeMap(HashMap<String, Integer> arrSelectedQuestions, int position) {
        Set<String> keys = arrSelectedQuestions.keySet();
        ArrayList<String> keyList = new ArrayList<>(keys);
        for (int i = 0; i < keyList.size(); i++) {
            if (arrSelectedQuestions.get(keyList.get(i)) > position) {
                arrSelectedQuestions.put(keyList.get(i), arrSelectedQuestions.get(keyList.get(i)) - 1);
            }
        }
    }

    public ArrayList<ZiploanQuestion> getQuestionList() {
        ArrayList<ZiploanQuestion> arrQuestions = new ArrayList<>();
        try {
            int count = binding.llQuestionItem.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = binding.llQuestionItem.getChildAt(i);
                TextView tvQuestion = ((TextView) view.findViewById(R.id.tv_question));

                MultiSelectSpinner multiSelectSpinner = null;
                try {
                    multiSelectSpinner = view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                AppCompatSpinner spinner = null;
                try {
                    if (multiSelectSpinner == null)
                        spinner = view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                } catch (Exception e) {
                }
                EditText editText = null;
                try {
                    if (spinner == null)
                        editText = view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                } catch (Exception e) {
                }
                String question = tvQuestion.getText().toString();
                String answer = "";
                if (spinner != null
                        && spinner.getSelectedItem() != null
                        && !TextUtils.isEmpty(spinner.getSelectedItem().toString())) {
                    An answerObj = null;
                    try {
                        answerObj = (An) ((Spinner) view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())))).getSelectedItem();
                    } catch (Exception e) {
                    }
                    if (answerObj != null) {
                        answer = answerObj.getKey();
                    } else {
                        answer = ((Spinner) view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())))).getSelectedItem().toString();
                    }

                    try {
                        editText = view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())) + 29911);
                    } catch (Exception e) {
                    }
                    if (editText != null) {
                        arrQuestions.add(new ZiploanQuestion(editText.getHint().toString().trim(), editText.getText().toString().trim()));
                    }
                } else if (editText != null && !TextUtils.isEmpty(editText.getText())) {
                    answer = ((EditText) view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())))).getText().toString();
                } else if (multiSelectSpinner != null && !TextUtils.isEmpty(multiSelectSpinner.buildSelectedItemString())) {
                    answer = ((MultiSelectSpinner) view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())))).buildSelectedItemString();
                }
                if (!TextUtils.isEmpty(answer)
                        && !answer.equalsIgnoreCase("Select Answer")) {
                    arrQuestions.add(new ZiploanQuestion(question.substring(question.indexOf(".") + 1).trim(), answer.trim()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrQuestions;
    }

    public ArrayList<ZiploanNewQuestion> getFinalQuestionList(boolean isFinal) {
        ArrayList<ZiploanNewQuestion> arrQuestions = new ArrayList<>();
        try {
            int count = binding.llQuestionItem.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = binding.llQuestionItem.getChildAt(i);
                com.ziploan.team.verification_module.borrowerdetails.questions.Questionnaire questionnaire = (com.ziploan.team.verification_module.borrowerdetails.questions.Questionnaire)view.getTag();

                TextView tvQuestion = ((TextView) view.findViewById(R.id.tv_question));
                An anObj = new An();
                Val val;
                MultiSelectSpinner multiSelectSpinner = null;
                try {
                    multiSelectSpinner = view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                } catch (Exception e) {
                }

                AppCompatSpinner spinner = null;
                try {
                    if (multiSelectSpinner == null)
                        spinner = view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                } catch (Exception e) {
                }
                EditText editText = null;
                try {
                    if (spinner == null)
                        editText = view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                } catch (Exception e) {
                }
                String question = tvQuestion.getText().toString();
                String answer = "";
                if (spinner != null
                        && spinner.getSelectedItem() != null
                        && !TextUtils.isEmpty(spinner.getSelectedItem().toString())) {
                    An answerObj = null;
                    try {
                        answerObj = (An) ((Spinner) view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())))).getSelectedItem();
                    } catch (Exception e) {
                    }
                    if (answerObj != null) {
                        answer = answerObj.getKey();
                    } else {
                        answer = ((Spinner) view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())))).getSelectedItem().toString();
                    }

                    try {
                        editText = view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())) + 29911);
                    } catch (Exception e) {
                    }
                    if (editText != null) {
                        if (isFinal) {
                            val = new Val();
                            val.setSubQues(editText.getHint().toString());
                            List<String> list = new ArrayList<>();
                            list.add(editText.getText().toString());
                            val.setSubAns(list);
                            anObj.setVal(val);
//                            answer = new Gson().toJson(anObj);
//                            arrQuestions.add(new ZiploanNewQuestion())
                        }
//                        else
//                            arrQuestions.add(new ZiploanQuestion(editText.getHint().toString(), editText.getText().toString()));
                    }
                } else if (editText != null && !TextUtils.isEmpty(editText.getText())) {
                    answer = ((EditText) view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())))).getText().toString();
                } else if (multiSelectSpinner != null && !TextUtils.isEmpty(multiSelectSpinner.buildSelectedItemString())) {
                    answer = ((MultiSelectSpinner) view.findViewById(Integer.parseInt(String.valueOf(tvQuestion.getTag())))).buildSelectedItemString();
                }

                if (!TextUtils.isEmpty(answer)
                        && !answer.equalsIgnoreCase("Select Answer")) {
                    anObj.setKey(answer);
                    arrQuestions.add(new ZiploanNewQuestion(question.substring(question.indexOf(".") + 1).trim(), anObj));
                } else {
                    if(questionnaire != null &&
                            questionnaire.getIsMendatory()){
                        arrQuestions.add(new ZiploanNewQuestion("mandatory", null));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrQuestions;
    }


    protected void showCustomQuestionDialog() {
        final Dialog dialog = ZiploanUtil.getCustomDialog(mContext, R.layout.dialog_edittext);
        dialog.setCancelable(false);
        final EditText etQuestion = (EditText) dialog.findViewById(R.id.et_question);
        dialog.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = etQuestion.getText().toString().trim();
                if (question.length() == 0) {
                    ((TextInputLayout) etQuestion.getParent()).setError("Please enter valid question.");
                } else {
                    addQuestionItem(new ZiploanQuestion(question, ""), true);
                }
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        try {
            if (mContext != null && !((Activity) mContext).isFinishing())
                dialog.show();
        } catch (Exception e) {
        }
    }

    private void loadData() {
        Call<QuestionAnswersModel> call = APIExecutor.getAPIService(mContext).getQuestionAnswers(2);
        call.enqueue(new Callback<QuestionAnswersModel>() {
            @Override
            public void onResponse(Call<QuestionAnswersModel> call, Response<QuestionAnswersModel> response) {
                if (response != null && response.isSuccessful()) {
                    questionnaire = response.body().getQuestionnaire();
                    filldata(questionnaire, false);
                }
            }

            @Override
            public void onFailure(Call<QuestionAnswersModel> call, Throwable t) {

            }
        });
    }

    private void loadNewData() {
        Call<NewQuestionModel> call = APIExecutor.getAPIService(mContext).getNewQuestionAnswers(2);
        call.enqueue(new Callback<NewQuestionModel>() {
            @Override
            public void onResponse(Call<NewQuestionModel> call, Response<NewQuestionModel> response) {
                if (response != null && response.isSuccessful()) {
                    newquestions = response.body().getQuestionnaire();
                    fillNewdata(newquestions, false);
                }
            }

            @Override
            public void onFailure(Call<NewQuestionModel> call, Throwable t) {

            }
        });
    }

    private void filldata(List<Questionnaire> questionnaire, boolean isCustomQuestion) {
        for (int i = 0; i < questionnaire.size(); i++) {

            FrameLayout item = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.item_question, binding.llQuestionItem, false);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            item.setLayoutParams(params);
            TextView tvQuestion = ((TextView) item.findViewById(R.id.tv_question));
            tvQuestion.setTag((this.getId() + new Random().nextInt(9999)));
            tvQuestion.setText((i + 1) + ". " + questionnaire.get(i).getQues());

            Spinner spinner = item.findViewById(R.id.answer_spinner);
            if (questionnaire.get(i).getAns() != null && questionnaire.get(i).getAns() != null
                    && questionnaire.get(i).getAns().size() > 0) {
                spinner.setId(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                spinner.setVisibility(View.VISIBLE);
                //populateSpinner(spinner, questionnaire.get(i).getAns());
                populateSpinner(spinner, questionnaire.get(i));
            } else {
                EditText etAnswer = ((EditText) item.findViewById(R.id.et_answer));
                etAnswer.setVisibility(View.VISIBLE);
                etAnswer.setId(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                if (questions != null && questions.size() > 0) {
                    for (int ii = 0; ii < questions.size(); ii++) {
                        if (questionnaire.get(i).getQues().equalsIgnoreCase(questions.get(ii).getQuestion())) {
                            etAnswer.setText(questions.get(ii).getAnswer());
                        }
                    }
                }
            }
            if (isCustomQuestion) {
                binding.llQuestionItem.addView(item, 0);
                arrangeNumbering();
            } else {
                binding.llQuestionItem.addView(item);
            }
            ImageView ivClose = (ImageView) item.findViewById(R.id.iv_delete);
            ivClose.setTag(questionnaire.get(i).getQues().trim());
            arrSelectedQuestions.put(questionnaire.get(i).getQues().trim(), binding.llQuestionItem.getChildCount() - 1);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String question = String.valueOf(view.getTag());
                    int position = arrSelectedQuestions.get(question);
                    try {
                        binding.llQuestionItem.removeViewAt(position);
                        arrSelectedQuestions.remove(question);
                        normalizeMap(arrSelectedQuestions, position);
                        arrangeNumbering();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if (isNeedToDisable) {
                enableField(spinner, !isNeedToDisable);
                ivClose.setVisibility(View.GONE);
            }
        }
    }

    private void fillNewdata(List<com.ziploan.team.verification_module.borrowerdetails.questions.Questionnaire> questionnaire, boolean isCustomQuestion) {

        for (int i = 0; i < questionnaire.size(); i++) {

            FrameLayout item = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.item_question, binding.llQuestionItem, false);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            item.setLayoutParams(params);
            item.setTag(questionnaire.get(i));
            TextView tvQuestion = ((TextView) item.findViewById(R.id.tv_question));
            tvQuestion.setTag((this.getId() + new Random().nextInt(9999)));
            if(questionnaire.get(i).getIsMendatory())
                tvQuestion.setText((i + 1) + ". " + questionnaire.get(i).getQues()  + Html.fromHtml("<sup>*</sup>"));
            else
                tvQuestion.setText((i + 1) + ". " + questionnaire.get(i).getQues());

            if (TextUtils.isEmpty(questionnaire.get(i).getDisplay()))
                return;
            if (questionnaire.get(i).getDisplay().equalsIgnoreCase(TEXT_AREA)) {
                EditText etAnswer = ((EditText) item.findViewById(R.id.et_answer));
                etAnswer.setVisibility(View.VISIBLE);
                etAnswer.setId(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                if (questions != null && questions.size() > 0) {
                    for (int ii = 0; ii < questions.size(); ii++) {
                        if (questionnaire.get(i).getQues().equalsIgnoreCase(questions.get(ii).getQuestion())) {
                            etAnswer.setText(questions.get(ii).getAnswer());
                            break;
                        }
                    }


                }
            } else if (questionnaire.get(i).getDisplay().equalsIgnoreCase(SINGLE_SELECT)) {
                Spinner spinner = item.findViewById(R.id.answer_spinner);
                spinner.setId(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                spinner.setVisibility(View.VISIBLE);
                if (questionnaire.get(i).getIsSubQues() == 1) {
                    populateSubSpinner(spinner, questionnaire.get(i), item, Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                } else {
                    populateNewSpinner(spinner, questionnaire.get(i));
                }
            } else if (questionnaire.get(i).getDisplay().equalsIgnoreCase(MULTI_SELECT)) {
                MultiSelectSpinner multiSelectSpinner = item.findViewById(R.id.multi_select_spinner);
                multiSelectSpinner.setId(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                multiSelectSpinner.setVisibility(View.VISIBLE);
                multiSelectSpinner.setItems(questionnaire.get(i).getAns());

                String ans = "";
                if (questions != null && questions.size() > 0) {
                    for (int ii = 0; ii < questions.size(); ii++) {
                        if (questionnaire.get(i).getQues().equalsIgnoreCase(questions.get(ii).getQuestion())) {
                            ans = questions.get(ii).getAnswer();
                        }
                    }
                }

                if (!TextUtils.isEmpty(ans)) {
                    String[] ansList = ans.trim().split(", ");
                    multiSelectSpinner.setSelection(ansList);
                    multiSelectSpinner.setSelect();
                }
            }

            if (isCustomQuestion) {
                binding.llQuestionItem.addView(item, 0);
                arrangeNumbering();
            } else {
                binding.llQuestionItem.addView(item);
            }
            ImageView ivClose = (ImageView) item.findViewById(R.id.iv_delete);
            if(questionnaire.get(i).getIsMendatory()){
                ivClose.setVisibility(View.GONE);
            }
            ivClose.setTag(questionnaire.get(i).getQues().trim());
            arrSelectedQuestions.put(questionnaire.get(i).getQues().trim(), binding.llQuestionItem.getChildCount() - 1);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String question = String.valueOf(view.getTag());
                    int position = arrSelectedQuestions.get(question);
                    try {
                        binding.llQuestionItem.removeViewAt(position);
                        arrSelectedQuestions.remove(question);
                        normalizeMap(arrSelectedQuestions, position);
                        arrangeNumbering();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if (isNeedToDisable) {
                //enableField(spinner, !isNeedToDisable);
                ivClose.setVisibility(View.GONE);
            }
        }
        fillAdditionalQuestions(questionnaire);
    }

    private void fillAdditionalQuestions(List<com.ziploan.team.verification_module.borrowerdetails.questions.Questionnaire> questionnaire){
        try {
            if (questions != null) {
                for (int j = 0; j < questions.size(); j++) {
                    boolean matched = false;
                    for (int k = 0; k < questionnaire.size(); k++) {
                        String ques = questions.get(j).getQuestion();
                        if (questionnaire.get(k).getQues().equalsIgnoreCase(ques.replace("*","")) || questionnaire.get(k).getIsSubQues() == 1) {
                            matched = true;
                        }
                    }
                    if (!matched) {
                        saved_questions.add(questions.get(0));
                    }
                }
            }
            if(saved_questions.size() > 0) {
                for (int i = 0; i < saved_questions.size(); i++) {

                    FrameLayout item = (FrameLayout) LayoutInflater.from(mContext).inflate(R.layout.item_question, binding.llQuestionItem, false);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    item.setLayoutParams(params);
                    TextView tvQuestion = ((TextView) item.findViewById(R.id.tv_question));
                    tvQuestion.setTag((this.getId() + new Random().nextInt(9999)));
                    tvQuestion.setText((i + 1) + ". " + saved_questions.get(i).getQuestion());

                    EditText etAnswer = ((EditText) item.findViewById(R.id.et_answer));
                    etAnswer.setVisibility(View.VISIBLE);
                    etAnswer.setId(Integer.parseInt(String.valueOf(tvQuestion.getTag())));
                    if (questions != null && questions.size() > 0) {
                        for (int ii = 0; ii < questions.size(); ii++) {
                            if (saved_questions.get(i).getQuestion().equalsIgnoreCase(questions.get(ii).getQuestion())) {
                                etAnswer.setText(questions.get(ii).getAnswer());
                                break;
                            }
                        }
                    }
                    binding.llQuestionItem.addView(item, 0);
                    arrangeNumbering();

                    ImageView ivClose = (ImageView) item.findViewById(R.id.iv_delete);
                    ivClose.setTag(saved_questions.get(i).getQuestion().trim());
                    arrSelectedQuestions.put(saved_questions.get(i).getQuestion().trim(), binding.llQuestionItem.getChildCount() - 1);
                    ivClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String question = String.valueOf(view.getTag());
                            int position = arrSelectedQuestions.get(question);
                            try {
                                binding.llQuestionItem.removeViewAt(position);
                                arrSelectedQuestions.remove(question);
                                normalizeMap(arrSelectedQuestions, position);
                                arrangeNumbering();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
        }
    }

    private void populateNewSpinner(Spinner spinner, com.ziploan.team.verification_module.borrowerdetails.questions.Questionnaire data) {
        try {
            if (data != null) {
                List<String> list = data.getAns();
                list.add(0, getString(R.string.select_anser));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(spinner.getContext(),
                        android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                for (int ii = 0; ii < questions.size(); ii++) {
                    if (data.getQues().equalsIgnoreCase(questions.get(ii).getQuestion())) {
                        for (int j = 0; j < data.getAns().size(); j++) {
                            if (data.getAns().get(j).equalsIgnoreCase(questions.get(ii).getAnswer())) {
                                spinner.setSelection(j);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }
    private void populateSubSpinner(Spinner spinner, com.ziploan.team.verification_module.borrowerdetails.questions.Questionnaire data, final FrameLayout item, final int id) {
        try {
            if (data != null) {
                List<An> list = data.getAnsObj();
                An val = new An();
                val.setKey(getString(R.string.select_anser));
                list.add(0, val);
                final MyArrayAdapter adapter = new MyArrayAdapter(spinner.getContext(),
                        android.R.layout.simple_spinner_item,list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        An answer = (An) adapterView.getSelectedItem();
                        if (answer != null && !answer.getKey().equalsIgnoreCase("Select Answer")) {
                            EditText etAnswer = ((EditText) item.findViewById(R.id.et_sub_answer));
                            if (answer.getVal() != null && !TextUtils.isEmpty(answer.getVal().getSubQues())) {
                                etAnswer.setId(id+ 29911);
                                etAnswer.setVisibility(View.VISIBLE);
                                etAnswer.setHint(answer.getVal().getSubQues());
                                etAnswer.requestFocus();
                                if(questions != null && questions.size() > 0) {

                                    for (int ii = 0; ii < questions.size(); ii++) {
                                        if (answer.getKey() != null) {
                                            if (answer.getVal().getSubQues().trim().equalsIgnoreCase(questions.get(ii).getQuestion().trim())) {
                                                etAnswer.setText(questions.get(ii).getAnswer());
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else {
                                if(etAnswer != null)
                                    etAnswer.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                for (int ii = 0; ii < questions.size(); ii++) {
                    if (data.getQues().equalsIgnoreCase(questions.get(ii).getQuestion())) {
                        if(data.getAnsObj() != null && data.getAnsObj().size() > 0) {
                            for (int j = 0; j < data.getAnsObj().size(); j++) {
                                if (data.getAnsObj().get(j).getKey().equalsIgnoreCase(questions.get(ii).getAnswer())) {
                                    spinner.setSelection(j);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public static class MyArrayAdapter extends ArrayAdapter<An>{

        private  List<An> list;
        public MyArrayAdapter(@NonNull Context context, int resource,List<An> list) {
            super(context, resource,list);
            this.list = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item,parent,false);

            An current = list.get(position);
            TextView name = (TextView) listItem.findViewById(android.R.id.text1);
            name.setText(current.getKey());

            return listItem;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null)
                listItem = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item,parent,false);

            An current = list.get(position);
            TextView name = (TextView) listItem.findViewById(android.R.id.text1);
            name.setText(current.getKey());

            return listItem;
        }
    }

    private void addReferenceBox(BankInfoModel referenceUser) {
        BankInfoLayoutBinding referenceBinding = BankInfoLayoutBinding.inflate(LayoutInflater.from(mContext), null, false);
        binding.bankInfo.addView(referenceBinding.getRoot());
        referenceBinding.setReference(referenceUser);
    }

    public ArrayList<BankInfoModel> getBankInfo() {
        ArrayList<BankInfoModel> bankInfoModels = new ArrayList<>();
        int count = binding.bankInfo.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = binding.bankInfo.getChildAt(i);
            String name = ((EditText) view.findViewById(R.id.bank_name)).getText().toString();
            String current_emi = ((EditText) view.findViewById(R.id.current_emi)).getText().toString();
            String loan = ((EditText) view.findViewById(R.id.active_loan)).getText().toString();
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(current_emi) && !TextUtils.isEmpty(loan)) {
                bankInfoModels.add(new BankInfoModel(name.trim(), loan.trim(), current_emi.trim()));
            }
        }
        return bankInfoModels;
    }

    public void showBankInfo() {
        int count = bankInfoModels.size();
        for (int i = 0; i < count; i++) {
            addReferenceBox(new BankInfoModel(bankInfoModels.get(i).getName(),bankInfoModels.get(i).getNo_of_active_loans(),
                    bankInfoModels.get(i).getCurrent_emi_amount()));
        }
    }
}
