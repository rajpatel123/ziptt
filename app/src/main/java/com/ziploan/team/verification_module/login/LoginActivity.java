package com.ziploan.team.verification_module.login;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.loans.LoansListActivity;
import com.ziploan.team.collection.service.PostRecordVisitJob;
import com.ziploan.team.databinding.ActivityLoginBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.NetworkUtil;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.verification_module.borrowerdetails.loginmethod.GenerateOTPResponse;
import com.ziploan.team.verification_module.borrowerdetails.loginmethod.LoginAction;
import com.ziploan.team.verification_module.borrowerdetails.loginmethod.LoginMethodTypeRequest;
import com.ziploan.team.verification_module.borrowerdetails.loginmethod.LoginMethodTypeResponse;
import com.ziploan.team.verification_module.borrowerslist.BorrowersListActivity;
import com.ziploan.team.verification_module.services.PostApplicationDataJob;
import com.ziploan.team.verification_module.services.SyncFiltersJob;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;
import com.ziploan.team.webapi.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Created by ZIploan-Nitesh on 2/2/2017.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    private static final String TAG = "Call" + LoginActivity.class.getSimpleName();
    ArrayList<ZiploanTeamUser> arrayList = new ArrayList<>();
    ActivityLoginBinding allViews;
    private LoginAction login_btn_action = LoginAction.GET_LOGIN_METHOD;
    private String username;
    private int time_to_left = 10;
    private CountDownTimer countDownTimer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityLoginBinding) views;
        allViews.ivLogo.setColorFilter(Color.parseColor("#ff8f00"), PorterDuff.Mode.SRC_ATOP);

        ZiploanSPUtils.getInstance(LoginActivity.this).setAccessId("");
        ZiploanSPUtils.getInstance(LoginActivity.this).setAccessToken("");
        setListeners();
        fillSpinnerData();
        autoCompleteUserIds();
    }

    private void autoCompleteUserIds() {
        String[] emails = ZiploanSPUtils.getInstance(mContext).getUsedEmailsForLogin();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, emails);
        allViews.etUsername.setAdapter(adapter);
    }


    private void setListeners() {
        allViews.tvLogin.setOnClickListener(this);
        allViews.etPassword.setOnEditorActionListener(this);
        allViews.tvResendOtp.setOnClickListener(this);
        allViews.tvNotRcvd.setOnClickListener(this);
    }

    private void fillSpinnerData() {
        arrayList = new ArrayList<>();
        arrayList.add(new ZiploanTeamUser("Select User Type", "0"));
        arrayList.add(new ZiploanTeamUser("Physical Verification", AppConstant.UserType.PHYSICAL_VERIFICATION));
        arrayList.add(new ZiploanTeamUser("Asset Manager", AppConstant.UserType.ASSET_MANAGEMENT));
        //arrayList.add(new ZiploanTeamUser("Collection",AppConstant.UserType.COLLECTION));
        allViews.spinner.setAdapter(new UserTypeSpinnerAdapter(arrayList));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:

                switch (login_btn_action) {
                    case GET_LOGIN_METHOD:
                        getLoginMethod();
                        break;
                    case LOGIN_OTP:
                        int userTypePosition = allViews.spinner.getSelectedItemPosition();
                        String userTypeId = arrayList.get(userTypePosition).getUser_id();
                        String username = allViews.etUsername.getText().toString().trim();
                        String password = allViews.etOtp.getText().toString().trim();
                        if (checkValidation(userTypePosition, username, password)) {
                            loginUser(username, password, userTypeId);
                        }
                        break;
                    case LOGIN_PASSWORD:
                        int userTypePos = allViews.spinner.getSelectedItemPosition();
                        String userType = arrayList.get(userTypePos).getUser_id();
                        String email = allViews.etUsername.getText().toString().trim();
                        String pwd = allViews.etPassword.getText().toString().trim();
                        if (checkValidation(userTypePos, email, pwd)) {
                            loginUser(email, pwd, userType);
                        }
                        break;

                }

                break;

            case R.id.tv_resend_otp:
            case R.id.tv_not_rcvd:
                if (!TextUtils.isEmpty(username) && NetworkUtil.getConnectivityStatus(this) > 0) {
                    showProgressDialog();
                    generateOTP(username);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            allViews.tvResendOtpLL.setVisibility(View.GONE);
                        }
                    },2*1000);

                } else {
                    showAlertInfo("" + R.string.no_internet);
                }
                break;


        }
    }

    private void getLoginMethod() {
        username = allViews.etUsername.getText().toString().trim();
        LoginMethodTypeRequest request = new LoginMethodTypeRequest(username, "app");

        showProgressDialog();
        Call<LoginMethodTypeResponse> call = APIExecutor.getAPIServiceWithLS(this).getLoginMethodType(request);


        Log.d(TAG, "Api End Points ==>sourcing/v3/authentication/login_type/");
        Log.d(TAG, "Request ==>" + new Gson().toJson(request));

        call.enqueue(new Callback<LoginMethodTypeResponse>() {
            @Override
            public void onResponse(Call<LoginMethodTypeResponse> call, Response<LoginMethodTypeResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    LoginMethodTypeResponse loginMethodTypeResponse = response.body();
                    Log.d(TAG, "Response ==>" + new Gson().toJson(loginMethodTypeResponse));
                    if (loginMethodTypeResponse.getStatus().equalsIgnoreCase("success")) {
                        if (!TextUtils.isEmpty(loginMethodTypeResponse.getResponseData().getData())
                                && loginMethodTypeResponse.getResponseData().getData().equalsIgnoreCase("OTP")) {

                            generateOTP(username);

                        } else {
                            hideProgressDialog();

                            allViews.userTypeLL.setVisibility(View.VISIBLE);
                            allViews.etPassword.setVisibility(View.VISIBLE);
                            login_btn_action = LoginAction.LOGIN_PASSWORD;
                            allViews.tvLogin.setText("LOGIN");
                        }
                    } else {
                        hideProgressDialog();

                        showToast(LoginActivity.this, loginMethodTypeResponse.getStatusMessage());
                    }

                } else {
                    try {
                        hideProgressDialog();

                        String rawData = response.errorBody().string();
                        if (!TextUtils.isEmpty(rawData)) {
                            Log.d(TAG, "Response ==>" + rawData);

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(rawData);
                                showToast(LoginActivity.this, jsonObject.optString("status_message"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<LoginMethodTypeResponse> call, Throwable t) {
                hideProgressDialog();
                Log.d(TAG, "Connection Failed ==>" + t.getMessage());

            }
        });

    }

    private void generateOTP(String username) {

        Call<GenerateOTPResponse> call = APIExecutor.getAPIServiceWithLS(this).generate_otp(username);
        Log.d(TAG, "Api End Points ==>sourcing/v3/authentication/generate_otp/");
        Log.d(TAG, "Request Parameter ==>" + username);
        call.enqueue(new Callback<GenerateOTPResponse>() {
            @Override
            public void onResponse(Call<GenerateOTPResponse> call, Response<GenerateOTPResponse> response) {
                hideProgressDialog();
                if (response.code() == 200 && response.body() != null) {
                    GenerateOTPResponse generateOTPResponse = response.body();

                    Log.d(TAG, "Request Parameter ==>" + new Gson().toJson(generateOTPResponse));

                    if (generateOTPResponse.getStatus().equalsIgnoreCase("success")) {
                        allViews.userTypeLL.setVisibility(View.VISIBLE);
                        allViews.etOtp.setVisibility(View.VISIBLE);
                        login_btn_action = LoginAction.LOGIN_OTP;
                        startTimer();
                        allViews.tvResendOtpLL.setVisibility(View.VISIBLE);
                        allViews.tvLogin.setText("SUBMIT");
                        allViews.tvOtpsentMessage.setVisibility(View.VISIBLE);
                        allViews.tvOtpsentMessage.setText("" + generateOTPResponse.getStatusMessage());
//                        showToast(LoginActivity.this, generateOTPResponse.getStatusMessage());
                    } else {
                        showToast(LoginActivity.this, generateOTPResponse.getStatusMessage());
                    }


                } else {
                    try {

                        String rawData = response.errorBody().string();
                        if (!TextUtils.isEmpty(rawData)) {
                            Log.d(TAG, "Response ==>" + rawData);

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(rawData);
                                showToast(LoginActivity.this, jsonObject.optString("status_message"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<GenerateOTPResponse> call, Throwable t) {
                hideProgressDialog();
                Log.d(TAG, "Connection Failed ==>" + t.getMessage());
                allViews.etOtp.setVisibility(View.VISIBLE);
                login_btn_action = LoginAction.LOGIN_OTP;

            }
        });


    }

    private void loginUser(final String username, String password, final String userType) {
        showProgressDialog();
        LoginRequest request;
        if (TextUtils.isEmpty(userType)) {
            request = new LoginRequest(username, password, null);
        } else {
            request = new LoginRequest(username, password, userType);
        }


        Log.d(TAG, "Api End Points ==>sourcing/v3/authentication/login/");
        Log.d(TAG, "Request Parameter ==>" + new Gson().toJson(request));
        Call<ApiResponse> call = APIExecutor.getAPIServiceWithLS(mContext).loginUser(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                if (response != null && response.body() != null) {
                    ZiploanSPUtils.getInstance(mContext).addUsedEmailsForLogin(username);

                    Log.d(TAG, "Response ==>" + new Gson().toJson(response.body()));

                    if (!TextUtils.isEmpty(response.body().getUser_account_status()) && (response.body().getUser_account_status().equalsIgnoreCase("1") || response.body().getUser_account_status().equalsIgnoreCase("1.0"))) {
                        ZiploanSPUtils.getInstance(mContext).setIsLoggedIn(true);
                        ZiploanSPUtils.getInstance(mContext).setAccessToken(response.body().getAccess_token());
                        ZiploanSPUtils.getInstance(mContext).setAccessId(response.body().getUser_account_id());
                        ZiploanSPUtils.getInstance(mContext).setExpirationDate(response.body().getToken_expiration_time());
                        ZiploanSPUtils.getInstance(mContext).setAccessStatus(true);
                        resumeSyncingData();
                        SyncFiltersJob.scheduleJob();
                        navigateToDashboard(userType);
                        finish();
                    } else {
                        showAlertInfo(response.body().getMessage());
                    }
                } else if (response != null && response.errorBody() != null) {

                    try {

                        String rawData = response.errorBody().string();
                        if (!TextUtils.isEmpty(rawData)) {
                            Log.d(TAG, "Response ==>" + rawData);

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(rawData);
                                showToast(LoginActivity.this, jsonObject.optString("status_message"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(TAG, "Connection Failed ==>" + t.getMessage());
                hideProgressDialog();
                showAlertInfo(t.getMessage() != null ? t.getMessage() : "Server not responding");
            }
        });
    }

    private void navigateToDashboard(String userType) {
        ZiploanSPUtils.getInstance(mContext).setLoggedInUserType(userType);
        switch (userType) {
            case AppConstant.UserType.PHYSICAL_VERIFICATION:
                BorrowersListActivity.start(mContext, new Bundle());
                break;
            case AppConstant.UserType.ASSET_MANAGEMENT:
                LoansListActivity.start(mContext, new Bundle());
                break;
            case AppConstant.UserType.COLLECTION:
                // ApplicationListActivity.start(mContext,new Bundle());
                break;
        }
    }

    private void resumeSyncingData() {
        PostApplicationDataJob.scheduleAdvancedJob();
        PostRecordVisitJob.scheduleAdvancedJob();
    }

    private boolean checkValidation(int userType, String username, String password) {
        if (userType == 0) {
            showAlertInfo(getString(R.string.choose_valid_type));
            return false;
        } else if (username.length() == 0) {
            showAlertInfo(getString(R.string.valid_username));
            return false;
        } else if (password.length() == 0) {
            if (login_btn_action == LoginAction.LOGIN_OTP) {
                showAlertInfo(getString(R.string.valid_otp));
            } else {
                showAlertInfo(getString(R.string.valid_pasword));
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return false;
    }


    private void startTimer() {
        countDownTimer = new CountDownTimer(time_to_left * 1000, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                allViews.tvResendOtp.setEnabled(true);
                allViews.tvResendOtp.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                allViews.tvNotRcvd.setEnabled(true);
            }
        };

        countDownTimer.start();
    }
}
