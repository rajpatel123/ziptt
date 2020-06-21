package com.ziploan.team.verification_module.caching;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ziploan.team.collection.db.BankNameTable;
import com.ziploan.team.collection.db.RecordVisitTable;
import com.ziploan.team.collection.db.SaveApplicationList;
import com.ziploan.team.verification_module.borrowerdetails.ekitsign.EsignFieldTable;
import com.ziploan.team.verification_module.caching.tables.ApplicationVerificationDetailTable;
import com.ziploan.team.verification_module.caching.tables.ApplicationsTable;
import com.ziploan.team.verification_module.caching.tables.DocumentsTable;
import com.ziploan.team.verification_module.caching.tables.FiltersTable;

/**
 * Created By Nitesh Singh
 * This Class handle All things Related to Database :
 * Create DB, Upgrade,Create table , Upgrade table, Open and Close Db
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String TAG = SQLiteHelper.class.getName();
    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "ziploanteam.db";
    private static SQLiteHelper sqliteHelper;

    private SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ApplicationsTable.createTable(db);
        ApplicationVerificationDetailTable.createTable(db);
        FiltersTable.createTable(db);
        RecordVisitTable.createTable(db);
        DocumentsTable.createTable(db);
        EsignFieldTable.createTable(db);
        SaveApplicationList.createTable(db);
        BankNameTable.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE applications_detail");
        onCreate(db);
//        switch (oldVersion) {
//            case 6:
//                String addColumnQuery6 = "ALTER TABLE " + ApplicationVerificationDetailTable.NAME + " ADD " + ApplicationVerificationDetailTable.Cols.app_not_installed_reason + " TEXT";
//                db.execSQL(addColumnQuery6);
//
//                String addColumnQuery61 = "ALTER TABLE " + ApplicationVerificationDetailTable.NAME + " ADD " + ApplicationVerificationDetailTable.Cols.business_category + " TEXT";
//                db.execSQL(addColumnQuery61);
//                break;
//            case 8:
//                String addColumnQuery8 = "ALTER TABLE " + ApplicationVerificationDetailTable.NAME + " ADD " + ApplicationVerificationDetailTable.Cols.business_category + " TEXT";
//                db.execSQL(addColumnQuery8);
//                break;
//            case 9:
//                db.execSQL("DROP TABLE IF EXISTS "+ApplicationVerificationDetailTable.NAME);
//                onCreate(db);
//                break;
//
//            case 10:
//                String addColumnQuery9 = "ALTER TABLE " + ApplicationVerificationDetailTable.NAME + " ADD " + ApplicationVerificationDetailTable.Cols.PLACE_CHANGE_REASON + " TEXT";
//                db.execSQL(addColumnQuery9);
//                break;
//        }
    }

    public static SQLiteHelper getInstance(Context mContext) {
        if (sqliteHelper == null)
            sqliteHelper = new SQLiteHelper(mContext);
        return sqliteHelper;
    }
}