package com.ziploan.team.verification_module.verifyekyc;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ziploan.team.R;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.verification_module.borrowerslist.SimpleTextChangeLister;
import com.ziploan.team.databinding.ActivityVerifyOtpEnterBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyByOTPEnterActivity extends BaseActivity implements View.OnClickListener {

    private ActivityVerifyOtpEnterBinding allViews;
    private OTPInitiateRequest otpInitiateRequest;
    private GenerateAadhaarEKYCRequest generateAadhaarEKYCRequest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_verify_otp_enter;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityVerifyOtpEnterBinding) views;
        setListeners();
        allViews.etOtp.post(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allViews.etOtp.setFocusable(true);
                        allViews.etOtp.setFocusableInTouchMode(true);
                        allViews.etOtp.requestFocus();
                        openKeyboard();
                    }
                });
            }
        });
        Intent intent = getIntent();
        if(intent!=null){
            if(intent.hasExtra(AppConstant.Key.EXTRA_OBJECT)){
                otpInitiateRequest = getIntent().getParcelableExtra(AppConstant.Key.EXTRA_OBJECT);
            }
            if(intent.hasExtra(AppConstant.Key.EXTRA_OBJECT1)){
                generateAadhaarEKYCRequest = getIntent().getParcelableExtra(AppConstant.Key.EXTRA_OBJECT1);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void openKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(allViews.etOtp.getWindowToken(), InputMethodManager.SHOW_FORCED,0);

    }

    private void setListeners() {
        allViews.ivBack.setOnClickListener(this);
        allViews.btnVerify.setOnClickListener(this);
        allViews.tvResendOtp.setOnClickListener(this);
        allViews.tvCode.setOnClickListener(this);
        allViews.etOtp.addTextChangedListener(new SimpleTextChangeLister(){
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setCodeToView(charSequence.toString());
            }
        });
    }

    private void setCodeToView(String otp) {
        allViews.tvCode.setText(otp+getDash(4-otp.length()));
    }

    private String getDash(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<length;i++){
            builder.append("-");
        }
        return builder.toString();
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, VerifyByOTPEnterActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.btn_verify:
                String otp = allViews.etOtp.getText().toString().trim();
                if(!TextUtils.isEmpty(otp)){
                    generateAadhaarEKYCResponse(otp);
                }else {
                    showAlertInfo(getString(R.string.enter_valid_otp));
                }
                break;
            case R.id.tv_resend_otp:
                resendOTP();
                break;
            case R.id.tv_code:
                try{
                    openKeyboard();
                    }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private void resendOTP() {
        showProgressDialog();
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).initiateOTPForAadhaar(otpInitiateRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                if(response!=null && response.body()!=null && !TextUtils.isEmpty(response.body().getAuthorization_key())){
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

    private void generateAadhaarEKYCResponse(String otp) {
        generateAadhaarEKYCRequest.setOtp(otp);
        showProgressDialog();
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).generateAadhaarEKYCResponse(generateAadhaarEKYCRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                if(response!=null && response.body()!=null && !TextUtils.isEmpty(response.body().getResult())){
                    String result = response.body().getResult();
                    if(result.equalsIgnoreCase("1")){
                        showVerifySuccessAlert(getString(R.string.aadhaar_matched));
                    }else {
                        showAlertInfo(getString(R.string.aadhaar_not_matched));
                    }
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

    protected void showVerifySuccessAlert(String msg) {
        final Dialog dialog = ZiploanUtil.getMessageCustomDialog(this, R.layout.dialog_common_prompt, msg);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.tv_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if(!isFinishing())
            dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}