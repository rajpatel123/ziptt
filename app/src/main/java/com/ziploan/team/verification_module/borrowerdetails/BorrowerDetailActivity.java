package com.ziploan.team.verification_module.borrowerdetails;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.ViewDataBinding;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.ziploan.team.R;
import com.ziploan.team.databinding.ActivityBorrowerDetailBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.PermissionUtil;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.verification_module.base.BaseFragment;
import com.ziploan.team.verification_module.borrowerdetails.credit.CreditFragment;
import com.ziploan.team.verification_module.borrowerdetails.ekitsign.EsignRelatedDetailsFragments;
import com.ziploan.team.verification_module.borrowerdetails.ekitsign.EsignRelatedDocumentsFragment;
import com.ziploan.team.verification_module.borrowerdetails.ekitsign.esignfeildrequest.ESignFeildRequest;
import com.ziploan.team.verification_module.borrowerdetails.questions.ZiploanNewBorrowerDetails;
import com.ziploan.team.verification_module.borrowerslist.BorrowersUnverified;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.verification_module.services.LocationListener;
import com.ziploan.team.verification_module.services.PostApplicationDataJob;
import com.ziploan.team.verification_module.verifyekyc.ApplicantListActivity;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.EkycDetail;
import com.ziploan.team.webapi.LoanRequestDataResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowerDetailActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = BorrowerDetailActivity.class.getSimpleName();
    private static final int LOCATION_ENABLE_REQUEST = 1000;
    private ActivityBorrowerDetailBinding allViews;
    private BorrowersUnverified borrower = new BorrowersUnverified();
    private MyPagerAdapter mPagerAdapter;
    private TextView[] tabViews;
    private ZiploanBorrowerDetails details;
    private boolean needToDisable;
    private AlertDialog itemListDialog;
    private SparseArray<BaseFragment> mPageReferenceMap = new SparseArray<BaseFragment>();
    private LocationManager mLocationManager;
    private static final int LOCATION_INTERVAL = 1000;//1000;
    private static final float LOCATION_DISTANCE = 0;
    private LocationListener[] mLocationListeners;

    private GoogleApiClient googleApiClient;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_borrower_detail;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityBorrowerDetailBinding) views;
        tabViews = new TextView[]{allViews.tvSiteInfo, allViews.tvQuestion, allViews.credit, allViews.tvDocuments, allViews.tvFields};

        setListeners();
        if (getIntent() != null && getIntent().hasExtra(AppConstant.Key.EXTRA_OBJECT)) {
            borrower = getIntent().getParcelableExtra(AppConstant.Key.EXTRA_OBJECT);
            allViews.setBorrower(borrower);
            ZiploanSPUtils.getInstance(mContext).setSelectedLoanRequestId(borrower.getLoan_request_id());
        } else {
            finish();
            Toast.makeText(mContext, "Something went wrong.", Toast.LENGTH_LONG).show();
        }

        details = DatabaseManger.getInstance().getBorrowerApplicationDetails(borrower);

        if (Float.parseFloat(borrower.getLoan_request_status()) >= 7.5 || (details != null && details.is_final())) {
            needToDisable = true;
            allViews.btnSubmit.setVisibility(View.GONE);
        } else {
            if (PermissionUtil.checkLocationPermission(mContext)) {
                if (!ZiploanUtil.isLocationProviderEnabled(this)) {
                    openLocationRequest();
                } else {
                    startLocationService();
                }
            } else {
                PermissionUtil.requestLocationPermission(mContext);
            }

            //setPager();
        }
        setPager();
