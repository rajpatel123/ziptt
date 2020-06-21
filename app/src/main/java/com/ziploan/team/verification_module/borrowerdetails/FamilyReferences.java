package com.ziploan.team.verification_module.borrowerdetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZIploan-Nitesh on 2/15/2017.
 */
public class FamilyReferences implements Parcelable{
    @SerializedName("name") private String name;
    @SerializedName("mobile") private String mobile;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @SerializedName("address") private String address;
    @SerializedName("relationship") private String relationship;

    public FamilyReferences() {
    }

    public FamilyReferences(String name, String mobile,String address,String relationship) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.relationship = relationship;
    }

    protected FamilyReferences(Parcel in) {
        name = in.readString();
        mobile = in.readString();
        address = in.readString();
        relationship = in.readString();
    }

    public static final Creator<FamilyReferences> CREATOR = new Creator<FamilyReferences>() {
        @Override
        public FamilyReferences createFromParcel(Parcel in) {
            return new FamilyReferences(in);
        }

        @Override
        public FamilyReferences[] newArray(int size) {
            return new FamilyReferences[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(mobile);
        parcel.writeString(address);
        parcel.writeString(relationship);
    }
}
