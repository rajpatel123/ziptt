package com.ziploan.team.collection.db;

import android.database.sqlite.SQLiteDatabase;

public class SaveApplicationList {

    public static final String NAME = "save_application";

    public class Cols{
        public static final String LOAN_REQUEST_ID = "loan_request_id";
        public static final String LOAN_APP_NO = "loan_application_number";
        public static final String AMOUNT_OVERDUE = "amount_overdue";
        public static final String APPLICATION_NAME = "applicant_name";
        public static final String BUSINESS_STATE = "business_state";
        public static final String BUSINESS_PINCODE = "business_pincode";
        public static final String BUSINESS_CITY = "business_city";
        public static final String BUSINESS_ADDESS = "business_address";
        public static final String BUSINESS_NAME = "business_name";
        public static final String DELIQUENCY_BUCKET = "deliquency_bucket";
        public static final String EMI = "emi";
        public static final String FORECLOSURE_AMOUNT = "foreclosure_amount";
        public static final String LAST_VIST_DATE = "last_visit_date";
        public static final String LOAN_AMOUNT = "loan_amount";
        public static final String MOBILE_NUMBER = "mobile_number";
        public static final String OVERDUE_BREAKUP = "overdue_breakup";
        public static final String PORTFOLIO_AMOUNT = "portfolio_amount";
        public static final String RESIDENCE_ADDRESS = "residence_address";
        public static final String LAST_VISIT_COORDINATES = "last_visited_coordinates";
        public static final String APPLICANT_REFERENCE_NUMBER = "applicant_references";
    }

    public static void createTable(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS "
                + NAME+" "
                + "("+Cols.LOAN_REQUEST_ID + " VARCHAR(40) PRIMARY KEY,"
                + Cols.LOAN_APP_NO + " VARCHAR(40),"
                + Cols.AMOUNT_OVERDUE + " VARCHAR(40),"
                + Cols.APPLICATION_NAME + " VARCHAR(40),"
                + Cols.BUSINESS_STATE + " VARCHAR(200),"
                + Cols.BUSINESS_PINCODE + " VARCHAR(255),"
                + Cols.BUSINESS_CITY + " VARCHAR(10),"
                + Cols.BUSINESS_ADDESS + " VARCHAR(30),"
                + Cols.BUSINESS_NAME + " VARCHAR(30),"
                + Cols.DELIQUENCY_BUCKET + " VARCHAR(20),"
                + Cols.EMI + " VARCHAR(40),"
                + Cols.FORECLOSURE_AMOUNT + " VARCHAR(40),"
                + Cols.LAST_VIST_DATE + " VARCHAR(40),"
                + Cols.LOAN_AMOUNT + " VARCHAR(200),"
                + Cols.MOBILE_NUMBER + " VARCHAR(20),"
                + Cols.LAST_VISIT_COORDINATES + " VARCHAR(20),"
                + Cols.OVERDUE_BREAKUP + " VARCHAR(500),"
                + Cols.APPLICANT_REFERENCE_NUMBER + " VARCHAR(500),"
                + Cols.PORTFOLIO_AMOUNT + " VARCHAR(100),"
                + Cols.RESIDENCE_ADDRESS + " VARCHAR(100)"
                +")";
        db.execSQL(query);
    }
}
