package com.ziploan.team.verification_module.verifyekyc;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ziploan.team.R;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.verification_module.borrowerslist.BorrowersUnverified;
import com.ziploan.team.databinding.ActivityVerifyOtpBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;
import com.ziploan.team.webapi.EkycDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyByOTPActivity extends BaseActivity implements View.OnClickListener {

    private ActivityVerifyOtpBinding allViews;
    private ApplicantListAdapter mAdapter;
    private List<Applicant> arrApplicant = new ArrayList<>();
    private EkycDetail applicant;
    private BorrowersUnverified borrower;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_otp;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityVerifyOtpBinding) views;
        setListeners();
        Intent bundle = getIntent();
        if(bundle!=null){
            if(bundle.hasExtra(AppConstant.Key.EXTRA_OBJECT)){
                applicant = bundle.getParcelableExtra(AppConstant.Key.EXTRA_OBJECT);
                if(applicant!=null && !TextUtils.isEmpty(applicant.getAadhar_number())){
                    allViews.etAadhaarNo.setText(applicant.getAadhar_number());
                    allViews.etAadhaarNo.setSelection(applicant.getAadhar_number().length());
                }
            }
            if(bundle.hasExtra(AppConstant.Key.EXTRA_OBJECT1)){
                borrower = bundle.getParcelableExtra(AppConstant.Key.EXTRA_OBJECT1);
            }
        }
    }

    private void setListeners() {
        allViews.ivBack.setOnClickListener(this);
        allViews.btnSendOtp.setOnClickListener(this);
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, VerifyByOTPActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btn_send_otp:
                String aadhaarNo = allViews.etAadhaarNo.getText().toString().trim();
                if(ZiploanUtil.isAadhaarValid(aadhaarNo)){
                    sendOTP(aadhaarNo);
                }else {
                    showFieldError(allViews.etAadhaarNo,"Please enter valid aadhaar number.");
                }
                break;
        }
    }


    private void sendOTP(String aadhaarNo) {
        showProgressDialog();
        final OTPInitiateRequest request = new OTPInitiateRequest(borrower.getLoan_request_id(),applicant.getApplicant_type(),aadhaarNo);
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).initiateOTPForAadhaar(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                if(response!=null && response.body()!=null && !TextUtils.isEmpty(response.body().getAuthorization_key())){
                    GenerateAadhaarEKYCRequest request1 = new GenerateAadhaarEKYCRequest(borrower.getLoan_request_id(),applicant.getApplicant_type(),response.body().getAuthorization_key(),"");
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT,request);
                    bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT1,request1);
                    Intent intent = new Intent(mContext, VerifyByOTPEnterActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,AppConstant.VERIFY_ENTER_OTP_REQUEST);
                }else {
                    showAlertInfo(getString(R.string.something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                hideProgressDialog();
                showAlertInfo(t.getMessage()!=null?t.getMessage():getString(R.string.server_not_responding));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppConstant.VERIFY_ENTER_OTP_REQUEST){
            setResult(resultCode);
            finish();
        }
    }
}