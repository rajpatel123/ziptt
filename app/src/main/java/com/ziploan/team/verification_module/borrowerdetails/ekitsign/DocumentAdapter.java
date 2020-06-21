package com.ziploan.team.verification_module.borrowerdetails.ekitsign;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ziploan.team.databinding.DocumentChildItemBinding;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.MyViewHolder> {

    private final Context mContext;
    private int viewIndex = 0;
    private int selectedSource;
    private EsignDoc photoLists;
    private DocumentsAdapterListener delegate;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final DocumentChildItemBinding mBinding;

        public MyViewHolder(DocumentChildItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mBinding.documentName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (delegate != null){
                        delegate.openDocument(getAdapterPosition(), photoLists.getFiles().get(getAdapterPosition()));
                    }
                }
            });


        }
    }

    public DocumentAdapter(Context context, EsignDoc photosList, DocumentsAdapterListener siteInfoFragment) {
        this.photoLists = photosList;
        this.mContext = context;
        this.delegate = siteInfoFragment;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        DocumentChildItemBinding binding = DocumentChildItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final String photo = photoLists.getFilenames().get(position);

        holder.mBinding.documentName.setText("" + photo);
        holder.mBinding.deletePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!photoLists.getState().equalsIgnoreCase("read")) {
                    delegate.deletePhoto(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (photoLists.getFiles() != null)
            return photoLists.getFiles().size();
        else return 0;
    }

    public interface DocumentsAdapterListener {
        void deletePhoto(int position);

        void retryUpload(int position, int index);

        void openDocument(int position, String filename);

        void openCameraGalleyOptions(int position, int index, boolean multi_selection);
    }
}