package com.ziploan.team.asset_module.visits;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.itextpdf.text.pdf.parser.Line;
import com.ziploan.team.MyWebViewActivity;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.databinding.ActivityAssetPastVisitsBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.borrowerdetails.SimpleSpinnerAdapter;
import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;
import com.ziploan.team.verification_module.verifyekyc.PhotosAdapter;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.Loan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 */

public class AssetPastVisitsDialogScreen extends AssetPastVisitsScreen {

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        super.onViewMapped(views);
        allViews.actionBar.toolbar.setVisibility(View.GONE);
        allViews.llSpinner.getLayoutParams().height = 0;
        allViews.llSpinner.setVisibility(View.INVISIBLE);
        allViews.llAddNewVisit.setVisibility(View.GONE);
        allViews.ivClose.setVisibility(View.VISIBLE);
        allViews.ivClose.setOnClickListener(this);
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext,AssetPastVisitsDialogScreen.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}