package com.ziploan.team.verification_module.caching.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ZIploan-Nitesh on 2/2/2017.
 */

public class ApplicationsTable {
    public static final String NAME = "applications";

    public class Cols{
        public static final String LOAN_REQUEST_ID = "loan_request_id";
        public static final String LOAN_APP_NO = "loan_application_number";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String BUSINESS_NAME = "business_name";
        public static final String BUSINESS_ADDRESS = "business_address";
        public static final String LOAN_REQUEST_STATUS = "loan_request_status";
        public static final String LOAN_REQUEST_STATUS_NAME = "loan_request_status_name";
        public static final String MOBILE = "mobile";
        public static final String LOAN_REQUEST_ASSIGNED_DATE = "loan_request_assigned_date";
        public static final String EKYC_STATUS = "ekyc_status";
    }
    public static void createTable(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS "
                + NAME+" "
                + "("+Cols.LOAN_REQUEST_ID + " VARCHAR(40) PRIMARY KEY,"
                + Cols.LOAN_APP_NO + " VARCHAR(14),"
                + Cols.FIRST_NAME + " VARCHAR(40),"
                + Cols.LAST_NAME + " VARCHAR(40),"
                + Cols.BUSINESS_NAME + " TEXT,"
                + Cols.BUSINESS_ADDRESS + " TEXT,"
                + Cols.LOAN_REQUEST_STATUS + " VARCHAR(4),"
                + Cols.LOAN_REQUEST_STATUS_NAME + " VARCHAR(30),"
                + Cols.MOBILE + " VARCHAR(13),"
                + Cols.LOAN_REQUEST_ASSIGNED_DATE + " VARCHAR(30),"
                + Cols.EKYC_STATUS + " VARCHAR(1)"
                +")";
        db.execSQL(query);
    }
}
