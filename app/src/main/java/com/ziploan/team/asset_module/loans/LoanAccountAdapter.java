package com.ziploan.team.asset_module.loans;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ziploan.team.databinding.ItemLoanBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.webapi.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanAccountAdapter extends RecyclerView.Adapter<LoanAccountAdapter.MyViewHolder> {

    private final Context mContext;
    private List<Loan> loanAccounts;
    private boolean loadMore = false;

    public void setLoadMoreProgress(boolean b) {
        this.loadMore = b;
        notifyItemChanged(getItemCount()-1);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemLoanBinding mBinding;

        public MyViewHolder(ItemLoanBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT,loanAccounts.get(getAdapterPosition()));
                    LoansDetailsActivity.start(mContext,bundle);
                }
            });
        }
    }
    public LoanAccountAdapter(Context context, ArrayList<Loan> moviesList) {
        this.loanAccounts = moviesList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemLoanBinding binding = ItemLoanBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Loan loan = loanAccounts.get(position);
        holder.mBinding.setLoan(loan);
        if(this.loadMore && (loanAccounts.size()-1) == position){
            holder.mBinding.loadMoreProgress.setVisibility(View.VISIBLE);
        }else {
            holder.mBinding.loadMoreProgress.setVisibility(View.GONE);
        }
    }
 
    @Override
    public int getItemCount() {
        return loanAccounts.size();
    }
}