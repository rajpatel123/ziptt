
package com.ziploan.team.webapi.cibil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Datereportedcertified {

    @SerializedName("$date")
    @Expose
    private long $date;

    public long get$date() {
        return $date;
    }

    public void set$date(Integer $date) {
        this.$date = $date;
    }

    public String getDate(){
        return new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)
                .format(new Date($date));
    }
}
