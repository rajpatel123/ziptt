package com.ziploan.team.webapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.verification_module.borrowerdetails.BankInfoModel;
import com.ziploan.team.verification_module.borrowerdetails.FamilyReferences;
import com.ziploan.team.verification_module.borrowerdetails.ReferenceUser;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanNewQuestion;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZIploan-Nitesh on 2/10/2017.
 */

public class VerificationDetailsPostRequest implements Parcelable {
    @SerializedName("business_category")
    private String business_category;
    @SerializedName("app_not_installed_reason")
    private String app_not_installed_reason;
    @SerializedName("calling_source")
    private int callingSource;
    @SerializedName("loan_request_id")
    private String loan_request_id;

    @SerializedName("references")
    private ArrayList<ReferenceUser> references;


    @SerializedName("family_references")
    private ArrayList<FamilyReferences> familyReferences;


    private List<BankInfoModel> loan_details;

    @SerializedName("business_place_photos_url")
    private ArrayList<String> business_place_photos_url;
    @SerializedName("business_information_questionnaire")
    private ArrayList<ZiploanNewQuestion> business_information_questionnaire;
    @SerializedName("business_place_seperate_residence_place")
    private Integer business_place_seperate_residence_place;
    @SerializedName("stock_inventory_amount")
    private String stock_inventory_amount;
    @SerializedName("fixed_asset_machinery_amount")
    private String fixed_asset_machinery_amount;
    @SerializedName("no_employees")
    private String no_employees;
    @SerializedName("primary_applicant_pan_url")
    private String primary_applicant_pan_url;
    @SerializedName("secondary_applicant_pan_url")
    private String secondary_applicant_pan_url;
    @SerializedName("loan_account_type")
    private int loan_account_type;
    @SerializedName("loan_account_type_business_entity_proof_document_type")
    private String loan_account_type_business_entity_proof_document_type;
    @SerializedName("loan_account_type_business_entity_proof_number")
    private String loan_account_type_business_entity_proof_number;
    @SerializedName("loan_account_type_business_entity_proof_url")
    private String loan_account_type_business_entity_proof_url;
    @SerializedName("business_place_address_proof_url")
    private String business_place_address_proof_url;
    @SerializedName("business_place_address_proof_number")
    private String business_place_address_proof_number;
    @SerializedName("business_place_address_proof_document_type")
    private String business_place_address_proof_document_type;
    @SerializedName("residence_place_address_proof_document_type")
    private String residence_place_address_proof_document_type;
    @SerializedName("residence_place_address_proof_number")
    private String residence_place_address_proof_number;
    @SerializedName("residence_place_address_proof_url")
    private String residence_place_address_proof_url;
    @SerializedName("business_place_longitude_captured_on_verification")
    private double business_place_longitude_captured_on_verification;
    @SerializedName("business_place_latitude_captured_on_verification")
    private double business_place_latitude_captured_on_verification;
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

    @SerializedName("nature_of_work")
    private String nature_of_work;

    public String getText_nature_of_work() {
        return text_nature_of_work;
    }

    public void setText_nature_of_work(String text_nature_of_work) {
        this.text_nature_of_work = text_nature_of_work;
    }

    @SerializedName("text_nature_of_work")
    private String text_nature_of_work;

    @SerializedName("designation_in_office")
    private String designation_in_office;

    @SerializedName("sign_board_observed")
    private String sign_board_observed;
    @SerializedName("email")
    private String email;
    @SerializedName("long_shot_photos_url")
    private ArrayList<String> business_place_long_shot_photos_url;

    @SerializedName("id_proof_photos_url")
    private ArrayList<String> id_proof_photos_url;
    @SerializedName("business_stability")
    private String business_stability;
    public String getBusiness_stability_details() {
        return business_stability_details;
    }

    public void setBusiness_stability_details(String business_stability_details) {
        this.business_stability_details = business_stability_details;
    }

    @SerializedName("business_stability_details")
    private String business_stability_details;

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

    public VerificationDetailsPostRequest() {
    }

