/*
package com.ziploan.team.verification_module.borrowerdetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.squareup.picasso.Picasso;
import com.ziploan.team.R;
import com.ziploan.team.verification_module.base.BaseFragment;
import com.ziploan.team.databinding.FragmentDocumentBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ImageUtils;
import com.ziploan.team.utils.JsonLocalDataFetcher;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.verifyekyc.PhotosAdapter;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by ZIploan-Nitesh on 2/4/2017.
 *//*

public class DocumentFragment extends BaseFragment implements View.OnClickListener, PhotosAdapter.PhotosAdapterListener, AdapterView.OnItemSelectedListener {

    private FragmentDocumentBinding binding;
    private List<ZiploanPhoto> arrEntityPhotos = null;
    private List<ZiploanPhoto> arrBusinessPhotos = null;
    private List<ZiploanPhoto> arrResidencePhotos = null;
    private PhotosAdapter adapterEntity;
    private PhotosAdapter adapterBusiness;
    private PhotosAdapter adapterResidence;
    private int currentCameraIndex;
    private ZiploanDocumentInfo documentInfo;
    private boolean isNeedToDisable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDocumentBinding.inflate(inflater,container,false);
        documentInfo = getDocumentInfoFromBundle(getArguments());
        setListeners();
        fillSpinnersData();
        arrEntityPhotos = new ArrayList<>();
        arrBusinessPhotos = new ArrayList<>();
        arrResidencePhotos = new ArrayList<>();
        setDataToViews(documentInfo);
        return binding.getRoot();
    }

    private void setDataToViews(ZiploanDocumentInfo documentInfo) {
        if(documentInfo!=null){

            if(!TextUtils.isEmpty(documentInfo.getLoan_account_type())){
                binding.spinnerLoanType.setSelection(((SimpleSpinnerAdapter)binding.spinnerLoanType.getAdapter()).getPositionByItem(documentInfo.getLoan_account_type()));
            }

            if(!TextUtils.isEmpty(documentInfo.getEntity_proof_document_no())){
                binding.etEntityProofNumber.setText(documentInfo.getEntity_proof_document_no());
            }


            if(!TextUtils.isEmpty(documentInfo.getBusiness_address_proof_document_no())){
                binding.etBusinessProofNumber.setText(documentInfo.getBusiness_address_proof_document_no());
            }

            if(!TextUtils.isEmpty(documentInfo.getResidence_address_proof_document_no())){
                binding.etResidenceProofNumber.setText(documentInfo.getResidence_address_proof_document_no());
            }

            List<ZiploanPhoto> arrPhotosEntity = documentInfo.getEntity_proof_document_url();
            List<ZiploanPhoto> arrPhotosResidence = documentInfo.getResidence_address_proof_document_url();
            List<ZiploanPhoto> arrPhotosBusiness = documentInfo.getBusiness_address_proof_document_url();

            if(isNeedToDisable){
                disableAllViews();
                hideAllSpinner();
                manageViewAndDataForPANBlock(documentInfo);
                manageViewAndDataForLoanTypeBloack();
                manageViewAndDataForEntityProof();
                manageViewAndDataForBusinessAddProof();
                manageViewAndDataForResidenceAddProof();

            }else {
                if(!TextUtils.isEmpty(documentInfo.getPrimary_applicant_pan_url())){
                    binding.ivFirstPan.setTag(documentInfo.getPrimary_applicant_pan_url());
                    Picasso.with(mContext).load(new File(documentInfo.getPrimary_applicant_pan_url())).into(binding.ivFirstPan);
                }
                if(!TextUtils.isEmpty(documentInfo.getSecondary_applicant_pan_url())){
                    binding.ivSecondPan.setTag(documentInfo.getSecondary_applicant_pan_url());
                    Picasso.with(mContext).load(new File(documentInfo.getSecondary_applicant_pan_url())).into(binding.ivSecondPan);
                }

                if(arrPhotosResidence!=null && arrPhotosResidence.size()>0){
                    for(ZiploanPhoto photo:arrPhotosResidence){
                        setResidencePhotosViews(photo);
                    }
                }
                //setResidencePhotosViews(new ZiploanPhoto(""));
                if(arrPhotosEntity!=null && arrPhotosEntity.size()>0){
                    for(ZiploanPhoto photo:arrPhotosEntity){
                        setEntityPhotosViews(photo);
                    }
                }
                //setEntityPhotosViews(new ZiploanPhoto(""));
                if(arrPhotosBusiness!=null && arrPhotosBusiness.size()>0){
                    for(ZiploanPhoto photo:arrPhotosBusiness){
                        setBusinessPhotosViews(photo);
                    }
                }
                //setBusinessPhotosViews(new ZiploanPhoto(""));


                if(!TextUtils.isEmpty(documentInfo.getEntity_proof_document_type())){
                    int position = ((SimpleSpinnerAdapter)binding.spinnerEntity.getAdapter()).getPositionByItem(documentInfo.getEntity_proof_document_type());
                    binding.spinnerEntity.setSelection(position);
                    if(position==binding.spinnerEntity.getCount()-1){
                        binding.etEntityType.setText(documentInfo.getEntity_proof_document_type());
                    }
                }

                if(!TextUtils.isEmpty(documentInfo.getBusiness_address_proof_document_type())){
                    int position = ((SimpleSpinnerAdapter)binding.spinnerBusinessAddress.getAdapter()).getPositionByItem(documentInfo.getBusiness_address_proof_document_type());
                    binding.spinnerBusinessAddress.setSelection(position);
                    if(position==binding.spinnerBusinessAddress.getCount()-1){
                        binding.etBusinessAddProof.setText(documentInfo.getBusiness_address_proof_document_type());
                    }
                }
                if(!TextUtils.isEmpty(documentInfo.getResidence_address_proof_document_type())){
                    int position = ((SimpleSpinnerAdapter)binding.spinnerResidenceAddress.getAdapter()).getPositionByItem(documentInfo.getResidence_address_proof_document_type());
                    binding.spinnerResidenceAddress.setSelection(position);
                    if(position==binding.spinnerResidenceAddress.getCount()-1){
                        binding.etResidenceAddProof.setText(documentInfo.getResidence_address_proof_document_type());
                    }
                }
            }
        }
    }

    private void disableAllViews() {
        enableField(binding.spinnerLoanType,false);
        enableField(binding.spinnerEntity,false);
        enableField(binding.spinnerBusinessAddress,false);
        enableField(binding.spinnerResidenceAddress,false);
        enableField(binding.etResidenceAddProof,false);
        enableField(binding.etResidenceProofNumber,false);
        enableField(binding.etBusinessAddProof,false);
        enableField(binding.etBusinessProofNumber,false);
        enableField(binding.etEntityProofNumber,false);
        enableField(binding.etEntityType,false);
    }

    private void manageViewAndDataForResidenceAddProof() {
        String residenceType = documentInfo.getResidence_address_proof_document_type();
        String residenceNo = documentInfo.getResidence_address_proof_document_no();
        List<ZiploanPhoto> photoArr = documentInfo.getResidence_address_proof_document_url();
        if(!isPhotoArrayNotEmpty(photoArr) && TextUtils.isEmpty(residenceNo)){
            binding.labelResidenceAddProof.setVisibility(View.GONE);
            binding.cardViewResidenceAddProof.setVisibility(View.GONE);
        }else {
            if (isPhotoArrayNotEmpty(photoArr)) {
                binding.tvViewResidenceDoc.setVisibility(View.VISIBLE);
                binding.tvViewResidenceDoc.setTag(photoArr.get(0).getPhotoPath());
            }
            ((View)binding.rvResidencePhotos.getParent()).setVisibility(View.GONE);
            binding.etResidenceProofNumber.setVisibility(TextUtils.isEmpty(residenceNo)?View.GONE:View.VISIBLE);
            ((View)binding.etResidenceAddProof.getParent()).setVisibility(TextUtils.isEmpty(residenceType)?View.GONE:View.VISIBLE);
            binding.etResidenceAddProof.setText(residenceType);
        }

    }

    private void manageViewAndDataForBusinessAddProof() {
        String businessType = documentInfo.getBusiness_address_proof_document_type();
        String businessNo = documentInfo.getBusiness_address_proof_document_no();
        List<ZiploanPhoto> photoArr = documentInfo.getBusiness_address_proof_document_url();
        if(!isPhotoArrayNotEmpty(photoArr) && TextUtils.isEmpty(businessNo)){
            binding.labelBusinessAddProof.setVisibility(View.GONE);
            binding.cardViewBusinesAddProof.setVisibility(View.GONE);
        }else {
            if(isPhotoArrayNotEmpty(photoArr)){
                binding.tvViewBusinessDoc.setVisibility(View.VISIBLE);
                binding.tvViewBusinessDoc.setTag(photoArr.get(0).getPhotoPath());
            }
            ((View)binding.rvBusinessPhotos.getParent()).setVisibility(View.GONE);
            binding.etBusinessAddProof.setText(businessType);
            ((View)binding.etBusinessAddProof.getParent()).setVisibility(TextUtils.isEmpty(businessType)?View.GONE:View.VISIBLE);
            binding.etBusinessProofNumber.setVisibility(TextUtils.isEmpty(businessNo)?View.GONE:View.VISIBLE);
        }

    }

    private void manageViewAndDataForEntityProof() {
        String entityNo = documentInfo.getEntity_proof_document_no();
        List<ZiploanPhoto> urlArray = documentInfo.getEntity_proof_document_url();
        String entityType = documentInfo.getEntity_proof_document_type();
        if(!isPhotoArrayNotEmpty(urlArray) && TextUtils.isEmpty(entityNo)){
            binding.labelEntityProof.setVisibility(View.GONE);
            binding.cardViewEntityProof.setVisibility(View.GONE);
        }else {
            if(isPhotoArrayNotEmpty(urlArray)){
                binding.tvViewEntityDoc.setTag(urlArray.get(0).getPhotoPath());
                binding.tvViewEntityDoc.setVisibility(View.VISIBLE);
            }
            binding.etEntityProofNumber.setVisibility(TextUtils.isEmpty(entityNo)?View.GONE:View.VISIBLE);
            ((View)binding.rvEntityPhotos.getParent()).setVisibility(View.GONE);
            ((View)binding.etEntityType.getParent()).setVisibility(TextUtils.isEmpty(entityType)?View.GONE:View.VISIBLE);
            binding.etEntityType.setText(entityType);
        }
    }

    private void manageViewAndDataForLoanTypeBloack() {
        if(TextUtils.isEmpty(documentInfo.getLoan_account_type())){
            binding.labelLoantype.setVisibility(View.GONE);
            binding.cardViewLoanType.setVisibility(View.GONE);
            binding.cardViewEntityProof.setVisibility(View.VISIBLE);
            binding.labelEntityProof.setVisibility(View.VISIBLE);
        }else {
            binding.labelLoantype.setVisibility(View.VISIBLE);
            ((View)binding.etLoanType.getParent()).setVisibility(View.VISIBLE);
            binding.etLoanType.setText(JsonLocalDataFetcher.fetchLoanType(mContext).get(Integer.parseInt(documentInfo.getLoan_account_type())));
            enableField(binding.etLoanType,false);
        }
    }

    private void manageViewAndDataForPANBlock(ZiploanDocumentInfo documentInfo) {
        String panFirst = documentInfo.getPrimary_applicant_pan_url();
        String panSecond = documentInfo.getSecondary_applicant_pan_url();
        if(TextUtils.isEmpty(panFirst) && TextUtils.isEmpty(panSecond)){
            binding.labelPan.setVisibility(View.GONE);
            binding.cardViewPan.setVisibility(View.GONE);
        }else {
            binding.ivFirstPan.setVisibility(View.GONE);
            binding.ivSecondPan.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(panFirst)){
                binding.tvViewPanFirst.setVisibility(View.VISIBLE);
                binding.tvViewPanFirst.setTag(panFirst);
            }else {
                ((View)binding.tvViewPanFirst.getParent()).setVisibility(View.GONE);
            }

            if(!TextUtils.isEmpty(panSecond)){
                binding.tvViewPanSecond.setVisibility(View.VISIBLE);
                binding.tvViewPanSecond.setTag(panSecond);
            }else {
                ((View)binding.tvViewPanSecond.getParent()).setVisibility(View.GONE);
            }
        }
    }

    private boolean isVisible(View view) {
        return view.getVisibility()== View.VISIBLE;
    }

    private void hideAllSpinner() {
        ((View)binding.spinnerLoanType.getParent()).setVisibility(View.GONE);
        ((View)binding.spinnerEntity.getParent()).setVisibility(View.GONE);
        ((View)binding.spinnerBusinessAddress.getParent()).setVisibility(View.GONE);
        ((View)binding.spinnerResidenceAddress.getParent()).setVisibility(View.GONE);
    }

    private ZiploanDocumentInfo getDocumentInfoFromBundle(Bundle bundle) {
        ZiploanDocumentInfo info = new ZiploanDocumentInfo();
        if(bundle!=null && bundle.containsKey(AppConstant.Key.EXTRA_DOCUMENT_INFO)){
            info = bundle.getParcelable(AppConstant.Key.EXTRA_DOCUMENT_INFO);
        }
        if(bundle!=null && bundle.containsKey(AppConstant.Key.IS_NEED_TO_DISABLE)){
            isNeedToDisable = bundle.getBoolean(AppConstant.Key.IS_NEED_TO_DISABLE);
        }
        return info;
    }

    private void fillSpinnersData() {
        binding.spinnerLoanType.setAdapter(new SimpleSpinnerAdapter(JsonLocalDataFetcher.fetchLoanType(mContext)));
        binding.spinnerEntity.setAdapter(new SimpleSpinnerAdapter(JsonLocalDataFetcher.fetchEntityProofs(mContext)));
        binding.spinnerBusinessAddress.setAdapter(new SimpleSpinnerAdapter(JsonLocalDataFetcher.fetchBusinessProofs(mContext)));
        binding.spinnerResidenceAddress.setAdapter(new SimpleSpinnerAdapter(JsonLocalDataFetcher.fetchResidenceProofs(mContext)));
    }

    private void setListeners() {
        binding.spinnerLoanType.setOnItemSelectedListener(this);
        binding.spinnerEntity.setOnItemSelectedListener(this);
        binding.spinnerBusinessAddress.setOnItemSelectedListener(this);
        binding.spinnerResidenceAddress.setOnItemSelectedListener(this);
        binding.ivFirstPan.setOnClickListener(this);
        binding.ivSecondPan.setOnClickListener(this);
        binding.tvViewPanFirst.setOnClickListener(this);
        binding.tvViewPanSecond.setOnClickListener(this);
        binding.tvViewEntityDoc.setOnClickListener(this);
        binding.tvViewBusinessDoc.setOnClickListener(this);
        binding.tvViewResidenceDoc.setOnClickListener(this);
        binding.deleteFirstPanPhoto.setOnClickListener(this);
        binding.deleteSecondPanPhoto.setOnClickListener(this);
    }

    private void setEntityPhotosViews(ZiploanPhoto photo) {
        arrEntityPhotos.add(photo);
        adapterEntity = new PhotosAdapter(mContext,arrEntityPhotos,this, AppConstant.ENTITY_PHOTO);
        binding.rvEntityPhotos.setLayoutManager(new GridLayoutManager(mContext,3));
        binding.rvEntityPhotos.setAdapter(adapterEntity);
    }

    private void setBusinessPhotosViews(ZiploanPhoto photo) {
        arrBusinessPhotos.add(photo);
        adapterBusiness = new PhotosAdapter(mContext,arrBusinessPhotos,this,AppConstant.BUSINESS_PHOTO);
        binding.rvBusinessPhotos.setLayoutManager(new GridLayoutManager(mContext,3));
        binding.rvBusinessPhotos.setAdapter(adapterBusiness);
    }

    private void setResidencePhotosViews(ZiploanPhoto photo) {
        arrResidencePhotos.add(photo);
        adapterResidence = new PhotosAdapter(mContext,arrResidencePhotos,this,AppConstant.RESIDENCE_PHOTO);
        binding.rvResidencePhotos.setLayoutManager(new GridLayoutManager(mContext,3));
        binding.rvResidencePhotos.setAdapter(adapterResidence);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ivFirstPan:
                if(binding.ivFirstPan.getTag()!=null && !TextUtils.isEmpty(binding.ivFirstPan.getTag().toString())){
                    ZiploanUtil.openPhotoInZoom(mContext,binding.ivFirstPan.getTag().toString());
                }else {
                    openCameraGalleyOptions(0,AppConstant.PAN_FIRST_PHOTO,false);
                }
                break;
            case R.id.ivSecondPan:
                if(binding.ivSecondPan.getTag()!=null && !TextUtils.isEmpty(binding.ivSecondPan.getTag().toString())){
                    ZiploanUtil.openPhotoInZoom(mContext,binding.ivSecondPan.getTag().toString());
                }else {
                    openCameraGalleyOptions(0,AppConstant.PAN_SECOND_PHOTO,false);
                }
                break;
            case R.id.tv_view_pan_first:
            case R.id.tv_view_pan_second:
            case R.id.tv_view_entity_doc:
            case R.id.tv_view_business_doc:
            case R.id.tv_view_residence_doc:
                openPDFBrowser(view);
                break;
            case R.id.delete_first_pan_photo:
                binding.ivFirstPan.setTag("");
                binding.ivFirstPan.setImageResource(R.mipmap.photo);
                binding.deleteFirstPanPhoto.setVisibility(View.GONE);
                break;
            case R.id.delete_second_pan_photo:
                binding.ivSecondPan.setTag("");
                binding.ivSecondPan.setImageResource(R.mipmap.photo);
                binding.deleteSecondPanPhoto.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void deletePhoto(int position, int index) {
        switch (index){
            case AppConstant.ENTITY_PHOTO:
                arrEntityPhotos.remove(position);
                adapterEntity.notifyDataSetChanged();
                break;
            case AppConstant.BUSINESS_PHOTO:
                arrBusinessPhotos.remove(position);
                adapterBusiness.notifyDataSetChanged();
                break;
            case AppConstant.RESIDENCE_PHOTO:
                arrResidencePhotos.remove(position);
                adapterResidence.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void retryUpload(int position, int index) {

    }

    @Override
    public void openCameraGalleyOptions(int position,int index,boolean multi_selection) {
        currentCameraIndex = index;
        openCameraGalleryOptions(multi_selection);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()){
            case R.id.spinnerLoanType:
                if(binding.spinnerLoanType.getSelectedItem().toString().toLowerCase().contains("business")){
                    binding.labelEntityProof.setVisibility(View.VISIBLE);
                    binding.cardViewEntityProof.setVisibility(View.VISIBLE);
                }else {
                    binding.labelEntityProof.setVisibility(View.GONE);
                    binding.cardViewEntityProof.setVisibility(View.GONE);
                }
                break;
            case R.id.spinnerEntity:
                if(position==adapterView.getAdapter().getCount()-1){
                    ((View)binding.etEntityType.getParent()).setVisibility(View.VISIBLE);
                }else {
                    ((View)binding.etEntityType.getParent()).setVisibility(View.GONE);
                }
                break;
            case R.id.spinnerBusinessAddress:
                if(position==adapterView.getAdapter().getCount()-1){
                    ((View)binding.etBusinessAddProof.getParent()).setVisibility(View.VISIBLE);
                }else {
                    ((View)binding.etBusinessAddProof.getParent()).setVisibility(View.GONE);
                }
                break;
            case R.id.spinnerResidenceAddress:
                if(position==adapterView.getAdapter().getCount()-1){
                    ((View)binding.etResidenceAddProof.getParent()).setVisibility(View.VISIBLE);
                }else {
                    ((View)binding.etResidenceAddProof.getParent()).setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case AppConstant.CAMERA_REQUEST:
                    setImageToCorresPondingView(ImageUtils.compressImage(mContext, capturedImageUri, null));
                    break;
                case AppConstant.GALLERY_REQUEST:
                    if(data.getData()!=null){
                        setImageToCorresPondingView(ImageUtils.compressImage(mContext, data.getData(),null));
                    }else if(data.getClipData()!=null){
                        showProgressDialog(mContext,"Processing images...");
                        ImageUtils.asyncCompressMultipleImage(mContext,data.getClipData(), new ImageUtils.OnCompressDone() {
                            @Override
                            public void onSuccess(List<String> uris) {// This method runs on Main Thread
                                hideProgressDialog();
                                if(data.getClipData().getItemCount()>uris.size()){
                                    showLongToast("Some of the selected images not found.");
                                }
                                setImagesToCorresPondingView((ArrayList<String>) uris);
                            }
                        });
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setImagesToCorresPondingView(ArrayList<String> imageUris) {
        if(imageUris!=null && imageUris.size()>0){
            for(int i=0;i<imageUris.size();i++){
                setImageToCorresPondingView(imageUris.get(i));
            }
        }
    }
    private void setImageToCorresPondingView(String imageUri) {
        switch (currentCameraIndex){
            case AppConstant.ENTITY_PHOTO:
                //arrEntityPhotos.add(arrEntityPhotos.size()-1,new ZiploanPhoto(imageUri));
                adapterEntity.notifyDataSetChanged();
                scrollToView(binding.rvEntityPhotos);

                break;
            case AppConstant.BUSINESS_PHOTO:
                //arrBusinessPhotos.add(arrBusinessPhotos.size()-1,new ZiploanPhoto(imageUri));
                adapterBusiness.notifyDataSetChanged();
                scrollToView(binding.rvBusinessPhotos);
                break;
            case AppConstant.RESIDENCE_PHOTO:
                //arrResidencePhotos.add(arrResidencePhotos.size()-1,new ZiploanPhoto(imageUri));
                adapterResidence.notifyDataSetChanged();
                scrollToView(binding.rvResidencePhotos);
                break;
            case AppConstant.PAN_FIRST_PHOTO:
                binding.ivFirstPan.setTag(imageUri);
                Picasso.with(mContext).load(new File(binding.ivFirstPan.getTag().toString())).into(binding.ivFirstPan);
                binding.deleteFirstPanPhoto.setVisibility(View.VISIBLE);
                break;
            case AppConstant.PAN_SECOND_PHOTO:
                binding.ivSecondPan.setTag(imageUri);
                Picasso.with(mContext).load(new File(binding.ivSecondPan.getTag().toString())).into(binding.ivSecondPan);
                binding.deleteSecondPanPhoto.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void scrollToView(View view) {
        view.getParent().requestChildFocus(view,view);
    }

    public ZiploanDocumentInfo getDocumentInfo() {
        ZiploanDocumentInfo info = new ZiploanDocumentInfo();
        try{
            info.setPrimary_applicant_pan_url(binding.ivFirstPan.getTag()!=null?binding.ivFirstPan.getTag().toString():"");
            info.setSecondary_applicant_pan_url(binding.ivSecondPan.getTag()!=null?binding.ivSecondPan.getTag().toString():"");
            info.setLoan_account_type(binding.spinnerLoanType.getSelectedItemPosition()==0?"":binding.spinnerLoanType.getSelectedItemPosition()+"");

            info.setEntity_proof_document_type(binding.spinnerEntity.getSelectedItemPosition()==0?"":binding.spinnerEntity.getSelectedItem().toString());
            if(info.getEntity_proof_document_type().equalsIgnoreCase(binding.spinnerEntity.getItemAtPosition(binding.spinnerEntity.getCount()-1).toString())){
                info.setEntity_proof_document_type(binding.etEntityType.getText().toString().trim());
            }

            info.setEntity_proof_document_no(binding.etEntityProofNumber.getText().toString().trim());
            info.setEntity_proof_document_url(arrEntityPhotos.subList(0,arrEntityPhotos.size()-1));

            info.setBusiness_address_proof_document_type(binding.spinnerBusinessAddress.getSelectedItemPosition()==0?"":binding.spinnerBusinessAddress.getSelectedItem().toString());
            if(info.getBusiness_address_proof_document_type().equalsIgnoreCase(binding.spinnerBusinessAddress.getItemAtPosition(binding.spinnerBusinessAddress.getCount()-1).toString())){
                info.setBusiness_address_proof_document_type(binding.etBusinessAddProof.getText().toString().trim());
            }

            info.setBusiness_address_proof_document_no(binding.etBusinessProofNumber.getText().toString().trim());
            info.setBusiness_address_proof_document_url(arrBusinessPhotos.subList(0,arrBusinessPhotos.size()-1));

            info.setResidence_address_proof_document_type(binding.spinnerResidenceAddress.getSelectedItemPosition()==0?"":binding.spinnerResidenceAddress.getSelectedItem().toString());
            if(info.getResidence_address_proof_document_type().equalsIgnoreCase(binding.spinnerResidenceAddress.getItemAtPosition(binding.spinnerResidenceAddress.getCount()-1).toString())){
                info.setResidence_address_proof_document_type(binding.etResidenceAddProof.getText().toString().trim());
            }

            info.setResidence_address_proof_document_no(binding.etResidenceProofNumber.getText().toString().trim());
            info.setResidence_address_proof_document_url(arrResidencePhotos.subList(0,arrResidencePhotos.size()-1));

        }catch (Exception e){}

        return info;
    }
}
*/
