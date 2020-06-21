package com.ziploan.team.collection.db;

import android.database.sqlite.SQLiteDatabase;

public class RecordVisitTable {

    public static final String NAME = "record_visit";

    public class Cols{
        public static final String LOAN_REQUEST_ID = "loan_request_id";
        public static final String LOAN_APP_NO = "loan_application_number";
        public static final String AMOUNT = "amount";
        public static final String BANK_NAME = "bank_name";
        public static final String COMMENT = "comment";
        public static final String DENOMINATIONS = "denominations";
        public static final String IS_PERSON_MET = "is_person_met";
        public static final String MODE = "mode";
        public static final String PERSON_MET = "person_met";
        public static final String REFERENCE_NUMBER = "refrence_number";
        public static final String VALUE_DATE = "value_date";
        public static final String FILE_URL = "file_url";
    }
    public static void createTable(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS "
                + NAME+" "
                + "("+Cols.LOAN_REQUEST_ID + " VARCHAR(40) PRIMARY KEY,"
                + Cols.LOAN_APP_NO + " VARCHAR(40),"
                + Cols.AMOUNT + " VARCHAR(40),"
                + Cols.BANK_NAME + " VARCHAR(40),"
                + Cols.COMMENT + " VARCHAR(200),"
                + Cols.DENOMINATIONS + " VARCHAR(255),"
                + Cols.IS_PERSON_MET + " VARCHAR(10),"
                + Cols.MODE + " VARCHAR(30),"
                + Cols.PERSON_MET + " VARCHAR(30),"
                + Cols.REFERENCE_NUMBER + " VARCHAR(30),"
                + Cols.VALUE_DATE + " VARCHAR(20),"
                + Cols.FILE_URL + " VARCHAR(40)"
                +")";
        db.execSQL(query);
    }
}
