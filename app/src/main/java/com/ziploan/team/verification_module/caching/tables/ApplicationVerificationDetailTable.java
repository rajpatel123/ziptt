package com.ziploan.team.verification_module.caching.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ZIploan-Nitesh on 2/2/2017.
 */

public class ApplicationVerificationDetailTable {
    public static final String NAME = "applications_detail";

    public class Cols{

        public static final String RAW_MATERIAL = "raw_material";
        public static final String rent_amount = "rent_amount";
        public static final String investment_in_business = "investment_in_business";
        public static final String no_of_machines = "no_of_machines";
        public static final String actual_no_of_employees = "actual_no_of_employees";
        public static final String person_met = "person_met";
        public static final String nature_of_work = "nature_of_work";
        public static final String TEXT_nature_of_work = "text_nature_of_work";
        public static final String TEXT_DESIGNATION = "text_designation";
        public static final String designation_in_office = "designation_in_office";
        public static final String sign_board_observed = "sign_board_observed";
        public static final String app_not_installed_reason = "app_not_installed_reason";
        public static final String business_category = "business_category";


        public static final String LOAN_REQUEST_ID = "loan_request_id";
        public static final String REFERENCE = "user_references";
        public static final String REFERENCE_family = "user_references_family";
        public static final String BANK_INFO = "loan_details";
        public static final String BUSINESS_RESIDENCE_SEPERATE = "business_residence_seperate";
        public static final String BUSINESS_PLACE_PHOTO = "business_place_photo";
        public static final String BUSINESS_LONG_SHOT_PHOTO = "business_long_shot_photo";
        public static final String BUSINESS_ID_PROOF_PHOTO = "business_id_proof_photo";
        public static final String QUESTIONS = "questions";
        public static final String STOCK_INVENTORY_AMOUNT = "stock_inventory_amount";
        public static final String ASSET_MACHINERY_AMOUNT = "fixed_asset_machinery_amount";
        public static final String EMPLOYEE_NO = "no_employees";
        public static final String PRIMARY_APPLICANT_PAN = "primary_applicant_pan_url";
        public static final String SECONDARY_APPLICANT_PAN = "secondary_applicant_pan_url";
        public static final String LOAN_ACCOUNT_TYPE = "loan_account_type";
        public static final String ENTITY_DOCUMENT_TYPE = "entity_document_type";
        public static final String ENTITY_DOCUMENT_NO = "entity_document_no";
        public static final String ENTITY_DOCUMENT_URL = "entity_document_url";
        public static final String BUSINESS_ADD_PROOF_DOC_TYPE = "business_add_proof_doc_type";
        public static final String BUSINESS_ADD_PROOF_DOC_NO = "business_add_proof_doc_no";
        public static final String BUSINESS_ADD_PROOF_DOC_URL = "business_add_proof_doc_url";
        public static final String RESIDENCE_ADD_PROOF_DOC_TYPE = "residence_add_proof_doc_type";
        public static final String RESIDENCE_ADD_PROOF_DOC_NO = "residence_add_proof_doc_no";
        public static final String RESIDENCE_ADD_PROOF_DOC_URL = "residence_add_proof_doc_url";
        public static final String CUSTOM_ENTITY_TYPE = "custom_entity_type";
        public static final String CUSTOM_BUSINESS_ADD_TYPE = "custom_business_add_type";
        public static final String CUSTOM_RESIDENCE_ADD_TYPE = "custom_residence_add_type";
        public static final String LAT = "lattitude";
        public static final String LNG = "longitude";
        public static final String IS_FINAL = "is_final"; // wil contain 0/1

