package com.ziploan.team.verification_module.borrowerslist;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ziploan.team.BuildConfig;
import com.ziploan.team.GPSTracker;
import com.ziploan.team.R;
import com.ziploan.team.databinding.ActivityBorrowersBinding;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;
import com.ziploan.team.webapi.BusinessCategoryResponse;
import com.ziploan.team.webapi.model.sub_cat_.SubCatResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 */

public class BorrowersListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private ActivityBorrowersBinding allViews;
    private ArrayList<BorrowersUnverified> arrBorrowers = new ArrayList<>();
    private BorrowersListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_borrowers;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityBorrowersBinding) views;
        setListeners();
        getLocations();
    }

    private void loadDataOffline() {
        setDataToView(DatabaseManger.getInstance().getApplicationList());
    }

    private void setListeners() {
        allViews.etSearch.addTextChangedListener(new SimpleTextChangeLister() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mAdapter != null)
                    mAdapter.filter(charSequence.toString().toLowerCase());
            }
        });
        allViews.swipeLayout.setOnRefreshListener(this);
        allViews.tvLogout.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataOffline();
//        loadData();
        loadBusinessCategoriesData();
    }

    private void loadData() {
        allViews.swipeLayout.setRefreshing(true);
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).fetchUnverifiedBorrowers("5788b990b251b30ea1231dd1");
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                allViews.swipeLayout.setRefreshing(false);
                if (response != null && response.isSuccessful() && response.body() != null && response.body().verification_manager_loan_request_data != null) {
                    DatabaseManger.getInstance().saveApplicationList(response.body().verification_manager_loan_request_data);
                    setDataToView(response.body().verification_manager_loan_request_data);
                    if (BuildConfig.VERSION_CODE < response.body().getApp_version()) {
                        showUpdateAppDialog("Please update your App");
                    }
                } else {
                    checkTokenValidity(response);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                allViews.swipeLayout.setRefreshing(false);
            }
        });
    }

    private void loadBusinessCategoriesData() {
        showProgressDialog();
        allViews.swipeLayout.setRefreshing(true);
        Call<BusinessCategoryResponse> call = APIExecutor.getAPIService(mContext).getBusinessCategories();
        call.enqueue(new Callback<BusinessCategoryResponse>() {
            @Override
            public void onResponse(Call<BusinessCategoryResponse> call, Response<BusinessCategoryResponse> response) {
                hideProgressDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    BusinessCategoryResponse businessCategoryResponse = response.body();
                    ZiploanSPUtils.getInstance(getApplicationContext()).saveBusinessCategories(businessCategoryResponse.getBusiness_Category_List());
                    ZiploanSPUtils.getInstance(getApplicationContext()).saveBusinessSubCategories(businessCategoryResponse.getBusinessCategoryData());
                    loadData();
                } else {
                    checkTokenValidity(response);
                }
            }

            @Override
            public void onFailure(Call<BusinessCategoryResponse> call, Throwable t) {
                allViews.swipeLayout.setRefreshing(false);
                hideProgressDialog();
            }
        });
    }

    private void setDataToView(ArrayList<BorrowersUnverified> borrowerList) {
        arrBorrowers.clear();
        arrBorrowers.addAll(borrowerList);
        Collections.sort(arrBorrowers, new Comparator<BorrowersUnverified>() {
            @Override
            public int compare(BorrowersUnverified borrowersUnverified, BorrowersUnverified t1) {
                return t1.getLoan_request_assigned_date().compareTo(borrowersUnverified.getLoan_request_assigned_date());
            }
        });
        mAdapter = new BorrowersListAdapter(mContext, arrBorrowers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allViews.rvBorrowers.setLayoutManager(mLayoutManager);
        allViews.rvBorrowers.setItemAnimator(new DefaultItemAnimator());
        allViews.rvBorrowers.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
//        loadData();
        loadBusinessCategoriesData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:
                displayLogoutAlert(getResources().getString(R.string.want_to_logout));
                break;
        }
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, BorrowersListActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void getLocations(){
        new GPSTracker(this);
    }
}
