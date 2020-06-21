package com.ziploan.team.verification_module.verifyekyc;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.squareup.picasso.Picasso;
import com.ziploan.team.R;
import com.ziploan.team.verification_module.base.BaseFragment;
import com.ziploan.team.databinding.ItemPhotoBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanUtil;

import java.io.File;
import java.net.URL;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    private final Context mContext;
    private int viewIndex=0;
    private int selectedSource;
    private List<ZiploanPhoto> photoLists;
    private PhotosAdapterListener delegate;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemPhotoBinding mBinding;

        public MyViewHolder(ItemPhotoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.deletePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delegate.deletePhoto(getAdapterPosition(),viewIndex);
                }
            });
            mBinding.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(TextUtils.isEmpty(photoLists.get(getAdapterPosition()).getPhotoPath()))
                        delegate.openCameraGalleyOptions(getAdapterPosition(),viewIndex,true);
                    else
                        ZiploanUtil.openPhotoInZoom(mContext,photoLists.get(getAdapterPosition()).getPhotoPath());
                }
            });

            mBinding.tvDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(photoLists.get(getAdapterPosition()).getUpload_status() == AppConstant.UploadStatus.UPLOADING_FAILED){
                        delegate.retryUpload(getAdapterPosition(),viewIndex);
                    }else {
                        ZiploanUtil.openPhotoInZoom(mContext,photoLists.get(getAdapterPosition()).getPhotoPath());
                    }
                }
            });
        }
    }

    public PhotosAdapter(Context context, List<ZiploanPhoto> photosList, PhotosAdapterListener siteInfoFragment) {
        this.photoLists = photosList;
        this.mContext = context;
        this.delegate = siteInfoFragment;
    }

    public PhotosAdapter(Context context, List<ZiploanPhoto> moviesList, PhotosAdapterListener siteInfoFragment, int selectedSource) {
        this.photoLists = moviesList;
        this.mContext = context;
        this.delegate = siteInfoFragment;
        this.viewIndex = selectedSource;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemPhotoBinding binding = ItemPhotoBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }
 
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ZiploanPhoto photo = photoLists.get(position);
        switch (photo.getUpload_status()){
            case AppConstant.UploadStatus.UPLOADING_STARTED:
                holder.mBinding.tvDone.setVisibility(View.GONE);
                holder.mBinding.pbUploading.setVisibility(View.VISIBLE);
                break;
            case AppConstant.UploadStatus.UPLOADING_FAILED:
                holder.mBinding.tvDone.setVisibility(View.VISIBLE);
                holder.mBinding.pbUploading.setVisibility(View.GONE);
                holder.mBinding.tvDone.setText("RETRY");
                holder.mBinding.tvDone.setTextColor(Color.parseColor("#ff0000"));
                break;
            case AppConstant.UploadStatus.UPLOADING_SUCCESS:
                holder.mBinding.tvDone.setVisibility(View.VISIBLE);
                holder.mBinding.pbUploading.setVisibility(View.GONE);
                holder.mBinding.tvDone.setText(Html.fromHtml("&#10004;"));
                holder.mBinding.tvDone.setTextColor(Color.parseColor("#00ff00"));

                break;
            default:
                holder.mBinding.tvDone.setVisibility(View.GONE);
                holder.mBinding.pbUploading.setVisibility(View.GONE);
                break;
        }
        if(photo.getPhotoPath().length()>0){
            if(URLUtil.isValidUrl(photo.getPhotoPath())){
                holder.mBinding.deletePhoto.setVisibility(View.GONE);
                Picasso.with(mContext).load(photo.getPhotoPath()).into(holder.mBinding.ivPhoto);
            }else {
                holder.mBinding.deletePhoto.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(new File(photo.getPhotoPath())).into(holder.mBinding.ivPhoto);
            }
        }else {
            holder.mBinding.deletePhoto.setVisibility(View.GONE);
            holder.mBinding.ivPhoto.setImageResource(R.mipmap.photo);
        }
    }

    @Override
    public int getItemCount() {
        return photoLists.size();
    }

    public interface PhotosAdapterListener{
        void deletePhoto(int position,int index);
        void retryUpload(int position,int index);
        void openCameraGalleyOptions(int position,int index,boolean multi_selection);
    }
}