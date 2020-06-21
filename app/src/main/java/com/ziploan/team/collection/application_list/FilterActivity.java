package com.ziploan.team.collection.application_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ziploan.team.R;
import com.ziploan.team.collection.model.filter.DeliquencyBucket;
import com.ziploan.team.collection.model.filter.FilterModel;
import com.ziploan.team.collection.model.filter.PincodeFilterModel;
import com.ziploan.team.databinding.FilterItemLayoutBinding;
import com.ziploan.team.databinding.FilterLayoutBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.verification_module.borrowerslist.SimpleTextChangeLister;
import com.ziploan.team.webapi.APIExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends BaseActivity implements View.OnClickListener {
    private FilterLayoutBinding allViews;
    private List<DeliquencyBucket> dpdList;
    private List<PincodeFilterModel> pincodeList = new ArrayList<>();
    DeliquencyBucketFilterAdapter mAdapter;
    PincodeFilterAdapter pincodeFilterAdapter;

    public static void start(Activity mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, FilterActivity.class);
        intent.putExtras(bundle);
        mContext.startActivityForResult(intent,1011);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.filter_layout;
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        allViews = (FilterLayoutBinding) views;
        mAdapter = new DeliquencyBucketFilterAdapter();
        pincodeFilterAdapter = new PincodeFilterAdapter();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        RecyclerView.LayoutManager mthreeLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        allViews.rvDpdList.setLayoutManager(mLayoutManager);
        allViews.rvDpdList.setItemAnimator(new DefaultItemAnimator());
        allViews.rvDpdList.setAdapter(mAdapter);

        allViews.rvPincodeList.setLayoutManager(mthreeLayoutManager);
        allViews.rvPincodeList.setItemAnimator(new DefaultItemAnimator());
        allViews.rvPincodeList.setAdapter(pincodeFilterAdapter);
        setListeners();
        getFilters();
    }

    private void setListeners() {
        allViews.search.addTextChangedListener(new SimpleTextChangeLister() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() > 0) {
                    if (mAdapter != null)
                        mAdapter.filter(charSequence.toString().toLowerCase());
                    if (pincodeFilterAdapter != null)
                        pincodeFilterAdapter.filter(charSequence.toString().toLowerCase());
                } else {
                    mAdapter.setData(dpdList);
                    pincodeFilterAdapter.setData(pincodeList);
                }
            }
        });
        allViews.close.setOnClickListener(this);
        allViews.reset.setOnClickListener(this);
        allViews.save.setOnClickListener(this);
    }

    private void getFilters() {
        showProgressDialog();
        Call<FilterModel> call = APIExecutor.getAPIService(mContext).getCollectionFilter("http://demo7506595.mockable.io/filters");
        call.enqueue(new Callback<FilterModel>() {
            @Override
            public void onResponse(Call<FilterModel> call, Response<FilterModel> response) {
                hideProgressDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    dpdList = response.body().getDeliquencyBuckets();
                    List<String> pins = response.body().getAvailablePincodes();
                    for (String pin : pins){
                        PincodeFilterModel pincodeFilterModel = new PincodeFilterModel();
                        pincodeFilterModel.setPincode(pin);
                        pincodeList.add(pincodeFilterModel);
                    }
                    pincodeFilterAdapter.setData(pincodeList);
                    mAdapter.setData(dpdList);
                } else {
                    checkTokenValidity(response);
                }
            }

            @Override
            public void onFailure(Call<FilterModel> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close:
                finish();
                break;
            case R.id.reset:
                pincodeFilterAdapter.clear();
                mAdapter.clear();
                pincodeFilterAdapter.setData(pincodeList);
                mAdapter.setData(dpdList);
                break;
            case R.id.save:
                Intent intent = new Intent();
                intent.putExtra("data","");
                setResult(1011,intent);
                finish();
                break;
        }
    }

    public class DeliquencyBucketFilterAdapter extends RecyclerView.Adapter {

        private List<DeliquencyBucket> data;

        public DeliquencyBucketFilterAdapter(){
            data = null;
        }

        public void setData(List<DeliquencyBucket> data) {
            this.data = new ArrayList<>(data);
            notifyDataSetChanged();
        }

        public void clear(){
            data.clear();
            notifyDataSetChanged();
        }

        class ApplicationListViewHolder extends RecyclerView.ViewHolder {

            private FilterItemLayoutBinding itemView;

            ApplicationListViewHolder(FilterItemLayoutBinding itemView) {
                super(itemView.getRoot());
                this.itemView = itemView;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            FilterItemLayoutBinding binding = FilterItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ApplicationListViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            final ApplicationListViewHolder bindingViewholder = (ApplicationListViewHolder) holder;
            DeliquencyBucket result = data.get(position);
            bindingViewholder.itemView.item.setText(result.getBucketCategory());
            bindingViewholder.itemView.executePendingBindings();
            bindingViewholder.itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int ii = (holder).getAdapterPosition();
                    DeliquencyBucket dataItem = data.get(ii);
                    if (dataItem.isSelected()) {
                        bindingViewholder.itemView.item.setBackground(view.getResources().getDrawable(R.drawable.ractangle_background));
                        bindingViewholder.itemView.item.setTextColor(view.getResources().getColor(R.color.color_424242));
                        dataItem.setSelected(false);
                    } else {
                        dataItem.setSelected(true);
                        bindingViewholder.itemView.item.setBackground(view.getResources().getDrawable(R.drawable.coloured_ractangle_button));
                        bindingViewholder.itemView.item.setTextColor(view.getResources().getColor(R.color.white));
                    }
                }
            });
            bindingViewholder.itemView.item.setBackground(bindingViewholder.itemView.item.getResources().getDrawable(R.drawable.ractangle_background));
            bindingViewholder.itemView.item.setTextColor(bindingViewholder.itemView.item.getResources().getColor(R.color.color_424242));
        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        public void filter(String constraint) {
            if (data != null) {
                data.clear();
                for (int i = 0; i < dpdList.size(); i++) {
                    if (dpdList.get(i).getBucketCategory().toLowerCase().contains(constraint)) {
                        data.add(dpdList.get(i));
                    }
                }
                notifyDataSetChanged();
            }
        }

    }

    public class PincodeFilterAdapter extends RecyclerView.Adapter {

        private List<PincodeFilterModel> data;

        public PincodeFilterAdapter(){
            data = null;
        }

        public void setData(List<PincodeFilterModel> data) {
            this.data = new ArrayList<>(data);
            notifyDataSetChanged();
        }

        public void clear(){
            data.clear();
            notifyDataSetChanged();
        }

        class ApplicationListViewHolder extends RecyclerView.ViewHolder {

            private FilterItemLayoutBinding itemView;

            ApplicationListViewHolder(FilterItemLayoutBinding itemView) {
                super(itemView.getRoot());
                this.itemView = itemView;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            FilterItemLayoutBinding binding = FilterItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ApplicationListViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            final ApplicationListViewHolder bindingViewholder = (ApplicationListViewHolder) holder;
            PincodeFilterModel result = data.get(position);
            bindingViewholder.itemView.item.setText(result.getPincode());
            bindingViewholder.itemView.executePendingBindings();
            bindingViewholder.itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int ii = holder.getAdapterPosition();
                    PincodeFilterModel dataItem = data.get(ii);
                    if (dataItem.isSelected()) {
                        bindingViewholder.itemView.item.setBackground(view.getResources().getDrawable(R.drawable.ractangle_background));
                        bindingViewholder.itemView.item.setTextColor(view.getResources().getColor(R.color.color_424242));
                        dataItem.setSelected(false);
                    } else {
                        dataItem.setSelected(true);
                        bindingViewholder.itemView.item.setBackground(view.getResources().getDrawable(R.drawable.coloured_ractangle_button));
                        bindingViewholder.itemView.item.setTextColor(view.getResources().getColor(R.color.white));
                    }
                }
            });
//            if (result.isSelected()) {
//                bindingViewholder.itemView.item.setBackground(bindingViewholder.itemView.item.getResources().getDrawable(R.drawable.coloured_ractangle_button));
//                bindingViewholder.itemView.item.setTextColor(bindingViewholder.itemView.item.getResources().getColor(R.color.white));
//            } else {
                bindingViewholder.itemView.item.setBackground(bindingViewholder.itemView.item.getResources().getDrawable(R.drawable.ractangle_background));
                bindingViewholder.itemView.item.setTextColor(bindingViewholder.itemView.item.getResources().getColor(R.color.color_424242));
//            }
        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        public void filter(String constraint) {
            if (data != null) {
                data.clear();
                for (int i = 0; i < pincodeList.size(); i++) {
                    if (pincodeList.get(i).getPincode().toLowerCase().startsWith(constraint)) {
                        data.add(pincodeList.get(i));
                    }
                }
                notifyDataSetChanged();
            }
        }
    }
}
