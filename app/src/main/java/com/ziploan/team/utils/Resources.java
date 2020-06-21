package com.ziploan.team.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by Ziploan-Anand on 7/5/2016.
 */
public class Resources {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static Typeface getTypeface(Context context, String name) {
        Typeface tf = fontCache.get(name);
        if (tf == null) {
            try {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }
}
