package com.ziploan.team.collection.application_list;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ziploan.team.R;
import com.ziploan.team.collection.model.app_list.OverdueBreakup;
import com.ziploan.team.collection.model.emi.EmiBreakupModel;
import com.ziploan.team.collection.model.emi.OverdueAmount;
import com.ziploan.team.databinding.OverdueBreakupLayoutBinding;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.webapi.APIExecutor;

import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OverdueBreakupActivity extends BaseActivity {

    private OverdueBreakupLayoutBinding allViews;
    private List<OverdueBreakup> result;
    private String mAmountOverdue;

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, OverdueBreakupActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.overdue_breakup_text));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.overdue_breakup_layout;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        if (getIntent().hasExtra("data")) {
            result =(List<OverdueBreakup>) getIntent().getSerializableExtra("data");
        }
        mAmountOverdue = getIntent().getStringExtra("overdue");
        allViews = (OverdueBreakupLayoutBinding) views;
        allViews.setModel(result);
        showSpinnerData(result);
        allViews.overdueBreakupText.setText(getOverdueText());
    }

    public String getOverdueText() {
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        return "Now Amount Outstanding as on Today " +format.format(Double.parseDouble(mAmountOverdue));
    }

    private void showSpinnerData(List<OverdueBreakup> overdueAmounts){
        ArrayAdapter<OverdueBreakup> adapter = new ArrayAdapter<OverdueBreakup>(this,
                R.layout.spinner_item_layout,overdueAmounts);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        allViews.emiSpinner.setAdapter(adapter);

        allViews.emiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                allViews.setIndex(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
