package com.ziploan.team.verification_module.verifyekyc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.ziploan.team.R;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.databinding.ActivityEkycVerificationStatusBinding;
import com.ziploan.team.utils.AppConstant;

public class EKYCVerificationStatusActivity extends BaseActivity implements View.OnClickListener {

    private ActivityEkycVerificationStatusBinding allViews;
    private Bundle bundle;
    private int ekycStatus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ekyc_verification_status;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityEkycVerificationStatusBinding) views;
        setListeners();
        bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(AppConstant.EXTRA_EKYC_VER_STATUS)){
            ekycStatus = bundle.getInt(AppConstant.EXTRA_EKYC_VER_STATUS);
            showViewAsStatus(ekycStatus);
        }
    }

    private void showViewAsStatus(int ekycStatus) {
        switch (ekycStatus){
            case 0:
                allViews.imageScanStatus.setImageResource(R.mipmap.ekyc_ver_failed);
                allViews.tvVerStatusTitle.setText("Verification Failed !");
                allViews.tvBiometricScanStatus.setText("Aadhar Number and Finger print did not   matched !");
                allViews.btnTryAgain.setVisibility(View.VISIBLE);
                break;
            case 1:
                allViews.imageScanStatus.setImageResource(R.mipmap.ekyc_verified);
                allViews.tvVerStatusTitle.setText("Verification Successful !");
                allViews.tvBiometricScanStatus.setText("Aadhar Number and Finger print matched successfuly.");
                allViews.btnTryAgain.setVisibility(View.GONE);
                break;
        }
    }

    private void setListeners() {
        allViews.btnTryAgain.setOnClickListener(this);
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, EKYCVerificationStatusActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public static void start(Activity mContext, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mContext, EKYCVerificationStatusActivity.class);
        intent.putExtras(bundle);
        mContext.startActivityForResult(intent,requestCode);
    }

    @Override
    public void onBackPressed() {
        if(ekycStatus == 0){
            setResult(RESULT_CANCELED);
        }else if(ekycStatus == 1){
            setResult(RESULT_OK);
        }
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_try_again:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
