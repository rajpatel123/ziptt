package com.ziploan.team.verification_module.borrowerdetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZIploan-Nitesh on 2/15/2017.
 */
public class ZiploanSiteInfo implements Parcelable {
    @SerializedName("arrReferenceUsers")
    private ArrayList<ReferenceUser> arrReferenceUsers = new ArrayList<>();


    @SerializedName("arrFamilyReferenceUsers")
    private ArrayList<FamilyReferences> arrFamilyReferenceUsers = new ArrayList<>();
//    @SerializedName("business_place_seperate_residence_place")
//    private int business_place_seperate_residence_place;
    @SerializedName("stock_inventory_amount")
    private String stock_inventory_amount;
    @SerializedName("fixed_asset_machinery_amount")
    private String fixed_asset_machinery_amount;
    @SerializedName("no_employees")
    private int no_employees;
    @SerializedName("bisiness_place_photo_url")
    private List<ZiploanPhoto> bisiness_place_photo_url;

    @SerializedName("long_shot_photos_url")
    private List<ZiploanPhoto> long_shot_photos;

    @SerializedName("id_proof_photos_url")
    private List<ZiploanPhoto> id_proof_photos;

    @SerializedName("raw_material")
    private String raw_material;
    @SerializedName("rent_amount")
    private int rent_amount;
    @SerializedName("investment_in_business")
    private String investment_in_business;
    @SerializedName("no_of_machines")
    private String no_of_machines;
    @SerializedName("actual_no_of_employees")
    private String actual_no_of_employees;

    @SerializedName("person_met")
    private String person_met;
    @SerializedName("email")
    private String email;
    @SerializedName("nature_of_work")
    private String nature_of_work;
    @SerializedName("designation_in_office")
    private String designation_in_office;
    @SerializedName("sign_board_observed")
    private String sign_board_observed;

    @SerializedName("app_not_installed_reason")
    private String app_not_installed_reason;

    @SerializedName("business_category")
    private String business_category;

    @SerializedName("text_nature_of_work")
    private String text_nature_of_work;

    @SerializedName("text_designation")
    private String text_designation;

    @SerializedName("business_stability")
    private String business_stability;


    public String getBusiness_place_change_reason() {
        return business_place_change_reason;
    }

    public void setBusiness_place_change_reason(String business_place_change_reason) {
        this.business_place_change_reason = business_place_change_reason;
    }

    //new parameter added and need to verify with backend
    @SerializedName("business_stability_details")
    private String business_place_change_reason;

    @SerializedName("landmark")
    private String landmark;

    @SerializedName("business_address_same_as_dashboard")
    private String business_address_same_as_dashboard;

    @SerializedName("locality_business_place")
    private String locality_business_place;

    @SerializedName("education_qualification")
    private String education_qualification;
    @SerializedName("business_address_same_as_dashboard_reason")
    private String business_address_same_as_dashboard_reason;


    public String getBusiness_category() {
        return business_category;
    }

    public void setBusiness_category(String business_category) {
        this.business_category = business_category;
    }

    public String getApp_not_installed_reason() {
        return app_not_installed_reason;
    }

    public void setApp_not_installed_reason(String app_not_installed_reason) {
        this.app_not_installed_reason = app_not_installed_reason;
    }

    public ZiploanSiteInfo() {
    }

