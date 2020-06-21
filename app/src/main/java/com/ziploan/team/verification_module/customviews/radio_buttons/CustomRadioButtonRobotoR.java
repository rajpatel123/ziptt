package com.ziploan.team.verification_module.customviews.radio_buttons;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.Resources;

/**
 * Created by ZIploan-Nitesh on 30/08/2016.
 */
public class CustomRadioButtonRobotoR extends RadioButton {
    private final Context mContext;

    public CustomRadioButtonRobotoR(Context context) {
        this(context,null);
        setTypeface(null);
    }

    public CustomRadioButtonRobotoR(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        setTypeface(null);
    }

    public CustomRadioButtonRobotoR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setTypeface(null);
    }

    @Override
    public void setTypeface(Typeface tf) {
        Typeface tfCustom = Resources.getTypeface(mContext, AppConstant.Fonts.FONT_ROBOTO_REGULAR);
        super.setTypeface(tfCustom);
    }
}
