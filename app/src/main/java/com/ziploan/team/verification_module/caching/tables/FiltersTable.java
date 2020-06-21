package com.ziploan.team.verification_module.caching.tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ZIploan-Nitesh on 2/2/2017.
 */

public class FiltersTable {
    public static final String NAME = "filters";

    public class Cols{
        public static final String FILTER_KEY = "filter_key";
        public static final String FILTER_NAME = "filter_name";
        public static final String FILTER_ID = "filter_id";
    }
    public static void createTable(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS "
                + NAME+" "
                + "("+ Cols.FILTER_KEY + " VARCHAR(80),"
                + Cols.FILTER_NAME + " TEXT,"
                + Cols.FILTER_ID + " VARCHAR(3),"
                + "PRIMARY KEY ("+Cols.FILTER_KEY+","+Cols.FILTER_ID+")"
                +")";
        db.execSQL(query);
    }
}
