package com.ziploan.team.webapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ZIploan-Nitesh on 8/21/2017.
 */

public class RecordNewVisitPojo implements Serializable, Parcelable{
    @SerializedName("loan_request_id") private String loan_request_id;
    @SerializedName("asset_manager_id") private String asset_manager_id;
    @SerializedName("person_met") private String person_met;
    @SerializedName("nature_of_work") private String nature_of_work;
    @SerializedName("business_premise_availability") private String business_premise_availability;
    @SerializedName("business_operational_at_given_business_address") private String business_operational_at_given_business_address;
    @SerializedName("employee_number_declaration") private String employee_number_declaration;
    @SerializedName("employee_number_on_site") private String employee_number_on_site;
    @SerializedName("sign_board_availability") private String sign_board_availability;
    @SerializedName("raw_material_value_in_rupees") private String raw_material_value_in_rupees;
    @SerializedName("stock_or_inventory_in_rupees") private String stock_or_inventory_in_rupees;
    @SerializedName("asset_or_machinery_value_in_rupees") private String asset_or_machinery_value_in_rupees;
    @SerializedName("number_of_customers") private String number_of_customers;
    @SerializedName("average_debtor_cycle") private String average_debtor_cycle;
    @SerializedName("average_creditor_cycle") private String average_creditor_cycle;
    @SerializedName("business_place_photo") private ArrayList<String> business_place_photo;
    @SerializedName("date_of_visit") private String date_of_visit;
    @SerializedName("time_of_visit") private String time_of_visit;
    @SerializedName("visited_business_address") private String visited_business_address;
    @SerializedName("recorded_coordinates") private String recorded_coordinates;
    @SerializedName("secondary_applicant_alternative_no") private String secondary_applicant_alternative_no;
    @SerializedName("primary_applicant_alternative_no") private String primary_applicant_alternative_no;
    @SerializedName("feedback") private String feedback;
    @SerializedName("app_uninstallation_reason") private String app_uninstallation_reason;
    @SerializedName("other_questions") private ArrayList<ZiploanQuestion> other_questions;
    @SerializedName("asset_manager_feedback") private String asset_manager_feedback;
    @SerializedName("param") private String param;
    @SerializedName("visit_unsucessful_comments") private String visit_unsucessful_comments;
    @SerializedName("customer_agreed") private String customer_agreed;
    @SerializedName("business_status") private String business_status;
    @SerializedName("change_in_business_address") private String change_in_business_address;

    public RecordNewVisitPojo() {
    }

    protected RecordNewVisitPojo(Parcel in) {
        loan_request_id = in.readString();
        asset_manager_id = in.readString();
        person_met = in.readString();
        nature_of_work = in.readString();
        business_premise_availability = in.readString();
        business_operational_at_given_business_address = in.readString();
        employee_number_declaration = in.readString();
        employee_number_on_site = in.readString();
        sign_board_availability = in.readString();
        raw_material_value_in_rupees = in.readString();
        stock_or_inventory_in_rupees = in.readString();
        asset_or_machinery_value_in_rupees = in.readString();
        number_of_customers = in.readString();
        average_debtor_cycle = in.readString();
        average_creditor_cycle = in.readString();
        business_place_photo = in.createStringArrayList();
        date_of_visit = in.readString();
        time_of_visit = in.readString();
        visited_business_address = in.readString();
        recorded_coordinates = in.readString();
        secondary_applicant_alternative_no = in.readString();
        primary_applicant_alternative_no = in.readString();
        feedback = in.readString();
        app_uninstallation_reason = in.readString();
        other_questions = in.createTypedArrayList(ZiploanQuestion.CREATOR);
        asset_manager_feedback = in.readString();
        param = in.readString();
        visit_unsucessful_comments = in.readString();
        customer_agreed = in.readString();
        business_status = in.readString();
        change_in_business_address = in.readString();
    }

    public static final Creator<RecordNewVisitPojo> CREATOR = new Creator<RecordNewVisitPojo>() {
        @Override
        public RecordNewVisitPojo createFromParcel(Parcel in) {
            return new RecordNewVisitPojo(in);
        }

        @Override
        public RecordNewVisitPojo[] newArray(int size) {
            return new RecordNewVisitPojo[size];
        }
    };

    public String getCustomer_agreed() {
        return customer_agreed;
    }

