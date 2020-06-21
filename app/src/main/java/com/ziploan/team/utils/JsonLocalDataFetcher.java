package com.ziploan.team.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

import okhttp3.internal.Util;

/**
 * Created by ZIploan-Nitesh on 2/8/2017.
 */

public class JsonLocalDataFetcher {
    private static final String QUESTIONARIES = "questionaries";
    private static final String ENTITY_PROOOFS = "entity_proof";
    private static final String RESIDENCE_PROOOFS = "residence_place_address_proof";
    private static final String BUSINESS_PROOOFS = "business_place_address_proof";
    private static final String LOAN_TYPE = "loan_type";

    public static ArrayList<String> fetchQuestionaries(Context context){
        try {
            String jsonString = ZiploanUtil.getStringFromInputStream(context.getAssets().open("local_data.json"));
            if (!TextUtils.isEmpty(jsonString)){
                JSONObject object = new JSONObject(jsonString);
                return new Gson().fromJson(object.getJSONArray(QUESTIONARIES).toString(),new TypeToken<ArrayList<String>>(){}.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }
    public static ArrayList<String> fetchEntityProofs(Context context){
        try {
            String jsonString = ZiploanUtil.getStringFromInputStream(context.getAssets().open("local_data.json"));
            if (!TextUtils.isEmpty(jsonString)){
                JSONObject object = new JSONObject(jsonString);
                return new Gson().fromJson(object.getJSONArray(ENTITY_PROOOFS).toString(),new TypeToken<ArrayList<String>>(){}.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }
    public static ArrayList<String> fetchBusinessProofs(Context context){
        try {
            String jsonString = ZiploanUtil.getStringFromInputStream(context.getAssets().open("local_data.json"));
            if (!TextUtils.isEmpty(jsonString)){
                JSONObject object = new JSONObject(jsonString);
                return new Gson().fromJson(object.getJSONArray(BUSINESS_PROOOFS).toString(),new TypeToken<ArrayList<String>>(){}.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }
    public static ArrayList<String> fetchResidenceProofs(Context context){
        try {
            String jsonString = ZiploanUtil.getStringFromInputStream(context.getAssets().open("local_data.json"));
            if (!TextUtils.isEmpty(jsonString)){
                JSONObject object = new JSONObject(jsonString);
                return new Gson().fromJson(object.getJSONArray(RESIDENCE_PROOOFS).toString(),new TypeToken<ArrayList<String>>(){}.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }
    public static ArrayList<String> fetchLoanType(Context context){
        try {
            String jsonString = ZiploanUtil.getStringFromInputStream(context.getAssets().open("local_data.json"));
            if (!TextUtils.isEmpty(jsonString)){
                JSONObject object = new JSONObject(jsonString);
                return new Gson().fromJson(object.getJSONArray(LOAN_TYPE).toString(),new TypeToken<ArrayList<String>>(){}.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }
}
