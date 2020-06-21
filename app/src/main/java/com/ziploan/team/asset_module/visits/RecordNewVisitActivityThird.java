package com.ziploan.team.asset_module.visits;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

import com.google.gson.Gson;
import com.ziploan.team.PhotoUploadListener;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.asset_module.FileUploader;
import com.ziploan.team.asset_module.loans.LoansListActivity;
import com.ziploan.team.databinding.ActivityRecordNewVisitThirdBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.PermissionUtil;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.verifyekyc.PhotosAdapter;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;
import com.ziploan.team.webapi.Loan;
import com.ziploan.team.webapi.RecordNewVisitPojo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 */

public class RecordNewVisitActivityThird extends AssetsBaseActivity implements View.OnClickListener, LoadPhotoListener, PhotosAdapter.PhotosAdapterListener {

    private ActivityRecordNewVisitThirdBinding allViews;
    private Bundle bundle;
    private RecordNewVisitPojo visitedData;
    private ArrayList<ZiploanPhoto> arrPhotos = new ArrayList<>();
    private PhotosAdapter adapter;
    private Loan loan;

    @Override
    protected Toolbar getToolbar() {
        return allViews.actionBar.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_new_visit_third;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityRecordNewVisitThirdBinding) views;
        bundle = getIntent().getExtras();
        if(bundle.containsKey(AppConstant.Key.EXTRA_OBJECT_VISITED_DATA)){
            visitedData = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT_VISITED_DATA);
        }

        if(bundle.containsKey(AppConstant.Key.EXTRA_OBJECT)){
            loan = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT);
        }
        allViews.actionBar.setActionBarData(new ActionBarData(R.mipmap.ic_back,"Record New Visit"));
        allViews.actionBar.setLastVisitButton(true);
        setListeners();
        setPhotosViews();
    }

    private void setPhotosViews() {
        adapter = new PhotosAdapter(mContext,arrPhotos,this);
        allViews.rvPhotos.setLayoutManager(new GridLayoutManager(mContext,3));
        allViews.rvPhotos.setAdapter(adapter);
    }

    private void setListeners() {
        allViews.llCamera.setOnClickListener(this);
        allViews.llGallery.setOnClickListener(this);
        allViews.buttonSave.setOnClickListener(this);
        allViews.actionBar.buttonViewOld.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll_camera:
                if(PermissionUtil.checkCameraPermission(mContext)){
                    openCamera(RecordNewVisitActivityThird.this);
                }else {
                    PermissionUtil.requestCameraPermission(mContext);
                }
                break;
            case R.id.ll_gallery:
                if(PermissionUtil.checkStoragePermission(mContext)){
                    openGallery(RecordNewVisitActivityThird.this,true);
                }else {
                    PermissionUtil.requestStoragePermission(mContext);
                }
                break;
            case R.id.button_save:
                if(checkValidation()){
                    int businessPremiseAvail = (allViews.rgBusinessPremiseAvailability.getCheckedRadioButtonId() == allViews.rbAvailabilityYes.getId())?1:0;
                    int businessOperational = (allViews.rgBusinessOperational.getCheckedRadioButtonId() == allViews.rbOperationalYes.getId())?1:0;
                    int signBoardObserved = (allViews.rgSignBoardObserved.getCheckedRadioButtonId() == allViews.rbSignboardYes.getId())?1:0;
                    String feedback = getText(allViews.etFeedback);
                    String reason = getText((allViews.etReason));
                    visitedData.setBusiness_premise_availability(String.valueOf(businessPremiseAvail));
                    visitedData.setBusiness_operational_at_given_business_address(String.valueOf(businessOperational));
                    visitedData.setSign_board_availability(String.valueOf(signBoardObserved));
                    visitedData.setFeedback(String.valueOf(feedback));
                    visitedData.setApp_uninstallation_reason(String.valueOf(reason));
                    visitedData.setAsset_manager_feedback(getText(allViews.etAssetManagerFeedback));
                    visitedData.setBusiness_place_photo(getBusinessPlacePhotosIds());
                    visitedData.setRecorded_coordinates(ZiploanSPUtils.getInstance(mContext).getTempLat()+","+ZiploanSPUtils.getInstance(mContext).getTempLng());
                    visitedData.setDate_of_visit(ZiploanUtil.getDateFromTimestamp(Calendar.getInstance().getTimeInMillis(),"dd-MM-yyyy"));
                    visitedData.setTime_of_visit(ZiploanUtil.getDateFromTimestamp(Calendar.getInstance().getTimeInMillis(),"hh:mm"));
                    visitedData.setParam("1");
                    callNewRecordAPI(visitedData);
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
        if(allViews.rgBusinessPremiseAvailability.getCheckedRadioButtonId() == -1){
            arrRequiredFields.add("Availability on Business Premise");
        }
        if(allViews.rgBusinessOperational.getCheckedRadioButtonId() == -1){
            arrRequiredFields.add("Business Operational at Business Premise");
        }
        if(allViews.rgSignBoardObserved.getCheckedRadioButtonId() == -1){
            arrRequiredFields.add("Sign Board Observed");
        }
        if(arrPhotos.size() == 0){
            arrRequiredFields.add("At least one business place photo");
        }

        if(allViews.etAssetManagerFeedback.getText().toString().trim().length()==0){
            arrRequiredFields.add("Asset Manager's Feedback");
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

    private ArrayList<String> getBusinessPlacePhotosIds() {
        ArrayList<String> arrIds = new ArrayList<>();
        for(ZiploanPhoto file : arrPhotos){
            arrIds.add(file.getPhotoIdentifier());
        }
        return arrIds;
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
                        showAlertInfo("Congrats,Visit has been recorded successfully.", new DialogInterface.OnDismissListener(){

                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                Intent intent = new Intent(mContext,LoansListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }else {
                        showAlertInfo(response.body().getMessage()!=null?response.body().getMessage():"Something ge");
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

    @Override
    protected void onActionHomeButtonClicked() {
        onBackPressed();
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext,RecordNewVisitActivityThird.class);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PermissionUtil.CAMERA_REQUEST_CODE:
                if(PermissionUtil.checkCameraPermission(mContext)){
                    openCamera(RecordNewVisitActivityThird.this);
                }
                break;
            case PermissionUtil.STORAGE_REQUEST_CODE:
                if(PermissionUtil.checkStoragePermission(mContext)){
                    openGallery(RecordNewVisitActivityThird.this, true);
                }
                break;
        }
    }

    @Override
    public void getImages(List<ZiploanPhoto> arrImages, int sourceType) {
        int positionStart = arrPhotos.size();
        hideProgressDialog();
        arrPhotos.addAll(arrImages);
        adapter.notifyItemRangeInserted(positionStart,arrImages.size());
        System.out.println("Selected Photos = "+new Gson().toJson(arrImages));
        uploadFiles(arrImages);
    }

    @Override
    public void processingImages() {
        showProgressDialog("Compressing images");
    }

    @Override
    public void deletePhoto(int position, int index) {
        if(arrPhotos.get(position).getUpload_status() == AppConstant.UploadStatus.UPLOADING_SUCCESS){
            FileUploader.getInstance(mContext).deleteFile(arrPhotos.get(position),AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO,loan.getIdentifier());
        }
        arrPhotos.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void retryUpload(int position, int index) {
        uploadFiles(arrPhotos.subList(position,position+1));
    }

    @Override
    public void openCameraGalleyOptions(int position, int index, boolean multi_selection) {

    }

    private void uploadFiles(List<ZiploanPhoto> arrImages) {
        FileUploader.getInstance(mContext).upload(AppConstant.FileType.ASSET_BUSINESS_PLACE_PHOTO,arrImages,AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO,loan.getIdentifier(),new PhotoUploadListener(){
            @Override
            public void onUploadSuccess(ZiploanPhoto photo) {
                photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_SUCCESS);
                adapter.notifyItemChanged(arrPhotos.indexOf(photo));
            }

            @Override
            public void onUploadFailed(ZiploanPhoto photo) {
                photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_FAILED);
                adapter.notifyItemChanged(arrPhotos.indexOf(photo));
            }

            @Override
            public void onUploadStarted(ZiploanPhoto photo) {
                photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_STARTED);
                adapter.notifyItemChanged(arrPhotos.indexOf(photo));
            }
        });
    }
}