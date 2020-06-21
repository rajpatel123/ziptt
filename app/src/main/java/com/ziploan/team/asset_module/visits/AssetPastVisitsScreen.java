package com.ziploan.team.asset_module.visits;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.ziploan.team.MyWebViewActivity;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.borrowerdetails.SimpleSpinnerAdapter;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;
import com.ziploan.team.databinding.ActivityAssetPastVisitsBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.verification_module.verifyekyc.PhotosAdapter;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.Loan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 */

public class AssetPastVisitsScreen extends AssetsBaseActivity implements View.OnClickListener, PhotosAdapter.PhotosAdapterListener {

    protected ActivityAssetPastVisitsBinding allViews;
    private Bundle bundle;
    protected Loan loan;
    private AssetsQuestionViewAdapter adapter;
    private PhotosAdapter photoAdapter;
    private ArrayList<ZiploanQuestion> arrQuestions = new ArrayList<>();
    private List<ZiploanPhoto> arrPhotos = new ArrayList<>();
    private ArrayList<String> visitDates = new ArrayList<>();
    private ArrayList<HashMap<String, PastVisit>> data;

    @Override
    protected Toolbar getToolbar() {
        return allViews.actionBar.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_asset_past_visits;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityAssetPastVisitsBinding) views;
        bundle = getIntent().getExtras();
        if(bundle.containsKey(AppConstant.Key.EXTRA_OBJECT)){
            loan = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT);
        }
        allViews.actionBar.setActionBarData(new ActionBarData(R.mipmap.ic_back,"PAST VISITS DETAILS"));

        setListeners();
        loadData();
    }

    private void loadData() {
        showProgressDialog();
        Call<ArrayList<HashMap<String, PastVisit>>> call = APIExecutor.getAPIService(mContext).pastVisits(loan.getIdentifier());
        call.enqueue(new Callback<ArrayList<HashMap<String, PastVisit>>>() {
            @Override
            public void onResponse(Call<ArrayList<HashMap<String, PastVisit>>> call, Response<ArrayList<HashMap<String, PastVisit>>> response) {
                hideProgressDialog();
                if(response!=null && response.body()!=null){
                    checkTokenValidity(response);
                    data = response.body();
                    for(int i=0;i<data.size();i++){
                        visitDates.addAll(data.get(i).keySet());
                    }
                    if(visitDates.size()>0){
                        fillSpinnerData(visitDates);
                        allViews.spinnerDate.setSelection(visitDates.size()-1);
                    }else {
                        ZiploanUtil.showToast(mContext,"No visit found for you.");
                        onBackPressed();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HashMap<String, PastVisit>>> call, Throwable t) {
                hideProgressDialog();
                ZiploanUtil.showToast(mContext,"Server error");
                onBackPressed();
            }
        });
    }

    private void setListeners() {
        allViews.llAddNewVisit.setOnClickListener(this);
        allViews.buttonViewPdf.setOnClickListener(this);
        allViews.spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                bindServerDataToViews(data.get(position).get(visitDates.get(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void bindServerDataToViews(PastVisit visit) {
        if(visit!=null) {
            if(visit.getParam().equalsIgnoreCase("1")){
                allViews.llSuccessfullVisit.setVisibility(View.VISIBLE);
                allViews.cvUnsuccessfull.setVisibility(View.GONE);
                allViews.setVisit(visit);
                arrQuestions.clear();
                arrQuestions.add(new ZiploanQuestion("How many customers do you have?", visit.getNumber_of_customers()));
                arrQuestions.add(new ZiploanQuestion("What is your average debtor cycle?", visit.getAverage_debtor_cycle()));
                arrQuestions.add(new ZiploanQuestion("What is your average creditor cycle?", visit.getAverage_creditor_cycle()));
                if(visit.getOther_questions()!=null)
                    arrQuestions.addAll(visit.getOther_questions());

                adapter = new AssetsQuestionViewAdapter(mContext, arrQuestions);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                allViews.rvQuestions.setLayoutManager(mLayoutManager);
                allViews.rvQuestions.setItemAnimator(new DefaultItemAnimator());
                allViews.rvQuestions.setAdapter(adapter);

                if(visit.getBusiness_place_photo() instanceof String){
                    allViews.buttonViewPdf.setText(visit.isPhotoAvailable()?"CLICK HERE TO VIEW":"No Data");
                    allViews.buttonViewPdf.setClickable(visit.isPhotoAvailable()?true:false);
                    allViews.buttonViewPdf.setTag(((String)visit.getBusiness_place_photo()));
                }else {
                    setPhotosToView(((ArrayList<String>)visit.getBusiness_place_photo()));
                }
            }else {
                allViews.scrollView.setVisibility(View.VISIBLE);
                allViews.llSuccessfullVisit.setVisibility(View.GONE);
                allViews.cvUnsuccessfull.setVisibility(View.VISIBLE);
                allViews.tvUnsuccessfullComment.setText(visit.getVisit_unsucessful_comments());
            }
        }
    }

    private void setPhotosToView(List<String> business_place_photo) {
        arrPhotos.clear();
        for(int i=0;i<business_place_photo.size();i++){
            if(business_place_photo.get(i)!=null)
                arrPhotos.add(new ZiploanPhoto(business_place_photo.get(i),AppConstant.MediaType.IMAGE));
        }
        if(arrPhotos.size()>0){
            allViews.buttonViewPdf.setVisibility(View.GONE);
            photoAdapter = new PhotosAdapter(mContext,arrPhotos,this);
            allViews.rvPhotos.setLayoutManager(new GridLayoutManager(mContext,3));
            allViews.rvPhotos.setAdapter(photoAdapter);
        }else {
            allViews.buttonViewPdf.setText("No Data");
            allViews.buttonViewPdf.setClickable(false);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT,loan);
        switch (view.getId()) {
            case R.id.ll_add_new_visit:
                RecordNewVisitActivityFirst.start(mContext,bundle);
                break;
            case R.id.button_view_pdf:
                bundle.putString(AppConstant.Key.EXTRA_WEB_URL, view.getTag().toString());
                MyWebViewActivity.start(mContext,bundle);
                break;
            case R.id.iv_close:
                finish();
                break;
        }
    }

    private void fillSpinnerData(ArrayList<String> arrDemo) {
        allViews.spinnerDate.setAdapter(new SimpleSpinnerAdapter(arrDemo));
        ((SimpleSpinnerAdapter)allViews.spinnerDate.getAdapter()).setSelectedTextColor(android.R.color.white);
    }

    @Override
    protected void onActionHomeButtonClicked() {
        onBackPressed();
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext,AssetPastVisitsScreen.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void deletePhoto(int position, int index) {

    }

    @Override
    public void retryUpload(int position, int index) {

    }

    @Override
    public void openCameraGalleyOptions(int position, int index, boolean multi_selection) {

    }
}