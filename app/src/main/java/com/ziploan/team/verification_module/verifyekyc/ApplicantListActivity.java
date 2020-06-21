package com.ziploan.team.verification_module.verifyekyc;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

//import com.morpho.morphosmart.sdk.MorphoDevice;
import com.ziploan.team.R;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.verification_module.borrowerslist.BorrowersUnverified;
import com.ziploan.team.databinding.ActivityApplicantListBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.webapi.EkycDetail;

import java.util.ArrayList;

public class ApplicantListActivity extends BaseActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final int VERIFY_BY_BIOMETRIC_REQUEST = 100;
    private static final int VERIFY_BY_OTP_REQUEST = 101;
    private ActivityApplicantListBinding allViews;
    private ApplicantListAdapter mAdapter;
    private EkycDetail currentClickedObject;
    private BorrowersUnverified borrower;
    private ArrayList<EkycDetail> ekycDetails;
//    private MorphoDevice morphoDevice = new MorphoDevice();
    private Handler mHandler = new Handler();
    private String sensorName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_applicant_list;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityApplicantListBinding) views;
        if(getIntent()!=null && getIntent().hasExtra(AppConstant.Key.EXTRA_OBJECT)){
            borrower = getIntent().getParcelableExtra(AppConstant.Key.EXTRA_OBJECT);
        }

        if(getIntent()!=null && getIntent().hasExtra(AppConstant.Key.EXTRA_EKYC_DETAILS)){
            ekycDetails = getIntent().getParcelableArrayListExtra(AppConstant.Key.EXTRA_EKYC_DETAILS);
        }
        setListeners();
        setDataToView();

    }

    private void setListeners() {
        allViews.llVerifyOption.setOnClickListener(this);
        allViews.tvOtpVerified.setOnClickListener(this);
        allViews.tvBioVerified.setOnClickListener(this);
        allViews.ivBack.setOnClickListener(this);
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, ApplicantListActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void setDataToView() {
        mAdapter = new ApplicantListAdapter(mContext, ekycDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allViews.rvApplicant.setLayoutManager(mLayoutManager);
        allViews.rvApplicant.setItemAnimator(new DefaultItemAnimator());
        allViews.rvApplicant.setAdapter(mAdapter);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        allViews.llVerifyOption.setVisibility(View.VISIBLE);
        currentClickedObject = ekycDetails.get(position);
    }

    @Override
    public void onRefresh() {
        allViews.swipeLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(allViews.llVerifyOption.getVisibility() == View.VISIBLE){
            allViews.llVerifyOption.setVisibility(View.GONE);
        }else {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = null;
        switch (view.getId()){
            case R.id.ll_verify_option:
                view.setVisibility(View.GONE);
                break;
            case R.id.tv_otp_verified:
                allViews.llVerifyOption.setVisibility(View.GONE);
                bundle = new Bundle();
                bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT,currentClickedObject);
                bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT1,borrower);
                Intent intent = new Intent(mContext,VerifyByOTPActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, AppConstant.VERIFY_BY_OTP_REQUEST);
                break;
            case R.id.tv_bio_verified:

                allViews.llVerifyOption.setVisibility(View.GONE);
                bundle = new Bundle();
                bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT, currentClickedObject);
                bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT1, borrower);
//                VerifyByBiometricActivity.start(ApplicantListActivity.this, bundle, VERIFY_BY_BIOMETRIC_REQUEST);

                break;
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == VERIFY_BY_OTP_REQUEST || requestCode == VERIFY_BY_BIOMETRIC_REQUEST) && resultCode == RESULT_OK){
            currentClickedObject.setStatus("Verified successfully.");
            mAdapter.notifyDataSetChanged();
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(AppConstant.Key.EXTRA_EKYC_DETAILS,ekycDetails);
            setResult(RESULT_OK,intent);
        }
    }
}