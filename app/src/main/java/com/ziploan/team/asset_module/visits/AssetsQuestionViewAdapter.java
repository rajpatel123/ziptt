package com.ziploan.team.asset_module.visits;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;
import com.ziploan.team.databinding.ItemQuestionViewAssetsBinding;

import java.util.ArrayList;
import java.util.List;

public class AssetsQuestionViewAdapter extends RecyclerView.Adapter<AssetsQuestionViewAdapter.MyViewHolder> {

    private final Context mContext;
    private List<ZiploanQuestion> questionList;
    private int focusedPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemQuestionViewAssetsBinding mBinding;

        public MyViewHolder(ItemQuestionViewAssetsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
    public AssetsQuestionViewAdapter(Context context, ArrayList<ZiploanQuestion> questions) {
        this.questionList = questions;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemQuestionViewAssetsBinding binding = ItemQuestionViewAssetsBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ZiploanQuestion question = questionList.get(position);
        holder.mBinding.setQuestion(question);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}