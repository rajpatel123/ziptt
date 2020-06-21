//package com.ziploan.team.collection.application_list;
//
//import android.app.Activity;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.content.pm.PackageManager;
//import android.databinding.ViewDataBinding;
//import android.graphics.Paint;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TextInputLayout;
//import android.support.v7.widget.AppCompatAutoCompleteTextView;
//import android.support.v7.widget.AppCompatSpinner;
//import android.support.v7.widget.AppCompatTextView;
//import android.support.v7.widget.SwitchCompat;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.PendingResult;
//import com.google.android.gms.common.api.ResultCallback;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.LocationSettingsRequest;
//import com.google.android.gms.location.LocationSettingsResult;
//import com.google.android.gms.location.LocationSettingsStates;
//import com.google.android.gms.location.LocationSettingsStatusCodes;
//import com.ziploan.team.R;
//import com.ziploan.team.collection.model.app_list.Result;
//import com.ziploan.team.collection.model.record_visit.PastVisitResponse;
//import com.ziploan.team.collection.model.record_visit.RecordVisitRequestModel;
//import com.ziploan.team.collection.utils.UIErrorUtils;
//import com.ziploan.team.databinding.RecordVisitLayoutBinding;
//import com.ziploan.team.utils.AppConstant;
//import com.ziploan.team.utils.NetworkUtil;
//import com.ziploan.team.utils.PermissionUtil;
//import com.ziploan.team.utils.ZiploanSPUtils;
//import com.ziploan.team.utils.ZiploanUtil;
//import com.ziploan.team.verification_module.base.BaseActivity;
//import com.ziploan.team.verification_module.caching.DatabaseManger;
//import com.ziploan.team.verification_module.services.LocationListener;
//import com.ziploan.team.webapi.APIExecutor;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.TimeZone;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.ResponseData;
//
//public class RecordVisitActivity extends BaseActivity implements View.OnClickListener ,DatePickerDialog.OnDateSetListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//
//    private static final String TAG = "Recordvsit";
//    private RecordVisitLayoutBinding recordVisitLayoutBinding;
//    private String mLoanId;
//    Dialog dialog;
//    EditText persona_met;
//    EditText reason_text;
//    EditText amount_txt;
//    EditText receipt_txt;
//    TextView layout_reason;
//    TextInputLayout layout_person_met;
//    TextView layout_reason_invalid;
//    boolean personMet;
//    private Result result;
//    private String selectedOption;
//    AppCompatAutoCompleteTextView bank_name;
//    TextInputLayout bank_layout;
//    TextInputLayout amount_layout;
//    EditText reference_number;
//    TextInputLayout reference_layout;
//    EditText date;
//    TextInputLayout date_layout;
//    TextInputLayout receipt_layout;
//    String place;
//    String ptp_status;
//
//    private String Selecteddate;
//    private int year;
//    private int month;
//    private int day;
//
//    private static final int LOCATION_ENABLE_REQUEST = 1000;
//    private GoogleApiClient googleApiClient;
//    private LocationManager mLocationManager;
//    private static final int LOCATION_INTERVAL = 1000;//1000;
//    private static final float LOCATION_DISTANCE = 0;
//    private LocationListener[] mLocationListeners;
//
//
//    private List<String> mPaymentOptions = new ArrayList<>();
//    private String selectedBankCode = "";
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.record_visit_layout;
//    }
//
//    public static void start(Activity mContext, Bundle bundle) {
//        Intent intent = new Intent(mContext, RecordVisitActivity.class);
//        intent.putExtras(bundle);
//        mContext.startActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppThemeDark);
//        super.onCreate(savedInstanceState);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setTitle(getString(R.string.record_visit));
//        mPaymentOptions.add("Select Other Payment Method");
//        mPaymentOptions.add("NEFT");
//        mPaymentOptions.add("RTGS");
//        mPaymentOptions.add("NET BANKING");
//        mPaymentOptions.add("IMPS");
//        mPaymentOptions.add("FUND TRANSFER");
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onViewMapped(ViewDataBinding views) {
//        recordVisitLayoutBinding = (RecordVisitLayoutBinding) views;
//        recordVisitLayoutBinding.chequeLayout.setOnClickListener(this);
//        recordVisitLayoutBinding.cashLayout.setOnClickListener(this);
//        recordVisitLayoutBinding.yesBtn.setOnClickListener(this);
//        recordVisitLayoutBinding.noBtn.setOnClickListener(this);
//        mLoanId = getIntent().getStringExtra("id");
//        if(getIntent().getExtras() != null){
//            result =(Result) getIntent().getSerializableExtra(AppConstant.Key.LOAN_REQUEST_DATA);
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showSpinnerData();
//            }
//        },500);
//        recordVisitLayoutBinding.paymentSpinner.setAlpha(.5f);
//        recordVisitLayoutBinding.cashLayout.setAlpha(.5f);
//        recordVisitLayoutBinding.chequeLayout.setAlpha(.5f);
//
//        recordVisitLayoutBinding.cashLayout.setEnabled(false);
//        recordVisitLayoutBinding.chequeLayout.setEnabled(false);
//        recordVisitLayoutBinding.paymentSpinner.setEnabled(false);
//        getLocationOrgetPermission();
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.yes_btn:
//                recordVisitLayoutBinding.paymentSpinner.setAlpha(1);
//                recordVisitLayoutBinding.cashLayout.setAlpha(1);
//                recordVisitLayoutBinding.chequeLayout.setAlpha(1);
//                recordVisitLayoutBinding.yesBtn.setBackground(view.getResources().getDrawable(R.drawable.record_visit_coloured));
//                recordVisitLayoutBinding.yesBtn.setTextColor(view.getResources().getColor(R.color.white));
//
//                recordVisitLayoutBinding.noBtn.setBackground(view.getResources().getDrawable(R.drawable.record_visit_circle));
//                recordVisitLayoutBinding.noBtn.setTextColor(view.getResources().getColor(R.color.color_424242));
//
//                recordVisitLayoutBinding.cashLayout.setEnabled(true);
//                recordVisitLayoutBinding.chequeLayout.setEnabled(true);
//                recordVisitLayoutBinding.paymentSpinner.setEnabled(true);
//                break;
//            case R.id.no_btn:
//                recordVisitLayoutBinding.paymentSpinner.setAlpha(.5f);
//                recordVisitLayoutBinding.cashLayout.setAlpha(.5f);
//                recordVisitLayoutBinding.chequeLayout.setAlpha(.5f);
//
//                recordVisitLayoutBinding.cashLayout.setEnabled(false);
//                recordVisitLayoutBinding.chequeLayout.setEnabled(false);
//                recordVisitLayoutBinding.paymentSpinner.setEnabled(false);
//
//                recordVisitLayoutBinding.yesBtn.setBackground(view.getResources().getDrawable(R.drawable.record_visit_circle));
//                recordVisitLayoutBinding.yesBtn.setTextColor(view.getResources().getColor(R.color.color_424242));
//
//                recordVisitLayoutBinding.noBtn.setBackground(view.getResources().getDrawable(R.drawable.record_visit_coloured));
//                recordVisitLayoutBinding.noBtn.setTextColor(view.getResources().getColor(R.color.white));
//                noMet();
//                break;
//
//            case R.id.cash_layout:
//                Bundle bundlecash = new Bundle();
//                bundlecash.putString("id", mLoanId);
//                bundlecash.putSerializable(AppConstant.Key.LOAN_REQUEST_DATA, result);
//                RecordVisitCash.start(this, bundlecash);
//                break;
//            case R.id.cheque_layout:
//                Bundle bundleRecord = new Bundle();
//                bundleRecord.putString("id", mLoanId);
//                bundleRecord.putSerializable(AppConstant.Key.LOAN_REQUEST_DATA, result);
//                RecordVisitCheque.start(this, bundleRecord);
//                break;
//            case R.id.close_img:
//                UIErrorUtils.hideFromDialogue(dialog.getContext(),persona_met);
//                dialog.cancel();
//                break;
//            case R.id.submit_no_person:
//                resetfields();
//                submitNoPersonMet();
//                break;
//            case R.id.submit_other_bank:
//                if(validated())
//                    postOtherData();
//                break;
//            case R.id.calender_icon:
//                selectDate();
//                break;
//            case R.id.date:
//                selectDate();
//                break;
//            case R.id.success_ok:
//                dialog.dismiss();
//                backToHome();
//                break;
//        }
//    }
//
//    private void resetfields(){
//        UIErrorUtils.errorMethod(layout_person_met,getString(R.string.person_met_empty),false);
//        layout_reason.setTextColor(getResources().getColor(R.color.black));
//        layout_reason_invalid.setVisibility(View.GONE);
//    }
//
//    private void submitNoPersonMet() {
//        if(personMet) {
//            if (!TextUtils.isEmpty(persona_met.getText())) {
//                if (!TextUtils.isEmpty(reason_text.getText())) {
//                    UIErrorUtils.hideFromDialogue(dialog.getContext(), persona_met);
//                    postData();
//                } else {
//                    layout_reason.setTextColor(getResources().getColor(R.color.f61c00));
//                    layout_reason_invalid.setVisibility(View.VISIBLE);
//                }
//            } else {
//                UIErrorUtils.errorMethod(layout_person_met, getString(R.string.person_met_empty), true);
//            }
//        } else {
//            if (!TextUtils.isEmpty(reason_text.getText())) {
//                UIErrorUtils.hideFromDialogue(dialog.getContext(), reason_text);
//                postData();
//            } else {
//                layout_reason.setTextColor(getResources().getColor(R.color.f61c00));
//                layout_reason_invalid.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    private void postData() {
//        showProgressDialog();
//        RecordVisitRequestModel recordVisitRequestModel = new RecordVisitRequestModel();
//        recordVisitRequestModel.setAmount(0);
//        recordVisitRequestModel.setComment(reason_text.getText().toString());
//        recordVisitRequestModel.setIsPersonMet(personMet);
//        recordVisitRequestModel.setPersonMet(persona_met.getText().toString());
//        recordVisitRequestModel.setLat(ZiploanSPUtils.getInstance(mContext).getTempLat());
//        recordVisitRequestModel.setLng((ZiploanSPUtils.getInstance(mContext).getTempLng()));
//        if (NetworkUtil.getConnectivityStatus(this) != 0) {
//            Call<PastVisitResponse> call = APIExecutor.getAPIService(mContext).postPastVisits(mLoanId, recordVisitRequestModel,AppConstant.Key.VIEW_VALUE);
//            call.enqueue(new Callback<PastVisitResponse>() {
//                @Override
//                public void onResponse(Call<PastVisitResponse> call, ResponseData<PastVisitResponse> response) {
//                    hideProgressDialog();
//                    if (response != null && response.isSuccessful() && response.body() != null) {
//                        dialog.dismiss();
//                        showSuccessDialogue(getString(R.string.recroeded_succesfully));
//                    } else {
//                        checkTokenValidity(response);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<PastVisitResponse> call, Throwable t) {
//                    hideProgressDialog();
//                    showToast(dialog.getContext(), getString(R.string.something_went_wrong));
//                }
//            });
//        } else {
//            hideProgressDialog();
//            DatabaseManger.getInstance().saveRecordVisitData(recordVisitRequestModel,mLoanId);
//            dialog.dismiss();
//            showSuccessDialogue(getString(R.string.recroeded_succesfully));
//        }
//    }
//
//    private void backToHome() {
//        ApplicationListActivity.start(this,new Bundle());
//    }
//
//    private void noMet() {
//        dialog = ZiploanUtil.getCustomDialogCollection(this, R.layout.record_visit_with_no);
//        dialog.show();
//        ImageView close = dialog.findViewById(R.id.close_img);
//        persona_met = dialog.findViewById(R.id.personal_met);
//        reason_text = dialog.findViewById(R.id.reason_text);
//        layout_person_met = dialog.findViewById(R.id.layout_person_met);
//        layout_reason = dialog.findViewById(R.id.layout_reason_text);
//        layout_reason_invalid = dialog.findViewById(R.id.layout_reason_invalid);
//        SwitchCompat met_switch = dialog.findViewById(R.id.met_switch);
//        final Button submit_no_person = dialog.findViewById(R.id.submit_no_person);
//
//
//        met_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                personMet = b;
//                if(b){
//                    persona_met.setEnabled(true);
//                    compoundButton.setText(getString(R.string.person_met_yes));
//                } else {
//                    persona_met.setEnabled(false);
//                    compoundButton.setText(getString(R.string.person_met_no));
//                }
//            }
//        });
//
//        submit_no_person.setOnClickListener(this);
//        close.setOnClickListener(this);
//
//    }
//
//    private void showSuccessDialogue(String message) {
//        dialog = ZiploanUtil.getCustomDialogCollection(this, R.layout.submission_success);
//        dialog.show();
//        TextView success_mmessage = dialog.findViewById(R.id.success_message);
//        success_mmessage.setText(message);
//        final Button submit_no_person = dialog.findViewById(R.id.success_ok);
//        submit_no_person.setOnClickListener(this);
//    }
//
//    private void reset(){
//        UIErrorUtils.errorMethod(layout_person_met,getString(R.string.person_met_empty),false);
//        UIErrorUtils.errorMethod(receipt_layout,getString(R.string.person_met_empty),false);
//        UIErrorUtils.errorMethod(bank_layout,getString(R.string.person_met_empty),false);
//        UIErrorUtils.errorMethod(reference_layout,getString(R.string.person_met_empty),false);
//        UIErrorUtils.errorMethod(date_layout,getString(R.string.person_met_empty),false);
//        UIErrorUtils.errorMethod(amount_layout, getString(R.string.person_met_empty), false);
//
//    }
//
//    private boolean validated() {
//        reset();
//        if (!TextUtils.isEmpty(persona_met.getText())) {
//            if ((!TextUtils.isEmpty(selectedBankCode))|| (selectedOption.equalsIgnoreCase("IMPS") || selectedOption.equalsIgnoreCase("FUND TRANSFER"))) {
//                if (!TextUtils.isEmpty(amount_txt.getText().toString())) {
////                    if(result.getAmountOverdue() != null
////                            && Double.parseDouble(amount_txt.getText().toString()) <= Double.parseDouble(result.getAmountOverdue())) {
//                    if (!TextUtils.isEmpty(reference_number.getText())) {
//                        if (!TextUtils.isEmpty(date.getText())) {
//                            if(!TextUtils.isEmpty(receipt_txt.getText()) && receipt_txt.length() == 10){
//                                UIErrorUtils.hideFromDialogue(dialog.getContext(), persona_met);
//                                return true;
//                            } else {
//                                UIErrorUtils.errorMethod(receipt_layout, getString(R.string.person_met_empty), true);
//                            }
//                        } else {
//                            UIErrorUtils.errorMethod(date_layout, getString(R.string.person_met_empty), true);
//                        }
//                    } else {
//                        UIErrorUtils.errorMethod(reference_layout, getString(R.string.person_met_empty), true);
//                    }
////                    }
//                } else {
//                    UIErrorUtils.errorMethod(amount_layout, getString(R.string.person_met_empty), true);
//                }
//            } else {
//                UIErrorUtils.errorMethod(bank_layout, getString(R.string.invalid_bank_code), true);
//            }
//        } else {
//            UIErrorUtils.errorMethod(layout_person_met, getString(R.string.person_met_empty), true);
//        }
//        return false;
//    }
//
//    private void otherBankDialogue(String header) {
//        dialog = ZiploanUtil.getCustomDialogCollection(this, R.layout.other_bank_layout);
//        ImageView close = dialog.findViewById(R.id.close_img);
//        persona_met = dialog.findViewById(R.id.personal_met);
//        amount_txt = dialog.findViewById(R.id.amount);
//        receipt_txt = dialog.findViewById(R.id.receipt_box);
//        amount_layout = dialog.findViewById(R.id.amount_layout);
//        reason_text = dialog.findViewById(R.id.comment);
//        layout_person_met = dialog.findViewById(R.id.layout_person_met);
//        bank_name = dialog.findViewById(R.id.bank_name);
//        reference_number = dialog.findViewById(R.id.reference);
//        date  = dialog.findViewById(R.id.date);
//        receipt_layout = dialog.findViewById(R.id.receipt_layout);
//        TextView header_name =  dialog.findViewById(R.id.header_other_method);
//        header_name.setText(header);
//        AppCompatSpinner placeSp = dialog.findViewById(R.id.place_sp);
//        AppCompatSpinner ptpSp = dialog.findViewById(R.id.ptp_sp);
//
//        bank_layout = dialog.findViewById(R.id.layout_bank_name);
//        reference_layout = dialog.findViewById(R.id.layout_reference_number);
//        date_layout = dialog.findViewById(R.id.date_layout);
//
////        if(selectedOption.equalsIgnoreCase("IMPS") || selectedOption.equalsIgnoreCase("FUND TRANSFER")){
////            bank_layout.setVisibility(View.GONE);
////            bank_name.setVisibility(View.GONE);
////        } else {
//            UIErrorUtils.getBankAdapter(this, bank_name);
////            bank_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                @Override
////                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                    com.ziploan.team.collection.model.bank_names.ResponseData response = ((com.ziploan.team.collection.model.bank_names.ResponseData) adapterView.getItemAtPosition(i));
////                    if ( response != null && !response.getBankDisplayName().equalsIgnoreCase("Select Bank"))
////                        selectedBankCode = ((com.ziploan.team.collection.model.bank_names.ResponseData) adapterView.getItemAtPosition(i)).getBankCode();
////                }
////
////                @Override
////                public void onNothingSelected(AdapterView<?> adapterView) {
////
////                }
////            });
//            bank_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    selectedBankCode = ((com.ziploan.team.collection.model.bank_names.ResponseData) adapterView.getItemAtPosition(i)).getBankCode();
//                }
//            });
//        //}
//        receipt_txt.setText(result.getMobileNumber());
//
//        ImageView calender_img = dialog.findViewById(R.id.calender_icon);
//        date.setOnClickListener(this);
//        calender_img.setOnClickListener(this);
//        final Button submit_other_bank = dialog.findViewById(R.id.submit_other_bank);
//
//        submit_other_bank.setOnClickListener(this);
//        close.setOnClickListener(this);
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialogInterface) {
//                recordVisitLayoutBinding.paymentSpinner.setSelection(0);
//            }
//        });
//
//        final ArrayList<String> placeData = new ArrayList<>();
//        placeData.add("Select Place");
//        placeData.add("Business");
//        placeData.add("House");
//        placeData.add("3rd place");
//
//        ArrayList<String> ptpData = new ArrayList<>();
//        ptpData.add("Select PTP status");
//        ptpData.add("Paid");
//        ptpData.add("Partial payment");
//        ptpData.add("Broken");
//
//        UIErrorUtils.showSpinnerWithStringArray(dialog.getContext(),placeSp,placeData);
//        UIErrorUtils.showSpinnerWithStringArray(dialog.getContext(),ptpSp,ptpData);
//
//        placeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(!adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Select Place"))
//                place = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        ptpSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(!adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("Select PTP status"))
//                ptp_status = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        dialog.show();
//    }
//
//    private void showSpinnerData(){
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item,mPaymentOptions);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        recordVisitLayoutBinding.paymentSpinner.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//
//        recordVisitLayoutBinding.paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                selectedOption = mPaymentOptions.get(i);
//                if(!selectedOption.equalsIgnoreCase("Select Other Payment Method"))
//                    otherBankDialogue(selectedOption);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Log.d("s","s");
//                recordVisitLayoutBinding.paymentSpinner.setSelection(0);
//            }
//        });
//    }
//
//    private void postOtherData() {
//        showProgressDialog();
//
//        RecordVisitRequestModel recordVisitRequestModel = new RecordVisitRequestModel();
//        recordVisitRequestModel.setAmount(Integer.parseInt(amount_txt.getText().toString()));
//        recordVisitRequestModel.setBankName(selectedBankCode);
//        recordVisitRequestModel.setComment(reason_text.getText().toString());
//
//        recordVisitRequestModel.setDenominations(null);
//        recordVisitRequestModel.setIsPersonMet(true);
//        recordVisitRequestModel.setMode(getMode(selectedOption));
//        recordVisitRequestModel.setPersonMet(persona_met.getText().toString());
//        recordVisitRequestModel.setRefrenceNumber(reference_number.getText().toString());
//        recordVisitRequestModel.setPlace(place);
//        recordVisitRequestModel.setPtpStatus(ptp_status);
//        recordVisitRequestModel.setValueDate(Selecteddate);
//        recordVisitRequestModel.setLat(ZiploanSPUtils.getInstance(mContext).getTempLat());
//        recordVisitRequestModel.setLng((ZiploanSPUtils.getInstance(mContext).getTempLng()));
//        recordVisitRequestModel.setmMobile(receipt_txt.getText().toString());
//
//        if (NetworkUtil.getConnectivityStatus(this) != 0) {
//
//            Call<PastVisitResponse> call = APIExecutor.getAPIServiceSerializeNull(mContext).postPastVisits(mLoanId, recordVisitRequestModel,AppConstant.Key.VIEW_VALUE);
//            call.enqueue(new Callback<PastVisitResponse>() {
//                @Override
//                public void onResponse(Call<PastVisitResponse> call, ResponseData<PastVisitResponse> response) {
//                    hideProgressDialog();
//                    if (response != null && response.body() != null) {
//                        if (response.isSuccessful()
//                                && response.body().getmStatus() != null
//                                && response.body().getmStatus().equalsIgnoreCase("success")) {
//                            dialog.dismiss();
//                            showSuccessDialogue(getString(R.string.recroeded_succesfully) + "\n" +  "Invoice number : " + response.body().getmResponse().getmInvoiceNumber());
//                        } else {
//                            showToast(RecordVisitActivity.this, response.body().getmStatusMessage());
//                        }
//                    } else {
//                        checkTokenValidity(response);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<PastVisitResponse> call, Throwable t) {
//                    hideProgressDialog();
//                    showToast(dialog.getContext(), getString(R.string.something_went_wrong));
//                }
//            });
//        } else {
//            hideProgressDialog();
//            DatabaseManger.getInstance().saveRecordVisitData(recordVisitRequestModel,mLoanId);
//            dialog.dismiss();
//            showSuccessDialogue(getString(R.string.recroeded_succesfully));
//        }
//    }
//
//    public void selectDate() {
//        final Calendar c = Calendar.getInstance();
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                this, R.style.DialogTheme, this, year, month, day){
//            @Override
//            protected void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                AppCompatTextView date = (AppCompatTextView)getDatePicker().findViewById(getResources().getIdentifier("date_picker_header_date","id","android"));
//                AppCompatTextView year = (AppCompatTextView)getDatePicker().findViewById(getResources().getIdentifier("date_picker_header_year","id","android"));
//
//                year.setTextColor(getResources().getColor(R.color.white));
//                year.setTextSize(30);
//                year.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                year.setGravity(Gravity.CENTER_HORIZONTAL);
//                date.setTextSize(15);
//                year.setPaintFlags(year.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
//            }
//        };
//        c.set(year, month, day - 7);
//
//        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//        datePickerDialog.show();
//    }
//
//    @Override
//    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//        year  = i;
//        month = i1;
//        day   = i2;
//        Calendar c = Calendar.getInstance();
//        c.set(year,month,day);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        Selecteddate = sdf.format(c.getTime());
//        date.setText(new StringBuilder().append(day).append("/").append(month + 1)
//                .append("/").append(year));
//    }
//
//    private int getMode(String mode){
//        int selectedMode = 0;
//        if(mode.equalsIgnoreCase("NEFT")){
//            selectedMode = 3;
//        } else if(mode.equalsIgnoreCase("RTGS")){
//            selectedMode = 4;
//        } else if(mode.equalsIgnoreCase("NET BANKING")){
//            selectedMode = 5;
//        } else if(mode.equalsIgnoreCase("IMPS")){
//            selectedMode = 6;
//        } else if(mode.equalsIgnoreCase("FUND TRANSFER")){
//            selectedMode = 9;
//        }
//        return selectedMode;
//    }
//
//    /**
//     * Location Service
//     */
//    private void startLocationService() {
//        mLocationListeners = new LocationListener[]{
//                new LocationListener(mContext, LocationManager.GPS_PROVIDER),
//                new LocationListener(mContext, LocationManager.NETWORK_PROVIDER)
//        };
//        initializeLocationManager();
//        requestLocationUsingNetworkProvider();
//        requestLocationUsingGPSProvider();
//    }
//
//    private void initializeLocationManager() {
//        if (mLocationManager == null) {
//            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        }
//    }
//
//    /**
//     * Method will register location update api using GPS Provider
//     */
//    private void requestLocationUsingGPSProvider() {
//        try {
//            mLocationManager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
//                    mLocationListeners[0]);
//        } catch (SecurityException ex) {
//            Log.i(TAG, "fail to request location update, ignore", ex);
//        } catch (IllegalArgumentException ex) {
//            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Method will register location update api using Network Provider
//     */
//    private void requestLocationUsingNetworkProvider() {
//        try {
//            mLocationManager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
//                    mLocationListeners[1]);
//        } catch (SecurityException ex) {
//            Log.i(TAG, "fail to request location update, ignore", ex);
//        } catch (IllegalArgumentException ex) {
//            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void getLocationOrgetPermission(){
//        if (PermissionUtil.checkLocationPermission(mContext)) {
//            if (!ZiploanUtil.isLocationProviderEnabled(this)) {
//                openLocationRequest();
//            } else {
//                startLocationService();
//            }
//        } else {
//            PermissionUtil.requestLocationPermission(mContext);
//        }
//    }
//
//    private void openLocationRequest() {
//        if (googleApiClient == null) {
//            googleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(LocationServices.API).addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(RecordVisitActivity.this).build();
//            googleApiClient.connect();
//            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(30 * 1000);
//            locationRequest.setFastestInterval(5 * 1000);
//            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//            builder.setAlwaysShow(true);
//            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
//                    .checkLocationSettings(googleApiClient, builder.build());
//            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
//                @Override
//                public void onResult(LocationSettingsResult result) {
//                    final Status status = result.getStatus();
//                    final LocationSettingsStates state = result.getLocationSettingsStates();
//                    switch (status.getStatusCode()) {
//                        case LocationSettingsStatusCodes.SUCCESS:
//                            startLocationService();
//                            break;
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                            try {
//                                status.startResolutionForResult(RecordVisitActivity.this, LOCATION_ENABLE_REQUEST);
//                            } catch (IntentSender.SendIntentException e) {
//                            }
//                            break;
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            break;
//                    }
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults.length == 1 && requestCode == PermissionUtil.LOCATION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            if (!ZiploanUtil.isLocationProviderEnabled(this)) {
//                openLocationRequest();
//            } else {
//                startLocationService();
//            }
//        }
//    }
//}
