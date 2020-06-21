//package com.ziploan.team.utils;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.text.TextUtils;
//
//import com.amazonaws.ClientConfiguration;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
//
//import java.io.File;
//import java.net.URL;
//import java.util.Date;
//
///**
// * This class contains methods to upload files to server.
// * Created by Ziploan-Nitesh on 3/1/2017.
// */
//
//public class AmazonStorage {
//    public static final String MY_ACCESS_KEY_ID = "";
//    public static final String MY_SECRET_KEY = "";
//
//    /**Need to change these at time of release**/
//    public static final String BUCKET = BucketType.MY_BUCKET_UAT.getLabel();
//
//    public static final String PRODUCTION_FILE_BUCKET_FOLDER = "Verification_Data";
//    public static final String FILE_BUCKET_FOLDER_ASSET = "Asset_Data";
//    public static final String FILE_BUCKET_FOLDER_ASSET_CHANGE_DOCUMET = "Asset_Data/change_request";
//    public static final String DEFAULT_CONTENT_TYPE = "application/pdf";
//    public static final Date DEFAULT_EXPIRATION_TIME = new Date(System.currentTimeMillis() + 3600000 * 24 * 6);
//    public static final String CONTENT_TYPE_APP_CATION_PDF = "application/pdf";
//    public static final String CONTENT_TYPE_IMAGE_JPEG = "image/jpeg";
//    private static AmazonStorage _instance;
//    private String mBucket;
//    private String mFolder;
//    private String mContentType;
//    private Date mExpiration = DEFAULT_EXPIRATION_TIME;
//
//    private AmazonUploadListener delegate;
//
//    public AmazonStorage() {
//        mBucket = BUCKET;
//        mFolder = PRODUCTION_FILE_BUCKET_FOLDER;
//        mContentType = DEFAULT_CONTENT_TYPE;
//    }
//
//    public static AmazonStorage getInstance(){
//        if(_instance==null){
//            _instance = new AmazonStorage();
//        }
//        return _instance;
//    }
//
//    public String getBucket() {
//        return mBucket;
//    }
//
//    public AmazonStorage setBucket(String mBucket) {
//        this.mBucket = mBucket;
//        return this;
//    }
//
//    public String getFolder() {
//        return mFolder;
//    }
//
//    public AmazonStorage setFolder(String mFolder) {
//        this.mFolder = mFolder;
//        return this;
//    }
//
//    public String getContentType() {
//        return mContentType;
//    }
//
//    public AmazonStorage setContentType(String mContentType) {
//        this.mContentType = mContentType;
//        return this;
//    }
//
//    public Date getExpiration() {
//        return mExpiration;
//    }
//
//    public void setExpiration(Date mExpiration) {
//        this.mExpiration = mExpiration;
//    }
//
//    /**
//     * Upload given file to amazon s3 server in public read mode
//     * @param file File that need to be uploaded
//     * @return     The uploaded string url that does not contains the secret keys
//     */
//    private String uploadGet(File file){
//        ClientConfiguration configuration = new ClientConfiguration();
//        configuration.setConnectionTimeout(120000);
//        configuration.setSocketTimeout(120000);
//        AmazonS3Client s3Client = new AmazonS3Client(new BasicAWSCredentials(MY_ACCESS_KEY_ID, MY_SECRET_KEY),configuration);
//        if(mBucket.equalsIgnoreCase(BucketType.MY_BUCKET_UAT.getLabel())){
//            s3Client.setRegion(com.amazonaws.regions.Region.getRegion(Regions.AP_SOUTH_1));
//            if(!s3Client.doesBucketExist(mBucket)){
//                s3Client.createBucket(mBucket);
//            }
//        }else {
//            s3Client.createBucket(mBucket);
//        }
//        PutObjectRequest por = new PutObjectRequest(mBucket, mFolder+"/"+file.getName(),file);
//        por.withCannedAcl(CannedAccessControlList.PublicRead);
//        s3Client.putObject(por);
//        ResponseHeaderOverrides override = new ResponseHeaderOverrides();
//        override.setContentType(mContentType);
//        GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(mBucket, mFolder+"/"+file.getName());
//        urlRequest.setExpiration(mExpiration);
//        urlRequest.setResponseHeaders(override);
//        URL url = s3Client.generatePresignedUrl(urlRequest);
//        return ZiploanUtil.getUrlWithoutParams(url.toString());
//    }
//
//    public String uploadGet(String filePath){
//        try{
//            if(TextUtils.isEmpty(filePath) || filePath.contains("http")){
//                return filePath;
//            }
//            return uploadGet(new File(filePath));
//        }catch (Exception e){}
//        return "";
//    }
//
//    public void uploadAsync(final String filePath, final int fileIdentifier, final AmazonUploadListener uploadListener){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    if(TextUtils.isEmpty(filePath) || filePath.contains("http")){
//                        uploadListener.onUploadSuccess(filePath,fileIdentifier);
//                    }
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            uploadListener.onUploadStarted(fileIdentifier);
//                        }
//                    });
//                    final String url = uploadGet(new File(filePath));
//                    if(url == null)
//                        throw new Exception("Url is null");
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            uploadListener.onUploadSuccess(url, fileIdentifier);
//                        }
//                    });
//                }catch (final Exception e){
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        @Override
//                        public void run() {
//                            uploadListener.onUploadFailed(e.getMessage(),fileIdentifier);
//                        }
//                    });
//                }
//            }
//        }).start();
//    }
//
//    public interface AmazonUploadListener{
//        void onUploadSuccess(String url, int photoIdentifier);
//        void onUploadFailed(String error, int photoIdentifier);
//        void onUploadStarted(int photoIdentifier);
//    }
//
//    public enum BucketType{
//        MY_BUCKET_UAT("ziploandatauat"),MY_BUCKET_PRODUCTION("ziploandata");
//
//        private final String label;
//
//        BucketType(String ziploandatauat) {
//            this.label = ziploandatauat;
//        }
//        public String getLabel(){
//            return this.label;
//        }
//    }
//}
