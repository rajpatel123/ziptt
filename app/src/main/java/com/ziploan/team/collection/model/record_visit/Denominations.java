
package com.ziploan.team.collection.model.record_visit;

import com.google.gson.annotations.SerializedName;

public class Denominations {

    @SerializedName("2000")
    private int m2000;
    @SerializedName("500")
    private int m500;
    @SerializedName("200")
    private int m200;
    @SerializedName("100")
    private int m100;
    @SerializedName("50")
    private int m50;
    @SerializedName("20")
    private int m20;

    @SerializedName("10")
    private int m10;
    @SerializedName("5")
    private int m5;
    @SerializedName("2")
    private int m2;
    @SerializedName("1")
    private int m1;

    public Denominations(String m2000, String m500, String m200, String m100, String m50, String m20, String m10, String m5, String m2, String m1) {
        this.m2000 = Integer.parseInt(m2000);
        this.m500 = Integer.parseInt(m500);
        this.m200 = Integer.parseInt(m200);
        this.m100 = Integer.parseInt(m100);
        this.m50 = Integer.parseInt(m50);
        this.m20 = Integer.parseInt(m20);
        this.m10 = Integer.parseInt(m10);
        this.m5 = Integer.parseInt(m5);
        this.m2 = Integer.parseInt(m2);
        this.m1 = Integer.parseInt(m1);
    }

    public int getM2000() {
        return m2000;
    }

    public void setM2000(int m2000) {
        this.m2000 = m2000;
    }

    public int getM500() {
        return m500;
    }

    public void setM500(int m500) {
        this.m500 = m500;
    }

    public int getM200() {
        return m200;
    }

    public void setM200(int m200) {
        this.m200 = m200;
    }

    public int getM100() {
        return m100;
    }

    public void setM100(int m100) {
        this.m100 = m100;
    }

    public int getM50() {
        return m50;
    }

    public void setM50(int m50) {
        this.m50 = m50;
    }

    public int getM20() {
        return m20;
    }

    public void setM20(int m20) {
        this.m20 = m20;
    }

    public int getM10() {
        return m10;
    }

    public void setM10(int m10) {
        this.m10 = m10;
    }

    public int getM5() {
        return m5;
    }

    public void setM5(int m5) {
        this.m5 = m5;
    }

    public int getM2() {
        return m2;
    }

    public void setM2(int m2) {
        this.m2 = m2;
    }

    public int getM1() {
        return m1;
    }

    public void setM1(int m1) {
        this.m1 = m1;
    }
}
