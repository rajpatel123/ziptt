package com.ziploan.team.asset_module.loans;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ziploan.team.App;
import com.ziploan.team.GPSTracker;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.databinding.ActivityLoansBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.EndlessRecyclerViewScrollListener;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiLoanAccountsResponse;
import com.ziploan.team.webapi.Loan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 */

public class LoansListActivity extends AssetsBaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, TextView.OnEditorActionListener {

    private static final int PAGINATION_ITEM = 30;
    private static final int FIRST_PAGE = 1;
    private ActivityLoansBinding allViews;
    private ArrayList<Loan> arrLoans = new ArrayList<>();
    private LoanAccountAdapter mAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int totalRecord;

    @Override
    protected Toolbar getToolbar() {
        return allViews.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loans;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityLoansBinding) views;
        setListeners();
        allViews.setActionBarData(new ActionBarData(0,"loan accounts"));
        loadData(FIRST_PAGE,"");
        getLocations();
    }

    private void setListeners() {
        allViews.swipeLayout.setOnRefreshListener(this);
        allViews.etSearch.setOnEditorActionListener(this);
    }

    private void loadData(final int page, String search_str) {
        Map<String,String> params = new HashMap<>();
        if(page==FIRST_PAGE)
            allViews.swipeLayout.setRefreshing(true);
        else if(mAdapter!=null)
            mAdapter.setLoadMoreProgress(true);

        params.put("page", String.valueOf(page));
        if(!TextUtils.isEmpty(search_str))
            params.put("q", search_str);
        if(App.filters_params.size()>0)
            params.putAll(App.filters_params);

        Call<ApiLoanAccountsResponse> call = APIExecutor.getAPIService(mContext).getLoanAccounts(params);
        call.enqueue(new Callback<ApiLoanAccountsResponse>() {
            @Override
            public void onResponse(Call<ApiLoanAccountsResponse> call, Response<ApiLoanAccountsResponse> response) {
                if(mAdapter!=null)
                    mAdapter.setLoadMoreProgress(false);
                allViews.swipeLayout.setRefreshing(false);
                checkTokenValidity(response);
                if (response != null && response.body() != null && response.body().getLoans()!= null) {
                    totalRecord = response.body().getTotal_record();
                    if(page == FIRST_PAGE)
                        setDataToView(response.body().getLoans());
                    else{
                        addDataToView(response.body().getLoans());
                    }
                }else {
                    if(page == FIRST_PAGE) {
                        arrLoans.clear();
                        if(mAdapter!=null)
                            mAdapter.notifyDataSetChanged();
                    }
                    allViews.swipeLayout.setRefreshing(false);
                    try {
                        showAlertInfo(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiLoanAccountsResponse> call, Throwable t) {
                allViews.swipeLayout.setRefreshing(false);
                if(mAdapter!=null)
                    mAdapter.setLoadMoreProgress(false);
            }
        });
    }

    private void setDataToView(ArrayList<Loan> loans) {
        arrLoans.clear();
        arrLoans.addAll(loans);
        mAdapter = new LoanAccountAdapter(mContext, arrLoans);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allViews.rvLoans.setLayoutManager(mLayoutManager);
        allViews.rvLoans.setItemAnimator(new DefaultItemAnimator());
        allViews.rvLoans.setAdapter(mAdapter);
        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) allViews.rvLoans.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadData(page,allViews.etSearch.getText().toString().trim());
            }
        };

        if (arrLoans.size()>0){
            allViews.rvLoans.setVisibility(View.VISIBLE);
            allViews.noApplication.setVisibility(View.GONE);
        }else{
            allViews.rvLoans.setVisibility(View.GONE);
            allViews.noApplication.setVisibility(View.VISIBLE);
        }
        allViews.rvLoans.addOnScrollListener(scrollListener);
    }

    public void addDataToView(ArrayList<Loan> loans){
        int startPosition = arrLoans.size();
        arrLoans.addAll(loans);
        mAdapter.notifyItemRangeInserted(startPosition,PAGINATION_ITEM);
    }

    @Override
    public void onRefresh() {
        //arrLoans.clear();

        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
            scrollListener.resetState();
            loadData(FIRST_PAGE,allViews.etSearch.getText().toString().trim());
        }else {
            loadData(FIRST_PAGE,allViews.etSearch.getText().toString().trim());
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    protected void onActionHomeButtonClicked() {

    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext,LoansListActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            loadData(FIRST_PAGE,allViews.etSearch.getText().toString().trim());
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.filter:
                if(App.filtersKeyMain.size()>0){
                    Intent intent = new Intent(mContext,LoansListFiltersActivity.class);
                    startActivityForResult(intent, AppConstant.FILTER_REQUEST);
                }else {
                    showAlertInfo("Please wait... filters are loading");
                }
                break;

            case R.id.logout:
                displayLogoutAlert(getResources().getString(R.string.want_to_logout));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AppConstant.FILTER_REQUEST:
                if(resultCode == RESULT_OK){
                    String filter_params_string = (String) data.getExtras().get(AppConstant.FILTER_PARAMS);
                    if(!TextUtils.isEmpty(filter_params_string)){
                        String[] dataP = filter_params_string.split("&");
                        for(int i = 0 ;i<dataP.length;i++){
                            String[] params = dataP[i].split("=");
                            App.filters_params.put(params[0],params[1]);
                        }
                    }
                    loadData(FIRST_PAGE,getText(allViews.etSearch));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getLocations(){
        new GPSTracker(this);
    }
}