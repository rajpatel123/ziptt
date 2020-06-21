//package com.ziploan.team.collection.application_list;
//
//import android.app.Activity;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.databinding.ViewDataBinding;
//import android.graphics.Paint;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Handler;
//import android.os.Message;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.design.widget.TextInputEditText;
//import android.support.design.widget.TextInputLayout;
//import android.support.v4.content.FileProvider;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.AppCompatTextView;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.Toolbar;
//
//import com.google.gson.Gson;
//import com.ziploan.team.App;
//import com.ziploan.team.PhotoUploadListener;
//import com.ziploan.team.asset_module.FileUploader;
//import com.ziploan.team.asset_module.visits.LoadPhotoListener;
//import com.ziploan.team.collection.model.app_list.Result;
//import com.ziploan.team.collection.model.error.ErrorResponseModel;
//import com.ziploan.team.collection.model.record_visit.Denominations;
//import com.ziploan.team.collection.model.record_visit.PastVisitResponse;
//import com.ziploan.team.collection.model.record_visit.RecordVisitRequestModel;
//import com.ziploan.team.collection.utils.UIErrorUtils;
//import com.ziploan.team.databinding.RecordVisitChequeBinding;
//
//import android.text.InputFilter;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.ziploan.team.R;
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
//import java.io.IOException;
//import java.text.Format;
//import java.text.NumberFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.TimeZone;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.ResponseData;
//
//public class RecordVisitCheque extends BaseActivity implements View.OnClickListener , PhotosAdapter.PhotosAdapterListener, LoadPhotoListener,DatePickerDialog.OnDateSetListener,AdapterView.OnItemClickListener {
//
//    private static final Integer CHEQUE_MODE = 7;
//    private RecordVisitChequeBinding recordVisitChequeBinding;
//    private ArrayList<ZiploanPhoto> arrPhotos = null;
//    private PhotosAdapter adapter;
//    private String mLoanNumber;
//    private Result result;
//    private String date;
//    private int year;
//    private int month;
//    private int day;
//
//    private AlertDialog itemListDialog;
//    private LoadPhotoListener loadPhotoListener;
//    protected Uri capturedImageUri;
//    protected String mCurrentPhotoPath;
//    private String mFileUrl;
//    private Dialog dialog;
//    private ArrayAdapter<com.ziploan.team.collection.model.bank_names.ResponseData> arrayAdapter;
//    private List<com.ziploan.team.collection.model.bank_names.ResponseData> bankData;
//    private String selectedBankCode;
//    String place;
//    String ptp_status;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.record_visit_cheque;
//    }
//
//    public static void start(Activity mContext, Bundle bundle) {
//        Intent intent = new Intent(mContext, RecordVisitCheque.class);
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
//        setTitle(getString(R.string.record_visit_cheque));
//        arrPhotos = new ArrayList<>();
//        mLoanNumber = getIntent().getStringExtra("id");
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
//    @Override
//    protected void onViewMapped(ViewDataBinding views) {
//        recordVisitChequeBinding = (RecordVisitChequeBinding) views;
//        recordVisitChequeBinding.submitCheck.setOnClickListener(this);
//        recordVisitChequeBinding.takePhoto.setOnClickListener(this);
//        recordVisitChequeBinding.calenderIcon.setOnClickListener(this);
//        recordVisitChequeBinding.date.setOnClickListener(this);
//        if (getIntent().getExtras() != null) {
//            result = (Result) getIntent().getSerializableExtra(AppConstant.Key.LOAN_REQUEST_DATA);
//        }
//
//        UIErrorUtils.getBankAdapter(this, recordVisitChequeBinding.bankName);
//
////        recordVisitChequeBinding.bankName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                com.ziploan.team.collection.model.bank_names.ResponseData response = ((com.ziploan.team.collection.model.bank_names.ResponseData) adapterView.getItemAtPosition(i));
////                if (response != null && !response.getBankDisplayName().equalsIgnoreCase("Select Bank"))
////                    selectedBankCode = ((com.ziploan.team.collection.model.bank_names.ResponseData) adapterView.getItemAtPosition(i)).getBankCode();
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> adapterView) {
////
////            }
////        });
//        recordVisitChequeBinding.bankName.setOnItemClickListener(this);
//
//        InputFilter[] editFilters = recordVisitChequeBinding.ifsc.getFilters();
//        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
//        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
//        newFilters[editFilters.length] = new InputFilter.AllCaps();
//
//        recordVisitChequeBinding.ifsc.setFilters(newFilters);
//        recordVisitChequeBinding.receiptBox.setText(result.getMobileNumber());
//
//        ArrayList<String> placeData = new ArrayList<>();
//        placeData.add("Select Place");
//        placeData.add("Business");
//        placeData.add("House");
//        placeData.add("3rd place");
//
//        ArrayList<String> ptpData = new ArrayList<>();
//        ptpData.add("Select PTP status");
//        ptpData.add("Paid");
//        ptpData.add("Partial payment");
//        ptpData.add("Broken");
//
//        UIErrorUtils.showSpinnerWithStringArray(this,recordVisitChequeBinding.placeSp,placeData);
//        UIErrorUtils.showSpinnerWithStringArray(this,recordVisitChequeBinding.ptpSp,ptpData);
//
//        recordVisitChequeBinding.placeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
//        recordVisitChequeBinding.ptpSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.submit_check:
//                if(validate()){
//                    postData();
//                }
//                break;
//            case R.id.take_photo:
//                openCameraGalleryOptions(false);
//                break;
//            case R.id.calender_icon:
//                selectDate();
//                break;
//            case R.id.date:
//                selectDate();
//                break;
//            case R.id.success_ok:
//                dialog.dismiss();
//                backToHome();
//                break;
//
//        }
//    }
//
//    private boolean validate() {
//        reset();
//        if (!TextUtils.isEmpty(recordVisitChequeBinding.personMet.getText())) {
//            if (!TextUtils.isEmpty(selectedBankCode)) {
//                if (!TextUtils.isEmpty(recordVisitChequeBinding.checqueNum.getText()) && recordVisitChequeBinding.checqueNum.getText().toString().length() == 6) {
//                    if (!TextUtils.isEmpty(recordVisitChequeBinding.amount.getText())) {
////                        if(result.getAmountOverdue() != null
////                                && Double.parseDouble(recordVisitChequeBinding.amount.getText().toString()) <= Double.parseDouble(result.getAmountOverdue())) {
//                        if (!TextUtils.isEmpty(recordVisitChequeBinding.date.getText())) {
////                                if(!TextUtils.isEmpty(recordVisitChequeBinding.ifsc.getText())
////                                            && UIErrorUtils.validateIFSC(recordVisitChequeBinding.ifsc.getText().toString())
////                                        && recordVisitChequeBinding.ifsc.getText().toString().length() == 11){
//                            if (NetworkUtil.getConnectivityStatus(this) != 0) {
//                                if (!TextUtils.isEmpty(mFileUrl)) {
//                                    if(recordVisitChequeBinding.receiptBox.getText().length() == 10) {
//                                        return true;
//                                    }  else {
//                                        showToast(this, getString(R.string.invalid_mobile));
//                                    }
//                                } else {
//                                    showToast(this, getString(R.string.upload_check_image));
//                                }
//                            } else {
//                                return true;
//                            }
////                                } else {
////                                    enableError(recordVisitChequeBinding.ifscLayout);
////                                    recordVisitChequeBinding.ifscLayout.setError(getString(R.string.invalid_field));
////                                    ZiploanUtil.scrollToView(recordVisitChequeBinding.mainScroll,recordVisitChequeBinding.ifsc);
////                                }
//                        } else {
//                            enableError(recordVisitChequeBinding.dateLayout);
//                            recordVisitChequeBinding.dateLayout.setError(getString(R.string.invalid_field));
//                            ZiploanUtil.scrollToView(recordVisitChequeBinding.mainScroll, recordVisitChequeBinding.date);
//                        }
////                        } else {
////                            enableError(recordVisitChequeBinding.amountLayout);
////                            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
////                            recordVisitChequeBinding.amountLayout.setError("Amount should be " + format.format(Double.parseDouble(result.getAmountOverdue())));
////                            ZiploanUtil.scrollToView(recordVisitChequeBinding.mainScroll,recordVisitChequeBinding.amount);
////                        }
//                    } else {
//                        enableError(recordVisitChequeBinding.amountLayout);
//                        recordVisitChequeBinding.amountLayout.setError(getString(R.string.invalid_field));
//                        ZiploanUtil.scrollToView(recordVisitChequeBinding.mainScroll, recordVisitChequeBinding.amount);
//                    }
//                } else {
//                    enableError(recordVisitChequeBinding.chequeLayout);
//                    recordVisitChequeBinding.chequeLayout.setError(getString(R.string.invalid_field));
//                    ZiploanUtil.scrollToView(recordVisitChequeBinding.mainScroll, recordVisitChequeBinding.checqueNum);
//                }
//            } else {
//                enableError(recordVisitChequeBinding.bankNameLayout);
//                recordVisitChequeBinding.bankNameLayout.setError(getString(R.string.invalid_bank_code));
//                ZiploanUtil.scrollToView(recordVisitChequeBinding.mainScroll,recordVisitChequeBinding.bankName);
//            }
//        } else {
//            enableError(recordVisitChequeBinding.personMetLayout);
//            recordVisitChequeBinding.personMetLayout.setError(getString(R.string.invalid_field));
//            ZiploanUtil.scrollToView(recordVisitChequeBinding.mainScroll, recordVisitChequeBinding.personMet);
//        }
//        return false;
//    }
//
//    private void enableError(TextInputLayout textInputLayout){
//        textInputLayout.setErrorEnabled(true);
//    }
//
//    private void reset(){
//        recordVisitChequeBinding.personMetLayout.setErrorEnabled(false);
//        recordVisitChequeBinding.bankNameLayout.setErrorEnabled(false);
//        recordVisitChequeBinding.chequeLayout.setErrorEnabled(false);
//        recordVisitChequeBinding.amountLayout.setErrorEnabled(false);
//        recordVisitChequeBinding.dateLayout.setErrorEnabled(false);
//        recordVisitChequeBinding.ifscLayout.setErrorEnabled(false);
//
//        recordVisitChequeBinding.personMetLayout.setError(null);
//        recordVisitChequeBinding.bankNameLayout.setError(null);
//        recordVisitChequeBinding.chequeLayout.setError(null);
//        recordVisitChequeBinding.amountLayout.setError(null);
//        recordVisitChequeBinding.dateLayout.setError(null);
//        recordVisitChequeBinding.ifscLayout.setError(null);
//    }
//
//
//    private void setBusinessPhotosViews(List<ZiploanPhoto> arrImages) {
//        arrPhotos.clear();
//        arrPhotos.addAll(arrImages);
//        adapter = new PhotosAdapter(mContext, arrPhotos, this);
//        recordVisitChequeBinding.rvPhotos.setVisibility(View.VISIBLE);
//        recordVisitChequeBinding.takePhoto.setVisibility(View.GONE);
//        recordVisitChequeBinding.rvPhotos.setLayoutManager(new GridLayoutManager(mContext, 3));
//        recordVisitChequeBinding.rvPhotos.setAdapter(adapter);
//    }
//
//    @Override
//    public void getImages(List<ZiploanPhoto> arrImages, int sourceType) {
//        setBusinessPhotosViews(arrImages);
//        if(UIErrorUtils.isNetworkConnected(this)){
//            uploadFiles(arrImages);
//        } else {
//            mFileUrl = arrImages.get(0).getPhotoPath();
//        }
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
//        recordVisitChequeBinding.rvPhotos.setVisibility(View.GONE);
//        recordVisitChequeBinding.takePhoto.setVisibility(View.VISIBLE);
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
//                            openCamera(RecordVisitCheque.this);
//                        }else {
//                            PermissionUtil.requestCameraPermission(RecordVisitCheque.this);
//                        }
//                        break;
//                    case 1:
//                        if(PermissionUtil.checkStoragePermission(mContext)){
//                            openGallery(RecordVisitCheque.this,multi_selection);
//                        }else {
//                            PermissionUtil.requestStoragePermission(RecordVisitCheque.this);
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
//        }catch (Exception e){e.printStackTrace();}
//        return null;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case PermissionUtil.CAMERA_REQUEST_CODE:
//                if(PermissionUtil.checkCameraPermission(mContext)){
//                    openCamera(RecordVisitCheque.this);
//                }
//                break;
//            case PermissionUtil.STORAGE_REQUEST_CODE:
//                if(PermissionUtil.checkStoragePermission(mContext)){
//                    openGallery(RecordVisitCheque.this, false);
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
//    private void postData() {
//        showProgressDialog();
//        RecordVisitRequestModel recordVisitRequestModel = new RecordVisitRequestModel();
//        recordVisitRequestModel.setAmount(Integer.parseInt(recordVisitChequeBinding.amount.getText().toString()));
//        recordVisitRequestModel.setBankName(selectedBankCode);
//        recordVisitRequestModel.setComment(recordVisitChequeBinding.comment.getText().toString());
//        recordVisitRequestModel.setmIdentifier(result.getIdentifier());
//        recordVisitRequestModel.setDenominations(null);
//        recordVisitRequestModel.setIsPersonMet(true);
//        recordVisitRequestModel.setMode(CHEQUE_MODE);
//        recordVisitRequestModel.setPersonMet(recordVisitChequeBinding.personMet.getText().toString());
//        recordVisitRequestModel.setRefrenceNumber(recordVisitChequeBinding.checqueNum.getText().toString());
//        recordVisitRequestModel.setLat(ZiploanSPUtils.getInstance(mContext).getTempLat());
//        recordVisitRequestModel.setLng((ZiploanSPUtils.getInstance(mContext).getTempLng()));
//        recordVisitRequestModel.setmMobile(recordVisitChequeBinding.receiptBox.getText().toString());
//        recordVisitRequestModel.setPlace(place);
//        recordVisitRequestModel.setPtpStatus(ptp_status);
//
//        recordVisitRequestModel.setValueDate(date);
//        recordVisitRequestModel.setmFileUrl(mFileUrl);
//        if (NetworkUtil.getConnectivityStatus(this) != 0) {
//            Call<PastVisitResponse> call = APIExecutor.getAPIServiceSerializeNull(mContext).postPastVisits(result.getLoanApplicationNumber(), recordVisitRequestModel,AppConstant.Key.VIEW_VALUE);
//            call.enqueue(new Callback<PastVisitResponse>() {
//                @Override
//                public void onResponse(Call<PastVisitResponse> call, ResponseData<PastVisitResponse> response) {
//                    hideProgressDialog();
//                    if (response != null && response.isSuccessful() && response.body() != null
//                            && response.body().getmStatus() != null
//                            && response.body().getmStatus().equalsIgnoreCase("success")) {
//                        showSuccessDialogue(getString(R.string.recroeded_succesfully) + "\n" +  "Invoice number : " + response.body().getmResponse().getmInvoiceNumber());
//                    } else {
//                        if (response != null) {
//                            if (response.body() != null) {
//                                showToast(RecordVisitCheque.this, response.body().getmStatusMessage());
//                            }
//                        }
//                        checkTokenValidity(response);
//                    }
//                }
//
//
//                @Override
//                public void onFailure(Call<PastVisitResponse> call, Throwable t) {
//                    hideProgressDialog();
//                    showToast(RecordVisitCheque.this, getString(R.string.something_went_wrong));
//                }
//            });
//        } else {
//            hideProgressDialog();
//            DatabaseManger.getInstance().saveRecordVisitData(recordVisitRequestModel, result.getLoanApplicationNumber());
//            showSuccessDialogue(getString(R.string.recroeded_succesfully));
//        }
//    }
//
//    private void backToHome() {
//        if(dialog.isShowing())
//            dialog.dismiss();
//        ApplicationListActivity.start(this,new Bundle());
//    }
//
//    public void selectDate() {
//        final Calendar c = Calendar.getInstance();
//        year = c.get(Calendar.YEAR);
//        month = c.get(Calendar.MONTH);
//        day = c.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                this, R.style.DialogTheme, this, year, month, day){
//            @Override
//            protected void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                AppCompatTextView date = (AppCompatTextView)getDatePicker().findViewById(getResources().getIdentifier("date_picker_header_date","id","android"));
//                AppCompatTextView year = (AppCompatTextView)getDatePicker().findViewById(getResources().getIdentifier("date_picker_header_year","id","android"));
//
//                year.setTextColor(getResources().getColor(R.color.white));
//                year.setTextSize(30);
//                year.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                year.setGravity(Gravity.CENTER_HORIZONTAL);
//                date.setTextSize(15);
//                year.setPaintFlags(year.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
//            }
//        };
//        c.set(year, month, day - 7);
//
//        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
//        datePickerDialog.show();
//    }
//
//    @Override
//    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//        year  = i;
//        month = i1;
//        day   = i2;
//        Calendar c = Calendar.getInstance();
//        c.set(year,month,day);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//        date = sdf.format(c.getTime());
//        recordVisitChequeBinding.date.setText(new StringBuilder().append(day).append("/").append(month + 1)
//                .append("/").append(year));
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
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        selectedBankCode = ((com.ziploan.team.collection.model.bank_names.ResponseData) adapterView.getItemAtPosition(i)).getBankCode();
//    }
//}
