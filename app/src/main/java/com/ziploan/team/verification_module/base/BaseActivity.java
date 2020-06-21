package com.ziploan.team.verification_module.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.morpho.morphosmart.sdk.ErrorCodes;
import com.ziploan.team.App;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.utils.ConnectivityReceiver;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.verification_module.login.LoginActivity;

import java.util.Calendar;

import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 1/27/2017.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ViewDataBinding views;
    private Handler handler;
    private Runnable runnable;
    protected Context mContext;
    private ProgressDialog mProgressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        views = DataBindingUtil.setContentView(this, getLayoutId());
        onViewMapped(views);
        if (!(this instanceof LoginActivity) && !isTokenAlive(ZiploanSPUtils.getInstance(mContext).getExpirationDate())) {
            callLoginAgain();
        }
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

    protected void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    protected boolean isTokenAlive(String expirationDate) {
        if (!checkInternetConnection(mContext))
            return true;
        if (expirationDate.length() > 0) {
            Calendar cal = Calendar.getInstance();
            Long expirationTimeInMillis = AssetsBaseActivity.getTimeInLong("yyyy-MM-dd HH:mm:ss", expirationDate.substring(0, 19));
            if (expirationDate != null && (expirationTimeInMillis - System.currentTimeMillis()) > 60 * 1000) {
                return true;
            }
        }

        return false;
    }

    protected void callLoginAgain() {
        Toast.makeText(mContext, R.string.session_expired, Toast.LENGTH_SHORT).show();
        logout();
    }

    private void logout() {
        finish();
        ZiploanSPUtils.getInstance(mContext).setIsLoggedIn(false);
        Intent intent = new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected abstract int getLayoutId();

    protected abstract void onViewMapped(ViewDataBinding views);

    protected void startActivityWithDelay(final Intent intent, int delayInMillis) {
        try {
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            };
            handler.postDelayed(runnable, delayInMillis);
        } catch (Exception e) {
        }
    }

    protected void closeActivityInTransition() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

    protected void showAlertInfo(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = ZiploanUtil.getMessageCustomDialog(BaseActivity.this, R.layout.dialog_common_prompt, msg);
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
    protected void showAlertInfo(final String msg, final DialogInterface.OnDismissListener listener) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Dialog dialog = ZiploanUtil.getMessageCustomDialog(BaseActivity.this, R.layout.dialog_common_prompt, msg, Color.parseColor("#000000"));
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

    protected void showAlertInfo(final Spanned msg, final int... colorCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = ZiploanUtil.getMessageCustomDialog(BaseActivity.this, R.layout.dialog_common_prompt, msg, (colorCode.length > 0 ? colorCode[0] : Color.parseColor("#000000")));
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

    protected void showUpdateAppDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = ZiploanUtil.getMessageCustomDialog(BaseActivity.this, R.layout.dialog_common_prompt, msg);
                dialog.setCancelable(false);
                dialog.findViewById(R.id.tv_okay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
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
        if (!isFinishing()) {
            mProgressDialog.show();

        }
    }

    protected void displayBackConfirmAlert(String msg) {
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
                dialog.dismiss();
                finish();
            }
        });

        if (!isFinishing())
            dialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            if(!isFinishing())
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
                ZiploanSPUtils.getInstance(mContext).setIsLoggedIn(false);
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
                deleteListFromDB();
            }
        });

        if (!isFinishing())
            dialog.show();
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
            String msg = "Operation failed\n" ;
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

    private void deleteListFromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseManger.getInstance().deleteCollectionAppListData();
                DatabaseManger.getInstance().deleteAllRecordVisitData();
            }
        }).start();
    }
}
