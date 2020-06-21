package com.ziploan.team.asset_module;

import android.content.Context;
import android.text.TextUtils;

import com.ziploan.team.BuildConfig;
import com.ziploan.team.PhotoUploadListener;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.FileUploadResponse;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * AsyncTask, This will upload the files to amazon s3.
 */
public class FileUploader {

    private final Context mContext;

    public FileUploader(Context context) {
        this.mContext = context;
    }

    public static FileUploader getInstance(Context context) {
        return new FileUploader(context);
    }

    public void upload(String file_type, List<ZiploanPhoto> filePaths, int bucket_id, String loanId, final PhotoUploadListener photoUploadListener) {
        for (int i = 0; i < filePaths.size(); i++) {
            final ZiploanPhoto photo = filePaths.get(i);
            File file = new File(photo.getPhotoPath());
            RequestBody fileBody = RequestBody.create(MediaType.parse(photo.getMedia_type()), file);
            RequestBody loanRequestId = RequestBody.create(MediaType.parse("text/plain"), loanId);
            RequestBody persist = null;
            if (file_type == AppConstant.FileType.ASSET_BUSINESS_PLACE_PHOTO || file_type == AppConstant.FileType.COLLECTION)
                persist = RequestBody.create(MediaType.parse("application/json"), String.valueOf(true));
            else
                persist = RequestBody.create(MediaType.parse("application/json"), String.valueOf(false));

            RequestBody bucketId = RequestBody.create(MediaType.parse("application/json"), String.valueOf(bucket_id));
            RequestBody env = RequestBody.create(MediaType.parse("application/json"), BuildConfig.ENVIRONMENT);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            RequestBody fileType = RequestBody.create(MediaType.parse("application/json"), file_type);

            Map<String, RequestBody> map = new HashMap<>();
            map.put("uploaded_file\"; filename=\"" + file.getName(), fileBody);
            map.put("loan_request_id", loanRequestId);
            map.put("persist", persist);
            map.put("bucket_id", bucketId);
            map.put("env", env);
            map.put("fname", filename);
            map.put("file_type", fileType);

            photoUploadListener.onUploadStarted(photo);
            Call<FileUploadResponse> call = APIExecutor.getAPIService(mContext).uploadFile(map);
            call.enqueue(new Callback<FileUploadResponse>() {
                @Override
                public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
                    if (response != null && response.code() == 200) {
                        if (response.body() != null && !TextUtils.isEmpty(response.body().getUrl())) {
                            photo.setRemote_path(response.body().getUrl());
                            photo.setPhotoIdentifier(response.body().getUpdated_id());
                            photoUploadListener.onUploadSuccess(photo);
                        } else {
                            photoUploadListener.onUploadFailed(photo);
                        }
                    } else {
                        photoUploadListener.onUploadFailed(photo);
                    }
                }

                @Override
                public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                    photoUploadListener.onUploadFailed(photo);
                }
            });
        }
    }

    public void deleteFile(ZiploanPhoto file, int bucket_id, String loanId) {
        Map<String, String> query = new HashMap<>();
        query.put("loan_request_id", loanId);
        query.put("file_url", file.getRemote_path());
        query.put("bucket_id", String.valueOf(bucket_id));
        Call<FileUploadResponse> call = APIExecutor.getAPIService(mContext).deleteFile(query);
        call.enqueue(new Callback<FileUploadResponse>() {
            @Override
            public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
            }

            @Override
            public void onFailure(Call<FileUploadResponse> call, Throwable t) {
            }
        });
    }



    public void uploadApplicationDocument(String file_type,String applicant_type, List<ZiploanPhoto> filePaths, int bucket_id, String loanId, final PhotoUploadListener photoUploadListener) {
        for (int i = 0; i < filePaths.size(); i++) {
            final ZiploanPhoto photo = filePaths.get(i);
            File file = new File(photo.getPhotoPath());
            RequestBody fileBody = RequestBody.create(MediaType.parse(photo.getMedia_type()), file);
            RequestBody loanRequestId = RequestBody.create(MediaType.parse("text/plain"), loanId);
            RequestBody persist = null;

            persist = RequestBody.create(MediaType.parse("application/json"), String.valueOf(true));

            RequestBody bucketId = RequestBody.create(MediaType.parse("application/json"), String.valueOf(bucket_id));
            RequestBody env = RequestBody.create(MediaType.parse("application/json"), BuildConfig.ENVIRONMENT);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            RequestBody applicant_types = RequestBody.create(MediaType.parse("application/json"), applicant_type);
            RequestBody fileType = RequestBody.create(MediaType.parse("application/json"), file_type);

            Map<String, RequestBody> map = new HashMap<>();
            map.put("uploaded_file\"; filename=\"" + file.getName(), fileBody);
            map.put("loan_request_id", loanRequestId);
            map.put("persist", persist);
            map.put("bucket_id", bucketId);

            map.put("applicant_type", applicant_types);
            map.put("env", env);
            map.put("fname", filename);
            map.put("file_type", fileType);

            photoUploadListener.onUploadStarted(photo);
            Call<FileUploadResponse> call = APIExecutor.getAPIService(mContext).uploadFile(map);
            call.enqueue(new Callback<FileUploadResponse>() {
                @Override
                public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
                    if (response != null && response.code() == 200) {
                        if (response.body() != null && !TextUtils.isEmpty(response.body().getUrl())) {
                            photo.setRemote_path(response.body().getUrl());
                            photo.setPhotoIdentifier(response.body().getUpdated_id());
                            photoUploadListener.onUploadSuccess(photo);
                        } else {
                            photoUploadListener.onUploadFailed(photo);
                        }
                    } else {
                        photoUploadListener.onUploadFailed(photo);
                    }
                }

                @Override
                public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                    photoUploadListener.onUploadFailed(photo);
                }
            });
        }
    }

}