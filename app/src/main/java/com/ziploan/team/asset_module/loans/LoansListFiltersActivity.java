package com.ziploan.team.asset_module.loans;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ziploan.team.App;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.ActionBarData;
import com.ziploan.team.asset_module.AssetsBaseActivity;
import com.ziploan.team.databinding.ActivityLoansFiltersBinding;
import com.ziploan.team.databinding.ItemLoanFilterItemBinding;
import com.ziploan.team.databinding.ItemLoanFilterKeyBinding;
import com.ziploan.team.listeners.CustomOnItemClickListener;
import com.ziploan.team.listeners.RefreshParentListListener;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.verification_module.caching.FilterItem;
import com.ziploan.team.verification_module.caching.FilterKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by ZIploan-Nitesh on 3/2/2017.
 */

public class LoansListFiltersActivity extends AssetsBaseActivity implements View.OnClickListener, CustomOnItemClickListener, RefreshParentListListener {

    private ActivityLoansFiltersBinding allViews;
    private LoanFilterKeysAdapter mAdapterKeys;
    private LoanFilterItemsAdapter mAdapterItems;

    @Override
    protected Toolbar getToolbar() {
        return allViews.actionBar.toolbar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loans_filters;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (ActivityLoansFiltersBinding) views;
        setListeners();

        App.filtersKey = (ArrayList<FilterKey>) ZiploanUtil.getDeepCopyList(App.filtersKeyMain);
        App.filters = ZiploanUtil.getDeepCopyListItems(App.filtersMain);
        allViews.actionBar.setActionBarData(new ActionBarData(R.mipmap.ic_back,"FILTER BY"));
        allViews.actionBar.setFilterButton(true);

        loadFilterKeys();
    }

    private void setListeners() {
        allViews.actionBar.buttonClear.setOnClickListener(this);
        allViews.actionBar.buttonApply.setOnClickListener(this);
    }

    private void loadFilterKeys() {
        mAdapterKeys = new LoanFilterKeysAdapter(mContext, App.filtersKey,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allViews.rvCategory.setLayoutManager(mLayoutManager);
        allViews.rvCategory.setAdapter(mAdapterKeys);

        mAdapterKeys.setSelectedForPosition(0);
        loadFilterItems(mAdapterKeys.getList().get(0).getKey_name());
    }

    private void loadFilterItems(String key) {
        Collections.sort(App.filters.get(key));
        mAdapterItems = new LoanFilterItemsAdapter(mContext, App.filters.get(key),this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        allViews.rvCategoryItem.setLayoutManager(mLayoutManager);
        allViews.rvCategoryItem.setAdapter(mAdapterItems);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.button_clear:
                String selected_key = "";
                boolean isChanged = false;
                for(int i = 0;i< App.filtersKey.size();i++){
                    if(App.filtersKey.get(i).is_filter_selected()){
                        isChanged = true;
                    }
                    if(App.filtersKey.get(i).is_selected()){
                        selected_key = App.filtersKey.get(i).getKey_name();
                    }
                    App.filtersKey.get(i).setIs_filter_selected(false);
                    ArrayList<FilterItem> items = App.filters.get(App.filtersKey.get(i).getKey_name());
                    for(int j=0;j<items.size();j++){
                        items.get(j).setSelected(false);
                    }
                }
                if(isChanged){
                    mAdapterKeys.setList(App.filtersKey);
                    mAdapterItems.setList(App.filters.get(selected_key));
                }
                break;

            case R.id.button_apply:
                App.filters_params = new HashMap<>();
                App.filtersMain = App.filters;
                App.filtersKeyMain = App.filtersKey;
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.FILTER_PARAMS,getParamsStringFromFilter(App.filtersKey,App.filters));
                intent.putExtras(bundle);

                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    private String getParamsStringFromFilter(ArrayList<FilterKey> filtersKey, HashMap<String, ArrayList<FilterItem>> filters) {
        StringBuilder strBuilder = new StringBuilder();
        for(int i= 0 ;i<filtersKey.size();i++){
            if(filtersKey.get(i).is_filter_selected()){
                if(strBuilder.length()>0){
                    strBuilder.append("&");
                }
                strBuilder.append(filtersKey.get(i).getKey_name()+"=");
                ArrayList<FilterItem> filterItems = filters.get(filtersKey.get(i).getKey_name());
                for(int j = 0;j<filterItems.size();j++){
                    if(filterItems.get(j).isSelected())
                        strBuilder.append(filterItems.get(j).getFilter_id()+",");
                }
                strBuilder.deleteCharAt(strBuilder.length()-1);
            }
        }
        return strBuilder.toString();
    }

    @Override
    protected void onActionHomeButtonClicked() {
        onBackPressed();
    }

    public static void start(Context mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, LoansListFiltersActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void onItemClick(ViewDataBinding binding,int position) {
        mAdapterKeys.setSelectedForPosition(position);
        loadFilterItems(mAdapterKeys.getList().get(position).getKey_name());
    }

    @Override
    public void onRefresh(int position, boolean is_selected) {
        mAdapterKeys.refreshFilterMarkerForKey(is_selected);
    }
}