    protected VerificationDetailsPostRequest(Parcel in) {
        business_category = in.readString();
        app_not_installed_reason = in.readString();
        callingSource = in.readInt();
        loan_request_id = in.readString();
        references = in.createTypedArrayList(ReferenceUser.CREATOR);
        familyReferences = in.createTypedArrayList(FamilyReferences.CREATOR);
        business_place_photos_url = (ArrayList<String>) in.readSerializable();
        business_place_long_shot_photos_url = (ArrayList<String>) in.readSerializable();
        id_proof_photos_url = (ArrayList<String>) in.readSerializable();
        business_information_questionnaire = in.createTypedArrayList(ZiploanNewQuestion.CREATOR);
        business_place_seperate_residence_place = in.readInt();
        stock_inventory_amount = in.readString();
        fixed_asset_machinery_amount = in.readString();
        no_employees = in.readString();
        primary_applicant_pan_url = in.readString();
        secondary_applicant_pan_url = in.readString();
        loan_account_type = in.readInt();
        loan_account_type_business_entity_proof_document_type = in.readString();
        loan_account_type_business_entity_proof_number = in.readString();
        loan_account_type_business_entity_proof_url = in.readString();
        business_place_address_proof_url = in.readString();
        business_place_address_proof_number = in.readString();
        business_place_address_proof_document_type = in.readString();
        residence_place_address_proof_document_type = in.readString();
        residence_place_address_proof_number = in.readString();
        residence_place_address_proof_url = in.readString();
        business_place_longitude_captured_on_verification = in.readDouble();
        business_place_latitude_captured_on_verification = in.readDouble();

        raw_material = in.readString();
        rent_amount = in.readInt();
        investment_in_business = in.readString();
        no_of_machines = in.readString();
        actual_no_of_employees = in.readString();

        person_met = in.readString();
        nature_of_work = in.readString();
        designation_in_office = in.readString();
        sign_board_observed = in.readString();
        email = in.readString();
        loan_details = in.createTypedArrayList(BankInfoModel.CREATOR);

        business_stability = in.readString();
        landmark = in.readString();
        business_address_same_as_dashboard = in.readString();
        locality_business_place = in.readString();
        education_qualification = in.readString();
        business_address_same_as_dashboard_reason = in.readString();
    }

    public static final Creator<VerificationDetailsPostRequest> CREATOR = new Creator<VerificationDetailsPostRequest>() {
        @Override
        public VerificationDetailsPostRequest createFromParcel(Parcel in) {
            return new VerificationDetailsPostRequest(in);
        }

        @Override
        public VerificationDetailsPostRequest[] newArray(int size) {
            return new VerificationDetailsPostRequest[size];
        }
    };

    public List<BankInfoModel> getBank_info() {
        return loan_details;
    }

    public void setBank_info(List<BankInfoModel> bank_info) {
        this.loan_details = bank_info;
    }

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

    public String getLoan_request_id() {
        return loan_request_id;
    }

    public void setLoan_request_id(String loan_request_id) {
        this.loan_request_id = loan_request_id;
    }



