package com.ziploan.team.asset_module.ews;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import com.ziploan.team.R;
import com.ziploan.team.utils.ZiploanUtil;


public class ParentViewHolder extends GroupViewHolder {

    private TextView tvTriggerName;

    public ParentViewHolder(View itemView) {
        super(itemView);
        tvTriggerName = (TextView) itemView.findViewById(R.id.tv_trigger_name);
    }

    @Override
    public void expand() {
        tvTriggerName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.downward, 0);
    }

    @Override
    public void collapse() {
        tvTriggerName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.list_forword, 0);
    }

    public void setGroupName(ExpandableGroup group) {
        String title = ZiploanUtil.keyToText(group.getTitle());
        Spannable wordtoSpan = new SpannableString(title);
        int startPos = title.indexOf("-")+1;
        wordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), startPos, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTriggerName.setText(wordtoSpan);
    }
}