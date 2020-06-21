package com.ziploan.team.asset_module;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ziploan.team.BR;

/**
 * Created by ZIploan-Nitesh on 9/27/2017.
 */

public class CheckedPojo extends BaseObservable{
    private boolean checked;

    @Bindable
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        notifyPropertyChanged(BR.checked);
    }
}
