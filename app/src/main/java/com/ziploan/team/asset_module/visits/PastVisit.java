package com.ziploan.team.asset_module.visits;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;

import java.io.Serializable;
import java.util.ArrayList;

public class PastVisit implements Serializable, Parcelable{
    @SerializedName("business_operational_at_given_business_address")
    private String business_operational_at_given_business_address;
    @SerializedName("number_of_customers")
    private String number_of_customers;
    @SerializedName("employee_number_on_site")
    private String employee_number_on_site;
    @SerializedName("sign_board_availability")
    private String sign_board_availability;
    @SerializedName("average_creditor_cycle")
    private String average_creditor_cycle;
    @SerializedName("employee_number_declaration")
    private String employee_number_declaration;
    @SerializedName("asset_manager_name")
    private String asset_manager_name;
    @SerializedName("date_of_visit")
    private String date_of_visit;
    @SerializedName("time_of_visit")
    private String time_of_visit;
    @SerializedName("asset_or_machinery_value_in_rupees")
    private String asset_or_machinery_value_in_rupees;
    @SerializedName("raw_material_value_in_rupees")
    private String raw_material_value_in_rupees;
    @SerializedName("average_debtor_cycle")
    private String average_debtor_cycle;
    @SerializedName("business_premise_availability")
    private String business_premise_availability;
    @SerializedName("stock_or_inventory_in_rupees")
    private String stock_or_inventory_in_rupees;
    @SerializedName("person_met")
    private String person_met;
    @SerializedName("business_place_photo")
    private Object business_place_photo;
    @SerializedName("nature_of_work")
    private String nature_of_work;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("visited_business_address")
    private String visited_business_address;
    @SerializedName("feedback")
    private String feedback;
    @SerializedName("app_uninstallation_reason")
    private String app_uninstallation_reason;
    @SerializedName("recorded_coordinates")
    private ArrayList<String> recorded_coordinates;
    @SerializedName("other_questions")
    private ArrayList<ZiploanQuestion> other_questions;
    @SerializedName("asset_manager_feedback")
    private String asset_manager_feedback;
    @SerializedName("param")
    private String param;
    @SerializedName("visit_unsucessful_comments")
    private String visit_unsucessful_comments;


    protected PastVisit(Parcel in) {
        business_operational_at_given_business_address = in.readString();
        number_of_customers = in.readString();
        employee_number_on_site = in.readString();
        sign_board_availability = in.readString();
        average_creditor_cycle = in.readString();
        employee_number_declaration = in.readString();
        asset_manager_name = in.readString();
        date_of_visit = in.readString();
        time_of_visit = in.readString();
        asset_or_machinery_value_in_rupees = in.readString();
        raw_material_value_in_rupees = in.readString();
        average_debtor_cycle = in.readString();
        business_premise_availability = in.readString();
        stock_or_inventory_in_rupees = in.readString();
        person_met = in.readString();
        nature_of_work = in.readString();
        created_at = in.readString();
        recorded_coordinates = in.createStringArrayList();
        other_questions = in.createTypedArrayList(ZiploanQuestion.CREATOR);
        asset_manager_feedback = in.readString();
        param = in.readString();
        visit_unsucessful_comments = in.readString();
    }

