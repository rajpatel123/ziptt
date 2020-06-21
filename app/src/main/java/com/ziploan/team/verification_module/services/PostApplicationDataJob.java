package com.ziploan.team.verification_module.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.google.gson.Gson;
import com.ziploan.team.R;
import com.ziploan.team.collection.utils.UIErrorUtils;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.NetworkUtil;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanBorrowerDetails;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanSiteInfo;
import com.ziploan.team.verification_module.borrowerdetails.questions.ZiploanNewBorrowerDetails;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.verification_module.receivers.NotificationReceiver;
import com.ziploan.team.webapi.APIError;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;
import com.ziploan.team.webapi.VerificationDetailsPostRequest;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class PostApplicationDataJob extends Job {
    private static final String TAG = PostApplicationDataJob.class.getSimpleName();
    public static final String TAG_TASK_ONEOFF_LOG = "tag_task_one_off";
    Intent intent = null;
    ArrayList<Result> arrayListResult = new ArrayList<>();
    private ArrayList<ZiploanNewBorrowerDetails> arrDetails = new ArrayList<>();

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        arrDetails = DatabaseManger.getInstance().getNewBorrowerApplicationDetailsListFinalSubmitted();
        if (arrDetails != null && arrDetails.size() > 0) {
            for (int i = 0; i < arrDetails.size(); i++) {
                arrayListResult.add(runJobFor(arrDetails.get(i)));
            }
            if (arrayListResult.size() == arrDetails.size() && !(arrayListResult.contains(Result.FAILURE) || arrayListResult.contains(Result.RESCHEDULE))) {
                return resultSuccess(false);
            }
        } else {
            return resultSuccess(false);
        }
        return resultFailed();
    }

//    private Result runJobFor(ZiploanBorrowerDetails details) {
//        resultStarted();
//        return callSubmitAPI(details);
//    }

    private Result runJobFor(ZiploanNewBorrowerDetails details) {
        resultStarted();
        return callSubmitAPI(details);
    }

    private void resultStarted() {
        generateBroadcast(AppConstant.LocalBroadcastType.FILE_UPLOADING_STARTED);
    }

    @NonNull
    private Result resultFailed() {
        generateBroadcast(AppConstant.LocalBroadcastType.FILE_UPLOADING_FAILED);
        return Result.RESCHEDULE;
    }

    @NonNull
    private Result resultSuccess(boolean b) {
        generateBroadcast(AppConstant.LocalBroadcastType.FILE_UPLOADING_COMPLETED);
        if (b)
            startNotification(true);
        return Result.SUCCESS;
    }

    /**
     * This method will create a request data and call the API to Post Application data to server
     *
     * @return
     */
