package com.ziploan.team.collection.service;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.ziploan.team.PhotoUploadListener;
import com.ziploan.team.asset_module.FileUploader;
import com.ziploan.team.collection.model.record_visit.PastVisitResponse;
import com.ziploan.team.collection.model.record_visit.RecordVisitRequestModel;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
import com.ziploan.team.webapi.APIExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRecordVisitJob extends Job {
    public static final String RECOR_TAG = "tag_record_visit";
    private static final Integer CHEQUE_MODE = 7;
    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        List<HashMap<String, RecordVisitRequestModel>> recordedData = DatabaseManger.getInstance().getRecordVisitDataFromDb();
        if (recordedData != null && recordedData.size() > 0) {
            for (HashMap<String, RecordVisitRequestModel> data :
                    recordedData) {
                submmitData(data);
            }
            return resultSuccess();
        } else {
            return resultSuccess();
        }
    }

    @NonNull
    private Result resultFailed() {
        return Result.RESCHEDULE;
    }

    @NonNull
    private Result resultSuccess() {
        return Result.SUCCESS;
    }

    private void submmitData(HashMap<String,RecordVisitRequestModel> data) {
        Iterator<Map.Entry<String, RecordVisitRequestModel>> _data = data.entrySet().iterator();
        String keyData = "";
        RecordVisitRequestModel requestModel = null;
        while (_data.hasNext()) {
            Map.Entry<String, RecordVisitRequestModel> entry = _data.next();
            keyData = entry.getKey();
            requestModel = entry.getValue();
        }

        if((requestModel.getMode() != null) && requestModel.getMode().equals(CHEQUE_MODE)) {
            List<ZiploanPhoto> list = new ArrayList<>();
            if(requestModel.getmFileUrl().contains("/storage")) {
                ZiploanPhoto ziploanPhoto = new ZiploanPhoto(requestModel.getmFileUrl());
                list.add(ziploanPhoto);
                uploadFiles(list, requestModel.getmIdentifier(), keyData, requestModel);
            } else {
                postData(keyData, requestModel);
            }
        } else {
            postData(keyData, requestModel);
        }
    }

    private void postData(String id, RecordVisitRequestModel recordVisitRequestModel) {
        Call<PastVisitResponse> call = APIExecutor.getAPIServiceSerializeNull(getContext()).postPastVisits(id, recordVisitRequestModel,AppConstant.Key.VIEW_VALUE);
        call.enqueue(new Callback<PastVisitResponse>() {
            @Override
            public void onResponse(Call<PastVisitResponse> call, Response<PastVisitResponse> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    DatabaseManger.getInstance().deleteRecordVisitData(response.body().getmResponse().getmLoanApplicationNumber());
                }
            }

            @Override
            public void onFailure(Call<PastVisitResponse> call, Throwable t) {

            }
        });
    }

    private void uploadFiles(List<ZiploanPhoto> arrImages, String identifier, final String loan_app_number, final RecordVisitRequestModel recordVisitRequestModel) {
        FileUploader.getInstance(getContext()).upload(AppConstant.FileType.COLLECTION, arrImages, AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, identifier, new PhotoUploadListener() {
            @Override
            public void onUploadSuccess(ZiploanPhoto photo) {
                String fileUrl = photo.getRemote_path();
                recordVisitRequestModel.setmFileUrl(fileUrl);
                postData(loan_app_number, recordVisitRequestModel);
            }

            @Override
            public void onUploadFailed(ZiploanPhoto photo) {
                photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_FAILED);
            }

            @Override
            public void onUploadStarted(ZiploanPhoto photo) {
                photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_STARTED);
            }
        });
    }

    public static void scheduleAdvancedJob() {
         new JobRequest.Builder(PostRecordVisitJob.RECOR_TAG)
                .setPeriodic(15 * 60 * 1000)
                .setRequiresCharging(false)
                .setRequiresDeviceIdle(false)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
//                .setPersisted(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }

    private static void cancelJob(int jobId) {
        JobManager.instance().cancel(jobId);
    }
}
