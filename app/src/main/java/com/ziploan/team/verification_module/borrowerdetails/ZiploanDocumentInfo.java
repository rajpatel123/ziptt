//package com.ziploan.team.verification_module.borrowerdetails;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.google.gson.annotations.SerializedName;
//import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by ZIploan-Nitesh on 2/15/2017.
// */
//public class ZiploanDocumentInfo implements Parcelable{
//    @SerializedName("primary_applicant_pan_url") private String primary_applicant_pan_url;
//    @SerializedName("secondary_applicant_pan_url") private String secondary_applicant_pan_url;
//    @SerializedName("loan_account_type") private String loan_account_type;
//    @SerializedName("entity_proof_document_type") private String entity_proof_document_type;
//    @SerializedName("entity_proof_document_no") private String entity_proof_document_no;
//    @SerializedName("entity_proof_document_url") private List<ZiploanPhoto> entity_proof_document_url=new ArrayList<>();
//    @SerializedName("business_address_proof_document_type") private String business_address_proof_document_type;
//    @SerializedName("business_address_proof_document_no") private String business_address_proof_document_no;
//    @SerializedName("business_address_proof_document_url") private List<ZiploanPhoto> business_address_proof_document_url=new ArrayList<>();
//    @SerializedName("residence_address_proof_document_type") private String residence_address_proof_document_type;
//    @SerializedName("residence_address_proof_document_no") private String residence_address_proof_document_no;
//    @SerializedName("residence_address_proof_document_url") private List<ZiploanPhoto> residence_address_proof_document_url=new ArrayList<>();
//
//    public ZiploanDocumentInfo() {
//    }
//
//    protected ZiploanDocumentInfo(Parcel in) {
//        primary_applicant_pan_url = in.readString();
//        secondary_applicant_pan_url = in.readString();
//        loan_account_type = in.readString();
//        entity_proof_document_type = in.readString();
//        entity_proof_document_no = in.readString();
//        entity_proof_document_url = in.createTypedArrayList(ZiploanPhoto.CREATOR);
//        business_address_proof_document_type = in.readString();
//        business_address_proof_document_no = in.readString();
//        business_address_proof_document_url = in.createTypedArrayList(ZiploanPhoto.CREATOR);
//        residence_address_proof_document_type = in.readString();
//        residence_address_proof_document_no = in.readString();
//        residence_address_proof_document_url = in.createTypedArrayList(ZiploanPhoto.CREATOR);
//    }
//
//    public static final Creator<ZiploanDocumentInfo> CREATOR = new Creator<ZiploanDocumentInfo>() {
//        @Override
//        public ZiploanDocumentInfo createFromParcel(Parcel in) {
//            return new ZiploanDocumentInfo(in);
//        }
//
//        @Override
//        public ZiploanDocumentInfo[] newArray(int size) {
//            return new ZiploanDocumentInfo[size];
//        }
//    };
//
//    public String getPrimary_applicant_pan_url() {
//        return primary_applicant_pan_url;
//    }
//
//    public void setPrimary_applicant_pan_url(String primary_applicant_pan_url) {
//        this.primary_applicant_pan_url = primary_applicant_pan_url;
//    }
//
//    public String getSecondary_applicant_pan_url() {
//        return secondary_applicant_pan_url;
//    }
//
//    public void setSecondary_applicant_pan_url(String secondary_applicant_pan_url) {
//        this.secondary_applicant_pan_url = secondary_applicant_pan_url;
//    }
//
//    public String getLoan_account_type() {
//        return loan_account_type;
//    }
//
//    public void setLoan_account_type(String loan_account_type) {
//        this.loan_account_type = loan_account_type;
//    }
//
//    public String getEntity_proof_document_type() {
//        return entity_proof_document_type;
//    }
//
//    public void setEntity_proof_document_type(String entity_proof_document_type) {
//        this.entity_proof_document_type = entity_proof_document_type;
//    }
//
//    public String getEntity_proof_document_no() {
//        return entity_proof_document_no;
//    }
//
//    public void setEntity_proof_document_no(String entity_proof_document_no) {
//        this.entity_proof_document_no = entity_proof_document_no;
//    }
//
//    public List<ZiploanPhoto> getEntity_proof_document_url() {
//        return entity_proof_document_url;
//    }
//
//    public void setEntity_proof_document_url(List<ZiploanPhoto> entity_proof_document_url) {
//        this.entity_proof_document_url = entity_proof_document_url;
//    }
//
//    public String getBusiness_address_proof_document_type() {
//        return business_address_proof_document_type;
//    }
//
//    public void setBusiness_address_proof_document_type(String business_address_proof_document_type) {
//        this.business_address_proof_document_type = business_address_proof_document_type;
//    }
//
//    public String getBusiness_address_proof_document_no() {
//        return business_address_proof_document_no;
//    }
//
//    public void setBusiness_address_proof_document_no(String business_address_proof_document_no) {
//        this.business_address_proof_document_no = business_address_proof_document_no;
//    }
//
//    public List<ZiploanPhoto> getBusiness_address_proof_document_url() {
//        return business_address_proof_document_url;
//    }
//
//    public void setBusiness_address_proof_document_url(List<ZiploanPhoto> business_address_proof_document_url) {
//        this.business_address_proof_document_url = business_address_proof_document_url;
//    }
//
//    public String getResidence_address_proof_document_type() {
//        return residence_address_proof_document_type;
//    }
//
//    public void setResidence_address_proof_document_type(String residence_address_proof_document_type) {
//        this.residence_address_proof_document_type = residence_address_proof_document_type;
//    }
//
//    public String getResidence_address_proof_document_no() {
//        return residence_address_proof_document_no;
//    }
//
//    public void setResidence_address_proof_document_no(String residence_address_proof_document_no) {
//        this.residence_address_proof_document_no = residence_address_proof_document_no;
//    }
//
//    public List<ZiploanPhoto> getResidence_address_proof_document_url() {
//        return residence_address_proof_document_url;
//    }
//
//    public void setResidence_address_proof_document_url(List<ZiploanPhoto> residence_address_proof_document_url) {
//        this.residence_address_proof_document_url = residence_address_proof_document_url;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(primary_applicant_pan_url);
//        parcel.writeString(secondary_applicant_pan_url);
//        parcel.writeString(loan_account_type);
//        parcel.writeString(entity_proof_document_type);
//        parcel.writeString(entity_proof_document_no);
//        parcel.writeTypedList(entity_proof_document_url);
//        parcel.writeString(business_address_proof_document_type);
//        parcel.writeString(business_address_proof_document_no);
//        parcel.writeTypedList(business_address_proof_document_url);
//        parcel.writeString(residence_address_proof_document_type);
//        parcel.writeString(residence_address_proof_document_no);
//        parcel.writeTypedList(residence_address_proof_document_url);
//    }
//}
