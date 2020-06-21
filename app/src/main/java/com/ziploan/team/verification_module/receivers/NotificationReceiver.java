package com.ziploan.team.verification_module.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ziploan.team.verification_module.borrowerslist.BorrowersListActivity;
import com.ziploan.team.utils.AppConstant;

/**
 * Created by ZIploan-Nitesh on 14/09/2016.
 */
public class NotificationReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent!=null && intent.hasExtra(AppConstant.EXTRA_NOTIFICATION_TYPE)){
            String type = intent.getStringExtra(AppConstant.EXTRA_NOTIFICATION_TYPE);
            Intent intentBorrowerList = new Intent(context,BorrowersListActivity.class);
            intentBorrowerList.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            switch (type){
                case AppConstant.UPLOAD_SUCCESS:
                    context.startActivity(intentBorrowerList);
                    break;
                case AppConstant.UPLOAD_FAILED:
                    context.startActivity(intentBorrowerList);
                    break;
            }
        }
    }
}
