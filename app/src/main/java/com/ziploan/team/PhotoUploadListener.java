package com.ziploan.team;

import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;

/**
 * Created by ZIploan-Nitesh on 9/22/2017.
 */

public interface PhotoUploadListener {
    void onUploadSuccess(ZiploanPhoto photo);
    void onUploadFailed(ZiploanPhoto photo);
    void onUploadStarted(ZiploanPhoto photo);
}