    public ArrayList<ReferenceUser> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<ReferenceUser> references) {
        this.references = references;
    }

    public void setFamilyReferences(ArrayList<FamilyReferences> familyReferences) {
        this.familyReferences = familyReferences;
    }



    public ArrayList<FamilyReferences> getFamilyReferences() {
        return familyReferences;
    }



    public int getBusiness_place_seperate_residence_place() {
        return business_place_seperate_residence_place;
    }

    public void setBusiness_place_seperate_residence_place(int business_place_seperate_residence_place) {
        this.business_place_seperate_residence_place = business_place_seperate_residence_place;
    }

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

    public String getNo_employees() {
        return no_employees;
    }

    public void setNo_employees(String no_employees) {
        this.no_employees = no_employees;
    }

    public String getPrimary_applicant_pan_url() {
        return primary_applicant_pan_url;
    }

    public void setPrimary_applicant_pan_url(String primary_applicant_pan_url) {
        this.primary_applicant_pan_url = primary_applicant_pan_url;
    }

    public String getSecondary_applicant_pan_url() {
        return secondary_applicant_pan_url;
    }

    public void setSecondary_applicant_pan_url(String secondary_applicant_pan_url) {
        this.secondary_applicant_pan_url = secondary_applicant_pan_url;
    }

    public int getLoan_account_type() {
        return loan_account_type;
    }

    public void setLoan_account_type(int loan_account_type) {
        this.loan_account_type = loan_account_type;
    }

    public String getLoan_account_type_business_entity_proof_document_type() {
        return loan_account_type_business_entity_proof_document_type;
    }

    public void setLoan_account_type_business_entity_proof_document_type(String loan_account_type_business_entity_proof_document_type) {
        this.loan_account_type_business_entity_proof_document_type = loan_account_type_business_entity_proof_document_type;
    }

    public String getLoan_account_type_business_entity_proof_number() {
        return loan_account_type_business_entity_proof_number;
    }

    public void setLoan_account_type_business_entity_proof_number(String loan_account_type_business_entity_proof_number) {
        this.loan_account_type_business_entity_proof_number = loan_account_type_business_entity_proof_number;
    }

    public String getLoan_account_type_business_entity_proof_url() {
        return loan_account_type_business_entity_proof_url;
    }

    public void setLoan_account_type_business_entity_proof_url(String loan_account_type_business_entity_proof_url) {
        this.loan_account_type_business_entity_proof_url = loan_account_type_business_entity_proof_url;
    }

    public String getBusiness_place_address_proof_url() {
        return business_place_address_proof_url;
    }

    public void setBusiness_place_address_proof_url(String business_place_address_proof_url) {
        this.business_place_address_proof_url = business_place_address_proof_url;
    }

    public String getBusiness_place_address_proof_number() {
        return business_place_address_proof_number;
    }

    public void setBusiness_place_address_proof_number(String business_place_address_proof_number) {
        this.business_place_address_proof_number = business_place_address_proof_number;
    }

    public String getBusiness_place_address_proof_document_type() {
        return business_place_address_proof_document_type;
    }

    public void setBusiness_place_address_proof_document_type(String business_place_address_proof_document_type) {
        this.business_place_address_proof_document_type = business_place_address_proof_document_type;
    }

    public String getResidence_place_address_proof_document_type() {
        return residence_place_address_proof_document_type;
    }

    public void setResidence_place_address_proof_document_type(String residence_place_address_proof_document_type) {
        this.residence_place_address_proof_document_type = residence_place_address_proof_document_type;
    }

    public String getResidence_place_address_proof_number() {
        return residence_place_address_proof_number;
    }

    public void setResidence_place_address_proof_number(String residence_place_address_proof_number) {
        this.residence_place_address_proof_number = residence_place_address_proof_number;
    }

    public String getResidence_place_address_proof_url() {
        return residence_place_address_proof_url;
    }

    public void setResidence_place_address_proof_url(String residence_place_address_proof_url) {
        this.residence_place_address_proof_url = residence_place_address_proof_url;
    }

    public double getBusiness_place_longitude_captured_on_verification() {
        return business_place_longitude_captured_on_verification;
    }

    public void setBusiness_place_longitude_captured_on_verification(double business_place_longitude_captured_on_verification) {
        this.business_place_longitude_captured_on_verification = business_place_longitude_captured_on_verification;
    }

    public double getBusiness_place_latitude_captured_on_verification() {
        return business_place_latitude_captured_on_verification;
    }

    public void setBusiness_place_latitude_captured_on_verification(double business_place_latitude_captured_on_verification) {
        this.business_place_latitude_captured_on_verification = business_place_latitude_captured_on_verification;
    }

    public ArrayList<String> getBusiness_place_photos_url() {
        return business_place_photos_url;
    }

    public void setBusiness_place_photos_url(ArrayList<String> business_place_photos_url) {
        this.business_place_photos_url = business_place_photos_url;
    }

