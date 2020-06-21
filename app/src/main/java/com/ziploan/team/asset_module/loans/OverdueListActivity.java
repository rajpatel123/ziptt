package com.ziploan.team.asset_module.loans;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.Toast;

import com.ziploan.team.App;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.databinding.ActivityLoanOverdueBinding;
import com.ziploan.team.databinding.ActivityLoansBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.EndlessRecyclerViewScrollListener;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.ApiLoanAccountsResponse;
import com.ziploan.team.webapi.Loan;
import com.ziploan.team.webapi.OverdueBreakup;

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

public class OverdueListActivity extends AssetsBaseActivity implements View.OnClickListener {

    private static final int PAGINATION_ITEM = 30;
    private ActivityLoanOverdueBinding allViews;
    private LoanOverdueAdapter mAdapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private Bundle bundle;
    private Loan loan;

    @Override
    protected Toolbar getToolbar() {
        return allViews.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loan_overdue;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityLoanOverdueBinding) views;
        setListeners();
        bundle = getIntent().getExtras();
        if(bundle!=null && bundle.containsKey(AppConstant.Key.EXTRA_OBJECT)){
            loan = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT);
            allViews.setActionBarData(new ActionBarData(R.mipmap.ic_back,"Overdue Breakups"));
            setDataToView(loan.getOverdue_breakup());
        }else {
            showToast(getResources().getString(R.string.something_went_wrong));
            finish();
        }

    }

    private void setListeners() {
    }

    private void setDataToView(ArrayList<OverdueBreakup> loanOverdueBreakups) {
        mAdapter = new LoanOverdueAdapter(mContext, loanOverdueBreakups);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allViews.rvOverdues.setLayoutManager(mLayoutManager);
        allViews.rvOverdues.setItemAnimator(new DefaultItemAnimator());
        allViews.rvOverdues.setAdapter(mAdapter);
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
        Intent intent = new Intent(mContext,OverdueListActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}