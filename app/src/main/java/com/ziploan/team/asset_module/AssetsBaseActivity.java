package com.ziploan.team.asset_module;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.loans.LoansListActivity;
import com.ziploan.team.asset_module.visits.LoadPhotoListener;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ConnectivityReceiver;
import com.ziploan.team.utils.ImageUtils;
import com.ziploan.team.utils.PermissionUtil;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.borrowerslist.BorrowersListActivity;
import com.ziploan.team.verification_module.login.LoginActivity;
import com.ziploan.team.verification_module.services.LocationListener;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Response;

//import com.morpho.morphosmart.sdk.ErrorCodes;

/**
 * Created by ZIploan-Nitesh on 1/27/2017.
 */
public abstract class AssetsBaseActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    protected static final int LOCATION_ENABLE_REQUEST = 1000;
    private ViewDataBinding views;
    private Handler handler;
    private Runnable runnable;
    protected Context mContext;
    private ProgressDialog mProgressDialog;
    private Toolbar toolBar;
    private Uri capturedImageUri;
    private LoadPhotoListener loadPhotoListener;
    private LocationListener[] mLocationListeners;
    private LocationManager mLocationManager;
    private static final int LOCATION_INTERVAL = 1000;//1000;
    private static final float LOCATION_DISTANCE = 0;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        mContext = this;
        views = DataBindingUtil.setContentView(this, getLayoutId());
        onViewMapped(views);
        //This line should be after onViewMapped method
        toolBar = getToolbar();
        if (toolBar != null) {
            setSupportActionBar(toolBar);
            toolBar.findViewById(R.id.iv_drawer_icon).setOnClickListener(this);
        }
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("142355302032-2m51rnjc6lqoao7ikeis2aelh1r2jd1p.apps.googleusercontent.com")
                .requestEmail()
                .requestProfile()
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(AssetsBaseActivity.this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        if (PermissionUtil.checkLocationPermission(mContext)) {
            if (!ZiploanUtil.isLocationProviderEnabled(this)) {
                openLocationRequest();
            } else {
                startLocationService();
            }
        } else {
            PermissionUtil.requestLocationPermission(mContext);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 1 && requestCode == PermissionUtil.LOCATION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (!ZiploanUtil.isLocationProviderEnabled(this)) {
                openLocationRequest();
            } else {
                moveToDashboard();
            }
        } else {
            boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);
            if (!showRationale) {
                switch (requestCode) {
                    case PermissionUtil.LOCATION_REQUEST_CODE:
                        displayPermissionDeniedAlert(this, requestCode, true);
                        break;
                }
            } else {
                PermissionUtil.requestLocationPermission(mContext);
            }
        }
    }

    protected void moveToDashboard() {
        Intent intent = null;
        if (ZiploanSPUtils.getInstance(mContext).isLoggedIn() && isTokenAlive(ZiploanSPUtils.getInstance(mContext).getExpirationDate())) {
            String userType = ZiploanSPUtils.getInstance(mContext).getLoggedInUserType();
            switch (userType) {
                case AppConstant.UserType.PHYSICAL_VERIFICATION:
                    intent = new Intent(this, BorrowersListActivity.class);
                    break;
                case AppConstant.UserType.ASSET_MANAGEMENT:
                    intent = new Intent(this, LoansListActivity.class);
                    break;
                case AppConstant.UserType.COLLECTION:
                    //intent = new Intent(this, ApplicationListActivity.class);
                    break;
            }
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivityWithDelay(intent, 1000);
    }

    protected boolean isTokenAlive(String expirationDate) {
        if (!checkInternetConnection(mContext))
            return true;
        if (expirationDate.length() > 0) {
            Calendar cal = Calendar.getInstance();
            Long expirationTimeInMillis = getTimeInLong("yyyy-MM-dd HH:mm:ss", expirationDate.substring(0, 19));
            if (expirationDate != null && (expirationTimeInMillis - cal.getTimeInMillis()) > 60 * 1000) {
                return true;
            }
        }

        return false;
    }

    public static Long getTimeInLong(String format, String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
            Date date = dateFormat.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void displayPermissionDeniedAlert(final Activity activity, final int i, final boolean isNeverAskChecked) {
        final Dialog dialog = ZiploanUtil.getMessageCustomDialog(this, R.layout.dialog_confirm, "");
        dialog.setCancelable(false);
        String message = getResources().getString(R.string.allow_this_permission_to_proceed);
        switch (i) {
            case PermissionUtil.LOCATION_REQUEST_CODE:
                if (isNeverAskChecked) {
                    message = String.format(getResources().getString(R.string.allow_this_permission_to_proceed), getResources().getString(R.string.location_permission_name));
                } else {
                    message = String.format(getResources().getString(R.string.allow_this_permission_to_proceed), getResources().getString(R.string.location_permission_name));
                }
                break;
        }
        ((TextView) dialog.findViewById(R.id.tv_message)).setText(message);
        dialog.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                switch (i) {
                    case PermissionUtil.LOCATION_REQUEST_CODE:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        });

        dialog.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i) {
                    case PermissionUtil.LOCATION_REQUEST_CODE:
                        if (isNeverAskChecked) {
                            PermissionUtil.openAppSetting(activity, AppConstant.REQUEST_PERMISSION_SETTING_LOCATION);
                        } else {
                            PermissionUtil.requestSmsPermission(activity);
                        }
                        break;
                }
                dialog.dismiss();
            }
        });

        if (!isFinishing())
            dialog.show();
    }

    protected void openLocationRequest() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
        }

        if (googleApiClient != null && (googleApiClient.isConnecting() || googleApiClient.isConnected())) {

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true); // this is the key ingredient
            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                    .checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    final LocationSettingsStates state = result
                            .getLocationSettingsStates();
                    switch (status.getStatusCode()) {

                        case LocationSettingsStatusCodes.SUCCESS:

                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(AssetsBaseActivity.this, LOCATION_ENABLE_REQUEST);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        }
    }

    protected abstract Toolbar getToolbar();

    protected abstract int getLayoutId();

    protected abstract void onViewMapped(ViewDataBinding views);

    protected void startActivityWithDelay(final Intent intent, int delayInMillis) {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnable, delayInMillis);
    }

    protected void closeActivityInTransition() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    public String getText(TextView view) {
        return view.getText().toString().trim();
    }


    protected void showAlertInfo(final String msg, final DialogInterface.OnDismissListener listener) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Dialog dialog = ZiploanUtil.getMessageCustomDialog(AssetsBaseActivity.this, R.layout.dialog_common_prompt, msg, Color.parseColor("#000000"));
                    dialog.setCancelable(false);
                    ((TextView) dialog.findViewById(R.id.tv_okay)).setText("GO TO HOME");
                    dialog.findViewById(R.id.tv_okay).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            listener.onDismiss(dialogInterface);
                        }
                    });
                    if (!isFinishing())
                        dialog.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showAlertInfo(final String msg, int... colorCode) {
        try {
            showAlertInfo(Html.fromHtml(msg), colorCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showAlertInfo(final Spanned msg, final int... colorCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = ZiploanUtil.getMessageCustomDialog(AssetsBaseActivity.this, R.layout.dialog_common_prompt, msg, (colorCode.length > 0 ? colorCode[0] : Color.parseColor("#000000")));
                dialog.setCancelable(false);
                dialog.findViewById(R.id.tv_okay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                if (!isFinishing())
                    dialog.show();
            }
        });

    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Please Wait");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        if (!isFinishing()) {
            mProgressDialog.show();

        }
    }

    protected void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.setMessage(message);
        if (!isFinishing()) {
            mProgressDialog.show();

        }
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected void displayLogoutAlert(String msg) {
        final Dialog dialog = ZiploanUtil.getMessageCustomDialog(this, R.layout.dialog_confirm, msg);
        dialog.setCancelable(false);
        ((TextView) dialog.findViewById(R.id.tv_message)).setText(msg);
        dialog.findViewById(R.id.tv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.tv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                dialog.dismiss();
            }
        });

        if (!isFinishing())
            dialog.show();
    }

    private void logout() {
        ZiploanSPUtils.getInstance(mContext).setIsLoggedIn(false);
        Intent intent = new Intent(AssetsBaseActivity.this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startLocationService() {
        mLocationListeners = new LocationListener[]{
                new LocationListener(mContext, LocationManager.GPS_PROVIDER),
                new LocationListener(mContext, LocationManager.NETWORK_PROVIDER)
        };
        initializeLocationManager();
        requestLocationUsingNetworkProvider();
        requestLocationUsingGPSProvider();
    }


    private void initializeLocationManager() {
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    /**
     * Method will register location update api using GPS Provider
     */
    private void requestLocationUsingGPSProvider() {
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method will register location update api using Network Provider
     */
    private void requestLocationUsingNetworkProvider() {
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }


    protected void showFieldError(EditText editText, String msg) {
        if (editText.getParent() instanceof TextInputLayout) {
            ((TextInputLayout) editText.getParent()).setError(msg);
        } else {
            editText.setError(msg);
        }
    }

    protected boolean checkInternetConnection(Context mContext) {
        if (ConnectivityReceiver.isConnected(mContext)) return true;
        else {
            return false;
        }
    }

    protected void biometric_alert(int codeError, int internalError, String title, String message) {
        if (codeError != 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(title);
            String msg = "Operation failed\n";
            //+ ErrorCodes.getError(codeError, internalError);

            msg += ((message.equalsIgnoreCase("")) ? "" : "\n" + message);
            alertDialog.setMessage(msg);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            if (!isFinishing())
                alertDialog.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_drawer_icon:
                onActionHomeButtonClicked();
                break;
        }
    }

    protected void openGallery(LoadPhotoListener photoListener, boolean multi_selection) {
        loadPhotoListener = photoListener;
        Intent intent = new Intent();
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= 18 && multi_selection)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), AppConstant.GALLERY_REQUEST);
    }

    protected void openCamera(LoadPhotoListener photoListener) {
        loadPhotoListener = photoListener;
        capturedImageUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", createImageFile());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
        startActivityForResult(cameraIntent, AppConstant.CAMERA_REQUEST);
    }

    protected abstract void onActionHomeButtonClicked();


    private File createImageFile() {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  // prefix
                    ".jpg",         // suffix
                    storageDir      // directory
            );
            return image;
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            final List<ZiploanPhoto> arrList = new ArrayList<>();
            switch (requestCode) {

                case AppConstant.REQUEST_PERMISSION_SETTING_LOCATION:
                    arrList.add(new ZiploanPhoto(ImageUtils.compressImage(mContext, capturedImageUri, null), AppConstant.MediaType.IMAGE));
                    loadPhotoListener.getImages(arrList, AppConstant.ImageSourceType.CAMREA);
                    break;

                case AppConstant.CAMERA_REQUEST:
                    arrList.add(new ZiploanPhoto(ImageUtils.compressImage(mContext, capturedImageUri, null), AppConstant.MediaType.IMAGE));
                    loadPhotoListener.getImages(arrList, AppConstant.ImageSourceType.CAMREA);
                    break;
                case AppConstant.GALLERY_REQUEST:
                    if (data.getData() != null) {
                        String pickedImage = ImageUtils.compressImage(mContext, data.getData(), null);
                        if (pickedImage.equalsIgnoreCase("invalid")) {
                            showAlertInfo("Invalid image type");

                        }else{
                            arrList.add(new ZiploanPhoto(pickedImage, AppConstant.MediaType.IMAGE));
                            loadPhotoListener.getImages(arrList, AppConstant.ImageSourceType.GALLERY);
                        }



                    } else if (data.getClipData() != null) {
                        loadPhotoListener.processingImages();
                        ImageUtils.asyncCompressMultipleImage(mContext, data.getClipData(), new ImageUtils.OnCompressDone() {
                            @Override
                            public void onSuccess(List<String> uris) {
                                for (int i = 0; i < uris.size(); i++) {
                                    arrList.add(new ZiploanPhoto(uris.get(i), AppConstant.MediaType.IMAGE));
                                }
                                loadPhotoListener.getImages(arrList, AppConstant.ImageSourceType.GALLERY);
                            }
                        });
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    protected <T> void checkTokenValidity(Response<T> response) {
        try {
            if (response != null && response.code() == 401) {
                callLoginAgain();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callLoginAgain() {
        Toast.makeText(mContext, R.string.session_expired, Toast.LENGTH_SHORT).show();
        logout();
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void openMapWithLocationPathToDestination(String s) {
        try {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=" + s));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            showAlertInfo("Please install GoogleMap to use this feature.");
        }
    }
}
