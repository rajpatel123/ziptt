package com.ziploan.team.verification_module.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ziploan.team.BuildConfig;
import com.ziploan.team.MyWebViewActivity;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.visits.LoadPhotoListener;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ImageUtils;
import com.ziploan.team.utils.PermissionUtil;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.login.LoginActivity;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Response;

import static android.view.View.GONE;

/**
 * Created by ZIploan-Nitesh on 2/4/2017.
 */

public class BaseFragment extends Fragment implements LoadPhotoListener {
    protected Context mContext;
    private AlertDialog itemListDialog;
    protected String mCurrentPhotoPath;
    protected Uri capturedImageUri;
    private ProgressDialog mProgressDialog;
    private LoadPhotoListener loadPhotoListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    protected void openCameraGalleryOptions(final boolean multi_selection) {
        CharSequence[] items = new CharSequence[]{"Capture With Camera", "Choose From Gallery"};
        itemListDialog = new AlertDialog.Builder(mContext).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        if (PermissionUtil.checkCameraPermission(mContext)) {
                            openCamera(BaseFragment.this);
                        } else {
                            PermissionUtil.requestCameraPermission(BaseFragment.this);
                        }
                        break;
                    case 1:
                        if (PermissionUtil.checkStoragePermission(mContext)) {
                            openGallery(BaseFragment.this, multi_selection);
                        } else {
                            PermissionUtil.requestStoragePermission(BaseFragment.this);
                        }
                        break;


                }
                itemListDialog.dismiss();
            }
        }).show();
    }


    protected void openCameraGalleryOptionsForEsign(final boolean multi_selection, ProgressBar progressBar) {
        loadPhotoListener= BaseFragment.this;
        CharSequence[] items = new CharSequence[]{"Capture With Camera", "Choose From Gallery","Cancel"};
        itemListDialog = new AlertDialog.Builder(mContext).setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        if (PermissionUtil.checkCameraPermission(mContext)) {
                            progressBar.setVisibility(View.VISIBLE);

                            openCamera(BaseFragment.this);
                        } else {
                            PermissionUtil.requestCameraPermission(BaseFragment.this);
                        }
                        break;
                    case 1:
                        if (PermissionUtil.checkStoragePermission(mContext)) {
                            progressBar.setVisibility(View.VISIBLE);

                            openGallery(BaseFragment.this, multi_selection);
                        } else {
                            PermissionUtil.requestStoragePermission(BaseFragment.this);
                        }
                        break;
                    case 2:
                        loadPhotoListener.getImages(null, AppConstant.ImageSourceType.CAMREA);
                        progressBar.setVisibility(GONE);

                }
                itemListDialog.dismiss();
            }
        }).create();

        itemListDialog.setCancelable(false);
        itemListDialog.show();
    }

    private void openGallery(LoadPhotoListener photoListener, boolean multi_selection) {
        loadPhotoListener = photoListener;
        Intent intent = new Intent();
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= 18 && multi_selection)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), AppConstant.GALLERY_REQUEST);
    }

    private void openCamera(LoadPhotoListener photoListener) {
        loadPhotoListener = photoListener;
        capturedImageUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".provider", createImageFile());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
        startActivityForResult(cameraIntent, AppConstant.CAMERA_REQUEST);
    }

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
            mCurrentPhotoPath = "file://" + image.getAbsolutePath();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void showAlertInfo(String msg) {
        final Dialog dialog = ZiploanUtil.getMessageCustomDialog(mContext, R.layout.dialog_common_prompt, msg);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.tv_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        try {
            if (mContext != null && !((Activity) mContext).isFinishing())
                dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void openPDFBrowser(View view) {
        if (view.getTag() != null && !TextUtils.isEmpty(view.getTag().toString())) {
            String path = view.getTag().toString();
            if (!path.contains("http")) {
                openPDFFromLocalDirectory(path);
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.Key.EXTRA_WEB_URL, view.getTag().toString());
                MyWebViewActivity.start(mContext, bundle);
            }
        } else {
            showAlertInfo("PDF file not found");
        }
    }

    public void openPDFFromLocalDirectory(String filePath) {
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(new File(filePath)), "application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            showAlertInfo("Please install a PDF viewer app from play store.");
        }
    }

    protected boolean isPhotoArrayNotEmpty(List<ZiploanPhoto> arrayList) {
        if (arrayList != null && arrayList.size() > 0 && arrayList.get(0) != null && !TextUtils.isEmpty(arrayList.get(0).getPhotoPath())) {
            return true;
        }
        return false;
    }

    protected void enableField(View view, boolean b) {
        view.setEnabled(b);
        view.setFocusable(b);
        view.setFocusableInTouchMode(b);
//        if(!b)
//            view.setBackground(null);
    }

    protected void showProgressDialog(Context mContext, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        try {
            if (!((Activity) mContext).isFinishing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    protected void showShortToast(String s) {
        if (mContext != null && s != null)
            Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(String s) {
        if (mContext != null && s != null)
            Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
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

    protected void callLoginAgain() {
        Toast.makeText(mContext, R.string.session_expired, Toast.LENGTH_SHORT).show();
        logout();
    }

    private void logout() {
        getActivity().finish();
        ZiploanSPUtils.getInstance(mContext).setIsLoggedIn(false);
        Intent intent = new Intent(getActivity(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            final List<ZiploanPhoto> arrList = new ArrayList<>();
            switch (requestCode) {
                case AppConstant.CAMERA_REQUEST:
                    arrList.add(new ZiploanPhoto(ImageUtils.compressImage(mContext, capturedImageUri, null), AppConstant.MediaType.IMAGE));
                    loadPhotoListener.getImages(arrList, AppConstant.ImageSourceType.CAMREA);
                    break;
                case AppConstant.GALLERY_REQUEST:
                    if (data.getData() != null) {
                        arrList.add(new ZiploanPhoto(ImageUtils.compressImage(mContext, data.getData(), null), AppConstant.MediaType.IMAGE));
                        loadPhotoListener.getImages(arrList, AppConstant.ImageSourceType.GALLERY);
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

                    default:
                        loadPhotoListener.getImages(null, AppConstant.ImageSourceType.CAMREA);

            }
        }else{
            loadPhotoListener.getImages(null, AppConstant.ImageSourceType.CAMREA);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getImages(List<ZiploanPhoto> arrImages, int sourceType) {

    }

    @Override
    public void processingImages() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.CAMERA_REQUEST_CODE:
                if (PermissionUtil.checkCameraPermission(mContext)) {
                    openCamera(BaseFragment.this);
                }
                break;
            case PermissionUtil.STORAGE_REQUEST_CODE:
                if (PermissionUtil.checkStoragePermission(mContext)) {
                    openGallery(BaseFragment.this, true);
                }
                break;
        }
    }
}