//    public ArrayList<ZiploanQuestion> getBusiness_information_questionnaire() {
//        return business_information_questionnaire;
//    }
//
//    public void setBusiness_information_questionnaire(ArrayList<ZiploanQuestion> business_information_questionnaire) {
//        this.business_information_questionnaire = business_information_questionnaire;
//    }

    public ArrayList<ZiploanNewQuestion> getBusiness_information_questionnaire() {
        return business_information_questionnaire;
    }

    public void setBusiness_information_questionnaire(ArrayList<ZiploanNewQuestion> business_information_questionnaire) {
        this.business_information_questionnaire = business_information_questionnaire;
    }

    public int getCallingSource() {
        return callingSource;
    }

    public void setCallingSource(int callingSource) {
        this.callingSource = callingSource;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(callingSource);
        parcel.writeString(loan_request_id);
        parcel.writeTypedList(references);
        parcel.writeTypedList(familyReferences);
        parcel.writeSerializable(business_place_photos_url);
        parcel.writeTypedList(business_information_questionnaire);
        parcel.writeInt(business_place_seperate_residence_place);
        parcel.writeString(stock_inventory_amount);
        parcel.writeString(fixed_asset_machinery_amount);
        parcel.writeString(no_employees);
        parcel.writeString(primary_applicant_pan_url);
        parcel.writeString(secondary_applicant_pan_url);
        parcel.writeInt(loan_account_type);
        parcel.writeString(loan_account_type_business_entity_proof_document_type);
        parcel.writeString(loan_account_type_business_entity_proof_number);
        parcel.writeString(loan_account_type_business_entity_proof_url);
        parcel.writeString(business_place_address_proof_url);
        parcel.writeString(business_place_address_proof_number);
        parcel.writeString(business_place_address_proof_document_type);
        parcel.writeString(residence_place_address_proof_document_type);
        parcel.writeString(residence_place_address_proof_number);
        parcel.writeString(residence_place_address_proof_url);
        parcel.writeDouble(business_place_longitude_captured_on_verification);
        parcel.writeDouble(business_place_latitude_captured_on_verification);

        parcel.writeString(raw_material);
        parcel.writeInt(rent_amount);
        parcel.writeString(investment_in_business);
        parcel.writeString(no_of_machines);
        parcel.writeString(actual_no_of_employees);

        parcel.writeString(person_met);
        parcel.writeString(nature_of_work);
        parcel.writeString(designation_in_office);
        parcel.writeString(sign_board_observed);

        parcel.writeString(app_not_installed_reason);
        parcel.writeString(business_category);
        parcel.writeString(email);
        parcel.writeTypedList(loan_details);
        parcel.writeString(business_stability);
        parcel.writeString(landmark);
        parcel.writeString(business_address_same_as_dashboard);
        parcel.writeString(locality_business_place);
        parcel.writeString(education_qualification);
        parcel.writeSerializable(business_place_long_shot_photos_url);
        parcel.writeSerializable(id_proof_photos_url);
        parcel.writeSerializable(business_address_same_as_dashboard_reason);
    }

    public ArrayList<String> getBusiness_place_long_shot_photos_url() {
        return business_place_long_shot_photos_url;
    }

    public void setBusiness_place_long_shot_photos_url(ArrayList<String> business_place_long_shot_photos_url) {
        this.business_place_long_shot_photos_url = business_place_long_shot_photos_url;
    }

    public ArrayList<String> getId_proof_photos_url() {
        return id_proof_photos_url;
    }

    public void setId_proof_photos_url(ArrayList<String> id_proof_photos_url) {
        this.id_proof_photos_url = id_proof_photos_url;
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

    public String getBusiness_address_same_as_dashboard_reason() {
        return business_address_same_as_dashboard_reason;
    }

    public void setBusiness_address_same_as_dashboard_reason(String business_address_same_as_dashboard_reason) {
        this.business_address_same_as_dashboard_reason = business_address_same_as_dashboard_reason;
    }
}
