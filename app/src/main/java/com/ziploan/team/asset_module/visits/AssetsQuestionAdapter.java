package com.ziploan.team.asset_module.visits;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ziploan.team.verification_module.borrowerdetails.ZiploanQuestion;
import com.ziploan.team.databinding.ItemQuestionAssetsBinding;
import com.ziploan.team.verification_module.borrowerslist.SimpleTextChangeLister;

import java.util.ArrayList;
import java.util.List;

public class AssetsQuestionAdapter extends RecyclerView.Adapter<AssetsQuestionAdapter.MyViewHolder> {

    private final Context mContext;
    private List<ZiploanQuestion> questionList;
    private int focusedPosition = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemQuestionAssetsBinding mBinding;

        public MyViewHolder(ItemQuestionAssetsBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.etAnswer.addTextChangedListener(new SimpleTextChangeLister(){
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    questionList.get(getAdapterPosition()).setAnswer(charSequence.toString());
                    super.onTextChanged(charSequence, i, i1, i2);
                }
            });
        }

    }
    public AssetsQuestionAdapter(Context context, ArrayList<ZiploanQuestion> questions) {
        this.questionList = questions;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemQuestionAssetsBinding binding = ItemQuestionAssetsBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ZiploanQuestion question = questionList.get(position);
        if(position == focusedPosition){
            holder.mBinding.etAnswer.requestFocus();
        }
        holder.mBinding.setQuestion(question);
    }

    public void setFocusedTextField(int position){
        focusedPosition = position;
    }
 
    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public ArrayList<ZiploanQuestion> getOtherQuestion(){
        ArrayList<ZiploanQuestion> arrQuestions = new ArrayList<>();
        for(int i=0;i<questionList.size();i++){
            if(!TextUtils.isEmpty(questionList.get(i).getAnswer())){
                arrQuestions.add(questionList.get(i));
            }
        }
        return arrQuestions;
    }
}