        public static final String STABILITY = "business_stability";
        public static final String PLACE_CHANGE_REASON = "business_place_change_reason";
        public static final String LANDMARK = "landmark";
        public static final String BUSINESS_ADDRESS_DASHBOARD = "business_address_same_as_dashboard";
        public static final String BUSINESS_ADDRESS_DASHBOARD_REASON = "business_address_same_as_dashboard_reason";
        public static final String locality_business_place = "locality_business_place";
        public static final String education_qualification = "education_qualification";

    }
    public static void createTable(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS "
                + NAME+" "
                + "("+ Cols.LOAN_REQUEST_ID + " VARCHAR(40) PRIMARY KEY,"
                + Cols.REFERENCE + " TEXT,"
                + Cols.REFERENCE_family + " TEXT,"
                + Cols.BANK_INFO + " TEXT,"
                + Cols.BUSINESS_RESIDENCE_SEPERATE + " VARCHAR(10),"
                + Cols.BUSINESS_PLACE_PHOTO + " TEXT,"
                + Cols.BUSINESS_LONG_SHOT_PHOTO + " TEXT,"
                + Cols.BUSINESS_ID_PROOF_PHOTO + " TEXT,"
                + Cols.QUESTIONS + " TEXT,"
                + Cols.STOCK_INVENTORY_AMOUNT + " VARCHAR(15),"
                + Cols.ASSET_MACHINERY_AMOUNT + " VARCHAR(15),"
                + Cols.EMPLOYEE_NO + " VARCHAR(15),"
                + Cols.PRIMARY_APPLICANT_PAN + " TEXT,"
                + Cols.SECONDARY_APPLICANT_PAN + " TEXT,"
                + Cols.LOAN_ACCOUNT_TYPE + " TEXT,"
                + Cols.ENTITY_DOCUMENT_TYPE + " TEXT,"
                + Cols.ENTITY_DOCUMENT_NO + " TEXT,"
                + Cols.TEXT_DESIGNATION + " TEXT,"
                + Cols.RAW_MATERIAL + " TEXT,"
                + Cols.rent_amount + " TEXT,"
                + Cols.investment_in_business + " TEXT,"
                + Cols.no_of_machines + " TEXT,"
                + Cols.actual_no_of_employees + " TEXT,"
                + Cols.person_met + " TEXT,"
                + Cols.BUSINESS_ADDRESS_DASHBOARD_REASON + " TEXT,"
                + Cols.nature_of_work + " TEXT,"
                + Cols.TEXT_nature_of_work + " TEXT,"
                + Cols.designation_in_office + " TEXT,"
                + Cols.sign_board_observed + " TEXT,"
                + Cols.app_not_installed_reason + " TEXT,"
                + Cols.business_category + " TEXT,"


                + Cols.ENTITY_DOCUMENT_URL + " TEXT,"
                + Cols.BUSINESS_ADD_PROOF_DOC_TYPE + " TEXT,"
                + Cols.BUSINESS_ADD_PROOF_DOC_NO + " TEXT,"
                + Cols.BUSINESS_ADD_PROOF_DOC_URL + " TEXT,"
                + Cols.RESIDENCE_ADD_PROOF_DOC_TYPE + " TEXT,"
                + Cols.RESIDENCE_ADD_PROOF_DOC_NO + " TEXT,"
                + Cols.RESIDENCE_ADD_PROOF_DOC_URL + " TEXT,"
                + Cols.CUSTOM_ENTITY_TYPE + " TEXT,"
                + Cols.CUSTOM_BUSINESS_ADD_TYPE + " TEXT,"
                + Cols.CUSTOM_RESIDENCE_ADD_TYPE + " TEXT,"
                + Cols.LAT + " TEXT,"
                + Cols.LNG + " TEXT,"

                + Cols.STABILITY + " TEXT,"
                + Cols.PLACE_CHANGE_REASON + " TEXT,"
                + Cols.LANDMARK + " TEXT,"
                + Cols.BUSINESS_ADDRESS_DASHBOARD + " TEXT,"
                + Cols.locality_business_place + " TEXT,"
                + Cols.education_qualification + " TEXT,"

                + Cols.IS_FINAL + " TEXT"
                +")";
        db.execSQL(query);
    }


}