//        loadData();
        LocalBroadcastManager.getInstance(mContext).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.hasExtra(AppConstant.LocalBroadcastType.APPLICATION_POST_STATUS)) {
                    switch (intent.getIntExtra(AppConstant.LocalBroadcastType.APPLICATION_POST_STATUS, 0)) {
                        case AppConstant.LocalBroadcastType.APPLICATION_SUBMITTED_LOCALLY:
                            break;
                        case AppConstant.LocalBroadcastType.FILE_UPLOADING_STARTED:
                            break;
                        case AppConstant.LocalBroadcastType.FILE_UPLOADING_COMPLETED:
                            break;
                        case AppConstant.LocalBroadcastType.FILE_UPLOADING_FAILED:
                            break;
                    }
                }
            }
        }, new IntentFilter(AppConstant.LocalBroadcastType.APPLICATION_POST_STATUS));
    }

    private void setPager() {
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), details);
        allViews.pager.setAdapter(mPagerAdapter);
        allViews.pager.setOffscreenPageLimit(5);


    }


    /**
     * API will fetch all submitted verification details of selected loan application
     * and applicants list with their ekyc status.
     */
    private void loadData() {
        showProgressDialog();
        allViews.verifyEkyc.setVisibility(View.GONE);
        Call<LoanRequestDataResponse> call = APIExecutor.getAPIService(mContext).getLoanRequestData(borrower.getLoan_request_id());
        call.enqueue(new Callback<LoanRequestDataResponse>() {
            @Override
            public void onResponse(Call<LoanRequestDataResponse> call, Response<LoanRequestDataResponse> response) {
                hideProgressDialog();
                allViews.verifyEkyc.setVisibility(View.VISIBLE);
                if (response != null && response.body() != null) {
                    if (!(Float.parseFloat(borrower.getLoan_request_status()) >= 7.5)) {
                        if (details == null)
                            details = new ZiploanBorrowerDetails();
                        details.setEkyc_details(response.body().getEkyc_details());
                    } else {
                        details = getLoanVerificationDetailFromServerResponse(response.body());
                    }
                }
                System.out.println("data from server = " + new Gson().toJson(response.body()));
                setPager();
            }

            @Override
            public void onFailure(Call<LoanRequestDataResponse> call, Throwable t) {
                hideProgressDialog();
                setPager();
            }
        });
    }

    /**
     * Will retrieve all data from all pages and make a bundle
     *
     * @param result
     * @return
     */
    private ZiploanBorrowerDetails getLoanVerificationDetailFromServerResponse(LoanRequestDataResponse result) {

        ArrayList<ZiploanPhoto> arrPhotos = null;
        ZiploanBorrowerDetails details = new ZiploanBorrowerDetails();

        ZiploanSiteInfo siteInfo = new ZiploanSiteInfo();
        siteInfo.setArrReferenceUsers(result.getReferences());
        //if (!TextUtils.isEmpty(result.getBusiness_place_seperate_residence_place()))
        // siteInfo.setBusiness_place_seperate_residence_place(ZiploanUtil.getIntFromString(result.getBusiness_place_seperate_residence_place()));
//        siteInfo.setStock_inventory_amount(result.getStock_inventory_amount());
//        siteInfo.setFixed_asset_machinery_amount(result.getFixed_asset_machinery_amount());
        siteInfo.setNo_employees(ZiploanUtil.getIntFromString(result.getNo_employees()));
        arrPhotos = new ArrayList<>();
        arrPhotos.add(new ZiploanPhoto(result.getBusiness_place_photos_url()));
        siteInfo.setBusiness_place_photo_url(arrPhotos);

        arrPhotos = new ArrayList<>();
        arrPhotos.add(new ZiploanPhoto(result.getLoan_account_type_business_entity_proof_url()));

        arrPhotos = new ArrayList<>();
        arrPhotos.add(new ZiploanPhoto(result.getBusiness_place_address_proof_url()));
        arrPhotos = new ArrayList<>();
        arrPhotos.add(new ZiploanPhoto(result.getResidence_place_address_proof_url()));

        details.setSite_info(siteInfo);
        details.setQuestions(result.getBusiness_information_questionnaire());
        details.setEkyc_details(result.getEkyc_details());
        return details;
    }

    private void setListeners() {
        allViews.tvSiteInfo.setOnClickListener(this);
        allViews.tvQuestion.setOnClickListener(this);
        allViews.credit.setOnClickListener(this);
        allViews.tvDocuments.setOnClickListener(this);
        allViews.tvFields.setOnClickListener(this);
        allViews.ivBack.setOnClickListener(this);
        allViews.verifyEkyc.setOnClickListener(this);
        allViews.btnSubmit.setOnClickListener(this);
        allViews.pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < tabViews.length; i++) {
                    if (i == position) {
                        tabViews[i].setBackgroundResource(R.color.borrower_detail_tab_unselected);
                        allViews.tabImage1.setBackgroundResource(R.color.borrower_detail_tab_unselected);
                        allViews.tabImage1.setImageResource(R.mipmap.arrow_selected);
                    } else {
                        tabViews[i].setBackgroundResource(R.color.borrower_detail_tab_selected);
                        allViews.tabImage1.setBackgroundResource(R.color.borrower_detail_tab_selected);
                        allViews.tabImage1.setImageResource(R.mipmap.arrow_unselected);
                    }

                    if (position == 2) {
                        allViews.tabsBar.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    } else if (position == 1) {
                        allViews.tabsBar.fullScroll(HorizontalScrollView.FOCUS_LEFT);

                    }


                    if (position == 4) {
                        allViews.tabsBar.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
            case R.id.tv_site_info:
                setPage(0);
                break;
            case R.id.credit:
                setPage(2);
                allViews.tabsBar.fullScroll(HorizontalScrollView.FOCUS_RIGHT);

                break;
            case R.id.tv_question:
                setPage(1);
                allViews.tabsBar.fullScroll(HorizontalScrollView.FOCUS_LEFT);

                break;

            case R.id.tv_documents:
                setPage(3);
                break;

            case R.id.tv_fields:
                setPage(4);
                break;

            case R.id.verify_ekyc:
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT, borrower);
                if (details != null) {
                    bundle.putParcelableArrayList(AppConstant.Key.EXTRA_EKYC_DETAILS, details.getEkyc_details());
                }
                Intent intent = new Intent(mContext, ApplicantListActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, AppConstant.APPLICANT_LIST_REQUEST);
                break;
            case R.id.btn_submit:
                openSubmitOptions();
                break;
        }
    }

    private ZiploanBorrowerDetails getDataAndSaveToDB(boolean isFinal) {

        ZiploanBorrowerDetails details = new ZiploanBorrowerDetails();
        ZiploanSiteInfo siteInfo = getSiteInfo(isFinal);
        if (siteInfo != null) {
            if (isFinal) {
                if (siteInfo.getBusiness_place_photo_url().size() >= 5) {
                    if (siteInfo.getLong_shot_photos().size() >= 1) {
                        if (siteInfo.getId_proof_photos().size() >= 1) {
                            if (siteInfo.getBusiness_place_photo_url().size() == ZiploanUtil.extractUrlList(siteInfo.getBusiness_place_photo_url()).size()) {
                                details.setSite_info(siteInfo);
                                details.setQuestions(getQuestionData());
                                details.setLoan_details(getBankInfo());
                                details.setLoan_request_id(borrower.getLoan_request_id());
                                details.setLat(String.valueOf(ZiploanSPUtils.getInstance(mContext).getTempLat()));
                                details.setLng(String.valueOf(ZiploanSPUtils.getInstance(mContext).getTempLng()));
//                details.
                                DatabaseManger.getInstance().saveApplicationDetails(details, isFinal);
                                System.out.println("Site Info=" + new Gson().toJson(DatabaseManger.getInstance().getBorrowerApplicationDetails(borrower)));
                                return details;
                            }
                        } else {
                            showAlertInfo("Please upload at least 1 ID proof photo");

                        }

                    } else {
                        showAlertInfo("Please upload at least 1 or more long shoot photo");

                    }


                } else {
                    showAlertInfo("Please upload at least 5 business place photos");
                }
            } else {
                details.setSite_info(siteInfo);
                details.setQuestions(getQuestionData());
                details.setLoan_details(getBankInfo());
                details.setLoan_request_id(borrower.getLoan_request_id());
                details.setLat(String.valueOf(ZiploanSPUtils.getInstance(mContext).getTempLat()));
                details.setLng(String.valueOf(ZiploanSPUtils.getInstance(mContext).getTempLng()));
//                details.
                DatabaseManger.getInstance().saveApplicationDetails(details, isFinal);
                System.out.println("Site Info=" + new Gson().toJson(DatabaseManger.getInstance().getBorrowerApplicationDetails(borrower)));
                return details;
            }


        }


        return null;
    }

    private ESignFeildRequest getEsignDetails(boolean isFinal) {

        EsignRelatedDetailsFragments fragment = (EsignRelatedDetailsFragments) mPagerAdapter.getmPageReferenceMap(4);
        if (fragment != null) {
            return fragment.getEsignFeildsDetails(isFinal);
        } else {
            return null;
        }
    }

    private ZiploanNewBorrowerDetails getNewDataAndSaveToDB(boolean isFinal) throws Exception {

        ZiploanNewBorrowerDetails details = new ZiploanNewBorrowerDetails();
        ZiploanSiteInfo siteInfo = getSiteInfo(isFinal);
        if (siteInfo != null) {

            if (isFinal) {
                if (siteInfo.getBusiness_place_photo_url().size() >= 5) {
                    if (siteInfo.getLong_shot_photos().size() >= 1) {
                        if (siteInfo.getId_proof_photos().size() >= 1) {
                            if (siteInfo.getBusiness_place_photo_url().size() == ZiploanUtil.extractUrlList(siteInfo.getBusiness_place_photo_url()).size()) {
                                details.setSite_info(siteInfo);
                                details.setLoan_details(getBankInfo());
                                details.setLoan_request_id(borrower.getLoan_request_id());
                                details.setLat(String.valueOf(ZiploanSPUtils.getInstance(mContext).getTempLat()));
                                details.setLng(String.valueOf(ZiploanSPUtils.getInstance(mContext).getTempLng()));

                                ArrayList<ZiploanNewQuestion> questions = getFinalQuestionData(isFinal);
                                ArrayList<ZiploanNewQuestion> questionsNew = new ArrayList<>();
                                boolean mandatory = false;
                                if (questions != null) {
                                    for (ZiploanNewQuestion ques : questions) {
                                        if (ques.getQuestion().equalsIgnoreCase("mandatory")) {
                                            mandatory = true;
                                        } else {
                                            questionsNew.add(ques);
                                        }
                                    }
                                }

                                details.setQuestions(questionsNew);
                                System.out.println("Site Info=" + new Gson().toJson(DatabaseManger.getInstance().getBorrowerApplicationDetails(borrower)));


                                if (mandatory) {
                                    DatabaseManger.getInstance().saveNewApplicationDetails(details, false);
                                    showAlertInfo(getString(R.string.fill_mandatroy_questions));
                                    return null;
                                } else {
                                    DatabaseManger.getInstance().saveNewApplicationDetails(details, isFinal);
                                }

                                return details;
                            }
                        } else {
                            showAlertInfo("Please upload at least 1 ID proof photo");
                        }

                    } else {
                        showAlertInfo("Please upload at least 1 long shot photo");

                    }
                } else {
                    showAlertInfo("Please upload at least 5 business place photo");

                }
            } else {
                details.setSite_info(siteInfo);
                details.setLoan_details(getBankInfo());
                details.setLoan_request_id(borrower.getLoan_request_id());
                details.setLat(String.valueOf(ZiploanSPUtils.getInstance(mContext).getTempLat()));
                details.setLng(String.valueOf(ZiploanSPUtils.getInstance(mContext).getTempLng()));

                ArrayList<ZiploanNewQuestion> questions = getFinalQuestionData(isFinal);
                ArrayList<ZiploanNewQuestion> questionsNew = new ArrayList<>();
                boolean mandatory = false;
                if (questions != null) {
                    for (ZiploanNewQuestion ques : questions) {
                        if (ques.getQuestion().equalsIgnoreCase("mandatory")) {
                            mandatory = true;
                        } else {
                            questionsNew.add(ques);
                        }
                    }
                }

                details.setQuestions(questionsNew);
                System.out.println("Site Info=" + new Gson().toJson(DatabaseManger.getInstance().getBorrowerApplicationDetails(borrower)));


                if (mandatory) {
                    DatabaseManger.getInstance().saveNewApplicationDetails(details, false);
                    showAlertInfo(getString(R.string.fill_mandatroy_questions));
                    return null;
                } else {
                    DatabaseManger.getInstance().saveNewApplicationDetails(details, isFinal);
                }

                return details;

            }


        }
        return null;
    }

    private ZiploanSiteInfo getSiteInfo(boolean isFinal) {
        SiteInfoFragment fragment = (SiteInfoFragment) mPagerAdapter.getmPageReferenceMap(0);
        if (fragment != null) {
            return fragment.getSiteInfo(isFinal);
        } else {
            return null;
        }
    }

    private ArrayList<ZiploanQuestion> getQuestionData() {
        QuestionariesFragment fragment = (QuestionariesFragment) mPagerAdapter.getmPageReferenceMap(1);
        return fragment.getQuestionList();
    }

    private ArrayList<ZiploanNewQuestion> getFinalQuestionData(boolean isFinal) {
        QuestionariesFragment fragment = (QuestionariesFragment) mPagerAdapter.getmPageReferenceMap(1);
        return fragment.getFinalQuestionList(isFinal);
    }

    private ArrayList<BankInfoModel> getBankInfo() {
        QuestionariesFragment fragment = (QuestionariesFragment) mPagerAdapter.getmPageReferenceMap(1);
        return fragment.getBankInfo();
    }

    private void setPage(int position) {
        allViews.pager.setCurrentItem(position);
    }

    public synchronized static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, BorrowerDetailActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        mContext.startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        private String[] arrItems = new String[]{"SITE INFO.", "QUESTIONS", "CREDIT", "E-sign Related Documents", "E-sign Related Details"};
        private final ZiploanBorrowerDetails mDetails;
        BaseFragment fragment = null;
        private Bundle bundle;

        public MyPagerAdapter(FragmentManager fragmentManager, ZiploanBorrowerDetails details) {
            super(fragmentManager);
            mDetails = details;
        }

        @Override
        public int getCount() {
            return arrItems.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    fragment = new SiteInfoFragment();
                    bundle = new Bundle();
                    bundle.putParcelable(AppConstant.Key.EXTRA_SITE_INFO, (mDetails != null && mDetails.getSite_info() != null) ? mDetails.getSite_info() : new ZiploanSiteInfo());
                    bundle.putBoolean(AppConstant.Key.IS_NEED_TO_DISABLE, needToDisable);
                    bundle.putString("business_address", borrower.getBusiness_address());
                    bundle.putInt(AppConstant.Key.APP_INTSALLED_STATUS, borrower.getApp_install_status());
                    bundle.putString(AppConstant.Key.LOAN_REQUEST_ID, borrower.getLoan_request_id());
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new QuestionariesFragment();
                    bundle = new Bundle();
                    bundle.putParcelableArrayList(AppConstant.Key.EXTRA_QUESTIONS, mDetails != null ? mDetails.getQuestions() : null);
                    bundle.putParcelableArrayList(AppConstant.Key.EXTRA_BANK_INFO, mDetails != null ? mDetails.getLoan_details() : null);
                    bundle.putBoolean(AppConstant.Key.IS_NEED_TO_DISABLE, needToDisable);
                    fragment.setArguments(bundle);
                    allViews.tabsBar.fullScroll(HorizontalScrollView.FOCUS_LEFT);

                    break;
                case 2:
                    fragment = new CreditFragment();
                    bundle = new Bundle();
                    bundle.putString(AppConstant.Key.LOAN_REQUEST_ID, borrower.getLoan_request_id());
                    fragment.setArguments(bundle);
                    allViews.tabsBar.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    break;

                case 3:
                    fragment = new EsignRelatedDocumentsFragment();
                    bundle = new Bundle();
                    bundle.putString(AppConstant.Key.LOAN_REQUEST_ID, borrower.getLoan_request_id());
                    bundle.putString(AppConstant.Key.ZL_ID, borrower.getLoan_application_number());
                    bundle.putString(AppConstant.Key.LENDER_NAME, borrower.getLender_name());
                    fragment.setArguments(bundle);
                    break;

                case 4:
                    fragment = new EsignRelatedDetailsFragments();
                    bundle = new Bundle();
                    bundle.putString(AppConstant.Key.LOAN_REQUEST_ID, borrower.getLoan_request_id());
                    bundle.putString(AppConstant.Key.ZL_ID, borrower.getLoan_application_number());
                    bundle.putString(AppConstant.Key.LENDER_NAME, borrower.getLender_name());
                    fragment.setArguments(bundle);
                    break;

                default:
                    return null;
            }
            mPageReferenceMap.put(position, fragment);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return arrItems[position];
        }

        public BaseFragment getmPageReferenceMap(int position) {
            return mPageReferenceMap.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        displayBackConfirmAlert("Are you sure you want to leave this page?");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.APPLICANT_LIST_REQUEST && resultCode == RESULT_OK) {
            if (data.hasExtra(AppConstant.Key.EXTRA_EKYC_DETAILS)) {
                ArrayList<EkycDetail> arr = data.getParcelableArrayListExtra(AppConstant.Key.EXTRA_EKYC_DETAILS);
                details.setEkyc_details(arr);
            }
        }

        if (requestCode == LOCATION_ENABLE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                startLocationService();
            }
        }
    }

    protected void openSubmitOptions() {
        CharSequence[] items = new CharSequence[]{"Save the Changes", "Submit"};
        itemListDialog = new AlertDialog.Builder(mContext).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:

                        ZiploanBorrowerDetails details1 = getDataAndSaveToDB(false);

                        ESignFeildRequest esignFeildDetails = getEsignDetails(false);
                        if (esignFeildDetails != null) {
                            patchEsignDetails(esignFeildDetails);
                            showAlertInfo("Changes has been saved successfully.");

                        } else {
                            setPage(4);
                            return;
                        }

                        break;
                    case 1:
                        confirmBeforeSubmit("Are you sure");
                        break;
                }
                itemListDialog.dismiss();
            }
        }).show();
    }

    private void patchEsignDetails(ESignFeildRequest esignDetails) {
        Call<ResponseBody> call = APIExecutor.getAPIServiceSerializeNull(mContext)
                .patchEsignFeilds(borrower.getLoan_application_number(), esignDetails);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // hideProgressDialog();
                if (response != null && response.body() != null) {

                    try {
                        String rawData = response.body().string();
                        JSONObject resonseJson = new JSONObject(rawData);
                        if (resonseJson != null) {
                            // DatabaseManger.getInstance().saveEsignFeildData(resonseJson.toString(), borrower.getLoan_application_number(), resonseJson.toString());
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    //TODO  handle id no info avaialable
                    // checkTokenValidity(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                // hideProgressDialog();
            }
        });

    }

    protected void showApplicationPostStatus(String msg) {
        final Dialog dialog = ZiploanUtil.getMessageCustomDialog(this, R.layout.dialog_common_prompt, msg);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.tv_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!isFinishing())
            dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });
    }


    protected void confirmBeforeSubmit(String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ZiploanNewBorrowerDetails details = null;
                        try {
                            details = getNewDataAndSaveToDB(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (details != null) {

                            Log.d("Final Data", new Gson().toJson(details));


                            PostApplicationDataJob.runJobImmediately();
                            ESignFeildRequest esignFeildDetails = getEsignDetails(false);
                            patchEsignDetails(esignFeildDetails);

                            showApplicationPostStatus(getString(R.string.application_is_queue));
                        }
                        dialog.cancel();

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 1 && requestCode == PermissionUtil.LOCATION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (!ZiploanUtil.isLocationProviderEnabled(this)) {
                openLocationRequest();
            } else {
                startLocationService();
            }
        }
    }

    private void startLocationService() {
        mLocationListeners = new LocationListener[]{
                new LocationListener(mContext, LocationManager.GPS_PROVIDER),
                new LocationListener(mContext, LocationManager.NETWORK_PROVIDER)
        };
        initializeLocationManager();
        requestLocationUsingNetworkProvider();
        requestLocationUsingGPSProvider();
    }

    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    /**
     * Method will register location update api using GPS Provider
     */
    private void requestLocationUsingGPSProvider() {
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method will register location update api using Network Provider
     */
    private void requestLocationUsingNetworkProvider() {
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Alert message for GPS not available
     */
    private void buildAlertMessageNoGps() {
        final Dialog dialog = ZiploanUtil.getMessageCustomDialog(this, R.layout.dialog_common_prompt, "Please enable your location. This is only required to capture the location when submitting the application.");
        dialog.setCancelable(false);

        dialog.findViewById(R.id.tv_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                dialog.dismiss();
            }
        });
        if (!isFinishing())
            dialog.show();


    }


    private void openLocationRequest() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(BorrowerDetailActivity.this).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);
            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            startLocationService();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(BorrowerDetailActivity.this, LOCATION_ENABLE_REQUEST);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        }
    }
}