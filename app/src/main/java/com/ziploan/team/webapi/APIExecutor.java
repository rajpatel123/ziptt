package com.ziploan.team.webapi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ziploan.team.BuildConfig;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanSPUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZIploan-Nitesh on 02/02/2017.
 * This class is having method to return the Retrofit Service instances, that will be responsible for
 * calling the APIs in Retrofit way
 */
public class APIExecutor {

    public static Retrofit retrofit;

    /**
     * Return Retrofit service for APIService Service interface
     *
     * @param mContext
     * @return
     */
    public static APIService getAPIService(final Context mContext) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient defaultHttpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader(AppConstant.Key.ACCESS_ID, ZiploanSPUtils.getInstance(mContext).getAccessId())
                                        .addHeader(AppConstant.Key.ACCESS_TOKEN, ZiploanSPUtils.getInstance(mContext).getAccessToken()).build();
                                return chain.proceed(request);
                            }
                        })
                .addInterceptor(logging)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(defaultHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(APIService.class);
    }




 /**
     * Return Retrofit service for APIService Service interface
     *
     * @param mContext
     * @return
     */
    public static APIService getAPIServiceWithLS(final Context mContext) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient defaultHttpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader(AppConstant.Key.ACCESS_ID, ZiploanSPUtils.getInstance(mContext).getAccessId())
                                        .addHeader(AppConstant.Key.ACCESS_TOKEN, ZiploanSPUtils.getInstance(mContext).getAccessToken()).build();
                                return chain.proceed(request);
                            }
                        })
                .addInterceptor(logging)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_LS)
                .client(defaultHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(APIService.class);
    }

    public static APIService getAPIServiceSerializeNull(final Context mContext) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient defaultHttpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader(AppConstant.Key.ACCESS_ID, ZiploanSPUtils.getInstance(mContext).getAccessId())
                                        .addHeader(AppConstant.Key.ACCESS_TOKEN, ZiploanSPUtils.getInstance(mContext).getAccessToken()).build();
                                return chain.proceed(request);
                            }
                        })
                .addInterceptor(logging)
                .build();
        Gson gson = new Gson();
        gson.serializeNulls();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(defaultHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .build();
        return retrofit.create(APIService.class);
    }
}
