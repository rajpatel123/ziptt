package com.ziploan.team;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;

import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.collection.model.config.AppConfigModel;
import com.ziploan.team.collection.model.config.SystemInfo;
import com.ziploan.team.collection.service.PostRecordVisitJob;
import com.ziploan.team.collection.utils.UIErrorUtils;
import com.ziploan.team.databinding.ActivitySplashBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.PermissionUtil;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.verification_module.services.SyncFiltersJob;
import com.ziploan.team.webapi.APIExecutor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AssetsBaseActivity {

    private ActivitySplashBinding allViews;

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivitySplashBinding) views;
        if (!TextUtils.isEmpty(ZiploanSPUtils.getInstance(mContext).getAccessId())
                && !TextUtils.isEmpty(ZiploanSPUtils.getInstance(mContext).getAccessToken())) {
            SyncFiltersJob.scheduleJob();
            PostRecordVisitJob.scheduleAdvancedJob();
        }

        allViews.ivLogo.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        allViews.tvVersion.setText("v" + ZiploanUtil.fetchAppVerison(mContext));
        saveScreenSize();

        App.filtersMain = DatabaseManger.getInstance().getAllAssetsFiltersItemsByKey();
        App.filtersKeyMain = DatabaseManger.getInstance().getAllAssetsFiltersKey();




        if (ZiploanUtil.isLocationProviderEnabled(this) && PermissionUtil.checkLocationPermission(mContext)) {
            if(UIErrorUtils.isNetworkConnected(this)){
                getConfigData();
            } else {
                moveToDashboard();
            }
        }


    }

    @Override
    protected void onActionHomeButtonClicked() {

    }

    private void saveScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ZiploanSPUtils.getInstance(mContext).saveScreenSize(size);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeActivityInTransition();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_ENABLE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (PermissionUtil.checkLocationPermission(mContext)) {
                    if(UIErrorUtils.isNetworkConnected(this)){
                        getConfigData();
                    } else {
                        moveToDashboard();
                    }
                }
            } else {
                openLocationRequest();
            }
        } else if (requestCode == AppConstant.REQUEST_PERMISSION_SETTING_LOCATION) {
            if (PermissionUtil.checkLocationPermission(mContext)) {
                if (ZiploanUtil.isLocationProviderEnabled(mContext)) {
                    if(UIErrorUtils.isNetworkConnected(this)){
                        getConfigData();
                    } else {
                        moveToDashboard();
                    }
                }
                else
                    openLocationRequest();
            } else {
                displayPermissionDeniedAlert(this, PermissionUtil.LOCATION_REQUEST_CODE, true);
            }
        }
    }

    public void getConfigData() {
        Call<AppConfigModel> call = APIExecutor.getAPIService(this).getConfig();
        call.enqueue(new Callback<AppConfigModel>() {
            @Override
            public void onResponse(Call<AppConfigModel> call, Response<AppConfigModel> response) {
                try {
                    if (response.isSuccessful()
                            && response.body() != null
                            && response.body().getSystemInfo() != null) {
                        if(response.body().getSystemInfo().getRefreshBank() != null
                                    && response.body().getSystemInfo().getRefreshBank() > 0) {
                            int bankSavedCounter =  ZiploanSPUtils.getInstance(SplashActivity.this).getBankSavedCounter();
                            if(response.body().getSystemInfo().getRefreshBank() > bankSavedCounter) {
                                App.refreshBankNames = true;
                                ZiploanSPUtils.getInstance(SplashActivity.this).setBankSavedCounter(response.body().getSystemInfo().getRefreshBank());
                            } else {
                                App.refreshBankNames = false;
                            }
                        } else {
                            App.refreshBankNames = false;
                        }
                        if (response.body().getSystemInfo().getForceUpgrade() == 1) {
                            showUpdateAppDialog(response.body().getSystemInfo(), true);
                        } else {
                            if (BuildConfig.VERSION_CODE >= response.body().getSystemInfo().getMinSupportedVersion()) {
                                if (BuildConfig.VERSION_CODE >= response.body().getSystemInfo().getCurrentAppVersion()) {
                                    moveToDashboard();
                                } else {
                                    showUpdateAppDialog(response.body().getSystemInfo(), false);
                                }
                            } else {
                                showUpdateAppDialog(response.body().getSystemInfo(), true);
                            }
                        }
                    } else {
                        moveToDashboard();
                    }
                } catch (Exception e){
                    moveToDashboard();
                }
            }

            @Override
            public void onFailure(Call<AppConfigModel> call, Throwable t) {
                moveToDashboard();
            }
        });
    }

    protected void showUpdateAppDialog(SystemInfo systemInfo, boolean isForce) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.update_app));
        builder.setMessage(systemInfo.getUpgradeMessage());
        builder.setPositiveButton(getString(R.string.update), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openPlayStoreToUpdate();
//                if(!isForce)
//                    finish();
            }
        });

        if (systemInfo.getForceUpgrade() == 1) {
            builder.setCancelable(false);
        } else {
            builder.setCancelable(false);
            if (!isForce) {
                builder.setNegativeButton(getString(R.string.later), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveToDashboard();
                    }
                });
            }
        }

        Dialog dialog = builder.create();
        if (!isFinishing()) {
            dialog.show();
        }
    }

    private void openPlayStoreToUpdate() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}


