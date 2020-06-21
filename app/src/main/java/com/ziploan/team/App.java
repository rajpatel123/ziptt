package com.ziploan.team;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.crashlytics.android.Crashlytics;
import com.evernote.android.job.JobManager;
import com.ziploan.team.collection.model.bank_names.Response;
import com.ziploan.team.verification_module.caching.FilterItem;
import com.ziploan.team.verification_module.caching.FilterKey;
import com.ziploan.team.verification_module.caching.SQLiteHelper;
import com.ziploan.team.verification_module.services.ZiploanTeamJobCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class App extends Application {
    public static List<Response> BankData;
    private static SQLiteDatabase db;
    private static App _instance;
    public static HashMap<String,ArrayList<FilterItem>> filtersMain = new HashMap<>();
    public static HashMap<String,ArrayList<FilterItem>> filters = new HashMap<>();
    public static ArrayList<FilterKey> filtersKeyMain = new ArrayList<>();
    public static ArrayList<FilterKey> filtersKey = new ArrayList<>();
    public static Map<String,String> filters_params = new HashMap<>();
    public static boolean activityListVisible;
    public static boolean refreshBankNames;

    public static SQLiteDatabase getDb() {
        return db;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        JobManager.create(this).addJobCreator(new ZiploanTeamJobCreator());
        db = SQLiteHelper.getInstance(getApplicationContext()).getWritableDatabase();
    }

    public static Context getInstance() {
        if (_instance == null) {
            _instance = new App();
        }
        return _instance;
    }
}