package com.ziploan.team.verification_module.borrowerdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ziploan.team.R;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.MyViewHolder> {

    private final Context mContext;
    private List<ZiploanQuestion> questionsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public EditText etAnswer;
        public TextView tvQuestion;

        public MyViewHolder(View view) {
            super(view);
            etAnswer = (EditText) view.findViewById(R.id.et_answer);
            tvQuestion = (TextView) view.findViewById(R.id.tv_question);
        }
    }

    public QuestionsAdapter(Context context, List<ZiploanQuestion> moviesList) {
        this.questionsList = moviesList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ZiploanQuestion question = questionsList.get(position);
        holder.tvQuestion.setText((position+1)+". "+question.getQuestion());
        holder.etAnswer.setText(question.getAnswer());
    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }
}