package com.ziploan.team.verification_module.services;

import android.support.annotation.NonNull;
import android.support.v4.util.TimeUtils;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.google.gson.Gson;
import com.ziploan.team.App;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 8/21/2017.
 */

public  class SyncFiltersJob extends Job{
    public static final String TAG_SYNC_FILTERS = "tag_sync_filters";

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Call<ResponseBody> call = APIExecutor.getAPIService(getContext()).getFilters();
        try {
            Response<ResponseBody> response  = call.execute();
            if(response!=null && response.body()!=null){
                try{
                    DatabaseManger.getInstance().saveFiltersData(response.body().string(), new OnSaveSuccess(){

                        @Override
                        public void onSuccess() {
                            App.filtersMain =  DatabaseManger.getInstance().getAllAssetsFiltersItemsByKey();
                            App.filtersKeyMain =  DatabaseManger.getInstance().getAllAssetsFiltersKey();

                        }
                    });
                }catch (Exception e){}
                /*****/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.SUCCESS;
    }

    /**
     * This will start a Job with advanced settings and features
     * @return
     */
    public static int scheduleJob() {
        int jobId = new JobRequest.Builder(SyncFiltersJob.TAG_SYNC_FILTERS)
                .setPeriodic(2*24*60*60*1000)
                .setRequiresCharging(false)
                .setRequiresDeviceIdle(false)
                .setRequirementsEnforced(true)
//                .setPersisted(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();
        return jobId;
    }
}
