package com.ziploan.team.asset_module.loans;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ziploan.team.databinding.ItemLoanBinding;
import com.ziploan.team.databinding.ItemLoanOverdueBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.webapi.Loan;
import com.ziploan.team.webapi.OverdueBreakup;

import java.util.ArrayList;
import java.util.List;

public class LoanOverdueAdapter extends RecyclerView.Adapter<LoanOverdueAdapter.MyViewHolder> {

    private final Context mContext;
    private List<OverdueBreakup> loanOverdueBreakups;
    private boolean loadMore = false;

    public void setLoadMoreProgress(boolean b) {
        this.loadMore = b;
        notifyItemChanged(getItemCount()-1);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemLoanOverdueBinding mBinding;

        public MyViewHolder(ItemLoanOverdueBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
    public LoanOverdueAdapter(Context context, ArrayList<OverdueBreakup> overdueBreakups) {
        this.loanOverdueBreakups = overdueBreakups;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemLoanOverdueBinding binding = ItemLoanOverdueBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OverdueBreakup loanOverdue = loanOverdueBreakups.get(position);
        holder.mBinding.setLoanOverdue(loanOverdue);
        if(this.loadMore && (loanOverdueBreakups.size()-1) == position){
            holder.mBinding.loadMoreProgress.setVisibility(View.VISIBLE);
        }else {
            holder.mBinding.loadMoreProgress.setVisibility(View.GONE);
        }
    }
 
    @Override
    public int getItemCount() {
        return loanOverdueBreakups.size();
    }
}