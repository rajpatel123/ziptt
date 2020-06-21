package com.ziploan.team.verification_module.customviews.checkbox;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.Resources;

/**
 * Created by ZIploan-Nitesh on 30/08/2016.
 */
public class CustomCheckboxRobotoR extends CheckBox {
    private final Context mContext;


    public CustomCheckboxRobotoR(Context context) {
        super(context);
        mContext = context;
        setTypeface();
    }

    public CustomCheckboxRobotoR(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setTypeface();
    }

    public CustomCheckboxRobotoR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setTypeface();
    }

    public CustomCheckboxRobotoR(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        setTypeface();
    }

    @Override
    public void setTypeface(Typeface tf) {
        Typeface tfCustom = Resources.getTypeface(mContext, AppConstant.Fonts.FONT_ROBOTO_REGULAR);
        super.setTypeface(tfCustom);
    }

    public void setTypeface() {
        Typeface tfCustom = Resources.getTypeface(mContext, AppConstant.Fonts.FONT_ROBOTO_REGULAR);
        super.setTypeface(tfCustom);
    }
}
