/*
package com.ziploan.team.asset_module.super_user;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ziploan.team.databinding.ItemAssetManagerBinding;
import com.ziploan.team.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class AssetsManagersAdapter extends RecyclerView.Adapter<AssetsManagersAdapter.MyViewHolder> {

    private final Context mContext;
    private List<ZipAssetManager> assetManagers;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemAssetManagerBinding mBinding;

        public MyViewHolder(ItemAssetManagerBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT, assetManagers.get(getAdapterPosition()));
                    AssetManagerVisitDetailsActivity.start(mContext,bundle);
                }
            });
        }
    }
    public AssetsManagersAdapter(Context context, ArrayList<ZipAssetManager> moviesList) {
        this.assetManagers = moviesList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemAssetManagerBinding binding = ItemAssetManagerBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ZipAssetManager assetMaanger = assetManagers.get(position);
        holder.mBinding.setAssetManager(assetMaanger);
    }
 
    @Override
    public int getItemCount() {
        return assetManagers.size();
    }
}*/
