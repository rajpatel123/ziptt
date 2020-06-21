package com.ziploan.team.asset_module.visits;

import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;

import java.util.List;

/**
 * Created by ZIploan-Nitesh on 9/4/2017.
 */

public interface LoadPhotoListener {
    public void getImages(List<ZiploanPhoto> arrImages,int sourceType);
    public void processingImages();
}
