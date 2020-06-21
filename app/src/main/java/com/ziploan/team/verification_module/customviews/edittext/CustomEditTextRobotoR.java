package com.ziploan.team.verification_module.customviews.edittext;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.Resources;

/**
 * Created by ZIploan-Nitesh on 30/08/2016.
 */
public class CustomEditTextRobotoR extends EditText {
    private final Context mContext;


    public CustomEditTextRobotoR(Context context) {
        super(context);
        mContext = context;
        setTypeface();
    }

    public CustomEditTextRobotoR(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setTypeface();
    }

    public CustomEditTextRobotoR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setTypeface();
    }

    public CustomEditTextRobotoR(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
