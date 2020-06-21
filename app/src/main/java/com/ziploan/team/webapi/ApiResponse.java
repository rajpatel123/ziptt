package com.ziploan.team.webapi;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.asset_module.super_user.ZipAssetManagerVisit;
import com.ziploan.team.verification_module.borrowerslist.BorrowersUnverified;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ZIploan-Nitesh on 2/3/2017.
 */
public class ApiResponse {
    @SerializedName("verification_manager_loan_request_data") public ArrayList<BorrowersUnverified> verification_manager_loan_request_data;
    @SerializedName("user_account_status") public String user_account_status;
    @SerializedName("app_version") public int app_version;
    @SerializedName("user_account_id") public String user_account_id;
    @SerializedName("user_account_name") public String user_account_name;
    @SerializedName("Authorization_Key") public String authorization_key;
    @SerializedName("access_token") public String access_token;
    @SerializedName("token_expiration_time") public String token_expiration_time;
    @SerializedName("result") public String result;
    @SerializedName("filters") public AssetsFilters filters;
    @SerializedName("message") public String message;
    @SerializedName("asset_managers") public HashMap<String,String> asset_managers;
    @SerializedName("total") public int total;
    @SerializedName("results") public ArrayList<ZipAssetManagerVisit> results;

    public int getApp_version() {
        return app_version;
    }

    public void setApp_version(int app_version) {
        this.app_version = app_version;
    }

    public ArrayList<BorrowersUnverified> getVerification_manager_loan_request_data() {
        return verification_manager_loan_request_data;
    }

    public void setVerification_manager_loan_request_data(ArrayList<BorrowersUnverified> verification_manager_loan_request_data) {
        this.verification_manager_loan_request_data = verification_manager_loan_request_data;
    }

    public String getUser_account_status() {
        return user_account_status;
    }

    public void setUser_account_status(String user_account_status) {
        this.user_account_status = user_account_status;
    }

    public String getUser_account_id() {
        return user_account_id;
    }

    public void setUser_account_id(String user_account_id) {
        this.user_account_id = user_account_id;
    }

    public String getUser_account_name() {
        return user_account_name;
    }

    public void setUser_account_name(String user_account_name) {
        this.user_account_name = user_account_name;
    }

    public String getAuthorization_key() {
        return authorization_key;
    }

    public void setAuthorization_key(String authorization_key) {
        this.authorization_key = authorization_key;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_expiration_time() {
        return token_expiration_time;
    }

    public void setToken_expiration_time(String token_expiration_time) {
        this.token_expiration_time = token_expiration_time;
    }

    public AssetsFilters getFilters() {
        return filters;
    }

    public void setFilters(AssetsFilters filters) {
        this.filters = filters;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, String> getAsset_managers() {
        return asset_managers;
    }

    public void setAsset_managers(HashMap<String, String> asset_managers) {
        this.asset_managers = asset_managers;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<ZipAssetManagerVisit> getResults() {
        return results;
    }

    public void setResults(ArrayList<ZipAssetManagerVisit> results) {
        this.results = results;
    }
}