//    private Result callSubmitAPI(ZiploanBorrowerDetails details) {
//        VerificationDetailsPostRequest request = new VerificationDetailsPostRequest();
//        request.setCallingSource(1); //always 1
//        request.setLoan_request_id(details.getLoan_request_id());
//        if (details.getSite_info() != null) {
//            ZiploanSiteInfo siteInfo = details.getSite_info();
//            request.setBusiness_category(siteInfo.getBusiness_category());
//            request.setReferences(siteInfo.getArrReferenceUsers());
//            request.setBusiness_place_seperate_residence_place(siteInfo.getBusiness_place_seperate_residence_place());
//            request.setStock_inventory_amount(siteInfo.getStock_inventory_amount());
//            request.setFixed_asset_machinery_amount(siteInfo.getFixed_asset_machinery_amount());
//            request.setNo_employees(siteInfo.getActual_no_of_employees());
//            request.setBusiness_place_photos_url(ZiploanUtil.extractUrlList(siteInfo.getBusiness_place_photo_url()));
//
////            request.setActual_no_of_employees(siteInfo.getActual_no_of_employees());
//            request.setRent_amount(siteInfo.getRent_amount());
//            request.setInvestment_in_business(siteInfo.getInvestment_in_business());
//            request.setRaw_material(siteInfo.getRaw_material());
//            request.setNo_of_machines(siteInfo.getNo_of_machines());
//            request.setApp_not_installed_reason(siteInfo.getApp_not_installed_reason());
//
//            request.setPerson_met(siteInfo.getPerson_met());
//            request.setDesignation_in_office(siteInfo.getDesignation_in_office());
//            request.setSign_board_observed(siteInfo.getSign_board_observed());
//            request.setNature_of_work(siteInfo.getNature_of_work());
//            request.setEmail(siteInfo.getPerson_email());
//        }
//        if (details.getQuestions() != null) {
//            request.setBusiness_information_questionnaire(details.getQuestions());
//        }
//
//        if (details.getLat() != null) {
//            request.setBusiness_place_latitude_captured_on_verification(Double.parseDouble(details.getLat()));
//        }
//        if (details.getLng() != null) {
//            request.setBusiness_place_longitude_captured_on_verification(Double.parseDouble(details.getLng()));
//        }
//        Gson gson = new Gson();
//        gson.serializeNulls();
//        System.out.println("Final request for API = " + gson.toJson(request));
//        Call<ApiResponse> call = APIExecutor.getAPIServiceSerializeNull(getContext()).postApplicationDetails(request);
//        try {
//            ResponseData<ApiResponse> response = call.execute();
//            if (response != null && response.isSuccessful() && response.body() != null && response.body().result != null && response.body().result.equalsIgnoreCase("1")) {
//                DatabaseManger.getInstance().deleteApplicationInfoByLoanRequestId(details.getLoan_request_id());
//                startNotificationWithFailureMessage(response.body().message);
//                return resultSuccess(true);
//            } else if (response.code() == 500) {
//                return resultFailed();
//            } else if (response.code() == 400) {
//                APIError apiError = NetworkUtil.parseErrorResponse(response);
//                if (apiError != null && apiError.message() != null) {
//                    startNotificationWithFailureMessage(apiError.message());
//                }
//                DatabaseManger.getInstance().updateVerificationApplicationStatus(details.getLoan_request_id());
//                return resultSuccess(false);
//            } else if (response.code() == 401) {
//                JobManager.instance().cancelAll();
//                startNotificationWithFailureMessage("Your token is expired! Login again in App");
//                return resultSuccess(false);
//            } else {
//                return resultSuccess(false);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            startNotificationWithFailureMessage(e.getMessage());
//            return resultFailed();
//        }
//    }

    private Result callSubmitAPI(ZiploanNewBorrowerDetails details) {
        if(UIErrorUtils.isNetworkConnected(getContext())) {

            VerificationDetailsPostRequest request = new VerificationDetailsPostRequest();
            request.setCallingSource(1); //always 1
            request.setLoan_request_id(details.getLoan_request_id());
            if (details.getSite_info() != null) {
                ZiploanSiteInfo siteInfo = details.getSite_info();
                request.setBusiness_category(siteInfo.getBusiness_category());
                request.setFamilyReferences(siteInfo.getArrFamilyReferenceUsers());
                request.setReferences(siteInfo.getArrReferenceUsers());
               // request.setBusiness_place_seperate_residence_place(siteInfo.getBusiness_place_seperate_residence_place());
                request.setStock_inventory_amount(siteInfo.getStock_inventory_amount());
                request.setFixed_asset_machinery_amount(siteInfo.getFixed_asset_machinery_amount());
                request.setNo_employees(siteInfo.getActual_no_of_employees());
                request.setBusiness_place_photos_url(ZiploanUtil.extractUrlList(siteInfo.getBusiness_place_photo_url()));
                request.setBusiness_place_long_shot_photos_url(ZiploanUtil.extractUrlList(siteInfo.getLong_shot_photos()));
                request.setId_proof_photos_url(ZiploanUtil.extractUrlList(siteInfo.getId_proof_photos()));

//            request.setActual_no_of_employees(siteInfo.getActual_no_of_employees());
                request.setRent_amount(siteInfo.getRent_amount());
                request.setInvestment_in_business(siteInfo.getInvestment_in_business());
                request.setRaw_material(siteInfo.getRaw_material());
                request.setNo_of_machines(siteInfo.getNo_of_machines());
                request.setApp_not_installed_reason(siteInfo.getApp_not_installed_reason());

                request.setPerson_met(siteInfo.getPerson_met());
                request.setDesignation_in_office(siteInfo.getDesignation_in_office());
                request.setSign_board_observed(siteInfo.getSign_board_observed());
                request.setNature_of_work(siteInfo.getNature_of_work());
                request.setText_nature_of_work(siteInfo.getText_nature_of_work());
                request.setEmail(siteInfo.getPerson_email());

                request.setBusiness_stability(siteInfo.getBusiness_stability());
                request.setBusiness_stability_details(siteInfo.getBusiness_place_change_reason());
                request.setLandmark(siteInfo.getLandmark());
                request.setBusiness_address_same_as_dashboard(siteInfo.getBusiness_address_same_as_dashboard());
                request.setBusiness_address_same_as_dashboard_reason(siteInfo.getBusiness_address_same_as_dashboard_reason());
                request.setLocality_business_place(siteInfo.getLocality_business_place());
                request.setEducation_qualification(siteInfo.getEducation_qualification());
            }
            if (details.getQuestions() != null) {
                request.setBusiness_information_questionnaire(details.getQuestions());
            }

            if (details.getLat() != null) {
                request.setBusiness_place_latitude_captured_on_verification(Double.parseDouble(details.getLat()));
            }
            if (details.getLng() != null) {
                request.setBusiness_place_longitude_captured_on_verification(Double.parseDouble(details.getLng()));
            }
            if (details.getLoan_details() != null) {
                request.setBank_info(details.getLoan_details());
            }


            Gson gson = new Gson();
            gson.serializeNulls();
            //System.out.println("Final request for API = " + gson.toJson(request));
            Call<ApiResponse> call = APIExecutor.getAPIServiceSerializeNull(getContext()).postApplicationDetails(request,2);
            try {
                Response<ApiResponse> response = call.execute();
                if (response != null && response.isSuccessful() && response.body() != null && response.body().result != null && response.body().result.equalsIgnoreCase("1")) {
                    DatabaseManger.getInstance().deleteApplicationInfoByLoanRequestId(details.getLoan_request_id());
                    startNotificationWithFailureMessage(response.body().message);
                    return resultSuccess(true);
                } else if (response.code() == 500) {
                    APIError apiError = NetworkUtil.parseErrorResponse(response);
                    if (apiError != null && apiError.message() != null) {
                        startNotificationWithFailureMessage(apiError.message());
                    }
                    DatabaseManger.getInstance().updateVerificationApplicationStatus(details.getLoan_request_id());
                    return resultFailed();
                } else if (response.code() == 400) {
                    APIError apiError = NetworkUtil.parseErrorResponse(response);
                    if (apiError != null && apiError.message() != null) {
                        startNotificationWithFailureMessage(apiError.message());
                    }
                    DatabaseManger.getInstance().updateVerificationApplicationStatus(details.getLoan_request_id());
                    return resultSuccess(false);
                } else if (response.code() == 401) {
                    JobManager.instance().cancelAll();
                    startNotificationWithFailureMessage("Your token is expired! Login again in App");
                    return resultSuccess(false);
                } else {
                    APIError apiError = NetworkUtil.parseErrorResponse(response);
                    if (apiError != null && apiError.message() != null) {
                        startNotificationWithFailureMessage(apiError.message());
                    }
                    DatabaseManger.getInstance().updateVerificationApplicationStatus(details.getLoan_request_id());
                    return resultSuccess(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
                startNotificationWithFailureMessage(e.getMessage());
                DatabaseManger.getInstance().updateVerificationApplicationStatus(details.getLoan_request_id());
                return resultFailed();
            }
        } else{
            return resultFailed();
        }
    }

    private void generateBroadcast(int status) {
        intent = new Intent(AppConstant.LocalBroadcastType.APPLICATION_POST_STATUS);
        intent.putExtra(AppConstant.LocalBroadcastType.APPLICATION_POST_STATUS, status);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    /**
     * This will create a notification which will display the status of file upload
     *
     * @param isSuccess
     */
    public void startNotification(boolean isSuccess) {
        if(!isSuccess) {
            Intent intent = new Intent(getContext(), NotificationReceiver.class);
            intent.putExtra(AppConstant.EXTRA_NOTIFICATION_TYPE, isSuccess ? AppConstant.UPLOAD_SUCCESS : AppConstant.UPLOAD_FAILED);
            PendingIntent pIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification n = new Notification.Builder(getContext())
                    .setContentTitle("Ziploan")
                    .setContentText(isSuccess ? "Application data submitted successfully." : "Application data submission failed.")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true).build();
            int notificationId = (int) (System.currentTimeMillis() / 1000);
            NotificationManager mNotificationManager =
                    (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(notificationId, n);
        }
    }

    public void startNotificationWithFailureMessage(String message) {
        Intent intent = new Intent(getContext(), NotificationReceiver.class);
        intent.putExtra(AppConstant.EXTRA_NOTIFICATION_TYPE, AppConstant.UPLOAD_FAILED);
        PendingIntent pIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification n = new Notification.Builder(getContext())
                .setContentTitle("Ziploan")
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true).build();
        int notificationId = (int) (System.currentTimeMillis() / 1000);
        NotificationManager mNotificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, n);
    }

    @Override
    protected void onReschedule(int newJobId) {
        System.out.println(TAG + " JOb has been rescheduled with this id = " + newJobId);
    }

    public static void scheduleJob() {
        new JobRequest.Builder(PostApplicationDataJob.TAG)
                .setExecutionWindow(0, 120000)
                .build()
                .schedule();
    }

    /**
     * This will start a Job with advanced settings and features
     *
     * @return
     */
    public static int scheduleAdvancedJob() {
        int jobId = new JobRequest.Builder(PostApplicationDataJob.TAG_TASK_ONEOFF_LOG)
                .setExecutionWindow(200, 180000)
                .setBackoffCriteria(5000, JobRequest.BackoffPolicy.LINEAR)
                .setRequiresCharging(false)
                .setRequiresDeviceIdle(false)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setRequirementsEnforced(true)
//                .setPersisted(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();
        return jobId;
    }

    public static int runJobImmediately() {
        int jobId = new JobRequest.Builder(PostApplicationDataJob.TAG_TASK_ONEOFF_LOG)
                .startNow()
                .build()
                .schedule();
        return jobId;
    }

    private void cancelJob(int jobId) {
        JobManager.instance().cancel(jobId);
    }
}
