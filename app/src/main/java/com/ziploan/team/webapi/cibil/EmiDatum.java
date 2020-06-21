
package com.ziploan.team.webapi.cibil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmiDatum {

    @SerializedName("month-year")
    @Expose
    private String monthYear;
    @SerializedName("emi")
    @Expose
    private Double emi;

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public Double getEmi() {
        return emi;
    }

    public void setEmi(Double emi) {
        this.emi = emi;
    }

}
