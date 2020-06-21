package com.ziploan.team.verification_module.borrowerdetails;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.ziploan.team.R;
import com.ziploan.team.verification_module.customviews.textview.CustomTextViewRobotoR;

import java.util.ArrayList;

/**
 * Created by ZIploan-Nitesh on 2/8/2017.
 */

public class SimpleSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final ArrayList<String> mArrList;
    private int selectedColor = R.color.text_color_black;

    public SimpleSpinnerAdapter(ArrayList<String> arrayList) {
        this.mArrList = arrayList;
    }

    @Override
    public int getCount() {
        return mArrList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        TextView text = new CustomTextViewRobotoR(parent.getContext());
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX,(int)parent.getContext().getResources().getDimension(R.dimen.ts_18));
        text.setTextColor(parent.getContext().getResources().getColor(selectedColor));
        text.setPadding(20,15,10,15);
        text.setText(mArrList.get(position));
        return text;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView text = new CustomTextViewRobotoR(parent.getContext());
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX,(int)parent.getContext().getResources().getDimension(R.dimen.ts_16));
        text.setTextColor(parent.getContext().getResources().getColor(R.color.text_color_black));
        text.setPadding(30,25,20,25);
        text.setText(mArrList.get(position));
        return text;
    }

    public int getPositionByItem(String value){
        for (int i=0;i<mArrList.size();i++){
            if(mArrList.get(i).equalsIgnoreCase(value))
                return i;
        }
        return mArrList.size()-1;
    }

    public void setSelectedTextColor(int color) {
        selectedColor = color;
        notifyDataSetChanged();
    }
}