    protected ZiploanSiteInfo(Parcel in) {
        arrReferenceUsers = in.createTypedArrayList(ReferenceUser.CREATOR);
        arrFamilyReferenceUsers = in.createTypedArrayList(FamilyReferences.CREATOR);
      //  business_place_seperate_residence_place = in.readInt();
        stock_inventory_amount = in.readString();
        fixed_asset_machinery_amount = in.readString();
        no_employees = in.readInt();

        raw_material = in.readString();
        rent_amount = in.readInt();
        investment_in_business = in.readString();
        no_of_machines = in.readString();
        actual_no_of_employees = in.readString();

        person_met = in.readString();
        nature_of_work = in.readString();
        designation_in_office = in.readString();
        sign_board_observed = in.readString();

        app_not_installed_reason = in.readString();
        business_category = in.readString();

        bisiness_place_photo_url = in.createTypedArrayList(ZiploanPhoto.CREATOR);
        email = in.readString();
        text_nature_of_work = in.readString();
        text_designation = in.readString();

        business_stability = in.readString();
        landmark = in.readString();
        business_address_same_as_dashboard = in.readString();
        locality_business_place = in.readString();
        education_qualification = in.readString();
        business_address_same_as_dashboard_reason = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(arrReferenceUsers);
        parcel.writeTypedList(arrFamilyReferenceUsers);
        //parcel.writeInt(business_place_seperate_residence_place);
        parcel.writeString(stock_inventory_amount);
        parcel.writeString(fixed_asset_machinery_amount);
        parcel.writeInt(no_employees);
        parcel.writeTypedList(bisiness_place_photo_url);

        parcel.writeString(raw_material);
        parcel.writeInt(rent_amount);
        parcel.writeString(investment_in_business);
        parcel.writeString(no_of_machines);
        parcel.writeString(actual_no_of_employees);

        parcel.writeString(person_met);
        parcel.writeString(nature_of_work);
        parcel.writeString(designation_in_office);
        parcel.writeString(sign_board_observed);
        parcel.writeString(business_category);

        parcel.writeString(app_not_installed_reason);
        parcel.writeString(email);
        parcel.writeString(text_nature_of_work);
        parcel.writeString(text_designation);

        parcel.writeString(business_stability);
        parcel.writeString(landmark);
        parcel.writeString(business_address_same_as_dashboard);
        parcel.writeString(locality_business_place);
        parcel.writeString(education_qualification);
        parcel.writeString(business_address_same_as_dashboard_reason);
    }

    public static final Creator<ZiploanSiteInfo> CREATOR = new Creator<ZiploanSiteInfo>() {
        @Override
        public ZiploanSiteInfo createFromParcel(Parcel in) {
            return new ZiploanSiteInfo(in);
        }

        @Override
        public ZiploanSiteInfo[] newArray(int size) {
            return new ZiploanSiteInfo[size];
        }
    };

    public String getRaw_material() {
        return raw_material;
    }

    public void setRaw_material(String raw_material) {
        this.raw_material = raw_material;
    }

    public int getRent_amount() {
        return rent_amount;
    }

    public void setRent_amount(int rent_amount) {
        this.rent_amount = rent_amount;
    }

    public String getInvestment_in_business() {
        return investment_in_business;
    }

    public void setInvestment_in_business(String investment_in_business) {
        this.investment_in_business = investment_in_business;
    }

    public String getNo_of_machines() {
        return no_of_machines;
    }

    public void setNo_of_machines(String no_of_machines) {
        this.no_of_machines = no_of_machines;
    }

    public String getActual_no_of_employees() {
        return actual_no_of_employees;
    }

    public void setActual_no_of_employees(String actual_no_of_employees) {
        this.actual_no_of_employees = actual_no_of_employees;
    }

    public String getPerson_met() {
        return person_met;
    }

    public void setPerson_met(String person_met) {
        this.person_met = person_met;
    }

    public String getNature_of_work() {
        return nature_of_work;
    }

    public void setNature_of_work(String nature_of_work) {
        this.nature_of_work = nature_of_work;
    }

    public String getDesignation_in_office() {
        return designation_in_office;
    }

    public void setDesignation_in_office(String designation_in_office) {
        this.designation_in_office = designation_in_office;
    }

    public String getSign_board_observed() {
        return sign_board_observed;
    }

    public void setSign_board_observed(String sign_board_observed) {
        this.sign_board_observed = sign_board_observed;
    }

