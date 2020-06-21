package com.ziploan.team.asset_module.super_user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ziploan.team.databinding.ItemAssetManagerBinding;
import com.ziploan.team.databinding.ItemAssetManagerVisitBinding;

import java.util.ArrayList;
import java.util.List;

public class AssetsManagerVisitsAdapter extends RecyclerView.Adapter<AssetsManagerVisitsAdapter.MyViewHolder> {

    private final Context mContext;
    private List<ZipAssetManagerVisit> visits;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemAssetManagerVisitBinding mBinding;

        public MyViewHolder(ItemAssetManagerVisitBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
    public AssetsManagerVisitsAdapter(Context context, ArrayList<ZipAssetManagerVisit> moviesList) {
        this.visits = moviesList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemAssetManagerVisitBinding binding = ItemAssetManagerVisitBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ZipAssetManagerVisit assetMaangerVisit = visits.get(position);
        holder.mBinding.setAssetManagerVisit(assetMaangerVisit);
    }
 
    @Override
    public int getItemCount() {
        return visits.size();
    }
}