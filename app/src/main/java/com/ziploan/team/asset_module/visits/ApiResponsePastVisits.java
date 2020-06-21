package com.ziploan.team.asset_module.visits;

import com.google.gson.annotations.SerializedName;
import com.ziploan.team.asset_module.visits.PastVisit;

import java.util.ArrayList;
import java.util.HashMap;

public class ApiResponsePastVisits{
    @SerializedName("data")
    ArrayList<HashMap<String, PastVisit>> data;
}