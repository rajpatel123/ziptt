package com.ziploan.team.asset_module.change_request;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

import com.ziploan.team.PhotoUploadListener;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.asset_module.ChangeRequestPojo;
import com.ziploan.team.asset_module.FileUploader;
import com.ziploan.team.asset_module.visits.LoadPhotoListener;
import com.ziploan.team.databinding.ActivityChangeRequestBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.PermissionUtil;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;
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

public class ChangeRequestActivity extends AssetsBaseActivity implements View.OnClickListener, LoadPhotoListener {


    private ActivityChangeRequestBinding allViews;
    private Bundle bundle;
    private Loan loan;
    private ChangeRequestPojo changeRequest = new ChangeRequestPojo();
    private HashMap<Integer, Object> data = new HashMap<>();
    private int proofFor = 0;
    private AlertDialog itemListDialog;

    @Override
    protected Toolbar getToolbar() {
        return allViews.actionBar.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_request;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityChangeRequestBinding) views;
        bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(AppConstant.Key.EXTRA_OBJECT)) {
            loan = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT);
            allViews.setLoan(loan);
        }
        allViews.actionBar.setActionBarData(new ActionBarData(R.mipmap.ic_back, "CHANGE REQUEST"));
        setListeners();
        allViews.setChangeRequest(changeRequest);
        data.put(1, new BusinessAddressChangeRequest());
        data.put(2, new ResidenceAddressChangeRequest());
        data.put(3, new ResidenceAddressChangeRequest());
        data.put(6, new MobileChangeRequest());
        data.put(7, new RepaymentBankChangeRequest());
        if (getIntent() != null) {
            if (getIntent().hasExtra("collection")) {
                allViews.secCardview.setVisibility(View.GONE);
            }
            if (getIntent().hasExtra("coApplicant")) {
                allViews.primeryTxt.setText("Co-Applicant Residence Address");
                allViews.mobileTxt.setText("Co-Applicant Mobile Number");
                allViews.newPrimeryText.setText("Old CoApplicant Residence Address");
                allViews.oldPrimeryText.setText("New CoApplicant Residence Address");
                allViews.secCardview.setVisibility(View.GONE);
                allViews.businessAddressCardview.setVisibility(View.GONE);
                allViews.bankAccountCv.setVisibility(View.GONE);
            }
        }
    }

    private void setListeners() {
        allViews.tvPriApplicantAddChange.setOnClickListener(this);
        allViews.tvBusinessAddressChange.setOnClickListener(this);
        allViews.tvSecApplicantAddChange.setOnClickListener(this);
        allViews.tvMobileNo.setOnClickListener(this);
        allViews.tvBankAccount.setOnClickListener(this);

        allViews.tvAttachDocumentBusiness.setOnClickListener(this);
        allViews.tvAttachDocumentPri.setOnClickListener(this);
        allViews.tvAttachDocumentSec.setOnClickListener(this);
        allViews.tvAttachDocumentBank.setOnClickListener(this);
        allViews.tvAttachDocumentMobile.setOnClickListener(this);

        allViews.ivDeleteBusinessProof.setOnClickListener(this);
        allViews.ivDeletePriProof.setOnClickListener(this);
        allViews.ivDeleteSecProof.setOnClickListener(this);
        allViews.ivDeleteBankProof.setOnClickListener(this);
        allViews.ivDeleteMobileProof.setOnClickListener(this);

        allViews.buttonSaveBusiness.setOnClickListener(this);
        allViews.buttonSavePriAddress.setOnClickListener(this);
        allViews.buttonSaveSecApplicantAdd.setOnClickListener(this);
        allViews.buttonSaveMobile.setOnClickListener(this);
        allViews.buttonSaveBankDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_business_address_change:
            case R.id.tv_pri_applicant_add_change:
            case R.id.tv_sec_applicant_add_change:
            case R.id.tv_mobile_no:
            case R.id.tv_bank_account:
                changeRequest.reset(view);
                allViews.setChangeRequest(changeRequest);
                break;
            case R.id.tv_attach_document_business:
            case R.id.tv_attach_document_pri:
            case R.id.tv_attach_document_sec:
            case R.id.tv_attach_document_bank:
                proofFor = (view.getId() == R.id.tv_attach_document_business) ? 0 : ((view.getId() == R.id.tv_attach_document_pri) ? 1 : ((view.getId() == R.id.tv_attach_document_sec) ? 2 : ((view.getId() == R.id.tv_attach_document_bank) ? 3 : 4)));
                openCameraGalleryOptions();
                break;

            case R.id.iv_delete_business_proof:
                ((BusinessAddressChangeRequest) data.get(1)).setProof_url(null);
                allViews.setBusinessAddress(((BusinessAddressChangeRequest) data.get(1)));

                break;
            case R.id.iv_delete_pri_proof:
                ((ResidenceAddressChangeRequest) data.get(2)).setProof_url(null);
                allViews.setPriAddress((ResidenceAddressChangeRequest) data.get(2));

                break;
            case R.id.iv_delete_sec_proof:
                ((ResidenceAddressChangeRequest) data.get(3)).setProof_url(null);
                allViews.setSecAddress((ResidenceAddressChangeRequest) data.get(3));

                break;
            case R.id.iv_delete_bank_proof:
                ((RepaymentBankChangeRequest) data.get(7)).setProof_url(null);
                allViews.setBankDetails((RepaymentBankChangeRequest) data.get(7));
                break;
            case R.id.iv_delete_mobile_proof:
                ((MobileChangeRequest) data.get(6)).setProof_url(null);
                allViews.setMobileDetails((MobileChangeRequest) data.get(6));
                break;

            case R.id.button_save_business:
                saveBusinessChangeAddress();
                break;
            case R.id.button_save_pri_address:
                saveResidenceChangeAddress(2);
                break;
            case R.id.button_save_sec_applicant_add:
                saveResidenceChangeAddress(3);
                break;
            case R.id.button_save_mobile:
                apiCallMobile();
                break;
            case R.id.button_save_bank_detail:
                saveRepaymentBankDetailChange();
                break;
        }
    }

    private void saveRepaymentBankDetailChange() {
        final RepaymentBankChangeRequest request = (RepaymentBankChangeRequest) data.get(7);
        request.setRepayment_bank_name(getText(allViews.etBankName));
        request.setRepayment_bank_account_no(getText(allViews.etBankAccountNo));
        request.setRepayment_bank_account_holder_name(getText(allViews.etBankAccountHolderName));
        request.setRepayment_bank_account_type(getText(allViews.etAccountType));
        request.setRepayment_bank_branch(getText(allViews.etBankBranch));
        request.setRepayment_bank_ifsc(getText(allViews.etIfscCode));
        request.setRepayment_bank_city(getText(allViews.etBankCity));
        request.setLoan_request_id(loan.getIdentifier());
        request.setRequest_category(String.valueOf(7));
        if (checkValidation(request)) {
            if (request.getProof_url().contains("http")) {
                callZipLoanApiForRepaymentBankChange(request);
            } else {
                ArrayList<ZiploanPhoto> arrPhotos = new ArrayList<>();
                arrPhotos.add(new ZiploanPhoto(request.getProof_url(), AppConstant.MediaType.IMAGE));
                FileUploader.getInstance(mContext).upload(AppConstant.FileType.ASSET_BUSINESS_PLACE_PHOTO, arrPhotos, AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, loan.getIdentifier(), new PhotoUploadListener() {
                    @Override
                    public void onUploadSuccess(ZiploanPhoto photo) {
                        request.setProof_url(photo.getRemote_path());
                        callZipLoanApiForRepaymentBankChange(request);
                    }

                    @Override
                    public void onUploadFailed(ZiploanPhoto photo) {
                        showAlertInfo(getString(R.string.attachment_uploading_failed));
                    }

                    @Override
                    public void onUploadStarted(ZiploanPhoto photo) {
                        showProgressDialog("Attachment uploading...");
                    }
                });
            }
        }
    }

    private void callZipLoanApiForRepaymentBankChange(RepaymentBankChangeRequest request) {
        showProgressDialog("Please wait...");
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).changeBankDetail(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                checkTokenValidity(response);
                if (response != null && response.body() != null && response.body().getResult().equalsIgnoreCase("1")) {
                    showAlertInfo("Repayment Bank details change request has been sent.", new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            finish();
                        }
                    });
                } else {
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

    private void apiCallMobile() {
        final MobileChangeRequest request = (MobileChangeRequest) data.get(6);
        request.setNew_mobile_number(getText(allViews.etMobile));
        request.setConfirm_mobile_number(getText(allViews.etConfirmMobile));
        request.setLoan_request_id(loan.getIdentifier());
        request.setRequest_category(String.valueOf(6));
        request.setmApplicantType(loan.getmApplicantType());
        if (checkValidation(request)) {
            showProgressDialog("Please wait...");
            Call<ApiResponse> call = APIExecutor.getAPIService(mContext).changeMobile(request);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    hideProgressDialog();
                    checkTokenValidity(response);
                    if (response != null && response.body() != null && response.body().getResult().equalsIgnoreCase("1")) {
                        showAlertInfo("Mobile Number change request has been sent.", new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                finish();
                            }
                        });
                    } else {
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
    }

    private void saveResidenceChangeAddress(int i) {
        final ResidenceAddressChangeRequest request = (ResidenceAddressChangeRequest) data.get(i);
        switch (i) {
            case 2:
                request.setResidence_address(getText(allViews.etPriAddress));
                request.setResidence_city(getText(allViews.etPriCity));
                request.setResidence_state(getText(allViews.etPriState));
                request.setResidence_pincode(getText(allViews.etPriPincode));
                break;
            case 3:
                request.setResidence_address(getText(allViews.etAddress));
                request.setResidence_city(getText(allViews.etCity));
                request.setResidence_state(getText(allViews.etState));
                request.setResidence_pincode(getText(allViews.etPincode));
                break;
        }
        request.setmApplicantType(loan.getmApplicantType());
        request.setLoan_request_id(loan.getIdentifier());
        request.setRequest_category(String.valueOf(i));
        if (checkValidation(request)) {
            if (request.getProof_url().contains("http")) {
                callResidenceAddressChangeZipLoanapi(request);
            } else {
                ArrayList<ZiploanPhoto> arrPhotos = new ArrayList<>();
                arrPhotos.add(new ZiploanPhoto(request.getProof_url(), AppConstant.MediaType.IMAGE));
                FileUploader.getInstance(mContext).upload(AppConstant.FileType.ASSET_BUSINESS_PLACE_PHOTO, arrPhotos, AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, loan.getIdentifier(), new PhotoUploadListener() {
                    @Override
                    public void onUploadSuccess(ZiploanPhoto photo) {
                        request.setProof_url(photo.getRemote_path());
                        callResidenceAddressChangeZipLoanapi(request);
                    }

                    @Override
                    public void onUploadFailed(ZiploanPhoto photo) {
                        showAlertInfo(getString(R.string.attachment_uploading_failed));
                    }

                    @Override
                    public void onUploadStarted(ZiploanPhoto photo) {
                        showProgressDialog("Attachment uploading...");
                    }
                });
            }
        }
    }

    private void saveBusinessChangeAddress() {
        final BusinessAddressChangeRequest request = (BusinessAddressChangeRequest) data.get(1);
        request.setBusiness_address(getText(allViews.etBusinessAddress));
        request.setBusiness_city(getText(allViews.etBusinessCity));
        request.setBusiness_state(getText(allViews.etBusninessState));
        request.setBusiness_pincode(getText(allViews.etBusinessPincode));
        request.setLoan_request_id(loan.getIdentifier());
        request.setRequest_category("1");
        if (checkValidation(request)) {
            if (request.getProof_url().contains("http")) {
                callBusinessAddressChangeZipLoanapi(request);
            } else {
                ArrayList<ZiploanPhoto> arrPhotos = new ArrayList<>();
                arrPhotos.add(new ZiploanPhoto(request.getProof_url(), AppConstant.MediaType.IMAGE));
                FileUploader.getInstance(mContext).upload(AppConstant.FileType.ASSET_BUSINESS_PLACE_PHOTO, arrPhotos, AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, loan.getIdentifier(), new PhotoUploadListener() {
                    @Override
                    public void onUploadSuccess(ZiploanPhoto photo) {
                        request.setProof_url(photo.getRemote_path());
                        callBusinessAddressChangeZipLoanapi(request);
                    }

                    @Override
                    public void onUploadFailed(ZiploanPhoto photo) {
                        showAlertInfo(getString(R.string.attachment_uploading_failed));
                    }

                    @Override
                    public void onUploadStarted(ZiploanPhoto photo) {
                        showProgressDialog("Attachment uploading...");
                    }
                });
            }
        }
    }

    private void callBusinessAddressChangeZipLoanapi(BusinessAddressChangeRequest request) {
        showProgressDialog("Please wait...");
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).changeRequestBusinessAddress(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                checkTokenValidity(response);
                if (response != null && response.body() != null && response.body().getResult().equalsIgnoreCase("1")) {
                    showAlertInfo("Business Address change request has been sent.", new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            finish();
                        }
                    });
                } else {
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

    private void callResidenceAddressChangeZipLoanapi(ResidenceAddressChangeRequest request) {
        showProgressDialog("Please wait...");
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).changePriResidenceAddress(request);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressDialog();
                checkTokenValidity(response);
                if (response != null && response.body() != null && response.body().getResult().equalsIgnoreCase("1")) {
                    showAlertInfo("Residence Address change request has been sent.", new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            finish();
                        }
                    });
                } else {
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

    private boolean checkValidation(BusinessAddressChangeRequest request) {
        ArrayList<String> arrRequiredFields = new ArrayList<>();
        if (request.getBusiness_address().trim().length() == 0) {
            arrRequiredFields.add("Business Address");
        }
        if (request.getBusiness_city().length() == 0) {
            arrRequiredFields.add("Business City");
        }
        if (request.getBusiness_state().length() == 0) {
            arrRequiredFields.add("Business State");
        }
        if (request.getBusiness_pincode().length() == 0) {
            arrRequiredFields.add("Business Pin");
        }
        if (request.getProof_url() == null || request.getProof_url().length() == 0) {
            arrRequiredFields.add("New Business Address proof");
        }

        if (arrRequiredFields.size() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("Required field(s) are : <br/>");
            for (int i = 0; i < arrRequiredFields.size(); i++)
                builder.append("&#9679; " + arrRequiredFields.get(i) + "<br/>");
            showAlertInfo(Html.fromHtml(builder.toString()), Color.parseColor("#ff0000"));
            return false;
        }
        return true;
    }

    private boolean checkValidation(MobileChangeRequest request) {
        ArrayList<String> arrRequiredFields = new ArrayList<>();
        if (!ZiploanUtil.isMobileValid(request.getNew_mobile_number().trim())) {
            arrRequiredFields.add("Valid New Mobile Number");
        }
        if (!ZiploanUtil.isMobileValid(request.getConfirm_mobile_number().trim())) {
            arrRequiredFields.add("Confirm New Mobile Number");
        }
        if (!request.getNew_mobile_number().trim().equalsIgnoreCase(request.getConfirm_mobile_number())) {
            arrRequiredFields.add("Mobile No and Confirm Mobile No should match");
        }

        if (arrRequiredFields.size() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("Required field(s) are : <br/>");
            for (int i = 0; i < arrRequiredFields.size(); i++)
                builder.append("&#9679; " + arrRequiredFields.get(i) + "<br/>");
            showAlertInfo(Html.fromHtml(builder.toString()), Color.parseColor("#ff0000"));
            return false;
        }
        return true;
    }

    private boolean checkValidation(RepaymentBankChangeRequest request) {
        ArrayList<String> arrRequiredFields = new ArrayList<>();
        if (request.getRepayment_bank_account_no().trim().length() == 0) {
            arrRequiredFields.add("Bank Account Number");
        }
        if (request.getRepayment_bank_account_holder_name().length() == 0) {
            arrRequiredFields.add("Account Holder Name");
        }
        if (request.getRepayment_bank_account_type().length() == 0) {
            arrRequiredFields.add("Bank Account Number");
        }
        if (request.getRepayment_bank_branch().length() == 0) {
            arrRequiredFields.add("Bank Branch");
        }
        if (request.getRepayment_bank_city().length() == 0) {
            arrRequiredFields.add("Bank City");
        }
        if (request.getRepayment_bank_ifsc().length() == 0) {
            arrRequiredFields.add("Bank Branch IFSC Code");
        }
        if (request.getRepayment_bank_name().length() == 0) {
            arrRequiredFields.add("Bank Name");
        }

        if (request.getProof_url() == null || request.getProof_url().length() == 0) {
            arrRequiredFields.add("New Bank Account proof");
        }

        if (arrRequiredFields.size() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("Required field(s) are : <br/>");
            for (int i = 0; i < arrRequiredFields.size(); i++)
                builder.append("&#9679; " + arrRequiredFields.get(i) + "<br/>");
            showAlertInfo(Html.fromHtml(builder.toString()), Color.parseColor("#ff0000"));
            return false;
        }
        return true;
    }

    private boolean checkValidation(ResidenceAddressChangeRequest request) {
        ArrayList<String> arrRequiredFields = new ArrayList<>();
        if (request.getResidence_address().trim().length() == 0) {
            arrRequiredFields.add("Residence Address");
        }
        if (request.getResidence_city().length() == 0) {
            arrRequiredFields.add("Residence City");
        }
        if (request.getResidence_state().length() == 0) {
            arrRequiredFields.add("Residence State");
        }
        if (request.getResidence_pincode().length() == 0) {
            arrRequiredFields.add("Residence Pin");
        }
        if (request.getProof_url() == null || request.getProof_url().length() == 0) {
            arrRequiredFields.add("New Residence Address proof");
        }

        if (arrRequiredFields.size() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("Required field(s) are : <br/>");
            for (int i = 0; i < arrRequiredFields.size(); i++)
                builder.append("&#9679; " + arrRequiredFields.get(i) + "<br/>");
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
        Intent intent = new Intent(mContext, ChangeRequestActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void getImages(List<ZiploanPhoto> arrImages, int sourceType) {
        switch (proofFor) {
            case 0:
                ((BusinessAddressChangeRequest) data.get(1)).setProof_url(arrImages.get(0).getPhotoPath());
                allViews.setBusinessAddress(((BusinessAddressChangeRequest) data.get(1)));
                break;
            case 1:
                ((ResidenceAddressChangeRequest) data.get(2)).setProof_url(arrImages.get(0).getPhotoPath());
                allViews.setPriAddress((ResidenceAddressChangeRequest) data.get(2));
                break;
            case 2:
                ((ResidenceAddressChangeRequest) data.get(3)).setProof_url(arrImages.get(0).getPhotoPath());
                allViews.setSecAddress((ResidenceAddressChangeRequest) data.get(3));
                break;
            case 3:
                ((RepaymentBankChangeRequest) data.get(7)).setProof_url(arrImages.get(0).getPhotoPath());
                allViews.setBankDetails((RepaymentBankChangeRequest) data.get(7));
                break;
            case 4:
                ((MobileChangeRequest) data.get(6)).setProof_url(arrImages.get(0).getPhotoPath());
                allViews.setMobileDetails((MobileChangeRequest) data.get(6));
                break;
        }
    }

    @Override
    public void processingImages() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtil.CAMERA_REQUEST_CODE:
                if (PermissionUtil.checkCameraPermission(mContext)) {
                    openCamera(this);
                }
                break;
            case PermissionUtil.STORAGE_REQUEST_CODE:
                if (PermissionUtil.checkStoragePermission(mContext)) {
                    openGallery(this, false);
                }
                break;
        }
    }

    protected void openCameraGalleryOptions() {
        CharSequence[] items = new CharSequence[]{"Capture With Camera", "Choose From Gallery"};
        itemListDialog = new AlertDialog.Builder(mContext).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        if (PermissionUtil.checkCameraPermission(mContext)) {
                            openCamera(ChangeRequestActivity.this);
                        } else {
                            PermissionUtil.requestCameraPermission(ChangeRequestActivity.this);
                        }
                        break;
                    case 1:
                        if (PermissionUtil.checkStoragePermission(mContext)) {
                            openGallery(ChangeRequestActivity.this, false);
                        } else {
                            PermissionUtil.requestStoragePermission(ChangeRequestActivity.this);
                        }
                        break;
                }
                itemListDialog.dismiss();
            }
        }).show();
    }

}