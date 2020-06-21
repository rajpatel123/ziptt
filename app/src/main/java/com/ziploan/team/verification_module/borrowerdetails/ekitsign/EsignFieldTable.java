package com.ziploan.team.verification_module.borrowerdetails.ekitsign;

import android.database.sqlite.SQLiteDatabase;

import com.ziploan.team.verification_module.caching.tables.DocumentsTable;

/**
 * Created by ZIploan-Nitesh on 2/2/2017.
 */

public class EsignFieldTable {
    public static final String NAME = "esign";

    public class Cols{
        public static final String ZL_FEILD_DATA = "data";
        public static final String ZL_NUMBER = "zl_number";
        public static final String DOC_ID ="id" ;
        public static final String ZL_FILES = "files";

    }
    public static void createTable(SQLiteDatabase db) {
        String query =  "CREATE TABLE " + NAME + "("
                + Cols.ZL_NUMBER + " TEXT PRIMARY KEY,"
                + Cols.DOC_ID + " INTEGER ,"
                + Cols.ZL_FILES + " TEXT,"
                + Cols.ZL_FEILD_DATA + " TEXT"
                + ")";
        db.execSQL(query);
    }
}
