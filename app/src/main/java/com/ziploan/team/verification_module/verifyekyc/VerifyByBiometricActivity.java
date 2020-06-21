//package com.ziploan.team.verification_module.verifyekyc;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.databinding.ViewDataBinding;
//import android.hardware.usb.UsbManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Toast;
//
//import com.morpho.android.usb.USBManager;
//import com.morpho.morphosmart.sdk.CallbackMask;
//import com.morpho.morphosmart.sdk.CallbackMessage;
//import com.morpho.morphosmart.sdk.CompressionAlgorithm;
//import com.morpho.morphosmart.sdk.DetectionMode;
//import com.morpho.morphosmart.sdk.ErrorCodes;
//import com.morpho.morphosmart.sdk.LatentDetection;
//import com.morpho.morphosmart.sdk.MorphoDevice;
//import com.morpho.morphosmart.sdk.MorphoImage;
//import com.ziploan.team.R;
//import com.ziploan.team.verification_module.base.BaseActivity;
//import com.ziploan.team.verification_module.borrowerslist.BorrowersUnverified;
//import com.ziploan.team.databinding.ActivityVerifyBiometricBinding;
//import com.ziploan.team.utils.AppConstant;
//import com.ziploan.team.utils.ZiploanUtil;
//import com.ziploan.team.verification_module.verifyekyc.morpho.ProcessInfo;
//import com.ziploan.team.webapi.APIExecutor;
//import com.ziploan.team.webapi.ApiResponse;
//import com.ziploan.team.webapi.EkycDetail;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Observable;
//import java.util.Observer;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class VerifyByBiometricActivity extends BaseActivity implements View.OnClickListener, Observer {
//
//    private ActivityVerifyBiometricBinding allViews;
//    private ApplicantListAdapter mAdapter;
//    private List<Applicant> arrApplicant = new ArrayList<>();
//    private EkycDetail applicant;
//    private BorrowersUnverified borrower;
//    private String mFingerPrintData;
//    public static final int REQUEST_CODE = 100;
//    private MorphoDevice morphoDevice;
//    private Handler mHandler = new Handler();
//    private String sensorName;
//    private BroadcastReceiver mUsbReceiver;
//    private String strMessage;
//    private int currentCaptureBitmapId;
//
//    public VerifyByBiometricActivity() {
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_verify_biometric;
//    }
//
//    @Override
//    protected void onViewMapped(final ViewDataBinding views) {
//        allViews = (ActivityVerifyBiometricBinding) views;
//        setListeners();
//        Intent bundle = getIntent();
//        if(bundle!=null){
//            if(bundle.hasExtra(AppConstant.Key.EXTRA_OBJECT)){
//                applicant = bundle.getParcelableExtra(AppConstant.Key.EXTRA_OBJECT);
//                if(applicant!=null && !TextUtils.isEmpty(applicant.getAadhar_number())){
//                    allViews.etAadhaarNo.setText(applicant.getAadhar_number());
//                    allViews.etAadhaarNo.setSelection(applicant.getAadhar_number().length());
//                }
//            }
//            if(bundle.hasExtra(AppConstant.Key.EXTRA_OBJECT1)){
//                borrower = bundle.getParcelableExtra(AppConstant.Key.EXTRA_OBJECT1);
//            }
//        }
//
//        IntentFilter filter = new IntentFilter("com.ziploan.team.USB_ACTION");
//        registerReceiver(mUsbReceiver, filter);
//        mUsbReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Toast.makeText(mContext,"receiver",Toast.LENGTH_SHORT).show();
//                String action = intent.getAction();
//                if ("com.ziploan.team.USB_ACTION".equals(action)) {
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        allViews.btnConnectDevice.setText("Connect Device");
//                    }else {
//                        showAlertInfo("We need USB permission to proceed");
//                    }
//                }
//            }
//        };
//    }
//
//    private void setListeners() {
//        allViews.ivBack.setOnClickListener(this);
//        allViews.btnVerify.setOnClickListener(this);
//        allViews.btnConnectDevice.setOnClickListener(this);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }
//
//    @Override
//    protected void onPause() {
//        resetConnection();
//        super.onPause();
//    }
//
//    private void resetConnection() {
//        if (morphoDevice != null && ProcessInfo.getInstance().isStarted())
//        {
//            morphoDevice.cancelLiveAcquisition();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void start(Context mContext, Bundle bundle) {
//        Intent intent = new Intent(mContext, VerifyByBiometricActivity.class);
//        intent.putExtras(bundle);
//        mContext.startActivity(intent);
//    }
//
//    public void enumerate()
//    {
//        Integer nbUsbDevice = new Integer(0);
//        int ret = morphoDevice.initUsbDevicesNameEnum(nbUsbDevice);
//        if (ret == ErrorCodes.MORPHO_OK) {
//            if (nbUsbDevice > 0) {
//                sensorName = morphoDevice.getUsbDeviceName(0);
//                int ret1 = morphoDevice.openUsbDevice(sensorName, 0);
//                if (ret1 != ErrorCodes.MORPHO_OK)
//                {
//                    Toast.makeText(mContext, "Please wait and try",Toast.LENGTH_SHORT).show();
//                }else {
//                    morphoDevice.resumeConnection(30,this);
//                    morphoDeviceGetImage(this);
//                }
//            } else {
//                showAlertInfo("The device is not connected, or you have not accepted USB permissions.");
//            }
//        }
//        else {
//            resetConnection();
//            Toast.makeText(mContext, "Please wait and try",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.ivBack:
//                onBackPressed();
//                break;
//            case R.id.btn_verify:
//                String aadhaarNo = allViews.etAadhaarNo.getText().toString().trim();
//                if(ZiploanUtil.isAadhaarValid(aadhaarNo)){
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(AppConstant.EXTRA_EKYC_VER_STATUS,0);//TEMP STATIC VALUE will be change during final integration
//                    EKYCVerificationStatusActivity.start(VerifyByBiometricActivity.this, bundle,REQUEST_CODE);
//                    /*if(!TextUtils.isEmpty(mFingerPrintData))
//                        verifyData(aadhaarNo);
//                    else
//                        showAlertInfo("Finger print has not been scanned successfully, Please try again");
//*/
//                }else {
//                    showFieldError(allViews.etAadhaarNo,"Please enter valid aadhaar number.");
//                }
//                break;
//            case R.id.btn_connect_device:
//
//                if(USBManager.getInstance().isDevicesHasPermission())
//                {
//                    ProcessInfo.getInstance().setCommandBioStart(true);
//                    ProcessInfo.getInstance().setStarted(true);
//                    ProcessInfo.getInstance().setStarted(true);
//                    morphoDevice = new MorphoDevice();
//                    enumerate();
//                }else {
//                    USBManager.getInstance().initialize(this, "com.ziploan.team.USB_ACTION");
//                }
//                break;
//        }
//    }
//
//    private void verifyData(String aadhaarNo) {
//
//        showProgressDialog();
//        GenerateBiometricAadhaarEKYCRequest request = new GenerateBiometricAadhaarEKYCRequest();
//        request.setLoan_request_id(borrower.getLoan_request_id());
//        request.setApplicant_type(applicant.getApplicant_type());
//        request.setAadhar_no(aadhaarNo);
//        request.setFingerprint_image(mFingerPrintData);
//        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).generateBiometricAadhaarEKYCResponse(request);
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                hideProgressDialog();
//                if(response!=null && response.body()!=null && !TextUtils.isEmpty(response.body().getResult())){
//                    String result = response.body().getResult();
//                    if(result.equalsIgnoreCase("1")){
//                        Bundle bundle = new Bundle();
//                        EKYCVerificationStatusActivity.start(mContext, bundle);
//                    }else {
//                        showAlertInfo(getString(R.string.aadhaar_not_matched));
//                    }
//                }else {
//                    showAlertInfo(getString(R.string.server_not_responding));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                hideProgressDialog();
//                showAlertInfo(t.getMessage()!=null?t.getMessage():getString(R.string.server_not_responding));
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == REQUEST_CODE){
//            if(resultCode == RESULT_OK){
//                setResult(RESULT_OK);
//                finish();
//            }else {
//                mFingerPrintData = null;
//                allViews.imageScanStatus.setImageResource(R.mipmap.thumb_print_unsel);
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public static void start(Activity mContext, Bundle bundle, int requestCode) {
//        Intent intent = new Intent(mContext, VerifyByBiometricActivity.class);
//        intent.putExtras(bundle);
//        mContext.startActivityForResult(intent,requestCode);
//    }
//
//    @Override
//    public void update(Observable observable, Object data) {
//        if(data instanceof Boolean){
//            Boolean isOpenOK = (Boolean) data;
//            if(isOpenOK == true)
//            {
//                mHandler.post(new Runnable()
//                {
//                    @Override
//                    public synchronized void run()
//                    {
//                        //enableDisableBoutton(true);
//                    }
//                });
//            }
//            else
//            {
//                mHandler.post(new Runnable()
//                {
//                    @Override
//                    public synchronized void run()
//                    {
//                        biometric_alert(ErrorCodes.MORPHOERR_RESUME_CONNEXION,0,"Resume Connection","");
//                    }
//                });
//            }
//        }else {
//            try
//            {
//                CallbackMessage message = (CallbackMessage) data;
//                int type = message.getMessageType();
//                switch (type) {
//                    case 1:
//                        Integer command = (Integer) message.getMessage();
//                        // Analyze the command.
//                        switch (command)
//                        {
//                            case 0:
//                                strMessage = "Move No Finger";
//                                break;
//                            case 1:
//                                strMessage = "Move Finger Up";
//                                break;
//                            case 2:
//                                strMessage = "Move Finger Down";
//                                break;
//                            case 3:
//                                strMessage = "Move Finger Left";
//                                break;
//                            case 4:
//                                strMessage = "Move Finger Right";
//                                break;
//                            case 5:
//                                strMessage = "Press harder";
//                                break;
//                            case 6:
//                                strMessage = "Move Latent";
//                                break;
//                            case 7:
//                                strMessage = "Remove Finger";
//                                break;
//                            case 8:
//                                strMessage = "Finger Ok";
//                                currentCaptureBitmapId = R.id.image_scan_status;
//                        }
//
//                        mHandler.post(new Runnable()
//                        {
//                            @Override
//                            public synchronized void run()
//                            {
//                                updateSensorMessage(strMessage);
//                            }
//                        });
//
//                        break;
//                    case 2:
//
//                    break;
//                    case 3:
//                        // message is the coded image quality.
//                        final Integer quality = (Integer) message.getMessage();
//                        mHandler.post(new Runnable()
//                        {
//                            @Override
//                            public synchronized void run() {
//                                updateSensorProgressBar(quality);
//                            }
//                        });
//                        break;
//                }
//            }
//            catch (Exception e) {
//                showAlertInfo(e.getMessage());
//            }
//        }
//    }
//
//    private void updateSensorProgressBar(Integer level) {
//        int imageResourceId = R.mipmap.thumb_print_scanned;
//        if(level<10){
//            imageResourceId = R.mipmap.thumb_print_unsel;
//        }else if (level <= 50)
//        {
//            imageResourceId = R.mipmap.thumb_print_progress;
//        }
//        allViews.imageScanStatus.setImageResource(imageResourceId);
//    }
//
//    private void updateSensorMessage(String sensorMessage) {
//        if(!TextUtils.isEmpty(sensorMessage)){
//            allViews.tvBiometricScanStatus.setText(sensorMessage);
//        }
//    }
//    public void morphoDeviceGetImage(final Observer observer)
//    {
//        Thread commandThread = (new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                int timeOut = 10;
//                int acquisitionThreshold = 0;
//                CompressionAlgorithm compressAlgo = CompressionAlgorithm.MORPHO_NO_COMPRESS;
//                int compressRate = 0;
//                int detectModeChoice;
//                LatentDetection latentDetection = LatentDetection.LATENT_DETECT_ENABLE;
//                MorphoImage morphoImage = new MorphoImage();
//                int callbackCmd = ProcessInfo.getInstance().getCallbackCmd();
//                callbackCmd &= ~CallbackMask.MORPHO_CALLBACK_ENROLLMENT_CMD.getValue();
//                detectModeChoice = DetectionMode.MORPHO_ENROLL_DETECT_MODE.getValue();
//                detectModeChoice |= DetectionMode.MORPHO_FORCE_FINGER_ON_TOP_DETECT_MODE.getValue();
//
//                final int ret = morphoDevice.getImage(timeOut, acquisitionThreshold, compressAlgo, compressRate, detectModeChoice, latentDetection, morphoImage, callbackCmd, observer);
//                ProcessInfo.getInstance().setCommandBioStart(false);
//                if (ret == ErrorCodes.MORPHO_OK) {
//                    showAlertInfo("Finger Print data get successfully");
//                }else {
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public synchronized void run() {
//                            Toast.makeText(mContext, "Please wait and try.",Toast.LENGTH_SHORT).show();
//                            //biometric_alert(ret, internalError, "GetImage", alertMessage);
//                        }
//                    });
//                }
//            }
//        }));
//        commandThread.start();
//    }
//}