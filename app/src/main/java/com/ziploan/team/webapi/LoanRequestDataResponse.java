package com.ziploan.team.webapi;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.verification_module.borrowerdetails.ReferenceUser;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;

import java.util.ArrayList;

/**
 * Created by ZIploan-Nitesh on 2/18/2017.
 */
public class LoanRequestDataResponse {
    @SerializedName("business_information_questionnaire") private ArrayList<ZiploanQuestion> business_information_questionnaire;
    @SerializedName("residence_place_address_proof_number") private String residence_place_address_proof_number;
    @SerializedName("loan_account_type_business_entity_proof_number") private String loan_account_type_business_entity_proof_number;
    @SerializedName("business_place_address_proof_document_type") private String business_place_address_proof_document_type;
    @SerializedName("business_place_address_proof_url") private String business_place_address_proof_url;
    @SerializedName("business_place_photos_url") private String business_place_photos_url;
    @SerializedName("residence_place_address_proof_document_type") private String residence_place_address_proof_document_type;
    @SerializedName("fixed_asset_machinery_amount") private String fixed_asset_machinery_amount;
    @SerializedName("loan_account_type_business_entity_proof_url") private String loan_account_type_business_entity_proof_url;
    @SerializedName("business_place_seperate_residence_place") private String business_place_seperate_residence_place;
    @SerializedName("references") private ArrayList<ReferenceUser> references;
    @SerializedName("loan_account_type_business_entity_proof_document_type") private String loan_account_type_business_entity_proof_document_type;
    @SerializedName("business_place_address_proof_number") private String business_place_address_proof_number;
    @SerializedName("stock_inventory_amount") private String stock_inventory_amount;
    @SerializedName("business_place_longitude_captured_on_verification") private String business_place_longitude_captured_on_verification;
    @SerializedName("business_place_latitude_captured_on_verification") private String business_place_latitude_captured_on_verification;
    @SerializedName("residence_place_address_proof_url") private String residence_place_address_proof_url;
    @SerializedName("primary_applicant_pan_url") private String primary_applicant_pan_url;
    @SerializedName("no_employees") private String no_employees;
    @SerializedName("loan_account_type") private String loan_account_type;
    @SerializedName("secondary_applicant_pan_url") private String secondary_applicant_pan_url;
    @SerializedName("ekyc_details") private ArrayList<EkycDetail> ekyc_details;

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

    public String getLoan_account_type_business_entity_proof_number() {
        return loan_account_type_business_entity_proof_number;
    }

    public void setLoan_account_type_business_entity_proof_number(String loan_account_type_business_entity_proof_number) {
        this.loan_account_type_business_entity_proof_number = loan_account_type_business_entity_proof_number;
    }

    public String getBusiness_place_address_proof_document_type() {
        return business_place_address_proof_document_type;
    }

    public void setBusiness_place_address_proof_document_type(String business_place_address_proof_document_type) {
        this.business_place_address_proof_document_type = business_place_address_proof_document_type;
    }

    public String getBusiness_place_address_proof_url() {
        return business_place_address_proof_url;
    }

    public void setBusiness_place_address_proof_url(String business_place_address_proof_url) {
        this.business_place_address_proof_url = business_place_address_proof_url;
    }

    public String getBusiness_place_photos_url() {
        return business_place_photos_url;
    }

    public void setBusiness_place_photos_url(String business_place_photos_url) {
        this.business_place_photos_url = business_place_photos_url;
    }

    public String getResidence_place_address_proof_document_type() {
        return residence_place_address_proof_document_type;
    }

    public void setResidence_place_address_proof_document_type(String residence_place_address_proof_document_type) {
        this.residence_place_address_proof_document_type = residence_place_address_proof_document_type;
    }

    public String getFixed_asset_machinery_amount() {
        return fixed_asset_machinery_amount;
    }

    public void setFixed_asset_machinery_amount(String fixed_asset_machinery_amount) {
        this.fixed_asset_machinery_amount = fixed_asset_machinery_amount;
    }

    public String getLoan_account_type_business_entity_proof_url() {
        return loan_account_type_business_entity_proof_url;
    }

    public void setLoan_account_type_business_entity_proof_url(String loan_account_type_business_entity_proof_url) {
        this.loan_account_type_business_entity_proof_url = loan_account_type_business_entity_proof_url;
    }

    public String getBusiness_place_seperate_residence_place() {
        return business_place_seperate_residence_place;
    }

    public void setBusiness_place_seperate_residence_place(String business_place_seperate_residence_place) {
        this.business_place_seperate_residence_place = business_place_seperate_residence_place;
    }

    public ArrayList<ReferenceUser> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<ReferenceUser> references) {
        this.references = references;
    }

    public String getLoan_account_type_business_entity_proof_document_type() {
        return loan_account_type_business_entity_proof_document_type;
    }

    public void setLoan_account_type_business_entity_proof_document_type(String loan_account_type_business_entity_proof_document_type) {
        this.loan_account_type_business_entity_proof_document_type = loan_account_type_business_entity_proof_document_type;
    }

    public String getBusiness_place_address_proof_number() {
        return business_place_address_proof_number;
    }

    public void setBusiness_place_address_proof_number(String business_place_address_proof_number) {
        this.business_place_address_proof_number = business_place_address_proof_number;
    }

    public String getStock_inventory_amount() {
        return stock_inventory_amount;
    }

    public void setStock_inventory_amount(String stock_inventory_amount) {
        this.stock_inventory_amount = stock_inventory_amount;
    }

    public String getBusiness_place_longitude_captured_on_verification() {
        return business_place_longitude_captured_on_verification;
    }

    public void setBusiness_place_longitude_captured_on_verification(String business_place_longitude_captured_on_verification) {
        this.business_place_longitude_captured_on_verification = business_place_longitude_captured_on_verification;
    }

    public String getBusiness_place_latitude_captured_on_verification() {
        return business_place_latitude_captured_on_verification;
    }

    public void setBusiness_place_latitude_captured_on_verification(String business_place_latitude_captured_on_verification) {
        this.business_place_latitude_captured_on_verification = business_place_latitude_captured_on_verification;
    }

    public String getResidence_place_address_proof_url() {
        return residence_place_address_proof_url;
    }

    public void setResidence_place_address_proof_url(String residence_place_address_proof_url) {
        this.residence_place_address_proof_url = residence_place_address_proof_url;
    }

    public String getPrimary_applicant_pan_url() {
        return primary_applicant_pan_url;
    }

    public void setPrimary_applicant_pan_url(String primary_applicant_pan_url) {
        this.primary_applicant_pan_url = primary_applicant_pan_url;
    }

    public String getNo_employees() {
        return no_employees;
    }

    public void setNo_employees(String no_employees) {
        this.no_employees = no_employees;
    }

    public ArrayList<EkycDetail> getEkyc_details() {
        return ekyc_details;
    }

    public void setEkyc_details(ArrayList<EkycDetail> ekyc_details) {
        this.ekyc_details = ekyc_details;
    }

    public String getLoan_account_type() {
        return loan_account_type;
    }

    public void setLoan_account_type(String loan_account_type) {
        this.loan_account_type = loan_account_type;
    }

    public String getSecondary_applicant_pan_url() {
        return secondary_applicant_pan_url;
    }

    public void setSecondary_applicant_pan_url(String secondary_applicant_pan_url) {
        this.secondary_applicant_pan_url = secondary_applicant_pan_url;
    }
}