    public ArrayList<ReferenceUser> getArrReferenceUsers() {
        return arrReferenceUsers;
    }



    public ArrayList<FamilyReferences> getArrFamilyReferenceUsers() {
        return arrFamilyReferenceUsers;
    }

    public void setArrReferenceUsers(ArrayList<ReferenceUser> arrReferenceUsers) {
        this.arrReferenceUsers = arrReferenceUsers;
    }


    public void setArrFamilyReferenceUsers(ArrayList<FamilyReferences> arrFamilyReferenceUsers) {
        this.arrFamilyReferenceUsers = arrFamilyReferenceUsers;
    }



//
//    public int getBusiness_place_seperate_residence_place() {
//        return business_place_seperate_residence_place;
//    }
//
//    public void setBusiness_place_seperate_residence_place(int business_place_seperate_residence_place) {
//        this.business_place_seperate_residence_place = business_place_seperate_residence_place;
//    }

    public String getStock_inventory_amount() {
        return stock_inventory_amount;
    }

    public void setStock_inventory_amount(String stock_inventory_amount) {
        this.stock_inventory_amount = stock_inventory_amount;
    }

    public String getFixed_asset_machinery_amount() {
        return fixed_asset_machinery_amount;
    }

    public void setFixed_asset_machinery_amount(String fixed_asset_machinery_amount) {
        this.fixed_asset_machinery_amount = fixed_asset_machinery_amount;
    }

    public int getNo_employees() {
        return no_employees;
    }

    public void setNo_employees(int no_employees) {
        this.no_employees = no_employees;
    }

    public List<ZiploanPhoto> getBusiness_place_photo_url() {
        return bisiness_place_photo_url;
    }

    public void setBusiness_place_photo_url(List<ZiploanPhoto> bisiness_place_photo_url) {
        this.bisiness_place_photo_url = bisiness_place_photo_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPerson_email() {
        return email;
    }

    public void setPerson_email(String person_email) {
        this.email = person_email;
    }


    public String getText_nature_of_work() {
        return text_nature_of_work;
    }

    public void setText_nature_of_work(String text_nature_of_work) {
        this.text_nature_of_work = text_nature_of_work;
    }

    public String getText_designation() {
        return text_designation;
    }

    public void setText_designation(String text_designation) {
        this.text_designation = text_designation;
    }

    public String getBusiness_stability() {
        return business_stability;
    }

    public void setBusiness_stability(String business_stability) {
        this.business_stability = business_stability;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getBusiness_address_same_as_dashboard() {
        return business_address_same_as_dashboard;
    }

    public void setBusiness_address_same_as_dashboard(String business_address_same_as_dashboard) {
        this.business_address_same_as_dashboard = business_address_same_as_dashboard;
    }

    public String getLocality_business_place() {
        return locality_business_place;
    }

    public void setLocality_business_place(String locality_business_place) {
        this.locality_business_place = locality_business_place;
    }

    public String getEducation_qualification() {
        return education_qualification;
    }

    public void setEducation_qualification(String education_qualification) {
        this.education_qualification = education_qualification;
    }

    public List<ZiploanPhoto> getLong_shot_photos() {
        return long_shot_photos;
    }

    public void setLong_shot_photos(List<ZiploanPhoto> long_shot_photos) {
        this.long_shot_photos = long_shot_photos;
    }

    public List<ZiploanPhoto> getId_proof_photos() {
        return id_proof_photos;
    }

    public void setId_proof_photos(List<ZiploanPhoto> id_proof_photos) {
        this.id_proof_photos = id_proof_photos;
    }

    public String getBusiness_address_same_as_dashboard_reason() {
        return business_address_same_as_dashboard_reason;
    }

    public void setBusiness_address_same_as_dashboard_reason(String business_address_same_as_dashboard_reason) {
        this.business_address_same_as_dashboard_reason = business_address_same_as_dashboard_reason;
    }
}
