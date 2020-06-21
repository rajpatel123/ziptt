package com.ziploan.team.collection.service;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.ziploan.team.App;
import com.ziploan.team.collection.model.bank_names.BankNameModel;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.webapi.APIExecutor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBankListJob extends Job {
    public static final String TAG = "get_bank_names";

    @NonNull
    @Override
    protected Result onRunJob(@NonNull Params params) {
        getBankNames();
        return Result.SUCCESS;
    }

    private void getBankNames() {
        Call<BankNameModel> call = APIExecutor.getAPIService(getContext()).getBankNames(AppConstant.Key.VIEW_VALUE);
        call.enqueue(new Callback<BankNameModel>() {
            @Override
            public void onResponse(Call<BankNameModel> call, final Response<BankNameModel> response) {
                if (response != null && response.isSuccessful() && response.body() != null
                        && response.body().getResponse() != null && response.body().getResponse().size() > 0) {
                    try {
                        DatabaseManger.getInstance().saveBankNames(response.body().getResponse());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    App.BankData = response.body().getResponse();
                    ZiploanSPUtils.getInstance(getContext()).setBankSaved();
                }
            }

            @Override
            public void onFailure(Call<BankNameModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void runJobImmediately() {
        new JobRequest.Builder(GetBankListJob.TAG)
                .startNow()
                .build()
                .schedule();
    }
}
