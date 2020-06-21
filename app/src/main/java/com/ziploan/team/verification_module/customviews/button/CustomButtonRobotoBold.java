package com.ziploan.team.verification_module.customviews.button;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.Resources;

/**
 * Created by ZIploan-Nitesh on 30/08/2016.
 */
public class CustomButtonRobotoBold extends android.support.v7.widget.AppCompatButton {
    private final Context mContext;

    public CustomButtonRobotoBold(Context context) {
        this(context,null);
    }

    public CustomButtonRobotoBold(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomButtonRobotoBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    public void setTypeface(Typeface tf) {
        Typeface tfCustom = Resources.getTypeface(mContext, AppConstant.Fonts.FONT_ROBOTO_BOLD);
        super.setTypeface(tfCustom);
    }
}
