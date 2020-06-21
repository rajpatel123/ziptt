package com.ziploan.team.asset_module.loans;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.asset_module.change_request.ChangeRequestActivity;
import com.ziploan.team.asset_module.change_request.TopUpRequest;
import com.ziploan.team.asset_module.visits.AssetPastVisitsScreen;
import com.ziploan.team.asset_module.visits.RecordNewVisitActivityFirst;
import com.ziploan.team.asset_module.ews.EWSActivity;
import com.ziploan.team.collection.application_list.ViewKYCDocumentsActivity;
import com.ziploan.team.databinding.ActivityLoanDetailsBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;
import com.ziploan.team.verification_module.borrowerslist.SimpleTextChangeLister;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;
import com.ziploan.team.webapi.Loan;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 */

public class LoansDetailsActivity extends AssetsBaseActivity implements View.OnClickListener {

    private static final int TOP_REQUEST = 1;
    private static final int EMAIL_SOA = 2;
    private ActivityLoanDetailsBinding allViews;
    private Bundle bundle;
    private Loan loan;

    @Override
    protected Toolbar getToolbar() {
        return allViews.actionBar.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loan_details;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityLoanDetailsBinding) views;
        bundle = getIntent().getExtras();
        if(bundle.containsKey(AppConstant.Key.EXTRA_OBJECT)){
            loan = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT);
            allViews.setLoan(loan);
            allViews.actionBar.setActionBarData(new ActionBarData(R.mipmap.ic_back,loan.getLoan_application_number()));
            if(loan.getLast_visited_coordinates().size()>1){
                allViews.buttonTrackLastLocation.setVisibility(View.VISIBLE);
            }else {
                allViews.buttonTrackLastLocation.setVisibility(View.GONE);
            }
        }
        setListeners();
    }

    private void setListeners() {
        allViews.buttonEws.setOnClickListener(this);
        allViews.buttonTrackLastLocation.setOnClickListener(this);
        allViews.buttonPastVisits.setOnClickListener(this);
        allViews.buttonRecodVisit.setOnClickListener(this);
        allViews.buttonChangeRequest.setOnClickListener(this);
        allViews.buttonTopupRequest.setOnClickListener(this);
        allViews.buttonEmailSoa.setOnClickListener(this);
        allViews.viewKyc.setOnClickListener(this);
        if(!loan.getAmount_overdue().equalsIgnoreCase("0")){
            ZiploanUtil.setClickableLink(allViews.tvOverdueAmount,loan.getOverdueAmountCustom(),new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT, loan);
                    OverdueListActivity.start(mContext, bundle);
                }
            });
        }else {
            allViews.tvOverdueAmount.setText(loan.getOverdueAmountCustom());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT,loan);
        switch (view.getId()) {
            case R.id.button_ews:
                EWSActivity.start(mContext,bundle);
                break;
            case R.id.button_past_visits:
                AssetPastVisitsScreen.start(mContext,bundle);
                break;
            case R.id.button_recod_visit:
                RecordNewVisitActivityFirst.start(mContext,bundle);
                break;
            case R.id.view_kyc:
                Bundle kycIntent = new Bundle();
                kycIntent.putString("num", loan.getIdentifier());
                kycIntent.putString("id", loan.getLoan_application_number());
                kycIntent.putBoolean("asset", true);
                ViewKYCDocumentsActivity.start(this, kycIntent);
                break;
            case R.id.button_change_request:
                ChangeRequestActivity.start(mContext,bundle);
                break;
            case R.id.button_topup_request:
                ShowConfirmationPopUp(TOP_REQUEST);
                break;
            case R.id.button_email_soa:
                openEmailPopUp();
                break;
            case R.id.button_track_last_location:
                openMapWithLocationPathToDestination(TextUtils.join(",",loan.getLast_visited_coordinates()));
                break;
        }
    }

    private void openEmailPopUp() {
        final Dialog dialog = ZiploanUtil.getCustomDialog(mContext, R.layout.dialog_add_email);
        dialog.show();
        ImageView sendSOA = (ImageView) dialog.findViewById(R.id.iv_send_soa);
        final EditText etEmailId = (EditText) dialog.findViewById(R.id.et_email);
        etEmailId.addTextChangedListener(new SimpleTextChangeLister(){
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                super.onTextChanged(charSequence, i, i1, i2);
                etEmailId.setError(null);
            }
        });
        sendSOA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getText(etEmailId);
                if(ZiploanUtil.isValidEmail(email)){
                    emailSOA(loan.getLoan_application_number(),email);
                    dialog.dismiss();
                }else{
                    etEmailId.setError("Please enter valid Email address.");
                }
            }
        });
    }

    private void ShowConfirmationPopUp(final int index) {
        final Dialog dialog = ZiploanUtil.getCustomDialog(mContext,R.layout.dialog_confirm);
        if(!isFinishing())
            dialog.show();
        ((TextView)dialog.findViewById(R.id.tv_message)).setText(index==EMAIL_SOA?R.string.want_email_soa:R.string.want_to_request_topup);
        dialog.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestTopUp();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void emailSOA(String loan_application_number, String email) {
        showProgressDialog("Please wait...");
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).emailSOA(loan_application_number,email);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                checkTokenValidity(response);
                if(response!=null && response.body()!=null && response.body().getResult()!=null && response.body().getResult().equalsIgnoreCase("1")){
                    showAlertInfo(getString(R.string.email_soa_success));
                }else {
                    showAlertInfo(getResources().getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                hideProgressDialog();
                showAlertInfo(getResources().getString(R.string.server_not_responding));
            }
        });
    }

    private void requestTopUp() {
        showProgressDialog("Please wait...");
        TopUpRequest request = new TopUpRequest();
        request.setLoan_request_id(loan.getIdentifier());
        request.setRequest_category("8");
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).topUpRequst(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                checkTokenValidity(response);
                if(response!=null && response.body()!=null && response.body().getResult()!=null && response.body().getResult().equalsIgnoreCase("1")){
                    showAlertInfo(getString(R.string.top_up_request_success));
                }else {
                    showAlertInfo(getResources().getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                hideProgressDialog();
                showAlertInfo(getResources().getString(R.string.server_not_responding));
            }
        });
    }

    @Override
    protected void onActionHomeButtonClicked() {
        onBackPressed();
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext,LoansDetailsActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}