    public void setCustomer_agreed(String customer_agreed) {
        this.customer_agreed = customer_agreed;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public String getLoan_request_id() {
        return loan_request_id;
    }

    public void setLoan_request_id(String loan_request_id) {
        this.loan_request_id = loan_request_id;
    }

    public String getAsset_manager_id() {
        return asset_manager_id;
    }

    public void setAsset_manager_id(String asset_manager_id) {
        this.asset_manager_id = asset_manager_id;
    }

    public String getPerson_met() {
        return person_met;
    }

    public void setPerson_met(String person_met) {
        this.person_met = person_met;
    }

    public String getBusiness_premise_availability() {
        return business_premise_availability;
    }

    public void setBusiness_premise_availability(String business_premise_availability) {
        this.business_premise_availability = business_premise_availability;
    }

    public String getBusiness_operational_at_given_business_address() {
        return business_operational_at_given_business_address;
    }

    public void setBusiness_operational_at_given_business_address(String business_operational_at_given_business_address) {
        this.business_operational_at_given_business_address = business_operational_at_given_business_address;
    }

    public String getEmployee_number_declaration() {
        return employee_number_declaration;
    }

    public void setEmployee_number_declaration(String employee_number_declaration) {
        this.employee_number_declaration = employee_number_declaration;
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

    public String getRaw_material_value_in_rupees() {
        return raw_material_value_in_rupees;
    }

    public void setRaw_material_value_in_rupees(String raw_material_value_in_rupees) {
        this.raw_material_value_in_rupees = raw_material_value_in_rupees;
    }

    public String getStock_or_inventory_in_rupees() {
        return stock_or_inventory_in_rupees;
    }

    public void setStock_or_inventory_in_rupees(String stock_or_inventory_in_rupees) {
        this.stock_or_inventory_in_rupees = stock_or_inventory_in_rupees;
    }

    public String getAsset_or_machinery_value_in_rupees() {
        return asset_or_machinery_value_in_rupees;
    }

    public void setAsset_or_machinery_value_in_rupees(String asset_or_machinery_value_in_rupees) {
        this.asset_or_machinery_value_in_rupees = asset_or_machinery_value_in_rupees;
    }

    public String getNumber_of_customers() {
        return number_of_customers;
    }

    public void setNumber_of_customers(String number_of_customers) {
        this.number_of_customers = number_of_customers;
    }

    public String getAverage_debtor_cycle() {
        return average_debtor_cycle;
    }

    public void setAverage_debtor_cycle(String average_debtor_cycle) {
        this.average_debtor_cycle = average_debtor_cycle;
    }

    public String getAverage_creditor_cycle() {
        return average_creditor_cycle;
    }

    public void setAverage_creditor_cycle(String average_creditor_cycle) {
        this.average_creditor_cycle = average_creditor_cycle;
    }

    public ArrayList<String> getBusiness_place_photo() {
        return business_place_photo;
    }

    public void setBusiness_place_photo(ArrayList<String> business_place_photo) {
        this.business_place_photo = business_place_photo;
    }

    public String getDate_of_visit() {
        return date_of_visit;
    }

    public void setDate_of_visit(String date_of_visit) {
        this.date_of_visit = date_of_visit;
    }

    public String getVisited_business_address() {
        return visited_business_address;
    }

    public void setVisited_business_address(String visited_business_address) {
        this.visited_business_address = visited_business_address;
    }

    public String getNature_of_work() {
        return nature_of_work;
    }

    public void setNature_of_work(String nature_of_work) {
        this.nature_of_work = nature_of_work;
    }

    public String getRecorded_coordinates() {
        return recorded_coordinates;
    }

    public void setRecorded_coordinates(String recorded_coordinates) {
        this.recorded_coordinates = recorded_coordinates;
    }

    public String getSecondary_applicant_alternative_no() {
        return secondary_applicant_alternative_no;
    }

    public void setSecondary_applicant_alternative_no(String secondary_applicant_alternative_no) {
        this.secondary_applicant_alternative_no = secondary_applicant_alternative_no;
    }

    public String getPrimary_applicant_alternative_no() {
        return primary_applicant_alternative_no;
    }

    public void setPrimary_applicant_alternative_no(String primary_applicant_alternative_no) {
        this.primary_applicant_alternative_no = primary_applicant_alternative_no;
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

    public ArrayList<ZiploanQuestion> getOther_questions() {
        return other_questions;
    }

    public void setOther_questions(ArrayList<ZiploanQuestion> other_questions) {
        this.other_questions = other_questions;
    }

    public String getAsset_manager_feedback() {
        return asset_manager_feedback;
    }

    public void setAsset_manager_feedback(String asset_manager_feedback) {
        this.asset_manager_feedback = asset_manager_feedback;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getTime_of_visit() {
        return time_of_visit;
    }

    public void setTime_of_visit(String time_of_visit) {
        this.time_of_visit = time_of_visit;
    }

    public String getVisit_unsucessful_comments() {
        return visit_unsucessful_comments;
    }

    public void setVisit_unsucessful_comments(String visit_unsucessful_comments) {
        this.visit_unsucessful_comments = visit_unsucessful_comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(loan_request_id);
        parcel.writeString(asset_manager_id);
        parcel.writeString(person_met);
        parcel.writeString(nature_of_work);
        parcel.writeString(business_premise_availability);
        parcel.writeString(business_operational_at_given_business_address);
        parcel.writeString(employee_number_declaration);
        parcel.writeString(employee_number_on_site);
        parcel.writeString(sign_board_availability);
        parcel.writeString(raw_material_value_in_rupees);
        parcel.writeString(stock_or_inventory_in_rupees);
        parcel.writeString(asset_or_machinery_value_in_rupees);
        parcel.writeString(number_of_customers);
        parcel.writeString(average_debtor_cycle);
        parcel.writeString(average_creditor_cycle);
        parcel.writeStringList(business_place_photo);
        parcel.writeString(date_of_visit);
        parcel.writeString(time_of_visit);
        parcel.writeString(visited_business_address);
        parcel.writeString(recorded_coordinates);
        parcel.writeString(secondary_applicant_alternative_no);
        parcel.writeString(primary_applicant_alternative_no);
        parcel.writeString(feedback);
        parcel.writeString(app_uninstallation_reason);
        parcel.writeTypedList(other_questions);
        parcel.writeString(asset_manager_feedback);
        parcel.writeString(param);
        parcel.writeString(visit_unsucessful_comments);
        parcel.writeString(business_status);
        parcel.writeString(customer_agreed);
        parcel.writeString(change_in_business_address);
    }

    public String getChange_in_business_address() {
        return change_in_business_address;
    }

    public void setChange_in_business_address(String change_in_business_address) {
        this.change_in_business_address = change_in_business_address;
    }
}
