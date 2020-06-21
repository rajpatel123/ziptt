package com.ziploan.team.collection.db;

import android.database.sqlite.SQLiteDatabase;

public class BankNameTable {

    public static final String NAME = "bank_names";

    public class Cols{
        public static final String BANK_NAME = "bank_name";
        public static final String BANK_CODE = "bank_code";
    }

    public static void createTable(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS "
                + NAME+" "
                + "("+Cols.BANK_CODE + " VARCHAR(40) PRIMARY KEY,"
                + Cols.BANK_NAME + " VARCHAR(40)"
                +")";
        db.execSQL(query);
    }
}
