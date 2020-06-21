//package com.ziploan.team.collection.application_list;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.databinding.ViewDataBinding;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.ziploan.team.PhotoUploadListener;
//import com.ziploan.team.R;
//import com.ziploan.team.asset_module.FileUploader;
//import com.ziploan.team.asset_module.visits.LoadPhotoListener;
//import com.ziploan.team.collection.model.app_list.Result;
//import com.ziploan.team.collection.model.record_visit.Denominations;
//import com.ziploan.team.collection.model.record_visit.PastVisitResponse;
//import com.ziploan.team.collection.model.record_visit.RecordVisitRequestModel;
//import com.ziploan.team.collection.utils.UIErrorUtils;
//import com.ziploan.team.databinding.RecordVisitCashBinding;
//import com.ziploan.team.utils.AppConstant;
//import com.ziploan.team.utils.ImageUtils;
//import com.ziploan.team.utils.NetworkUtil;
//import com.ziploan.team.utils.PermissionUtil;
//import com.ziploan.team.utils.ZiploanSPUtils;
//import com.ziploan.team.utils.ZiploanUtil;
//import com.ziploan.team.verification_module.base.BaseActivity;
//import com.ziploan.team.verification_module.caching.DatabaseManger;
//import com.ziploan.team.verification_module.verifyekyc.PhotosAdapter;
//import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
//import com.ziploan.team.webapi.APIExecutor;
//
//import java.io.File;
//import java.text.Format;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.TimeZone;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.ResponseData;
//
//public class RecordVisitCash extends BaseActivity implements View.OnClickListener , PhotosAdapter.PhotosAdapterListener, LoadPhotoListener{
//    private static final Integer CASH_MODE = 0;
//    private ArrayList<ZiploanPhoto> arrPhotos = null;
//    private PhotosAdapter adapter;
//    private String mLoanNumber;
//    private RecordVisitCashBinding recordVisitCashBinding;
//    private static final int MORE_AMOUNT = 500;
//
//    private AlertDialog itemListDialog;
//    private LoadPhotoListener loadPhotoListener;
//    protected Uri capturedImageUri;
//    protected String mCurrentPhotoPath;
//    private Result result;
//    private String mFileUrl;
//    private Dialog dialog;
//    String place;
//    String ptp_status;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.record_visit_cash;
//    }
//
//    public static void start(Activity mContext, Bundle bundle) {
//        Intent intent = new Intent(mContext, RecordVisitCash.class);
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
//        setTitle(getString(R.string.record_visit_cash));
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
//
//
//
//    private void setBusinessPhotosViews(List<ZiploanPhoto> arrImages) {
//        arrPhotos.clear();
//        arrPhotos.addAll(arrImages);
//        adapter = new PhotosAdapter(mContext, arrPhotos, this);
//        recordVisitCashBinding.rvPhotos.setVisibility(View.VISIBLE);
//        recordVisitCashBinding.takePhoto.setVisibility(View.GONE);
//        recordVisitCashBinding.rvPhotos.setLayoutManager(new GridLayoutManager(mContext, 3));
//        recordVisitCashBinding.rvPhotos.setAdapter(adapter);
//    }
//
//    @Override
//    public void getImages(List<ZiploanPhoto> arrImages, int sourceType) {
//        setBusinessPhotosViews(arrImages);
//    }
//
//    @Override
//    public void processingImages() {
//    }
//
//    @Override
//    public void deletePhoto(int position, int index) {
//        if(arrPhotos.get(position).getUpload_status() == AppConstant.UploadStatus.UPLOADING_SUCCESS){
//            FileUploader.getInstance(mContext).deleteFile(arrPhotos.get(position),AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO,mLoanNumber);
//        }
//        mFileUrl = null;
//        arrPhotos.remove(position);
//        adapter.notifyItemRemoved(position);
//        recordVisitCashBinding.rvPhotos.setVisibility(View.GONE);
//        recordVisitCashBinding.takePhoto.setVisibility(View.VISIBLE);
//
//    }
//
//    @Override
//    public void retryUpload(int position, int index) {
//        uploadFiles(arrPhotos.subList(position, position + 1));
//    }
//
//    @Override
//    public void openCameraGalleyOptions(int position, int index, boolean multi_selection) {
//        openCameraGalleryOptions(multi_selection);
//    }
//
//    private void uploadFiles(List<ZiploanPhoto> arrImages) {
//        FileUploader.getInstance(mContext).upload(AppConstant.FileType.COLLECTION, arrImages, AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, result.getIdentifier(), new PhotoUploadListener() {
//            @Override
//            public void onUploadSuccess(ZiploanPhoto photo) {
//                photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_SUCCESS);
//                adapter.notifyItemChanged(arrPhotos.indexOf(photo));
//                mFileUrl = photo.getRemote_path();
//            }
//
//            @Override
//            public void onUploadFailed(ZiploanPhoto photo) {
//                photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_FAILED);
//                adapter.notifyItemChanged(arrPhotos.indexOf(photo));
//                mFileUrl = null;
//            }
//
//            @Override
//            public void onUploadStarted(ZiploanPhoto photo) {
//                photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_STARTED);
//                adapter.notifyItemChanged(arrPhotos.indexOf(photo));
//                mFileUrl = null;
//            }
//        });
//    }
//
//    protected void openCameraGalleryOptions(final boolean multi_selection){
//        CharSequence[] items = new CharSequence[]{"Capture With Camera","Choose From Gallery"};
//        itemListDialog = new AlertDialog.Builder(mContext).setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                switch (i){
//                    case 0:
//                        if(PermissionUtil.checkCameraPermission(mContext)){
//                            openCamera(RecordVisitCash.this);
//                        }else {
//                            PermissionUtil.requestCameraPermission(RecordVisitCash.this);
//                        }
//                        break;
//                    case 1:
//                        if(PermissionUtil.checkStoragePermission(mContext)){
//                            openGallery(RecordVisitCash.this,multi_selection);
//                        }else {
//                            PermissionUtil.requestStoragePermission(RecordVisitCash.this);
//                        }
//                        break;
//                }
//                itemListDialog.dismiss();
//            }
//        }).show();
//    }
//
//    private void openGallery(LoadPhotoListener photoListener , boolean multi_selection) {
//        loadPhotoListener = photoListener;
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        if(Build.VERSION.SDK_INT>=18 && multi_selection)
//            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), AppConstant.GALLERY_REQUEST);
//    }
//
//    private void openCamera(LoadPhotoListener photoListener) {
//        loadPhotoListener = photoListener;
//        capturedImageUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImageFile());
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
//        startActivityForResult(cameraIntent, AppConstant.CAMERA_REQUEST);
//    }
//
//    private File createImageFile(){
//        try{
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.ENGLISH).format(new Date());
//            String imageFileName = "JPEG_" + timeStamp + "_";
//            File storageDir = Environment.getExternalStoragePublicDirectory(
//                    Environment.DIRECTORY_PICTURES);
//            File image = File.createTempFile(
//                    imageFileName,  // prefix
//                    ".jpg",         // suffix
//                    storageDir      // directory
//            );
//            mCurrentPhotoPath = "file://" + image.getAbsolutePath();
//            return image;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case PermissionUtil.CAMERA_REQUEST_CODE:
//                if(PermissionUtil.checkCameraPermission(mContext)){
//                    openCamera(RecordVisitCash.this);
//                }
//                break;
//            case PermissionUtil.STORAGE_REQUEST_CODE:
//                if(PermissionUtil.checkStoragePermission(mContext)){
//                    openGallery(RecordVisitCash.this, true);
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
//        if(resultCode == Activity.RESULT_OK){
//            final List<ZiploanPhoto> arrList  =  new ArrayList<>();
//            switch (requestCode){
//                case AppConstant.CAMERA_REQUEST:
//                    arrList.add(new ZiploanPhoto(ImageUtils.compressImage(mContext,capturedImageUri, null),AppConstant.MediaType.IMAGE));
//                    loadPhotoListener.getImages(arrList,AppConstant.ImageSourceType.CAMREA);
//                    break;
//                case AppConstant.GALLERY_REQUEST:
//                    if(data.getData()!=null){
//                        arrList.add(new ZiploanPhoto(ImageUtils.compressImage(mContext,data.getData(), null),AppConstant.MediaType.IMAGE));
//                        loadPhotoListener.getImages(arrList,AppConstant.ImageSourceType.GALLERY);
//                    }else if(data.getClipData()!=null){
//                        loadPhotoListener.processingImages();
//                        ImageUtils.asyncCompressMultipleImage(mContext,data.getClipData(), new ImageUtils.OnCompressDone() {
//                            @Override
//                            public void onSuccess(List<String> uris) {
//                                for (int i=0;i<uris.size();i++){
//                                    arrList.add(new ZiploanPhoto(uris.get(i),AppConstant.MediaType.IMAGE));
//                                }
//                                loadPhotoListener.getImages(arrList,AppConstant.ImageSourceType.GALLERY);
//                            }
//                        });
//                    }
//                    break;
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    protected void onViewMapped(ViewDataBinding views) {
//        recordVisitCashBinding = (RecordVisitCashBinding)views;
//        recordVisitCashBinding.recordSaveCash.setOnClickListener(this);
//        recordVisitCashBinding.minus2000.setOnClickListener(this);
//        recordVisitCashBinding.plus2000.setOnClickListener(this);
//        recordVisitCashBinding.minus500.setOnClickListener(this);
//        recordVisitCashBinding.plus500.setOnClickListener(this);
//        recordVisitCashBinding.minus200.setOnClickListener(this);
//        recordVisitCashBinding.plus200.setOnClickListener(this);
//        recordVisitCashBinding.minus100.setOnClickListener(this);
//        recordVisitCashBinding.plus100.setOnClickListener(this);
//        recordVisitCashBinding.minus50.setOnClickListener(this);
//        recordVisitCashBinding.plus50.setOnClickListener(this);
//        recordVisitCashBinding.minus20.setOnClickListener(this);
//        recordVisitCashBinding.plus20.setOnClickListener(this);
//        recordVisitCashBinding.minus10.setOnClickListener(this);
//        recordVisitCashBinding.plus10.setOnClickListener(this);
//        recordVisitCashBinding.minus5.setOnClickListener(this);
//        recordVisitCashBinding.plus5.setOnClickListener(this);
//        recordVisitCashBinding.minus2.setOnClickListener(this);
//        recordVisitCashBinding.plus2.setOnClickListener(this);
//        recordVisitCashBinding.minus1.setOnClickListener(this);
//        recordVisitCashBinding.plus1.setOnClickListener(this);
//        recordVisitCashBinding.takePhoto.setOnClickListener(this);
//        if(getIntent().getExtras() != null){
//            result =(Result) getIntent().getSerializableExtra(AppConstant.Key.LOAN_REQUEST_DATA);
//        }
//        if(result.getAmountOverdue() != null) {
//            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
//            recordVisitCashBinding.expectedAmount.setText(format.format(Double.parseDouble(result.getAmountOverdue())));
//        }
//        recordVisitCashBinding.receiptBox.setText(result.getMobileNumber());
//        ArrayList<String> placeData = new ArrayList<>();
//        placeData.add("Select Place");
//        placeData.add("Business");
//        placeData.add("House");
//        placeData.add("3rd place");
//
//        final ArrayList<String> ptpData = new ArrayList<>();
//        ptpData.add("Select PTP status");
//        ptpData.add("Paid");
//        ptpData.add("Partial payment");
//        ptpData.add("Broken");
//
//        UIErrorUtils.showSpinnerWithStringArray(this,recordVisitCashBinding.placeSp,placeData);
//        UIErrorUtils.showSpinnerWithStringArray(this,recordVisitCashBinding.ptpSp,ptpData);
//
//        recordVisitCashBinding.placeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
//        recordVisitCashBinding.ptpSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.record_save_cash:
//                if(validate()){
//                    UIErrorUtils.hideSoftKeyboard(this);
//                    submitPersonMetCash();
//                }
//                break;
//            case R.id.take_photo:
//                openCameraGalleryOptions(false);
//                break;
//            case R.id.minus_2000:
//                minus(recordVisitCashBinding.qty2000,recordVisitCashBinding.minus2000,recordVisitCashBinding.plus2000);
//                break;
//            case R.id.plus_2000:
//                plus(recordVisitCashBinding.qty2000,recordVisitCashBinding.minus2000,recordVisitCashBinding.plus2000);
//                break;
//            case R.id.minus_500:
//                minus(recordVisitCashBinding.qty500,recordVisitCashBinding.minus500,recordVisitCashBinding.plus500);
//                break;
//            case R.id.plus_500:
//                plus(recordVisitCashBinding.qty500,recordVisitCashBinding.minus500,recordVisitCashBinding.plus500);
//                break;
//            case R.id.minus_200:
//                minus(recordVisitCashBinding.qty200,recordVisitCashBinding.minus200,recordVisitCashBinding.plus200);
//                break;
//            case R.id.plus_200:
//                plus(recordVisitCashBinding.qty200,recordVisitCashBinding.minus200,recordVisitCashBinding.plus200);
//                break;
//            case R.id.minus_100:
//                minus(recordVisitCashBinding.qty100,recordVisitCashBinding.minus100,recordVisitCashBinding.plus100);
//                break;
//            case R.id.plus_100:
//                plus(recordVisitCashBinding.qty100,recordVisitCashBinding.minus100,recordVisitCashBinding.plus100);
//                break;
//
//            case R.id.minus_50:
//                minus(recordVisitCashBinding.qty50,recordVisitCashBinding.minus50,recordVisitCashBinding.plus50);
//                break;
//            case R.id.plus_50:
//                plus(recordVisitCashBinding.qty50,recordVisitCashBinding.minus50,recordVisitCashBinding.plus50);
//                break;
//            case R.id.minus_20:
//                minus(recordVisitCashBinding.qty20,recordVisitCashBinding.minus20,recordVisitCashBinding.plus20);
//                break;
//            case R.id.plus_20:
//                plus(recordVisitCashBinding.qty20,recordVisitCashBinding.minus20,recordVisitCashBinding.plus20);
//                break;
//            case R.id.minus_10:
//                minus(recordVisitCashBinding.qty10,recordVisitCashBinding.minus10,recordVisitCashBinding.plus10);
//                break;
//            case R.id.plus_10:
//                plus(recordVisitCashBinding.qty10,recordVisitCashBinding.minus10,recordVisitCashBinding.plus10);
//                break;
//            case R.id.minus_5:
//                minus(recordVisitCashBinding.qty5,recordVisitCashBinding.minus5,recordVisitCashBinding.plus5);
//                break;
//            case R.id.plus_5:
//                plus(recordVisitCashBinding.qty5,recordVisitCashBinding.minus5,recordVisitCashBinding.plus5);
//                break;
//
//            case R.id.minus_2:
//                minus(recordVisitCashBinding.qty2,recordVisitCashBinding.minus2,recordVisitCashBinding.plus2);
//                break;
//            case R.id.plus_2:
//                plus(recordVisitCashBinding.qty2,recordVisitCashBinding.minus2,recordVisitCashBinding.plus2);
//                break;
//            case R.id.minus_1:
//                minus(recordVisitCashBinding.qty1,recordVisitCashBinding.minus1,recordVisitCashBinding.plus1);
//                break;
//            case R.id.plus_1:
//                plus(recordVisitCashBinding.qty1,recordVisitCashBinding.minus1,recordVisitCashBinding.plus1);
//                break;
//            case R.id.success_ok:
//                dialog.dismiss();
//                backToHome();
//                break;
//        }
//    }
//
//    private void minus(TextView textView, ImageView minusImg, ImageView plusImg) {
//        plusImg.setEnabled(true);
//        int qty = Integer.parseInt(textView.getText().toString());
//        if (qty > 0) {
//            qty--;
//            if (qty == 0) {
//                minusImg.setEnabled(false);
//            }
//        }
//        textView.setText(qty + "");
//        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
//        recordVisitCashBinding.totalAmount.setText(format.format(totalCalculate()));
//    }
//
//    private void plus(TextView textView, ImageView minusImg, ImageView plusImg) {
//        minusImg.setEnabled(true);
//        int qty = Integer.parseInt(textView.getText().toString());
//        if(calculate()) {
//            qty++;
//            textView.setText(qty + "");
//            if (!calculate()) {
//                plusImg.setEnabled(false);
//                showToast(this, getString(R.string.emi_amount_large) + " " + MORE_AMOUNT);
//            }
//        } else {
//            showToast(this, getString(R.string.emi_amount_large) + " " + MORE_AMOUNT);
//        }
//        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
//        recordVisitCashBinding.totalAmount.setText(format.format(totalCalculate()));
//    }
//
//    private boolean calculate(){
//        if(result.getAmountOverdue() != null) {
//            double totalAmount = Double.parseDouble(result.getAmountOverdue());
//            int currentAmount = getindividualAmount(recordVisitCashBinding.qty2000,2000) + getindividualAmount(recordVisitCashBinding.qty500,500)
//                    + getindividualAmount(recordVisitCashBinding.qty200,200) + getindividualAmount(recordVisitCashBinding.qty100,100)
//                    + getindividualAmount(recordVisitCashBinding.qty50,50) + getindividualAmount(recordVisitCashBinding.qty20,20)
//                    + getindividualAmount(recordVisitCashBinding.qty10,10) + getindividualAmount(recordVisitCashBinding.qty5,5)
//                    + getindividualAmount(recordVisitCashBinding.qty2,2) + getindividualAmount(recordVisitCashBinding.qty1,1) ;
//
//            return currentAmount <= (totalAmount) || currentAmount <= (totalAmount + 500);
//        } else {
//            return false;
//        }
//    }
//
//    private boolean canSubmit(){
//        if(result.getAmountOverdue() != null) {
//            double totalAmount = Double.parseDouble(result.getAmountOverdue());
//            int currentAmount = getindividualAmount(recordVisitCashBinding.qty2000,2000) + getindividualAmount(recordVisitCashBinding.qty500,500)
//                    + getindividualAmount(recordVisitCashBinding.qty200,200) + getindividualAmount(recordVisitCashBinding.qty100,100)
//                    + getindividualAmount(recordVisitCashBinding.qty50,50) + getindividualAmount(recordVisitCashBinding.qty20,20)
//                    + getindividualAmount(recordVisitCashBinding.qty10,10) + getindividualAmount(recordVisitCashBinding.qty5,5)
//                    + getindividualAmount(recordVisitCashBinding.qty2,2) + getindividualAmount(recordVisitCashBinding.qty1,1) ;
//
//            return currentAmount <= (totalAmount) || currentAmount <= (totalAmount + 500);
//        } else {
//            return false;
//        }
//    }
//
//    private int totalCalculate(){
//        int currentAmount = getindividualAmount(recordVisitCashBinding.qty2000,2000) + getindividualAmount(recordVisitCashBinding.qty500,500)
//                + getindividualAmount(recordVisitCashBinding.qty200,200) + getindividualAmount(recordVisitCashBinding.qty100,100)
//                + getindividualAmount(recordVisitCashBinding.qty50,50) + getindividualAmount(recordVisitCashBinding.qty20,20)
//                + getindividualAmount(recordVisitCashBinding.qty10,10) + getindividualAmount(recordVisitCashBinding.qty5,5)
//                + getindividualAmount(recordVisitCashBinding.qty2,2) + getindividualAmount(recordVisitCashBinding.qty1,1) ;
//        return currentAmount;
//    }
//
//    private int getindividualAmount(TextView qty, int amount){
//        return Integer.parseInt(qty.getText().toString()) * amount;
//    }
//
//    private void reset(){
//        UIErrorUtils.errorMethod(recordVisitCashBinding.personMetLayout,getString(R.string.person_met_empty),false);
//        UIErrorUtils.errorMethod(recordVisitCashBinding.receiptLayout,getString(R.string.person_met_empty),false);
//    }
//
//    private boolean validate() {
//        reset();
//        if (!TextUtils.isEmpty(recordVisitCashBinding.personMet.getText())) {
//            if (canSubmit()) {
//                if(!TextUtils.isEmpty(recordVisitCashBinding.receiptBox.getText()) && recordVisitCashBinding.receiptBox.length() == 10){
//                    return true;
//                } else {
//                    UIErrorUtils.errorMethod(recordVisitCashBinding.receiptLayout, getString(R.string.person_met_empty), true);
//                    UIErrorUtils.scrollToView(recordVisitCashBinding.mainScroll, recordVisitCashBinding.receiptBox);
//                }
//            } else {
//                showToast(this, getString(R.string.emi_amount_large) + " " + MORE_AMOUNT);
//            }
//        } else {
//            UIErrorUtils.errorMethod(recordVisitCashBinding.personMetLayout, getString(R.string.person_met_empty), true);
//            UIErrorUtils.scrollToView(recordVisitCashBinding.mainScroll, recordVisitCashBinding.personMet);
//        }
//        return false;
//    }
//
//    private void submitPersonMetCash() {
//        postData();
//    }
//
//    private void postData() {
//        showProgressDialog();
//        RecordVisitRequestModel recordVisitRequestModel = new RecordVisitRequestModel();
//        recordVisitRequestModel.setAmount(totalCalculate());
//        recordVisitRequestModel.setBankName(null);
//
//        if(!TextUtils.isEmpty(recordVisitCashBinding.comment.getText().toString())) {
//            recordVisitRequestModel.setComment(recordVisitCashBinding.comment.getText().toString());
//        } else {
//            recordVisitRequestModel.setComment(null);
//        }
//
//        Denominations denominations = new Denominations(recordVisitCashBinding.qty2000.getText().toString(),recordVisitCashBinding.qty500.getText().toString(),recordVisitCashBinding.qty200.getText().toString(),recordVisitCashBinding.qty100.getText().toString(),
//                recordVisitCashBinding.qty50.getText().toString(),recordVisitCashBinding.qty20.getText().toString(),recordVisitCashBinding.qty10.getText().toString(),recordVisitCashBinding.qty5.getText().toString(),recordVisitCashBinding.qty2.getText().toString(),recordVisitCashBinding.qty1.getText().toString());
//        recordVisitRequestModel.setDenominations(denominations);
//        recordVisitRequestModel.setIsPersonMet(true);
//        recordVisitRequestModel.setMode(CASH_MODE);
//        recordVisitRequestModel.setPersonMet(recordVisitCashBinding.personMet.getText().toString());
//        recordVisitRequestModel.setRefrenceNumber(recordVisitCashBinding.reference.getText().toString());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.ENGLISH);
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        recordVisitRequestModel.setValueDate(sdf.format(new Date()));
//        recordVisitRequestModel.setLat(ZiploanSPUtils.getInstance(mContext).getTempLat());
//        recordVisitRequestModel.setLng((ZiploanSPUtils.getInstance(mContext).getTempLng()));
//        recordVisitRequestModel.setmMobile(recordVisitCashBinding.receiptBox.getText().toString());
//        recordVisitRequestModel.setPlace(place);
//        recordVisitRequestModel.setPtpStatus(ptp_status);
//        if (NetworkUtil.getConnectivityStatus(this) != 0) {
//            Call<PastVisitResponse> call = APIExecutor.getAPIServiceSerializeNull(mContext).postPastVisits(result.getLoanApplicationNumber(), recordVisitRequestModel,AppConstant.Key.VIEW_VALUE);
//            call.enqueue(new Callback<PastVisitResponse>() {
//                @Override
//                public void onResponse(Call<PastVisitResponse> call, ResponseData<PastVisitResponse> response) {
//                    hideProgressDialog();
//                    if (response != null && response.isSuccessful() && response.body() != null
//                            && response.body().getmStatus() != null
//                            && response.body().getmStatus().equalsIgnoreCase("success")) {
//                        if(response.body().getmResponse() != null && response.body().getmResponse().getmInvoiceNumber() != null)
//                            showSuccessDialogue(getString(R.string.recroeded_succesfully) + "\n" +  "Invoice number : " + response.body().getmResponse().getmInvoiceNumber());
//                        else {
//                            showToast(RecordVisitCash.this, response.body().getmStatusMessage());
//                        }
//                    } else {
//                        if (response != null) {
//                            if (response.body() != null) {
//                                showToast(RecordVisitCash.this, response.body().getmStatusMessage());
//                            }
//                        }
//                        checkTokenValidity(response);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<PastVisitResponse> call, Throwable t) {
//                    hideProgressDialog();
//                    showToast(RecordVisitCash.this, getString(R.string.something_went_wrong));
//                }
//            });
//        } else{
//            hideProgressDialog();
//            DatabaseManger.getInstance().saveRecordVisitData(recordVisitRequestModel,result.getLoanApplicationNumber());
//            showSuccessDialogue(getString(R.string.recroeded_succesfully));
//        }
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
//    private void backToHome() {
//        if(dialog.isShowing())
//            dialog.dismiss();
//        ApplicationListActivity.start(this,new Bundle());
//    }
//
//}
