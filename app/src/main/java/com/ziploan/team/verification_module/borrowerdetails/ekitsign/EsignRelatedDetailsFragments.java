package com.ziploan.team.verification_module.borrowerdetails.ekitsign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.ziploan.team.BuildConfig;
import com.ziploan.team.PhotoUploadListener;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.FileUploader;
import com.ziploan.team.collection.application_list.ViewerActivity;
import com.ziploan.team.databinding.FragmentEsignRelatedFeildBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.verification_module.base.BaseFragment;
import com.ziploan.team.verification_module.borrowerdetails.ekitsign.esignfeildrequest.ESignFeildRequest;
import com.ziploan.team.verification_module.caching.DatabaseManger;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
import com.ziploan.team.webapi.APIExecutor;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EsignRelatedDetailsFragments extends BaseFragment {
    private static final int REQUEST_CODE = 111;
    private FragmentEsignRelatedFeildBinding binding;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLayoutManager1;
    private String zl_number;
    private TextView momineeDOBTV;
    private JSONObject resonseJson;
    private int applicant_type, fileIndex;
    private Map<String, String> photopathList;

    JSONObject jsonFileOBj = null;
    JSONArray fileArray = null;
    private String loan_request_id;

    public static EsignRelatedDetailsFragments newInstance(String id) {
        EsignRelatedDetailsFragments EsignRelatedDetails = new EsignRelatedDetailsFragments();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.Key.LOAN_REQUEST_ID, id);
        EsignRelatedDetails.setArguments(bundle);
        return EsignRelatedDetails;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEsignRelatedFeildBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        zl_number = getArguments().getString(AppConstant.Key.ZL_ID);
        loan_request_id = getArguments().getString(AppConstant.Key.LOAN_REQUEST_ID);
        photopathList = new HashMap<String, String>();
        jsonFileOBj = new JSONObject();
        fileArray = new JSONArray();

        Log.d("Loan_id", loan_request_id);
        updateFromLocal();

        if (ZiploanUtil.checkInternetConnection(mContext)) {
            getEsignFields(zl_number);
        }


    }

    private void updateFromLocal() {
        String data = DatabaseManger.getInstance().getEsignFeildData(zl_number);
        String files = DatabaseManger.getInstance().getEsignFeildFiles(zl_number);

        Log.d("Files From DB", "" + files);

        if (!TextUtils.isEmpty(files)) {

            try {
                jsonFileOBj = new JSONObject(files);

                fileArray = jsonFileOBj.optJSONArray("files");

                if (fileArray != null && fileArray.length() > 0) {
                    photopathList.clear();
                    String[] paths = null;

                    if (fileArray.length() == 1) {
                        paths = fileArray.getString(0).split("---");
                        photopathList.put("" + paths[0], paths[1]);

                    } else {
                        for (int i = 0; i < fileArray.length(); i++) {
                            String filrPath = fileArray.get(i).toString();
                            paths = filrPath.split("---");
                            if (paths.length > 0) {
                                if (paths[0].equalsIgnoreCase("" + i)) {
                                    photopathList.put("" + paths[0], paths[1]);
                                }
                            }
                        }
                    }


                } else {
                    fileArray = new JSONArray();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if (!TextUtils.isEmpty(data)) {
            try {
                resonseJson = new JSONObject(data);
                updateFormView(resonseJson);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void getEsignFields(String zl_id) {
        //showProgressDialog(getActivity(), "Please wait...");
        Call<ResponseBody> call = APIExecutor.getAPIServiceSerializeNull(mContext)
                .getEsignFeilds(zl_number);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // hideProgressDialog();
                if (response != null && response.body() != null) {

                    try {
                        String rawData = response.body().string();
                        resonseJson = new JSONObject(rawData);
                        if (resonseJson != null) {
                            DatabaseManger.getInstance().saveEsignFeildData(resonseJson.toString(), zl_number, jsonFileOBj.toString());

                            updateFormView(resonseJson);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    //TODO  handle id no info avaialable
                    // checkTokenValidity(response);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                // hideProgressDialog();
            }
        });
    }

    private void updateFormView(JSONObject responseJson) {
        if (responseJson.has("response")) {

            binding.applicationForms.removeAllViews();
            binding.ddeForms.removeAllViews();
            binding.insuranceForm.removeAllViews();
            try {
                JSONObject response = responseJson.getJSONObject("response");
                if (response.has("applicant_count")) {
                    int applicationCount = response.getInt("applicant_count");
                    boolean readOnly = response.getBoolean("read_only");
                    binding.tvLenderName.setText("" + response.getString("lender_name"));
                    //binding.tvLenderName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

                    binding.ddeForms.removeAllViews();
                    for (int i = 0; i < applicationCount; i++) {
                        View view = getLayoutInflater().inflate(R.layout.esign_related_fields_view, null, false);
                        TextView tvApplicantTitle = view.findViewById(R.id.tv_applicant_title);
                        TextView applicantName = view.findViewById(R.id.applicant_name);
                        TextView applicant_types = view.findViewById(R.id.applicant_type);

                        EditText etMothersName = view.findViewById(R.id.et_mothersName);
                        EditText weightTV = view.findViewById(R.id.weightTV);
                        EditText heightTV = view.findViewById(R.id.heightTV);
                        EditText inchesTV = view.findViewById(R.id.inchesTV);

                        if (readOnly) {
                            applicantName.setEnabled(false);
                            etMothersName.setEnabled(false);
                            weightTV.setEnabled(false);
                            heightTV.setEnabled(false);
                            inchesTV.setEnabled(false);
                        }


                        if (response.has("dde_details")) {
                            JSONArray ddeArray = response.getJSONArray("dde_details");
                            if (i < ddeArray.length()) {
                                tvApplicantTitle.setText("Applicant Name" + (i + 1) + ": ");
                                applicantName.setText("" + ddeArray.getJSONObject(i).optString("name"));
                                applicant_types.setText("" + ddeArray.getJSONObject(i).optString("applicant_type"));
                                if (!TextUtils.isEmpty(ddeArray.getJSONObject(i).optString("mother_name"))) {
                                    if (ddeArray.getJSONObject(i).optString("mother_name").equalsIgnoreCase("null")) {
                                        etMothersName.setText("");
                                    } else {
                                        etMothersName.setText("" + ddeArray.getJSONObject(i).optString("mother_name"));
                                    }
                                }

                                if (!TextUtils.isEmpty(ddeArray.getJSONObject(i).optString("weight"))) {
                                    if (ddeArray.getJSONObject(i).optString("weight").equalsIgnoreCase("null")) {
                                        weightTV.setText("");
                                    } else {
                                        weightTV.setText("" + ddeArray.getJSONObject(i).optString("weight"));
                                    }
                                }


                                if (!TextUtils.isEmpty(ddeArray.getJSONObject(i).optString("hFeet"))) {
                                    if (ddeArray.getJSONObject(i).optString("hFeet").equalsIgnoreCase("null")) {
                                        heightTV.setText("");
                                    } else {
                                        heightTV.setText("" + ddeArray.getJSONObject(i).optString("hFeet"));
                                    }
                                }


                                if (!TextUtils.isEmpty(ddeArray.getJSONObject(i).optString("hInches"))) {
                                    if (ddeArray.getJSONObject(i).optString("hInches").equalsIgnoreCase("null")) {
                                        inchesTV.setText("");
                                    } else {
                                        inchesTV.setText("" + ddeArray.getJSONObject(i).optString("hInches"));
                                    }
                                }

                            }
                        }


                        binding.ddeForms.addView(view);


                        View insuranceForm = getLayoutInflater().inflate(R.layout.esign_sign_insurance_form, null, false);
                        TextView applicantTitle = insuranceForm.findViewById(R.id.applicantTitle);
                        TextView name = insuranceForm.findViewById(R.id.name);
                        TextView etInsMothersName = insuranceForm.findViewById(R.id.et_mothersName);
                        TextView etNomineeContactNo = insuranceForm.findViewById(R.id.et_nominee_contact_no);
                        TextView nomineeDOB = insuranceForm.findViewById(R.id.nomineeDOB);
                        TextView etRelation = insuranceForm.findViewById(R.id.et_relation);
                        RadioButton maleRB = insuranceForm.findViewById(R.id.maleRB);
                        RadioButton femaileRB = insuranceForm.findViewById(R.id.femaleRB);

                        TextView applicant_typeIns = insuranceForm.findViewById(R.id.applicant_type);


                        if (readOnly) {
                            name.setEnabled(false);
                            etInsMothersName.setEnabled(false);
                            etNomineeContactNo.setEnabled(false);
                            nomineeDOB.setEnabled(false);
                            etRelation.setEnabled(false);
                            maleRB.setEnabled(false);
                            femaileRB.setEnabled(false);

                        }

                        if (response.has("nominee_details")) {
                            JSONArray nomineeArray = response.getJSONArray("nominee_details");
                            if (i < nomineeArray.length()) {

                                if (!TextUtils.isEmpty(nomineeArray.getJSONObject(i).optString("nominee_gender"))) {
                                    if (nomineeArray.getJSONObject(i).optString("nominee_gender").equalsIgnoreCase("male")) {
                                        maleRB.setChecked(true);
                                    } else {
                                        femaileRB.setChecked(true);
                                    }
                                } else {
                                    maleRB.setChecked(true);
                                }

                                applicantTitle.setText("Applicant Name" + (i + 1) + ": ");
                                name.setText("" + nomineeArray.getJSONObject(i).optString("name"));
                                etInsMothersName.setText("" + nomineeArray.getJSONObject(i).optString("nominee_name"));
                                etNomineeContactNo.setText("" + nomineeArray.getJSONObject(i).optString("nominee_mobile"));
                                applicant_typeIns.setText("" + nomineeArray.getJSONObject(i).optString("applicant_type"));


                                if (!TextUtils.isEmpty(nomineeArray.getJSONObject(i).optString("nominee_dob"))) {
                                    if (nomineeArray.getJSONObject(i).optString("nominee_dob").equalsIgnoreCase("null")) {
                                        nomineeDOB.setText("");
                                    } else {
                                        nomineeDOB.setText("" + nomineeArray.getJSONObject(i).optString("nominee_dob"));
                                    }
                                }
                                etRelation.setText("" + nomineeArray.getJSONObject(i).optString("nominee_relationship"));

                                // get fragment manager so we can launch from fragment
                                final FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();

                                // Using an onclick listener on the editText to show the datePicker
                                nomineeDOB.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // create the datePickerFragment
                                        momineeDOBTV = nomineeDOB;
                                        AppCompatDialogFragment newFragment = new DatePickerFragment();
                                        // set the targetFragment to receive the results, specifying the request code
                                        newFragment.setTargetFragment(EsignRelatedDetailsFragments.this, REQUEST_CODE);
                                        // show the datePicker
                                        newFragment.show(fm, "datePicker");
                                    }
                                });

                            }
                        }


                        binding.insuranceForm.addView(insuranceForm);

                        View applicationForm = getLayoutInflater().inflate(R.layout.application_item_photo, null, false);
                        TextView applicantNameOnApplication = applicationForm.findViewById(R.id.nameOfApplicant);
                        ImageView imageView = applicationForm.findViewById(R.id.iv_photo);
                        TextView uploadBtn = applicationForm.findViewById(R.id.uploadBtn);
                        TextView reupload = applicationForm.findViewById(R.id.reupload);
                        ProgressBar progressBar = applicationForm.findViewById(R.id.pb_uploading);
                        String photoIdURl = "";

                        if (readOnly) {
                            uploadBtn.setEnabled(false);
                            reupload.setEnabled(false);
                        }
                        if (response.has("applicants_details")) {
                            JSONArray applicationDetailArrry = response.getJSONArray("applicants_details");
                            if (i < applicationDetailArrry.length()) {
                                applicantNameOnApplication.setText("" + applicationDetailArrry.getJSONObject(i).optString("name"));
                                photoIdURl = applicationDetailArrry.getJSONObject(i).optString("photo_url_mapper_id");
                            }
                        } else {
                            JSONArray array = new JSONArray();
                            JSONObject jsonObject = new JSONObject();
                            array.put(jsonObject);
                            response.put("applicants_details", array);
                        }


                        if (!TextUtils.isEmpty(photoIdURl)) {

                            String urls = BuildConfig.BASE_URL + "media/" + photoIdURl;

                            //Picasso picasso = new Picasso.Builder(mContext).downloader(new OkHttpDownloader(okHttpClient)).build();


                            OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
                                    .authenticator(new Authenticator() {
                                        @org.jetbrains.annotations.Nullable
                                        @Override
                                        public Request authenticate(@org.jetbrains.annotations.Nullable Route route, @NotNull okhttp3.Response response) throws IOException {


                                            return response.request().newBuilder()
                                                    .addHeader(AppConstant.Key.ACCESS_ID, ZiploanSPUtils.getInstance(mContext).getAccessId())
                                                    .addHeader(AppConstant.Key.ACCESS_TOKEN, ZiploanSPUtils.getInstance(mContext).getAccessToken())
                                                    .build();
                                        }

                                    })
                                    .build();
                            Picasso picasso = new Picasso.Builder(mContext)
                                    .downloader(new OkHttp3Downloader(okHttpClient1))
                                    .build();
                            picasso.load(urls).into(imageView);
                            imageView.setVisibility(View.VISIBLE);
                            uploadBtn.setVisibility(View.GONE);
                            reupload.setVisibility(View.VISIBLE);


                            String finalPhotoIdURl = photoIdURl;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle kycIntent = new Bundle();
                                    kycIntent.putString("url", finalPhotoIdURl);
                                    kycIntent.putString("name", "esign");
                                    ViewerActivity.start(mContext, kycIntent);
                                }
                            });

                            int finalI1 = i;
                            reupload.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    openCameraGalleryOptionsForEsign(false, progressBar);
                                    applicant_type = (finalI1 + 1);
                                    fileIndex = finalI1;
                                }
                            });
//

                        }

//                        String filePath = photopathList.get("" + i);
//                        if (!TextUtils.isEmpty(filePath)) {
//                            String[] paths = null;
//                            if (filePath.length() > 0) {
//
//                                // paths = filePath.split("---");
//                                //if (paths[0].equalsIgnoreCase("" + i)) {
//                                imageView.setVisibility(View.VISIBLE);
//                                uploadBtn.setVisibility(View.GONE);
//                                reupload.setVisibility(View.VISIBLE);
//
//
//                                Picasso.with(mContext).load(new File(filePath)).into(imageView);
//
//
//                                //S//tring[] finalPaths = paths;
//                                imageView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        ZiploanUtil.openPhotoInZoom(mContext, filePath);
//                                    }
//                                });
//
//
//                                int finalI1 = i;
//                                reupload.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        progressBar.setVisibility(View.VISIBLE);
//                                        openCameraGalleryOptionsForEsign(false, progressBar);
//                                        applicant_type = (finalI1 + 1);
//                                        fileIndex = finalI1;
//                                    }
//                                });
//
//
//                            } else {
//                                imageView.setVisibility(View.GONE);
//                                reupload.setVisibility(View.GONE);
//                                uploadBtn.setVisibility(View.VISIBLE);
//                            }
//
//
//                        } else {
//
//
//
//
//
//
//                                imageView.setVisibility(View.GONE);
//                                reupload.setVisibility(View.GONE);
//                                uploadBtn.setVisibility(View.VISIBLE);
//
//                        }


//

                        int finalI = i;
                        uploadBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                progressBar.setVisibility(View.VISIBLE);
                                openCameraGalleryOptionsForEsign(false, progressBar);
                                applicant_type = (finalI + 1);
                                fileIndex = finalI;
                            }
                        });

                        binding.applicationForms.addView(applicationForm);


                        if (readOnly) {
                            binding.etIfscCode.setEnabled(false);
                            binding.etMicrcode.setEnabled(false);
                        }
                        if (response.has("drf_details")) {
                            JSONObject drfDetails = response.getJSONObject("drf_details");
                            binding.etIfscCode.setText("" + drfDetails.getString("ifsc_code"));
                            binding.etMicrcode.setText("" + drfDetails.getString("micr_code"));
                        }


                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for the results
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // get date from string
            String selectedDate = data.getStringExtra("selectedDate");
            // set the value of the editText
            momineeDOBTV.setText(selectedDate);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void getImages(List<ZiploanPhoto> arrImages, int sourceType) {
        super.getImages(arrImages, sourceType);
        if (arrImages != null) {
            uploadFiles(arrImages);
        } else {
            updateFormView(resonseJson);
        }

    }


    @Override
    public void processingImages() {
        super.processingImages();
    }


    private void uploadFiles(List<ZiploanPhoto> arrImages) {

        FileUploader.getInstance(mContext).uploadApplicationDocument("applicant_photo", "" + applicant_type, arrImages, AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, ZiploanSPUtils.getInstance(mContext).getSelectedLoanRequestId(), new PhotoUploadListener() {
            @Override
            public void onUploadSuccess(ZiploanPhoto photo) {
                try {

                    JSONObject response = resonseJson.getJSONObject("response");
                    JSONObject jsonObject = new JSONObject();
                    JSONArray applicationObjectArr = response.getJSONArray("applicants_details");
                    if (applicationObjectArr != null && applicationObjectArr.length() > 0) {
                        JSONObject applicationObject = applicationObjectArr.getJSONObject(fileIndex);
                        if (applicationObject != null) {
                            applicationObject.put("photo_url_mapper_id", photo.getRemote_path());
                            response.getJSONArray("applicants_details").put(fileIndex, applicationObject);

                        }
                    } else {
                        jsonObject.put("photo_url_mapper_id", photo.getRemote_path());
                        jsonObject.put("name", photo.getRemote_path());
                        response.getJSONArray("applicants_details").put(fileIndex, jsonObject);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    fileArray.put(fileIndex, fileIndex + "---" + photo.getPhotoPath());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                photopathList.put("" + fileIndex, fileIndex + "---" + photo.getPhotoPath());

                try {
                    if (jsonFileOBj == null) {
                        jsonFileOBj = new JSONObject();
                    }
                    jsonFileOBj.put("files", fileArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("Files To DB", "" + jsonFileOBj.toString());


                DatabaseManger.getInstance().saveEsignFeildData(resonseJson.toString(), zl_number, jsonFileOBj.toString());
                updateFromLocal();

            }

            @Override
            public void onUploadFailed(ZiploanPhoto photo) {
                Log.d("Upload:", "failed");
            }

            @Override
            public void onUploadStarted(ZiploanPhoto photo) {

            }
        });
    }


    public ESignFeildRequest getEsignFeildsDetails(boolean isFinal) {


        JSONObject completeEsignrequest = new JSONObject();
        JSONArray ddeArray = new JSONArray();
        JSONArray nomineeArray = new JSONArray();


        JSONObject drfDetails = new JSONObject();

        JSONObject stampPaper = new JSONObject();


        int ddeCount = binding.ddeForms.getChildCount();
        int nomineeCount = binding.insuranceForm.getChildCount();


        for (int i = 0; i < ddeCount; i++) {

            final View view = binding.ddeForms.getChildAt(i);

            TextView nameEdt = view.findViewById(R.id.applicant_name);
            EditText motherNameEdt = view.findViewById(R.id.et_mothersName);
            TextView applicant_type = view.findViewById(R.id.applicant_type);
            TextView height = view.findViewById(R.id.heightTV);
            TextView weight = view.findViewById(R.id.weightTV);
            TextView inches = view.findViewById(R.id.inchesTV);

            JSONObject ddeOBj = new JSONObject();


            try {


                ddeOBj.put("name", nameEdt.getText().toString());
                ddeOBj.put("applicant_type", Integer.parseInt(applicant_type.getText().toString()));
                ddeOBj.put("mother_name", motherNameEdt.getText().toString());

                if (!TextUtils.isEmpty(weight.getText().toString())) {
                    ddeOBj.put("weight", Integer.parseInt(weight.getText().toString()));

                } else {
                    ddeOBj.put("weight", 0);

                }


                if (!TextUtils.isEmpty(height.getText().toString())) {
                    if (Integer.parseInt(height.getText().toString()) < 10) {
                        ddeOBj.put("hFeet", Integer.parseInt(height.getText().toString()));
                    } else {
                        height.setError("Please enter less than 10 feet");
                        return null;

                    }

                } else {
                    ddeOBj.put("hFeet", 0);

                }

                if (!TextUtils.isEmpty(inches.getText().toString())) {

                    if (Integer.parseInt(inches.getText().toString()) < 12) {
                        ddeOBj.put("hInches", Integer.parseInt(inches.getText().toString()));
                    } else {
                        height.setError("Please enter less than 12 inches");
                        return null;

                    }
                } else {
                    ddeOBj.put("hInches", 0);

                }


                ddeArray.put(ddeOBj);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        try {
            completeEsignrequest.put("dde_details", ddeArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < nomineeCount; i++) {

            final View view = binding.insuranceForm.getChildAt(i);

            TextView nameEdt = view.findViewById(R.id.name);
            EditText motherNameEdt = view.findViewById(R.id.et_mothersName);
            TextView applicant_type = view.findViewById(R.id.applicant_type);
            TextView nomineeDOB = view.findViewById(R.id.nomineeDOB);
            RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
            TextView relationTv = view.findViewById(R.id.et_relation);
            TextView momineeMobile = view.findViewById(R.id.et_nominee_contact_no);

            JSONObject nomObject = new JSONObject();


            try {
                nomObject.put("name", nameEdt.getText().toString());
                nomObject.put("applicant_type", Integer.parseInt(applicant_type.getText().toString()));
                nomObject.put("nominee_name", motherNameEdt.getText().toString());
                nomObject.put("nominee_dob", nomineeDOB.getText().toString());

                RadioButton genderRd = view.findViewById(radioGroup.getCheckedRadioButtonId());

                nomObject.put("nominee_gender", genderRd.getText().toString());

                if (!TextUtils.isEmpty(momineeMobile.getText().toString())) {
                    if (momineeMobile.getText().toString().length() != 10) {
                        momineeMobile.setError("Please enter valid mobile numbers");
                        return null;
                    } else {
                        nomObject.put("nominee_mobile", momineeMobile.getText().toString());

                    }
                }
                nomObject.put("nominee_relationship", relationTv.getText().toString());


                nomineeArray.put(nomObject);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        try {
            completeEsignrequest.put("nominee_details", nomineeArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            drfDetails.put("micr_code", binding.etMicrcode.getText().toString());
            drfDetails.put("ifsc_code", binding.etIfscCode.getText().toString());


            completeEsignrequest.put("drf_details", drfDetails);
            completeEsignrequest.put("stamp_paper_details", stampPaper);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            if (jsonFileOBj == null) {
                jsonFileOBj = new JSONObject();
            }
            jsonFileOBj.put("files", fileArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Log.d("Files To DB", "" + jsonFileOBj.toString());

        String data = completeEsignrequest.toString();
        DatabaseManger.getInstance().saveEsignFeildData(resonseJson.toString(), zl_number, jsonFileOBj.toString());

        Log.d("data", "" + data);
        return new Gson().fromJson(data, ESignFeildRequest.class);
    }


}
