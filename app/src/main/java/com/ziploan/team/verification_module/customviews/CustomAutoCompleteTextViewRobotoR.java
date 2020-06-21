package com.ziploan.team.verification_module.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.Resources;

/**
 * Created by ZIploan-Nitesh on 30/08/2016.
 */
public class CustomAutoCompleteTextViewRobotoR extends AutoCompleteTextView {
    private final Context mContext;

    public CustomAutoCompleteTextViewRobotoR(Context context) {
        this(context,null);
        setTypeface(null);
    }

    public CustomAutoCompleteTextViewRobotoR(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        setTypeface(null);
    }

    public CustomAutoCompleteTextViewRobotoR(Context context, AttributeSet attrs, int defStyleAttr) {
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
