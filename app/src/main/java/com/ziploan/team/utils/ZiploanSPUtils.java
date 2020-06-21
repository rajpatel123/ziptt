package com.ziploan.team.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;

import com.ziploan.team.webapi.BusinessCategory;
import com.ziploan.team.webapi.model.sub_cat_.BusinessCategoryDatum;
import com.ziploan.team.webapi.model.sub_cat_.SubCategoryList;

import java.util.ArrayList;
import java.util.Objects;

public class ZiploanSPUtils {

    public static final String TAG = ZiploanSPUtils.class.getName();
    public static final String SHARED_PREF_NAME = "ziploan_team_preference";
    public static final int PRIVATE_MODE = Context.MODE_MULTI_PROCESS;
    private static SharedPreferences _pref;
    private static ZiploanSPUtils _instance;
    private static Context mContext;


    public ZiploanSPUtils() {
    }


    public void saveBusinessCategories(ArrayList<BusinessCategory> bcList) {
        SharedPreferences prefs = _pref;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Key.BUSINESS_CATEGORIES, ObjectSerializer.serialize(bcList));
        editor.commit();
    }

    public void saveBusinessSubCategories(ArrayList<BusinessCategoryDatum> bcList) {
        SharedPreferences prefs = _pref;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Key.BUSINESS_SUB_CATEGORIES, ObjectSerializer.serialize(bcList));
        editor.apply();
    }

    public ArrayList<BusinessCategoryDatum> getBusinessSubCategories() {
        SharedPreferences prefs = _pref;
        ArrayList arrayList = (ArrayList<BusinessCategoryDatum>) ObjectSerializer.deserialize(prefs.getString(Key.BUSINESS_SUB_CATEGORIES, ObjectSerializer.serialize(new ArrayList<BusinessCategoryDatum>())));
        return arrayList;
    }

    public ArrayList<BusinessCategory> getBusinessCategories() {
        SharedPreferences prefs = _pref;
        ArrayList arrayList = (ArrayList<BusinessCategory>) ObjectSerializer.deserialize(prefs.getString(Key.BUSINESS_CATEGORIES, ObjectSerializer.serialize(new ArrayList<BusinessCategory>())));
        return arrayList;
    }

    public static ZiploanSPUtils getInstance(Context context) {
        mContext = context;
        if (_pref == null) {
            _pref = context.getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE);
        }
        if (_instance == null) {
            _instance = new ZiploanSPUtils();
        }
        return _instance;
    }


    public boolean isLoggedIn() {
        return getBoolean(Key.IS_LOGGED_IN, false);
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        setBoolean(Key.IS_LOGGED_IN, isLoggedIn);
    }

    /**
     * This Method Clear shared preference.
     */
    public void clear() {
        SharedPreferences.Editor editor = _pref.edit();
        editor.remove(Key.IS_LOGGED_IN).commit();
    }

    private void setString(String key, String value) {
        if (key != null && value != null) {
            try {
                if (_pref != null) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.putString(key, value);
                    editor.apply();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //Logger.e(TAG, "Unable to set " + key + "= " + value + "in shared preference", e);
            }
        }
    }

    private void setLong(String key, long value) {
        if (key != null) {
            try {
                if (_pref != null) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.putLong(key, value);
                    editor.apply();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Logger.e(TAG, "Unable to set " + key + "= " + value + "in shared preference", e);
            }
        }
    }

    private void setInt(String key, int value) {
        if (key != null) {
            try {
                if (_pref != null) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.putInt(key, value);
                    editor.apply();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Logger.e(TAG, "Unable to set " + key + "= " + value + "in shared preference", e);
            }
        }
    }

    private void setDouble(String key, double value) {
        if (key != null) {
            try {
                if (_pref != null) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.putFloat(key, (float) value);
                    editor.apply();
                }
            } catch (Exception e) {e.printStackTrace();
                // Logger.e(TAG, "Unable to set " + key + "= " + value + "in shared preference", e);
            }
        }
    }

    private void setBoolean(String key, boolean value) {
        if (key != null) {
            try {
                if (_pref != null) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.putBoolean(key, value);
                    editor.apply();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Logger.e(TAG, "Unable to set " + key + "= " + value + "in shared preference", e);
            }
        }
    }

    private int getInt(String key, int defaultValue) {
        if (_pref != null && key != null && _pref.contains(key)) {
            return _pref.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    private long getLong(String key, long defaultValue) {
        if (_pref != null && key != null && _pref.contains(key)) {
            return _pref.getLong(key, defaultValue);
        }
        return defaultValue;
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        if (_pref != null && key != null && _pref.contains(key)) {
            return _pref.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }

    private String getString(String key, String defaultValue) {
        if (_pref != null && key != null && _pref.contains(key)) {
            return _pref.getString(key, defaultValue);
        }
        return defaultValue;
    }

    private double getDouble(String key, double defaultValue) {
        if (_pref != null && key != null && _pref.contains(key)) {
            return _pref.getFloat(key, (float) defaultValue);
        }
        return defaultValue;
    }

    private void removeString(String key) {
        if (key != null) {
            try {
                if (_pref != null && _pref.contains(key)) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.remove(key);
                    editor.apply();
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Logger.e(TAG, "Unable to remove key" + key, e);
            }
        }
    }

    public void setAccessToken(String accessToken) {
        setString(Key.ACCESS_TOKEN, accessToken);
    }

    public String getAccessToken() {
        return getString(Key.ACCESS_TOKEN, "");
    }

    public void setExpirationDate(String expirationDate) {
        setString(Key.ACCESS_EXPIRATION_TIME, expirationDate);
    }

    public long getCollectionSavedDate() {
        return getLong(Key.COLLECTION_SAVE_DATE, 0);
    }

    public void setCollectionSavedDate(long expirationDate) {
        setLong(Key.COLLECTION_SAVE_DATE, expirationDate);
    }

    public String getExpirationDate() {
        return getString(Key.ACCESS_EXPIRATION_TIME, "");
    }

    public void setAccessId(String accessId) {
        setString(Key.ACCESS_ID, accessId);
    }

    public String getAccessId() {
        return getString(Key.ACCESS_ID, "");
    }

    public void setTempLat(double latitude) {
        setDouble(Key.TEMP_LAT, latitude);
    }

    public void setTempLng(double lng) {
        setDouble(Key.TEMP_LNG, lng);
    }

    public double getTempLat() {
        return getDouble(Key.TEMP_LAT, 0);
    }

    public double getTempLng() {
        return getDouble(Key.TEMP_LNG, 0);
    }

    public void saveScreenSize(Point size) {
        setInt(Key.SCREEN_WIDTH, size.x);
        setInt(Key.SCREEN_HEIGHT, size.y);
    }

    public Point getScreenSize() {
        int width = getInt(Key.SCREEN_WIDTH, 0);
        int height = getInt(Key.SCREEN_HEIGHT, 0);
        return new Point(width, height);
    }

    public void setLoggedInUserType(String userType) {
        setString(Key.LOGGEDIN_USER_TYPE, userType);
    }


    public String getLoggedInUserType() {
        return getString(Key.LOGGEDIN_USER_TYPE, "");
    }

    public void setAccessStatus(boolean access_status) {
        setBoolean(Key.ACCESS_STATUS, access_status);
    }

    public boolean isAccessStatus() {
        return getBoolean(Key.ACCESS_STATUS, false); //TODO temp change
    }

    public String[] getUsedEmailsForLogin() {
        return getString(Key.LOGGED_IN_EMAILS, "").split(",");
    }


    public void addUsedEmailsForLogin(String email) {
        try {
            if (!getString(Key.LOGGED_IN_EMAILS, "").contains(email))
                setString(Key.LOGGED_IN_EMAILS, getString(Key.LOGGED_IN_EMAILS, "") + email + ",");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelectedLoanRequestId(String loan_request_id) {
        this.setString(Key.SELECTED_LOAN_REQUEST_ID, loan_request_id);
    }

    public String getSelectedLoanRequestId() {
        return this.getString(Key.SELECTED_LOAN_REQUEST_ID, "");
    }

    public void setBankSaved(){
        setBoolean(Key.BANK_LIST_SAVED,true);
    }

    public boolean getBankSaved(){
        return getBoolean(Key.BANK_LIST_SAVED,false);
    }

    public void setBankSavedCounter(int loan_request_id) {
        this.setInt(Key.BANK_LIST_SAVED_COUNTER, loan_request_id);
    }

    public int getBankSavedCounter() {
        return this.getInt(Key.BANK_LIST_SAVED_COUNTER,0);
    }


    private final class Key {
        public static final String COLLECTION_SAVE_DATE = "app_save_date";
        public static final String BUSINESS_CATEGORIES = "business_categories";
        public static final String BUSINESS_SUB_CATEGORIES = "business_sub_categories";
        public static final String IS_LOGGED_IN = "is_logged_in";
        public static final String ACCESS_TOKEN = "access_token";
        public static final String ACCESS_EXPIRATION_TIME = "acces_expiration_time";
        public static final String ACCESS_ID = "access_id";
        public static final String TEMP_LAT = "temp_lat";
        public static final String TEMP_LNG = "temp_lng";
        public static final String SCREEN_WIDTH = "screen_width";
        public static final String SCREEN_HEIGHT = "screen_height";
        public static final String LOGGEDIN_USER_TYPE = "loggedin_user_type";
        public static final String ACCESS_STATUS = "access_status";
        public static final String LOGGED_IN_EMAILS = "logged_in_emails";
        public static final String SELECTED_LOAN_REQUEST_ID = "selected_loan_request_id";
        public static final String BANK_LIST_SAVED = "bank_names_saved";
        public static final String BANK_LIST_SAVED_COUNTER = "bank_names_saved_counter";
    }
}