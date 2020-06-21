package com.ziploan.team.collection.application_list;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.ziploan.team.R;
import com.ziploan.team.collection.model.past_vist.PastVisitModel;
import com.ziploan.team.collection.utils.UIErrorUtils;
import com.ziploan.team.databinding.PastVistLayoutBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.webapi.APIExecutor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastVisitsActivity extends BaseActivity {

    private List<com.ziploan.team.collection.model.past_vist.Response> result;
    private PastVistLayoutBinding allViews;
    private String mLoanId;
    private com.ziploan.team.collection.model.past_vist.Response mPastVisitResponse;
    private FrameLayout relative_no_network;
    private boolean requested;

    @Override
    protected int getLayoutId() {
        return R.layout.past_vist_layout;
    }

    public static void start(Activity mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, PastVisitsActivity.class);
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
        setTitle(getString(R.string.past_visit));
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (PastVistLayoutBinding) views;
        mLoanId = getIntent().getStringExtra("id");
        relative_no_network = findViewById(R.id.relative_no_network);

//        if(UIErrorUtils.isNetworkConnected(this)) {
//            getPastVisits();
//        } else {
//            allViews.cardView.setVisibility(View.GONE);
//        }
        allViews.lastVisitMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(mPastVisitResponse != null)
                        openMap(mPastVisitResponse.getLat(), mPastVisitResponse.getLng());
                } catch (Exception e){}
            }
        });
    }

    private void showUI() {
        getPastVisits();
//        allViews.cardView.setVisibility(View.VISIBLE);
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

    private void getPastVisits() {
        showProgressDialog();
        Call<PastVisitModel> call = APIExecutor.getAPIService(mContext).getPastVisits(mLoanId,AppConstant.Key.VIEW_VALUE);
        call.enqueue(new Callback<PastVisitModel>() {
            @Override
            public void onResponse(Call<PastVisitModel> call, Response<PastVisitModel> response) {
                hideProgressDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    if(response.body().getStatus().equalsIgnoreCase("success")){
                        result = response.body().getResponse();
                        allViews.setModel(result);
                        showSpinnerData(result);
                        allViews.cardView.setVisibility(View.VISIBLE);
                    } else {
                        allViews.noPastVisit.setVisibility(View.VISIBLE);
                        allViews.cardView.setVisibility(View.GONE);
                        //showToast(PastVisitsActivity.this,response.body().getStatusMessage());
                    }
                } else {
                    checkTokenValidity(response);
                    allViews.noPastVisit.setVisibility(View.VISIBLE);
                    allViews.cardView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<PastVisitModel> call, Throwable t) {
                hideProgressDialog();
                allViews.noPastVisit.setVisibility(View.VISIBLE);
                allViews.cardView.setVisibility(View.GONE);
            }
        });
    }

    private void showSpinnerData(List<com.ziploan.team.collection.model.past_vist.Response> overdueAmounts){
        ArrayAdapter<com.ziploan.team.collection.model.past_vist.Response> adapter = new ArrayAdapter<com.ziploan.team.collection.model.past_vist.Response>(this,
                R.layout.spinner_item_layout,overdueAmounts);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        allViews.emiSpinner.setAdapter(adapter);

        allViews.emiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                allViews.setIndex(i);
                mPastVisitResponse = result.get(i);
                if(mPastVisitResponse != null){
                    if(mPastVisitResponse.getLat() > 0 && mPastVisitResponse.getLng() > 0){
                        allViews.locationLayout.setVisibility(View.VISIBLE);
                    } else {
                        allViews.locationLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void openMap(float lat, float lon) {
        Uri gmmIntentUri = Uri.parse("geo:"+ lat + "," + lon);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mMessageReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mMessageReceiver);
        super.onStop();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false);
            if (noConnectivity) {
                if(result == null || result.size() == 0)
                    relative_no_network.setVisibility(View.VISIBLE);
            } else {
                if(result == null || result.size() == 0)
                    showUI();
                relative_no_network.setVisibility(View.GONE);
            }
        }
    };
}
