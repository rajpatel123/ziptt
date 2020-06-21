package com.ziploan.team.verification_module.services;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.ziploan.team.utils.ZiploanSPUtils;

public class LocationListener implements android.location.LocationListener {

    private final Location mLastLocation;
    private final Context mContext;

    public LocationListener(Context context, String provider) {
        mLastLocation = new Location(provider);
        mContext = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        ZiploanSPUtils.getInstance(mContext).setTempLat(location.getLatitude());
        ZiploanSPUtils.getInstance(mContext).setTempLng(location.getLongitude());
        mLastLocation.set(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}