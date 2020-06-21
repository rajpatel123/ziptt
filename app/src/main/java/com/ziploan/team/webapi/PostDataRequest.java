package com.ziploan.team.webapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.verification_module.borrowerdetails.ReferenceUser;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;

import java.util.ArrayList;

public class PostDataRequest implements Parcelable{
    @SerializedName("loan_account_type_business_entity_proof_url") private String loan_account_type_business_entity_proof_url;
    @SerializedName("stock_inventory_amount") private String stock_inventory_amount;
    @SerializedName("primary_applicant_pan_url") private String primary_applicant_pan_url;
    @SerializedName("business_place_photos_url") private String business_place_photos_url;
    @SerializedName("business_place_ownership_papers") private String business_place_ownership_papers;
    @SerializedName("business_place_address_proof_document_type") private String business_place_address_proof_document_type;
    @SerializedName("business_place_address_proof_number") private String business_place_address_proof_number;
    @SerializedName("business_place_longitude_captured_on_verification") private String business_place_longitude_captured_on_verification;
    @SerializedName("business_place_seperate_residence_place") private String business_place_seperate_residence_place;
    @SerializedName("loan_request_id") private String loan_request_id;
    @SerializedName("residence_place_address_proof_url") private String residence_place_address_proof_url;
    @SerializedName("business_place_address_proof_url") private String business_place_address_proof_url;
    @SerializedName("loan_account_type_business_entity_proof_document_type") private String loan_account_type_business_entity_proof_document_type;
    @SerializedName("no_employees") private String no_employees;
    @SerializedName("fixed_asset_machinery_amount") private String fixed_asset_machinery_amount;
    @SerializedName("secondary_applicant_pan_url") private String secondary_applicant_pan_url;
    @SerializedName("references") private ArrayList<ReferenceUser> references;
    @SerializedName("business_place_latitude_captured_on_verification") private String business_place_latitude_captured_on_verification;
    @SerializedName("loan_account_type_business_entity_proof_number") private String loan_account_type_business_entity_proof_number;
    @SerializedName("loan_account_type") private String loan_account_type;
    @SerializedName("business_information_questionnaire") private ArrayList<ZiploanQuestion> business_information_questionnaire;
    @SerializedName("residence_place_address_proof_number") private String residence_place_address_proof_number;
    @SerializedName("residence_place_address_proof_document_type") private String residence_place_address_proof_document_type;

    protected PostDataRequest(Parcel in) {
        loan_account_type_business_entity_proof_url = in.readString();
        stock_inventory_amount = in.readString();
        primary_applicant_pan_url = in.readString();
        business_place_photos_url = in.readString();
        business_place_ownership_papers = in.readString();
        business_place_address_proof_document_type = in.readString();
        business_place_address_proof_number = in.readString();
        business_place_longitude_captured_on_verification = in.readString();
        business_place_seperate_residence_place = in.readString();
        loan_request_id = in.readString();
        residence_place_address_proof_url = in.readString();
        business_place_address_proof_url = in.readString();
        loan_account_type_business_entity_proof_document_type = in.readString();
        no_employees = in.readString();
        fixed_asset_machinery_amount = in.readString();
        secondary_applicant_pan_url = in.readString();
        references = in.createTypedArrayList(ReferenceUser.CREATOR);
        business_place_latitude_captured_on_verification = in.readString();
        loan_account_type_business_entity_proof_number = in.readString();
        loan_account_type = in.readString();
        business_information_questionnaire = in.createTypedArrayList(ZiploanQuestion.CREATOR);
        residence_place_address_proof_number = in.readString();
        residence_place_address_proof_document_type = in.readString();
    }

    public static final Creator<PostDataRequest> CREATOR = new Creator<PostDataRequest>() {
        @Override
        public PostDataRequest createFromParcel(Parcel in) {
            return new PostDataRequest(in);
        }

        @Override
        public PostDataRequest[] newArray(int size) {
            return new PostDataRequest[size];
        }
    };

    public String getLoan_account_type_business_entity_proof_url() {
        return loan_account_type_business_entity_proof_url;
    }

    public void setLoan_account_type_business_entity_proof_url(String loan_account_type_business_entity_proof_url) {
        this.loan_account_type_business_entity_proof_url = loan_account_type_business_entity_proof_url;
    }

    public String getStock_inventory_amount() {
        return stock_inventory_amount;
    }

    public void setStock_inventory_amount(String stock_inventory_amount) {
        this.stock_inventory_amount = stock_inventory_amount;
    }

    public String getPrimary_applicant_pan_url() {
        return primary_applicant_pan_url;
    }

    public void setPrimary_applicant_pan_url(String primary_applicant_pan_url) {
        this.primary_applicant_pan_url = primary_applicant_pan_url;
    }

    public String getBusiness_place_photos_url() {
        return business_place_photos_url;
    }

    public void setBusiness_place_photos_url(String business_place_photos_url) {
        this.business_place_photos_url = business_place_photos_url;
    }

    public String getBusiness_place_ownership_papers() {
        return business_place_ownership_papers;
    }

    public void setBusiness_place_ownership_papers(String business_place_ownership_papers) {
        this.business_place_ownership_papers = business_place_ownership_papers;
    }

    public String getBusiness_place_address_proof_document_type() {
        return business_place_address_proof_document_type;
    }

    public void setBusiness_place_address_proof_document_type(String business_place_address_proof_document_type) {
        this.business_place_address_proof_document_type = business_place_address_proof_document_type;
    }

