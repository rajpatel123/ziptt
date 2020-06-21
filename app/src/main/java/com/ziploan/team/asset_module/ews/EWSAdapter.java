package com.ziploan.team.asset_module.ews;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.EWSDisplay;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.webapi.EWS;

import java.util.List;


public class EWSAdapter extends ExpandableRecyclerViewAdapter<ParentViewHolder, ChildViewHOlder> {

    private Activity activity;

    public EWSAdapter(Activity activity, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.activity = activity;
    }

    @Override
    public ParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.group_view_holder, parent, false);

        return new ParentViewHolder(view);
    }

    @Override
    public ChildViewHOlder onCreateChildViewHolder(ViewGroup parent, final int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.child_view_holder, parent, false);
        return new ChildViewHOlder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHOlder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final EWSDisplay ewsDisplay = ((ParentData) group).getItems().get(childIndex);
        holder.onBind(ewsDisplay,group,activity);
    }

    @Override
    public void onBindGroupViewHolder(ParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }
}