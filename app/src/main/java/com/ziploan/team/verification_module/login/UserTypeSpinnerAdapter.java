package com.ziploan.team.verification_module.login;

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

public class UserTypeSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final ArrayList<ZiploanTeamUser> mArrList;

    public UserTypeSpinnerAdapter(ArrayList<ZiploanTeamUser> arrayList) {
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
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX,(int)parent.getContext().getResources().getDimension(R.dimen.ts_16));
        text.setTextColor(parent.getContext().getResources().getColor(R.color.text_color_black));
        text.setPadding(20,15,10,15);
        text.setText(mArrList.get(position).getUser_type());
        return text;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView text = new CustomTextViewRobotoR(parent.getContext());
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX,(int)parent.getContext().getResources().getDimension(R.dimen.ts_16));
        text.setTextColor(parent.getContext().getResources().getColor(R.color.text_color_black));
        text.setPadding(30,25,20,25);
        text.setText(mArrList.get(position).getUser_type());
        return text;
    }
}
