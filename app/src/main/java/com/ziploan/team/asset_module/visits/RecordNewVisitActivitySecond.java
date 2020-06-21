package com.ziploan.team.asset_module.visits;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;
import com.ziploan.team.databinding.ActivityRecordNewVisitSecondBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.webapi.Loan;
import com.ziploan.team.webapi.RecordNewVisitPojo;

import java.util.ArrayList;

/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 */

public class RecordNewVisitActivitySecond extends AssetsBaseActivity implements View.OnClickListener {

    private ActivityRecordNewVisitSecondBinding allViews;
    private Bundle bundle;
    private RecordNewVisitPojo visitedData;
    private AssetsQuestionAdapter adapter;
    private ArrayList<ZiploanQuestion> arrQuestions = new ArrayList<>();
    private Loan loan;

    @Override
    protected Toolbar getToolbar() {
        return allViews.actionBar.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_new_visit_second;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityRecordNewVisitSecondBinding) views;
        bundle = getIntent().getExtras();
        if(bundle.containsKey(AppConstant.Key.EXTRA_OBJECT_VISITED_DATA)){
            visitedData = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT_VISITED_DATA);
        }
        if(bundle.containsKey(AppConstant.Key.EXTRA_OBJECT)){
            loan = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT);
        }
        allViews.actionBar.setActionBarData(new ActionBarData(R.mipmap.ic_back,"Record New Visit"));
        allViews.actionBar.setLastVisitButton(true);
        setListeners();

        adapter = new AssetsQuestionAdapter(mContext, arrQuestions);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allViews.rvOtherQuestions.setLayoutManager(mLayoutManager);
        allViews.rvOtherQuestions.setItemAnimator(new DefaultItemAnimator());
        allViews.rvOtherQuestions.setAdapter(adapter);
    }

    public void addDataToView(ZiploanQuestion question){
        int startPosition = arrQuestions.size();
        arrQuestions.add(question);
        adapter.setFocusedTextField(startPosition);
        adapter.notifyItemRangeInserted(startPosition,1);
    }

    private void setListeners() {
        allViews.tvAddMoreQ.setOnClickListener(this);
        allViews.buttonNext.setOnClickListener(this);
        allViews.actionBar.buttonViewOld.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button_next:
                if(checkValidation()){
                    visitedData.setAverage_debtor_cycle(getText(allViews.etAvgDebtorCycle));
                    visitedData.setAverage_creditor_cycle(getText(allViews.etAvgCreditorCycle));
                    visitedData.setNumber_of_customers(getText(allViews.etCustomerNo));
                    ArrayList<ZiploanQuestion> arrayList = adapter.getOtherQuestion();
                    if(arrayList.size()>0){
                        visitedData.setOther_questions(arrayList);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT_VISITED_DATA, visitedData);
                    bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT,loan);
                    RecordNewVisitActivityThird.start(mContext,bundle);
                }

                break;
            case R.id.tv_add_more_q:
                final Dialog dialog = ZiploanUtil.getCustomDialog(mContext, R.layout.dialog_add_question);
                dialog.show();
                ImageView addQuestion = (ImageView) dialog.findViewById(R.id.iv_add_question);
                final EditText etQuestion = (EditText) dialog.findViewById(R.id.et_question);
                addQuestion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String question = getText(etQuestion);
                        if(question.length()>0){
                            addDataToView(new ZiploanQuestion(question,""));
                            dialog.dismiss();
                        }else{

                        }
                    }
                });
                break;
            case R.id.button_view_old:
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT,loan);
                AssetPastVisitsDialogScreen.start(mContext,bundle);
                break;
        }
    }

    private boolean checkValidation() {
        ArrayList<String> arrRequiredFields = new ArrayList<>();
        if(allViews.etCustomerNo.getText().toString().trim().length()==0){
            arrRequiredFields.add("No of Customer");
        }
        if(allViews.etAvgDebtorCycle.getText().toString().trim().length()==0){
            arrRequiredFields.add("Average debetor cycle");
        }
        if(allViews.etAvgCreditorCycle.getText().toString().trim().length()==0){
            arrRequiredFields.add("Average creditor cycle");
        }
        if(arrRequiredFields.size()>0){
            StringBuilder builder = new StringBuilder();
            builder.append("Required field(s) are : <br/>");
            for(int i=0;i<arrRequiredFields.size();i++)
                builder.append("&#9679; "+arrRequiredFields.get(i)+"<br/>");
            showAlertInfo(Html.fromHtml(builder.toString()), Color.parseColor("#ff0000"));
            return false;
        }
        return true;
    }

    @Override
    protected void onActionHomeButtonClicked() {
        onBackPressed();
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext,RecordNewVisitActivitySecond.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_ENABLE_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
            } else if (resultCode == Activity.RESULT_CANCELED) {
                openLocationRequest();
            }
        }
    }

}