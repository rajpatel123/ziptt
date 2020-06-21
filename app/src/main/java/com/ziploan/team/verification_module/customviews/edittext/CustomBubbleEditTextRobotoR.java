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
public class CustomBubbleEditTextRobotoR extends EditText {
    private final Context mContext;

    public CustomBubbleEditTextRobotoR(Context context) {
        super(context);
        mContext = context;
        setTypeface();
    }

    public CustomBubbleEditTextRobotoR(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setTypeface();
    }

    public CustomBubbleEditTextRobotoR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setTypeface();
    }

    public CustomBubbleEditTextRobotoR(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        String str = getText().toString();
        if((str.length()-3)>selEnd){
            setSelection(str.length());
        }
        //super.onSelectionChanged(selStart, selEnd);
    }
}
