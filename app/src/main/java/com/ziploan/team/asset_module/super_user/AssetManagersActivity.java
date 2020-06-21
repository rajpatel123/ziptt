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
import com.ziploan.team.databinding.ActivityAssetManagersBinding;
import com.ziploan.team.utils.EndlessRecyclerViewScrollListener;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.ResponseData;

*/
/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 *//*


public class AssetManagersActivity extends AssetsBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{

    private static final int PAGINATION_ITEM = 30;
    private ActivityAssetManagersBinding allViews;
    private ArrayList<ZipAssetManager> arrAssetManagers = new ArrayList<>();
    private AssetsManagersAdapter mAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected Toolbar getToolbar() {
        return allViews.actionBar.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_asset_managers;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityAssetManagersBinding) views;
        setListeners();
        allViews.actionBar.setActionBarData(new ActionBarData(R.mipmap.ic_back,"Asset Managers"));
        allViews.actionBar.setFilterButton(false);
        loadData(0);

    }

    private void setListeners() {
        allViews.swipeLayout.setOnRefreshListener(this);
    }

    private void loadData(final int page) {
        allViews.swipeLayout.setRefreshing(true);
        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).getAssetManagers();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, ResponseData<ApiResponse> response) {
                allViews.swipeLayout.setRefreshing(false);
                checkTokenValidity(response);
                if (response != null && response.body() != null && response.body().getFilters()!= null && response.body().getFilters().getAsset_managers()!= null) {
                    ArrayList<ZipAssetManager> arrManagers = mapTolist(response.body().getFilters().getAsset_managers());
                    if(page == 0)
                        setDataToView(arrManagers);
                    else{
                        addDataToView(arrManagers);
                        allViews.loadMoreProgress.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                hideProgressDialog();
                allViews.swipeLayout.setRefreshing(false);
                allViews.loadMoreProgress.setVisibility(View.GONE);
            }
        });
    }

    private ArrayList<ZipAssetManager> mapTolist(HashMap<String, String> asset_managers) {
        ArrayList<ZipAssetManager> arr = new ArrayList<>();
        if(asset_managers.size()>0){
            Iterator iterator = asset_managers.keySet().iterator();
            while (iterator.hasNext()){
                String key = iterator.next().toString();
                arr.add(new ZipAssetManager(key,asset_managers.get(key)));
            }
        }
        return arr;
    }

    private void setDataToView(ArrayList<ZipAssetManager> assetManagers) {
        arrAssetManagers.clear();
        arrAssetManagers.addAll(assetManagers);
        mAdapter = new AssetsManagersAdapter(mContext, arrAssetManagers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allViews.rvAssetManager.setLayoutManager(mLayoutManager);
        allViews.rvAssetManager.setItemAnimator(new DefaultItemAnimator());
        allViews.rvAssetManager.setAdapter(mAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) allViews.rvAssetManager.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadData(page);
            }
        };
        allViews.rvAssetManager.addOnScrollListener(scrollListener);
    }

    public void addDataToView(ArrayList<ZipAssetManager> loans){
        int startPosition = arrAssetManagers.size();
        arrAssetManagers.addAll(loans);
        mAdapter.notifyItemRangeInserted(startPosition,PAGINATION_ITEM);
    }

    @Override
    public void onRefresh() {
        arrAssetManagers.clear();
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
            scrollListener.resetState();
            loadData(0);
        }else {
            loadData(0);
        }
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
        Intent intent = new Intent(mContext,AssetManagersActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

}*/
