package com.ziploan.team.verification_module.borrowerdetails.ekitsign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ziploan.team.PhotoUploadListener;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.FileUploader;
import com.ziploan.team.collection.application_list.ViewerActivity;
import com.ziploan.team.databinding.EsignRelatedDocumentToUploadBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.base.BaseFragment;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.FileUploadResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EsignRelatedDocumentsFragment extends BaseFragment {
    //  private  Esign;
    EsignRelatedDocumentToUploadBinding binding;
    private String zl_number;
    private ESignDocuments eSignDocuments;
    private EsignDoc documentClicked;
    private String loan_request_id;

    public static EsignRelatedDocumentsFragment newInstance(String id) {
        EsignRelatedDocumentsFragment EsignRelatedDocuments = new EsignRelatedDocumentsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.Key.LOAN_REQUEST_ID, id);
        EsignRelatedDocuments.setArguments(bundle);
        return EsignRelatedDocuments;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EsignRelatedDocumentToUploadBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        zl_number = getArguments().getString(AppConstant.Key.ZL_ID);
        loan_request_id = getArguments().getString(AppConstant.Key.LOAN_REQUEST_ID);

        if (ZiploanUtil.checkInternetConnection(mContext)) {
            getEsignDocuments(zl_number);
        } else {

            String data = DatabaseManger.getInstance().getDocumentData(zl_number);
            if (!TextUtils.isEmpty(data)) {
                eSignDocuments = new Gson().fromJson(data, ESignDocuments.class);
                updateDocumentsView(eSignDocuments);

            }
        }

    }

    private void getEsignDocuments(String zl_number) {
        //showProgressDialog(getActivity(), "Please wait...");
        Call<ESignDocuments> call = APIExecutor.getAPIServiceSerializeNull(mContext)
                .getEsignDocuments("physical_verification", zl_number);
        call.enqueue(new Callback<ESignDocuments>() {
            @Override
            public void onResponse(Call<ESignDocuments> call, Response<ESignDocuments> response) {
                hideProgressDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    eSignDocuments = response.body();
                    DatabaseManger.getInstance().saveDocumentData(new Gson().toJson(eSignDocuments), zl_number);

                    updateDocumentsView(eSignDocuments);

                } else {
                    checkTokenValidity(response);
                }
            }

            @Override
            public void onFailure(Call<ESignDocuments> call, Throwable t) {
                t.printStackTrace();
                hideProgressDialog();
            }
        });
    }

    private void updateDocumentsView(ESignDocuments eSignDocuments) {
        eSignDocuments.getResponse();
        binding.progressBar.setVisibility(View.GONE);

        if (eSignDocuments.getResponse() != null && eSignDocuments.getResponse().getEsignDocs() != null &&
                eSignDocuments.getResponse().getEsignDocs().size() > 0) {
            if (!TextUtils.isEmpty(eSignDocuments.getResponse().getLenderName())){
                binding.tvLenderName.setText("" + eSignDocuments.getResponse().getLenderName());
               // binding.tvLenderName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            }
            binding.documents.removeAllViews();
            for (int i = 0; i < eSignDocuments.getResponse().getEsignDocs().size(); i++) {
                View view = getLayoutInflater().inflate(R.layout.esign_document_item, null, false);
                EsignDoc document = eSignDocuments.getResponse().getEsignDocs().get(i);
                TextView docTitle = view.findViewById(R.id.docTitle);
                // ProgressBar progressBar = view.findViewById(R.id.progressBar);
                RecyclerView docListRv = view.findViewById(R.id.documentviewRV);
                docTitle.setText("" + document.getSectionName());

                docListRv.setLayoutManager(new LinearLayoutManager(mContext));
                TextView uploadBtn = view.findViewById(R.id.uploadBtn);

                if (document.getState().equalsIgnoreCase("read")){
                   uploadBtn.setEnabled(false);
                }
                uploadBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        documentClicked = document;
                        openCameraGalleryOptionsForEsign(false, binding.progressBar);
                       // Toast.makeText(mContext, "Clicked On " + document.getSectionName(), Toast.LENGTH_SHORT).show();
                    }
                });


                List<String> uploadedFiles = document.getFiles();

                if (uploadedFiles != null && uploadedFiles.size() > 0) {
                    DocumentAdapter documentAdapter = new DocumentAdapter(mContext, document, new DocumentAdapter.DocumentsAdapterListener() {
                        @Override
                        public void deletePhoto(int position) {
                            binding.progressBar.setVisibility(View.VISIBLE);
                            deletePhotoFormServer(uploadedFiles.get(position), loan_request_id);
                        }

                        @Override
                        public void retryUpload(int position, int index) {

                        }

                        @Override
                        public void openDocument(int position, String path) {
                            Bundle kycIntent = new Bundle();
                            kycIntent.putString("url", path);
                            kycIntent.putString("name", "esign");
                            ViewerActivity.start(mContext,kycIntent);

                        }

                        @Override
                        public void openCameraGalleyOptions(int position,
                                                            int index, boolean multi_selection) {

                        }
                    });

                    docListRv.setAdapter(documentAdapter);


                }


                binding.documents.addView(view);
            }
        }
    }

    private void deletePhotoFormServer(String file_url, String loan_request_id) {
        Call<FileUploadResponse> call = APIExecutor.getAPIServiceSerializeNull(mContext)
                .deleteDocuments(loan_request_id, file_url, 1);
        call.enqueue(new Callback<FileUploadResponse>() {
            @Override
            public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
                hideProgressDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    getEsignDocuments(zl_number);
                } else {
                    checkTokenValidity(response);
                }
            }

            @Override
            public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                t.printStackTrace();
                hideProgressDialog();
            }
        });


    }


    @Override
    public void getImages(List<ZiploanPhoto> arrImages, int sourceType) {
        super.getImages(arrImages, sourceType);
        if (arrImages != null) {
            uploadFiles(arrImages);
        } else {
            updateDocumentsView(eSignDocuments);
        }

    }


    @Override
    public void processingImages() {
        super.processingImages();
    }


    private void uploadFiles(List<ZiploanPhoto> arrImages) {

        FileUploader.getInstance(mContext).uploadApplicationDocument(documentClicked.getFileType(), "", arrImages, AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, ZiploanSPUtils.getInstance(mContext).getSelectedLoanRequestId(), new PhotoUploadListener() {
            @Override
            public void onUploadSuccess(ZiploanPhoto photo) {
                documentClicked.getFiles().add(photo.getPhotoPath());
                DatabaseManger.getInstance().saveDocumentData(new Gson().toJson(eSignDocuments), zl_number);

                getEsignDocuments(zl_number);

            }

            @Override
            public void onUploadFailed(ZiploanPhoto photo) {

            }

            @Override
            public void onUploadStarted(ZiploanPhoto photo) {

            }
        });
    }

}