    public String getBusiness_place_address_proof_number() {
        return business_place_address_proof_number;
    }

    public void setBusiness_place_address_proof_number(String business_place_address_proof_number) {
        this.business_place_address_proof_number = business_place_address_proof_number;
    }

    public String getBusiness_place_longitude_captured_on_verification() {
        return business_place_longitude_captured_on_verification;
    }

    public void setBusiness_place_longitude_captured_on_verification(String business_place_longitude_captured_on_verification) {
        this.business_place_longitude_captured_on_verification = business_place_longitude_captured_on_verification;
    }

    public String getBusiness_place_seperate_residence_place() {
        return business_place_seperate_residence_place;
    }

    public void setBusiness_place_seperate_residence_place(String business_place_seperate_residence_place) {
        this.business_place_seperate_residence_place = business_place_seperate_residence_place;
    }

    public String getLoan_request_id() {
        return loan_request_id;
    }

    public void setLoan_request_id(String loan_request_id) {
        this.loan_request_id = loan_request_id;
    }

    public String getResidence_place_address_proof_url() {
        return residence_place_address_proof_url;
    }

    public void setResidence_place_address_proof_url(String residence_place_address_proof_url) {
        this.residence_place_address_proof_url = residence_place_address_proof_url;
    }

    public String getBusiness_place_address_proof_url() {
        return business_place_address_proof_url;
    }

    public void setBusiness_place_address_proof_url(String business_place_address_proof_url) {
        this.business_place_address_proof_url = business_place_address_proof_url;
    }

    public String getLoan_account_type_business_entity_proof_document_type() {
        return loan_account_type_business_entity_proof_document_type;
    }

    public void setLoan_account_type_business_entity_proof_document_type(String loan_account_type_business_entity_proof_document_type) {
        this.loan_account_type_business_entity_proof_document_type = loan_account_type_business_entity_proof_document_type;
    }

    public String getNo_employees() {
        return no_employees;
    }

    public void setNo_employees(String no_employees) {
        this.no_employees = no_employees;
    }

    public String getFixed_asset_machinery_amount() {
        return fixed_asset_machinery_amount;
    }

    public void setFixed_asset_machinery_amount(String fixed_asset_machinery_amount) {
        this.fixed_asset_machinery_amount = fixed_asset_machinery_amount;
    }

    public String getSecondary_applicant_pan_url() {
        return secondary_applicant_pan_url;
    }

    public void setSecondary_applicant_pan_url(String secondary_applicant_pan_url) {
        this.secondary_applicant_pan_url = secondary_applicant_pan_url;
    }

    public ArrayList<ReferenceUser> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<ReferenceUser> references) {
        this.references = references;
    }

    public String getBusiness_place_latitude_captured_on_verification() {
        return business_place_latitude_captured_on_verification;
    }

    public void setBusiness_place_latitude_captured_on_verification(String business_place_latitude_captured_on_verification) {
        this.business_place_latitude_captured_on_verification = business_place_latitude_captured_on_verification;
    }

    public String getLoan_account_type_business_entity_proof_number() {
        return loan_account_type_business_entity_proof_number;
    }

    public void setLoan_account_type_business_entity_proof_number(String loan_account_type_business_entity_proof_number) {
        this.loan_account_type_business_entity_proof_number = loan_account_type_business_entity_proof_number;
    }

    public String getLoan_account_type() {
        return loan_account_type;
    }

    public void setLoan_account_type(String loan_account_type) {
        this.loan_account_type = loan_account_type;
    }

    public ArrayList<ZiploanQuestion> getBusiness_information_questionnaire() {
        return business_information_questionnaire;
    }

    public void setBusiness_information_questionnaire(ArrayList<ZiploanQuestion> business_information_questionnaire) {
        this.business_information_questionnaire = business_information_questionnaire;
    }

    public String getResidence_place_address_proof_number() {
        return residence_place_address_proof_number;
    }

    public void setResidence_place_address_proof_number(String residence_place_address_proof_number) {
        this.residence_place_address_proof_number = residence_place_address_proof_number;
    }

    public String getResidence_place_address_proof_document_type() {
        return residence_place_address_proof_document_type;
    }

    public void setResidence_place_address_proof_document_type(String residence_place_address_proof_document_type) {
        this.residence_place_address_proof_document_type = residence_place_address_proof_document_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(loan_account_type_business_entity_proof_url);
        parcel.writeString(stock_inventory_amount);
        parcel.writeString(primary_applicant_pan_url);
        parcel.writeString(business_place_photos_url);
        parcel.writeString(business_place_ownership_papers);
        parcel.writeString(business_place_address_proof_document_type);
        parcel.writeString(business_place_address_proof_number);
        parcel.writeString(business_place_longitude_captured_on_verification);
        parcel.writeString(business_place_seperate_residence_place);
        parcel.writeString(loan_request_id);
        parcel.writeString(residence_place_address_proof_url);
        parcel.writeString(business_place_address_proof_url);
        parcel.writeString(loan_account_type_business_entity_proof_document_type);
        parcel.writeString(no_employees);
        parcel.writeString(fixed_asset_machinery_amount);
        parcel.writeString(secondary_applicant_pan_url);
        parcel.writeTypedList(references);
        parcel.writeString(business_place_latitude_captured_on_verification);
        parcel.writeString(loan_account_type_business_entity_proof_number);
        parcel.writeString(loan_account_type);
        parcel.writeTypedList(business_information_questionnaire);
        parcel.writeString(residence_place_address_proof_number);
        parcel.writeString(residence_place_address_proof_document_type);
    }
}