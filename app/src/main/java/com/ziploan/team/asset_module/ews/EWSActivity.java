package com.ziploan.team.asset_module.ews;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.asset_module.EWSDisplay;
import com.ziploan.team.asset_module.ews.res.NewewsResponses;
import com.ziploan.team.asset_module.ews.res.Result;
import com.ziploan.team.databinding.ActivityEwsBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.EWS;
import com.ziploan.team.webapi.Loan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 */

public class EWSActivity extends AssetsBaseActivity implements View.OnClickListener {

    private ActivityEwsBinding allViews;
    private Bundle bundle;
    private Loan loan;
    private RecyclerView recyclerView;
    private ArrayList<ParentData> data;
    private MyAdapter adapter;

    @Override
    protected Toolbar getToolbar() {
        return allViews.actionBar.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ews;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityEwsBinding) views;
        bundle = getIntent().getExtras();
        if(bundle.containsKey(AppConstant.Key.EXTRA_OBJECT)){
            loan = bundle.getParcelable(AppConstant.Key.EXTRA_OBJECT);
            allViews.actionBar.setActionBarData(new ActionBarData(R.mipmap.ic_back,loan.getLoan_application_number()));
        }
        setListeners();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        data = new ArrayList<>();

//        setData();
        getEwsInfo();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setListeners() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Result> mDataset;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView feature;
            public TextView value;
            public TextView score;

            public ViewHolder(View v) {
                super(v);
                feature = v.findViewById(R.id.feature);
                value = v.findViewById(R.id.value);
                score = v.findViewById(R.id.score);
            }
        }

        public void setData(List<Result> dataset){
            this.mDataset = dataset;
            notifyDataSetChanged();
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.new_ews_item_layout, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.feature.setText(mDataset.get(position).getFeature());
            holder.value.setText(mDataset.get(position).getValue());
            holder.score.setText(mDataset.get(position).getScore());
        }

        @Override
        public int getItemCount() {
            return mDataset != null ? mDataset.size() : 0;
        }
    }

    @Override
    protected void onActionHomeButtonClicked() {
        onBackPressed();
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext,EWSActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void getEwsInfo() {
        showProgressDialog("Please wait...");
        Call<NewewsResponses> call = APIExecutor.getAPIServiceSerializeNull(mContext)
                .getNewEws(loan.getLoan_application_number());
        call.enqueue(new Callback<NewewsResponses>() {
            @Override
            public void onResponse(Call<NewewsResponses> call, Response<NewewsResponses> response) {
                hideProgressDialog();
                if (response != null && response.isSuccessful() && response.body() != null
                && response.body().getResponse().getResults() != null) {
                    adapter.setData(response.body().getResponse().getResults());
                }
            }

            @Override
            public void onFailure(Call<NewewsResponses> call, Throwable t) {
                t.printStackTrace();
                hideProgressDialog();
            }
        });
    }

    private void setData() {
        Iterator iterator = loan.getEws_count().keySet().iterator();
        while (iterator.hasNext()){
            String key = (String) iterator.next();
            data.add(new ParentData(key+" - "+loan.getEws_count().get(key).size(), getEWSForDisplay(loan.getEws_count().get(key))));
        }
    }

    private List<EWSDisplay> getEWSForDisplay(ArrayList<EWS> ewses) {
        HashMap<String, ArrayList<EWS>> map = new HashMap<>();
        ArrayList<EWSDisplay> ewsDisplayList = new ArrayList<>();
        for (int i=0;i<ewses.size();i++){
            if(!map.containsKey(ewses.get(i).getEmi_date()))
                map.put(ewses.get(i).getEmi_date(),new ArrayList<EWS>());
            ArrayList<EWS> tempArray = map.get(ewses.get(i).getEmi_date());
            tempArray.add(ewses.get(i));
            map.put(ewses.get(i).getEmi_date(),tempArray);
        }
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            String key = (String) iterator.next();
            ArrayList<EWS> arrEWS = map.get(key);
            EWSDisplay ewsDisplay = new EWSDisplay();
            ewsDisplay.setEmi_date(key);
            ewsDisplay.setEmi(arrEWS.get(0).getEmi());
            ewsDisplay.setAmt_overdue(arrEWS.get(0).getAmt_overdue());
            ewsDisplay.setTriggers(arrEWS);
            ewsDisplayList.add(ewsDisplay);
        }
        Collections.sort(ewsDisplayList, new Comparator<EWSDisplay>() {
            @Override
            public int compare(EWSDisplay ewsDisplay, EWSDisplay t1) {
                return ZiploanUtil.emptyIfNull(ewsDisplay.getEmi_date()).compareTo(ZiploanUtil.emptyIfNull(t1.getEmi_date()));
            }
        });
        return ewsDisplayList;
    }
}