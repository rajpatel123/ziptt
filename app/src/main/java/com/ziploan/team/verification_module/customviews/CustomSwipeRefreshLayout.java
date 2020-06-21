package com.ziploan.team.verification_module.customviews;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.ziploan.team.R;

/**
 * Created by Nitesh Singh on 7/9/2016.
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {


    public CustomSwipeRefreshLayout(Context context) {
        super(context);
        doCommonSetting(context);
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        doCommonSetting(context);
    }

    private void doCommonSetting(Context context) {
        setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark);
    }

    @Override
    public void setRefreshing(final boolean refreshing) {
        this.post(new Runnable() {
            @Override
            public void run() {
                CustomSwipeRefreshLayout.super.setRefreshing(refreshing);
            }
        });
    }
}