package com.ziploan.team.asset_module.loans;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.ziploan.team.databinding.ItemLoanFilterItemBinding;
import com.ziploan.team.databinding.ItemLoanFilterKeyBinding;
import com.ziploan.team.listeners.CustomOnItemClickListener;
import com.ziploan.team.listeners.RefreshParentListListener;
import com.ziploan.team.verification_module.caching.FilterItem;

import java.util.ArrayList;
import java.util.List;

public class LoanFilterItemsAdapter extends RecyclerView.Adapter<LoanFilterItemsAdapter.MyViewHolder> {

    private final Context mContext;
    private final RefreshParentListListener mListener;
    private List<FilterItem> itemList;

    public void resetFilter() {
        for(int i=0;i<itemList.size();i++){
            itemList.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    public List<FilterItem> getList() {
        return itemList;
    }

    public void setList(ArrayList<FilterItem> filterItems) {
        itemList = filterItems;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemLoanFilterItemBinding mBinding;

        public MyViewHolder(final ItemLoanFilterItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.tvQuestion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    itemList.get(getAdapterPosition()).setSelected(b);
                    mListener.onRefresh(getAdapterPosition(),anyItemChecked());
                }
            });
        }
    }

    private boolean anyItemChecked() {
        for(int i=0;i<itemList.size();i++){
            if(itemList.get(i).isSelected()){
                return true;
            }
        }
        return false;
    }

    public LoanFilterItemsAdapter(Context context, ArrayList<FilterItem> items, RefreshParentListListener listener) {
        this.itemList = items;
        this.mContext = context;
        this.mListener = listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemLoanFilterItemBinding binding = ItemLoanFilterItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FilterItem item = itemList.get(position);
        holder.mBinding.setFilterItem(item);
    }
 
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}