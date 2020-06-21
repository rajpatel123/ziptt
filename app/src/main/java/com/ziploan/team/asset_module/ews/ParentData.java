package com.ziploan.team.asset_module.ews;

import android.annotation.SuppressLint;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.ziploan.team.asset_module.EWSDisplay;
import com.ziploan.team.webapi.EWS;

import java.util.List;

@SuppressLint("ParcelCreator")
public class ParentData extends ExpandableGroup<EWSDisplay> {

    public ParentData(String title, List<EWSDisplay> items) {
        super(title, items);
    }
}