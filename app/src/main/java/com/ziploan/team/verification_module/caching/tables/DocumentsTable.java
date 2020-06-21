package com.ziploan.team.verification_module.caching.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ZIploan-Nitesh on 2/2/2017.
 */

public class DocumentsTable {
    public static final String NAME = "documents";

    public class Cols{
        public static final String ZL_DATA = "data";
        public static final String ZL_NUMBER = "zl_number";
        public static final String DOC_ID ="id" ;
    }
    public static void createTable(SQLiteDatabase db) {
        String query =  "CREATE TABLE " + NAME + "("
                + Cols.ZL_NUMBER + " TEXT PRIMARY KEY,"
                + Cols.DOC_ID + " INTEGER,"
                + Cols.ZL_DATA + " TEXT"
                + ")";
        db.execSQL(query);
    }
}
