package com.ziploan.team.asset_module;

import android.os.Parcel;
import android.os.Parcelable;

public class ActionBarData implements Parcelable {
    public final int imageResource;
    public final String title;

    public ActionBarData(int imageResource, String title) {
        this.imageResource = imageResource;
        this.title = title;
    }

    protected ActionBarData(Parcel in) {
        imageResource = in.readInt();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResource);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActionBarData> CREATOR = new Creator<ActionBarData>() {
        @Override
        public ActionBarData createFromParcel(Parcel in) {
            return new ActionBarData(in);
        }

        @Override
        public ActionBarData[] newArray(int size) {
            return new ActionBarData[size];
        }
    };
}