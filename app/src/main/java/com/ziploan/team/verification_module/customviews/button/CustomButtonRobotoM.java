package com.ziploan.team.verification_module.customviews.button;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.Resources;

/**
 * Created by ZIploan-Nitesh on 30/08/2016.
 */
public class CustomButtonRobotoM extends Button {
    private final Context mContext;

    public CustomButtonRobotoM(Context context) {
        this(context,null);
    }

    public CustomButtonRobotoM(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomButtonRobotoM(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    public void setTypeface(Typeface tf) {
        Typeface tfCustom = Resources.getTypeface(mContext, AppConstant.Fonts.FONT_ROBOTO_MEDIUM);
        super.setTypeface(tfCustom);
    }
}
