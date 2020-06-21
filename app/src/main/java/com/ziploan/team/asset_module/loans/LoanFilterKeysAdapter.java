package com.ziploan.team.asset_module.loans;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ziploan.team.databinding.ItemLoanFilterKeyBinding;
import com.ziploan.team.listeners.CustomOnItemClickListener;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.caching.FilterKey;
import com.ziploan.team.webapi.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanFilterKeysAdapter extends RecyclerView.Adapter<LoanFilterKeysAdapter.MyViewHolder> {

    private final Context mContext;
    private final CustomOnItemClickListener mItemClickListener;
    private List<FilterKey> keyList;

    public List<FilterKey> getList() {
        return keyList;
    }

    public void refreshFilterMarkerForKey(boolean is_selected) {
        for(int i=0;i<getList().size();i++){
            if(getList().get(i).is_selected()){
                getList().get(i).setIs_filter_selected(is_selected);
                notifyItemChanged(i);
            }
        }
    }

    public void resetFilter() {
        for(int i=0;i<getList().size();i++){
            getList().get(i).setIs_filter_selected(false);
        }
        notifyDataSetChanged();
    }

    public void setList(ArrayList<FilterKey> filtersKey) {
        keyList = filtersKey;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemLoanFilterKeyBinding mBinding;

        public MyViewHolder(ItemLoanFilterKeyBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(mBinding,getAdapterPosition());
                }
            });
        }
    }
    public LoanFilterKeysAdapter(Context context, ArrayList<FilterKey> keys, CustomOnItemClickListener listener) {
        this.keyList = keys;
        this.mContext = context;
        this.mItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemLoanFilterKeyBinding binding = ItemLoanFilterKeyBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FilterKey key = keyList.get(position);
        holder.mBinding.setFilterKey(key);
    }

    public void setSelectedForPosition(int position){
        int prevSelectedPos = 0;
        for(int i=0;i<getList().size();i++){
            if(getList().get(i).is_selected())
                prevSelectedPos = i;
            getList().get(i).setIs_selected(false);
        }
        getList().get(position).setIs_selected(true);
        notifyItemChanged(prevSelectedPos);
        notifyItemChanged(position);
    }
 
    @Override
    public int getItemCount() {
        return keyList.size();
    }
}