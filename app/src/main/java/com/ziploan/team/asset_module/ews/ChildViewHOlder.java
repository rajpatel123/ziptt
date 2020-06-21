package com.ziploan.team.asset_module.ews;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.EWSDisplay;
import com.ziploan.team.databinding.ChildViewTriggerItemBinding;


public class ChildViewHOlder extends ChildViewHolder {

    private final TextView tvDueDate;
    private final TextView tvOverdue;
    private final TextView tvEMIAmount;
    private final LinearLayout llTriggerItem;
    private final FrameLayout flRepaymentBlock;

    public ChildViewHOlder(View itemView) {
        super(itemView);
        tvDueDate = itemView.findViewById(R.id.tv_due_date);
        tvOverdue = itemView.findViewById(R.id.tv_overdue);
        tvEMIAmount = itemView.findViewById(R.id.tv_emi_amount);
        llTriggerItem = itemView.findViewById(R.id.ll_trigger_items);
        flRepaymentBlock = itemView.findViewById(R.id.fl_repayment_block);
    }

    public void onBind(EWSDisplay ews, ExpandableGroup group, Activity activity) {
        if (!TextUtils.isEmpty(ews.getEmi_date())) {
            flRepaymentBlock.setVisibility(View.VISIBLE);
        } else {
            flRepaymentBlock.setVisibility(View.GONE);
        }
        tvOverdue.setText(ews.getAmt_overdue());
        tvDueDate.setText(ews.getEmi_date());
        tvEMIAmount.setText(ews.getEmi());
        llTriggerItem.removeAllViews();
        for (int i = 0; i < ews.getTriggers().size(); i++) {
            ChildViewTriggerItemBinding binding = ChildViewTriggerItemBinding.inflate(LayoutInflater.from(activity), llTriggerItem, false);
            binding.setEws(ews.getTriggers().get(i));
            llTriggerItem.addView(binding.getRoot());
        }
    }
}