    public static final Creator<PastVisit> CREATOR = new Creator<PastVisit>() {
        @Override
        public PastVisit createFromParcel(Parcel in) {
            return new PastVisit(in);
        }

        @Override
        public PastVisit[] newArray(int size) {
            return new PastVisit[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(business_operational_at_given_business_address);
        parcel.writeString(number_of_customers);
        parcel.writeString(employee_number_on_site);
        parcel.writeString(sign_board_availability);
        parcel.writeString(average_creditor_cycle);
        parcel.writeString(employee_number_declaration);
        parcel.writeString(asset_manager_name);
        parcel.writeString(date_of_visit);
        parcel.writeString(time_of_visit);
        parcel.writeString(asset_or_machinery_value_in_rupees);
        parcel.writeString(raw_material_value_in_rupees);
        parcel.writeString(average_debtor_cycle);
        parcel.writeString(business_premise_availability);
        parcel.writeString(stock_or_inventory_in_rupees);
        parcel.writeString(person_met);
        parcel.writeString(nature_of_work);
        parcel.writeString(created_at);
        parcel.writeStringList(recorded_coordinates);
        parcel.writeTypedList(other_questions);
        parcel.writeString(asset_manager_feedback);
        parcel.writeString(param);
        parcel.writeString(visit_unsucessful_comments);
    }

    public String getBusiness_operational_at_given_business_address() {
        return business_operational_at_given_business_address;
    }

    public void setBusiness_operational_at_given_business_address(String business_operational_at_given_business_address) {
        this.business_operational_at_given_business_address = business_operational_at_given_business_address;
    }

    public String getNumber_of_customers() {
        return number_of_customers;
    }

    public void setNumber_of_customers(String number_of_customers) {
        this.number_of_customers = number_of_customers;
    }

    public String getEmployee_number_on_site() {
        return employee_number_on_site;
    }

    public void setEmployee_number_on_site(String employee_number_on_site) {
        this.employee_number_on_site = employee_number_on_site;
    }

    public String getSign_board_availability() {
        return sign_board_availability;
    }

    public void setSign_board_availability(String sign_board_availability) {
        this.sign_board_availability = sign_board_availability;
    }

    public String getAverage_creditor_cycle() {
        return average_creditor_cycle;
    }

    public void setAverage_creditor_cycle(String average_creditor_cycle) {
        this.average_creditor_cycle = average_creditor_cycle;
    }

    public String getEmployee_number_declaration() {
        return employee_number_declaration;
    }

    public void setEmployee_number_declaration(String employee_number_declaration) {
        this.employee_number_declaration = employee_number_declaration;
    }

    public String getAsset_manager_name() {
        return asset_manager_name;
    }

    public void setAsset_manager_name(String asset_manager_name) {
        this.asset_manager_name = asset_manager_name;
    }

    public String getDate_of_visit() {
        return date_of_visit;
    }

    public void setDate_of_visit(String date_of_visit) {
        this.date_of_visit = date_of_visit;
    }

    public String getAsset_or_machinery_value_in_rupees() {
        return asset_or_machinery_value_in_rupees;
    }

    public void setAsset_or_machinery_value_in_rupees(String asset_or_machinery_value_in_rupees) {
        this.asset_or_machinery_value_in_rupees = asset_or_machinery_value_in_rupees;
    }

    public String getRaw_material_value_in_rupees() {
        return raw_material_value_in_rupees;
    }

    public void setRaw_material_value_in_rupees(String raw_material_value_in_rupees) {
        this.raw_material_value_in_rupees = raw_material_value_in_rupees;
    }

    public String getAverage_debtor_cycle() {
        return average_debtor_cycle;
    }

    public void setAverage_debtor_cycle(String average_debtor_cycle) {
        this.average_debtor_cycle = average_debtor_cycle;
    }

    public String getBusiness_premise_availability() {
        return business_premise_availability;
    }

    public void setBusiness_premise_availability(String business_premise_availability) {
        this.business_premise_availability = business_premise_availability;
    }

    public String getStock_or_inventory_in_rupees() {
        return stock_or_inventory_in_rupees;
    }

    public void setStock_or_inventory_in_rupees(String stock_or_inventory_in_rupees) {
        this.stock_or_inventory_in_rupees = stock_or_inventory_in_rupees;
    }

    public String getPerson_met() {
        return person_met;
    }

    public void setPerson_met(String person_met) {
        this.person_met = person_met;
    }

    public Object getBusiness_place_photo() {
        return business_place_photo;
    }

    public void setBusiness_place_photo(ArrayList<String> business_place_photo) {
        this.business_place_photo = business_place_photo;
    }

    public String getNature_of_work() {
        return nature_of_work;
    }

    public void setNature_of_work(String nature_of_work) {
        this.nature_of_work = nature_of_work;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public ArrayList<String> getRecorded_coordinates() {
        return recorded_coordinates;
    }

    public void setRecorded_coordinates(ArrayList<String> recorded_coordinates) {
        this.recorded_coordinates = recorded_coordinates;
    }

    public ArrayList<ZiploanQuestion> getOther_questions() {
        return other_questions;
    }

    public void setOther_questions(ArrayList<ZiploanQuestion> other_questions) {
        this.other_questions = other_questions;
    }

    public String getVisited_business_address() {
        return visited_business_address;
    }

    public void setVisited_business_address(String visited_business_address) {
        this.visited_business_address = visited_business_address;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getApp_uninstallation_reason() {
        return app_uninstallation_reason;
    }

    public void setApp_uninstallation_reason(String app_uninstallation_reason) {
        this.app_uninstallation_reason = app_uninstallation_reason;
    }

    public String getAsset_manager_feedback() {
        return asset_manager_feedback;
    }

    public void setAsset_manager_feedback(String asset_manager_feedback) {
        this.asset_manager_feedback = asset_manager_feedback;
    }

    public void setBusiness_place_photo(Object business_place_photo) {
        this.business_place_photo = business_place_photo;
    }

    public String getVisit_unsucessful_comments() {
        return visit_unsucessful_comments;
    }

    public void setVisit_unsucessful_comments(String visit_unsucessful_comments) {
        this.visit_unsucessful_comments = visit_unsucessful_comments;
    }

    public String getVisitDate(){
        return ZiploanUtil.changeDateFormat(this.date_of_visit,"dd-MM-yyyy","dd MMMM YYYY");
    }
    public String getVisitTime(){
        return ZiploanUtil.changeDateFormat(this.time_of_visit,"HH:mm","HH:mm a");
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String businessOperational(){
        if(this.business_operational_at_given_business_address==null)
            return this.business_operational_at_given_business_address;
        return (this.business_operational_at_given_business_address.equalsIgnoreCase("1"))?"YES":(this.business_operational_at_given_business_address.equalsIgnoreCase("0"))?"NO":this.business_operational_at_given_business_address;
    }
    public String businessPremiseAvailablity(){
        if(this.business_premise_availability==null)
            return this.business_premise_availability;
        return (this.business_premise_availability.equalsIgnoreCase("1"))?"YES":(this.business_premise_availability.equalsIgnoreCase("0"))?"NO":this.business_premise_availability;
    }
    public String signBoardObserved(){
        if(this.sign_board_availability==null)
            return this.sign_board_availability;
        return (this.sign_board_availability.equalsIgnoreCase("1"))?"YES":(this.sign_board_availability.equalsIgnoreCase("0"))?"NO":this.sign_board_availability;
    }

    public String getTime_of_visit() {
        return time_of_visit;
    }

    public void setTime_of_visit(String time_of_visit) {
        this.time_of_visit = time_of_visit;
    }

    public String getFeedbackCustom(){
        if(!TextUtils.isEmpty(this.feedback))
            return this.feedback;
        else
            return "N.A.";
    }
    public String fetchAssetManagerFeedback(){
        if(!TextUtils.isEmpty(this.asset_manager_feedback))
            return this.asset_manager_feedback;
        else
            return "N.A.";
    }
    public String getAPpInstallCustom(){
        if(!TextUtils.isEmpty(this.app_uninstallation_reason))
            return this.app_uninstallation_reason;
        else
            return "N.A.";
    }
    public String rawMaterialValue(){
        if(!TextUtils.isEmpty(this.raw_material_value_in_rupees))
            return "₹ "+this.raw_material_value_in_rupees;
        else
            return "N.A.";
    }
    public String stockValue(){
        if(!TextUtils.isEmpty(this.stock_or_inventory_in_rupees))
            return "₹ "+this.stock_or_inventory_in_rupees;
        else
            return "N.A.";
    }
    public String assetValue(){
        if(!TextUtils.isEmpty(this.asset_or_machinery_value_in_rupees))
            return "₹ "+this.asset_or_machinery_value_in_rupees;
        else
            return "N.A.";
    }

    public boolean isPhotoAvailable(){
        if(this.business_place_photo!=null){
            if(this.business_place_photo instanceof String){
                return (!TextUtils.isEmpty((CharSequence) this.business_place_photo) && URLUtil.isValidUrl((String) this.business_place_photo))?true:false;
            }else {
                return ((ArrayList<String>)this.business_place_photo).size()>0;
            }
        }
        return false;
    }
}