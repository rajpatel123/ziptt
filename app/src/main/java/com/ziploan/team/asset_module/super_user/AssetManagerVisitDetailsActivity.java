/*
package com.ziploan.team.asset_module.super_user;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.databinding.ActivityAssetManagerDetailsBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.EndlessRecyclerViewScrollListener;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.ResponseData;

*/
/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 *//*


public class AssetManagerVisitDetailsActivity extends AssetsBaseActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {

    private static final int PAGINATION_ITEM = 30;
    private ActivityAssetManagerDetailsBinding allViews;
    private Bundle bundle;
    private ZipAssetManager assetManager;
    private ArrayList<ZipAssetManagerVisit> arrAssetManagerVisits = new ArrayList<>();
    private AssetsManagerVisitsAdapter mAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int total=0;

    @Override
    protected Toolbar getToolbar() {
        return allViews.actionBar.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_asset_manager_details;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityAssetManagerDetailsBinding) views;
        bundle = getIntent().getExtras();
        if(bundle.containsKey(AppConstant.Key.EXTRA_OBJECT)){
            assetManager = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT);
            allViews.setAssetManager(assetManager);
            allViews.actionBar.setActionBarData(new ActionBarData(R.mipmap.ic_back, "ASSET MANAGER VISIT DETAILS"));
        }
        setListeners();
        loadData(0);
    }

    private void setListeners() {
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);

    }

    @Override
    protected void onActionHomeButtonClicked() {
        onBackPressed();
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext,AssetManagerVisitDetailsActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


    private void loadData(final int page) {
        if(assetManager!=null){
            allViews.swipeLayout.setRefreshing(true);
            Call<ApiResponse> call = APIExecutor.getAPIService(mContext).getAssetManagerDetails(assetManager.getManager_id());
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, ResponseData<ApiResponse> response) {
                    allViews.swipeLayout.setRefreshing(false);
                    checkTokenValidity(response);
                    if (response != null && response.body() != null) {
                        total = response.body().total;
                        if(response.body().getResults()!= null){
                            if(page == 0)
                                setDataToView(response.body().getResults());
                            else{
                                addDataToView(response.body().getResults());
                                allViews.loadMoreProgress.setVisibility(View.GONE);
                            }
                        }else {
                            showAlertInfo(getString(R.string.no_data_found));
                        }
                    }else {
                        showAlertInfo(getString(R.string.server_not_responding));
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    allViews.swipeLayout.setRefreshing(false);
                    allViews.loadMoreProgress.setVisibility(View.GONE);
                }
            });
        }
    }

    private void setDataToView(ArrayList<ZipAssetManagerVisit> assetManagers) {
        arrAssetManagerVisits.clear();
        arrAssetManagerVisits.addAll(assetManagers);
        mAdapter = new AssetsManagerVisitsAdapter(mContext, arrAssetManagerVisits);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allViews.rvManagerVisits.setLayoutManager(mLayoutManager);
        allViews.rvManagerVisits.setItemAnimator(new DefaultItemAnimator());
        allViews.rvManagerVisits.setAdapter(mAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) allViews.rvManagerVisits.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(page <= total/PAGINATION_ITEM)
                    loadData(page);
            }
        };
        allViews.rvManagerVisits.addOnScrollListener(scrollListener);
    }

    public void addDataToView(ArrayList<ZipAssetManagerVisit> loans){
        int startPosition = arrAssetManagerVisits.size();
        arrAssetManagerVisits.addAll(loans);
        mAdapter.notifyItemRangeInserted(startPosition,PAGINATION_ITEM);
    }

    @Override
    public void onRefresh() {
        arrAssetManagerVisits.clear();
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
            scrollListener.resetState();
            loadData(0);
        }else {
            loadData(0);
        }
    }

}*/
