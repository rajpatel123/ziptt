package com.ziploan.team.asset_module.visits;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.asset_module.CheckedPojo;
import com.ziploan.team.asset_module.loans.LoansListActivity;
import com.ziploan.team.databinding.ActivityRecordNewVisitFirstBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;
import com.ziploan.team.webapi.Loan;
import com.ziploan.team.webapi.RecordNewVisitPojo;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 */

public class RecordNewVisitActivityFirst extends AssetsBaseActivity implements View.OnClickListener {

    private ActivityRecordNewVisitFirstBinding allViews;
    private Bundle bundle;
    private Loan loan;
    private RecordNewVisitPojo visitData = new RecordNewVisitPojo();
    private CheckedPojo pojo;

    String customerAgreed[] = {"Select",
            "Agreed",
            "Postponed without any genuine reason",
            "Refused"};

    String changeBusinessAddress[] = {"Select",
            "Yes",
            "No"};

    String business_status[] = {"Select","Fully Operational",
            "Very limited operations visible",
            "Non operational"};

    @Override
    protected Toolbar getToolbar() {
        return allViews.actionBar.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_new_visit_first;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityRecordNewVisitFirstBinding) views;
        bundle = getIntent().getExtras();
        if(bundle.containsKey(AppConstant.Key.EXTRA_OBJECT)){
            loan = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT);
            allViews.actionBar.setActionBarData(new ActionBarData(R.mipmap.ic_back,"Record New Visit"));
        }
        allViews.actionBar.setLastVisitButton(true);
        pojo = new CheckedPojo();
        allViews.setNoDataChecked(pojo);
        setListeners();
        setBusinessStatusSpinner();
        setCustomerAgreedSpinner();
        setChangeAddressSpinner();
    }

    private void setListeners() {
        allViews.buttonNext.setOnClickListener(this);
        allViews.actionBar.buttonViewOld.setOnClickListener(this);
    }

    private void setCustomerAgreedSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customerAgreed);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        allViews.customerMeet.setAdapter(stringArrayAdapter);
        allViews.customerMeet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setChangeAddressSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, changeBusinessAddress);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        allViews.businessAddressChange.setAdapter(stringArrayAdapter);
        allViews.businessAddressChange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setBusinessStatusSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, business_status);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        allViews.businessStatus.setAdapter(stringArrayAdapter);
        allViews.businessStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button_next:

                visitData.setLoan_request_id(loan.getIdentifier());
                visitData.setAsset_manager_id(ZiploanSPUtils.getInstance(mContext).getAccessId());//Temp static
                if(pojo.isChecked()){
                    if(getText(allViews.etReason).length()>0){
                        visitData.setParam("0");
                        visitData.setRecorded_coordinates(ZiploanSPUtils.getInstance(mContext).getTempLat()+","+ZiploanSPUtils.getInstance(mContext).getTempLng());
                        visitData.setDate_of_visit(ZiploanUtil.getDateFromTimestamp(Calendar.getInstance().getTimeInMillis(),"dd-MM-yyyy"));
                        visitData.setTime_of_visit(ZiploanUtil.getDateFromTimestamp(Calendar.getInstance().getTimeInMillis(),"hh:mm"));
                        visitData.setVisit_unsucessful_comments(getText(allViews.etReason));
                        if(allViews.businessStatus.getSelectedItemPosition() != 0)
                            visitData.setBusiness_status(allViews.businessStatus.getSelectedItem().toString());
                        if(allViews.customerMeet.getSelectedItemPosition() != 0)
                            visitData.setCustomer_agreed(allViews.customerMeet.getSelectedItem().toString());
                        if(allViews.businessAddressChange.getSelectedItemPosition() != 0)
                            visitData.setChange_in_business_address(allViews.businessAddressChange.getSelectedItem().toString());
                        callNewRecordAPI(visitData);
                    }else {
                        showAlertInfo(Html.fromHtml("Please enter valid reason."), Color.parseColor("#ff0000"));
                    }
                }else if(checkValidation()){
                    visitData.setPerson_met(getText(allViews.etPersonMet));
                    visitData.setSecondary_applicant_alternative_no(getText(allViews.etSecondaryApplicantPhone));
                    visitData.setPrimary_applicant_alternative_no(getText(allViews.etPrimaryApplicantPhone));
                    visitData.setNature_of_work(getText(allViews.etNatureOfWork));
                    visitData.setVisited_business_address(getText(allViews.etNewBusinessAddress));
                    visitData.setEmployee_number_declaration(getText(allViews.etNoOfEmployeeRcorded));
                    visitData.setEmployee_number_on_site(getText(allViews.etSiteEmployees));
                    visitData.setRaw_material_value_in_rupees(TextUtils.isEmpty(getText(allViews.etRawMaterialValue))?null:getText(allViews.etRawMaterialValue));
                    visitData.setStock_or_inventory_in_rupees(TextUtils.isEmpty(getText(allViews.etStockInventoryData))?null:getText(allViews.etStockInventoryData));
                    visitData.setAsset_or_machinery_value_in_rupees(TextUtils.isEmpty(getText(allViews.etMachineryValue))?null:getText(allViews.etMachineryValue));
                    if(allViews.businessStatus.getSelectedItemPosition() != 0)
                        visitData.setBusiness_status(allViews.businessStatus.getSelectedItem().toString());
                    if(allViews.customerMeet.getSelectedItemPosition() != 0)
                        visitData.setCustomer_agreed(allViews.customerMeet.getSelectedItem().toString());
                    if(allViews.businessAddressChange.getSelectedItemPosition() != 0)
                        visitData.setChange_in_business_address(allViews.businessAddressChange.getSelectedItem().toString());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT_VISITED_DATA,visitData);
                    bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT,loan);
                    RecordNewVisitActivitySecond.start(mContext,bundle);
                }
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
        if(allViews.etPersonMet.getText().toString().trim().length()==0){
            arrRequiredFields.add("Person Met");
        }
        if(allViews.etNatureOfWork.getText().toString().trim().length()==0){
            arrRequiredFields.add("Nature of Work");
        }
        if(allViews.etNoOfEmployeeRcorded.getText().toString().trim().length()==0){
            arrRequiredFields.add("Declared no of Employees");
        }
        if(allViews.etSiteEmployees.getText().toString().trim().length()==0){
            arrRequiredFields.add("No of Employees on Site");
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
        Intent intent = new Intent(mContext,RecordNewVisitActivityFirst.class);
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


    private void callNewRecordAPI(RecordNewVisitPojo visitedData) {
        showProgressDialog("Please wait...");
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).recordAssetVisit(visitedData);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                if(response!=null && response.body()!=null){
                    String result = response.body().getResult();
                    if(result.equalsIgnoreCase("1")){
                        showAlertInfo("Reason has been recorded successfully.", new DialogInterface.OnDismissListener(){

                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                Intent intent = new Intent(mContext,LoansListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }else {
                        showAlertInfo(response.body().getMessage()!=null?response.body().getMessage():getResources().getString(R.string.something_went_wrong));
                    }
                }else {
                    showAlertInfo(getString(R.string.server_not_responding));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                hideProgressDialog();
                showAlertInfo(getString(R.string.server_not_responding));
            }
        });
    }
}