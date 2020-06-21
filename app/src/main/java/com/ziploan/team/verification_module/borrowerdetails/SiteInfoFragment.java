package com.ziploan.team.verification_module.borrowerdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.ziploan.team.PhotoUploadListener;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.FileUploader;
import com.ziploan.team.asset_module.visits.LoadPhotoListener;
import com.ziploan.team.collection.utils.UIErrorUtils;
import com.ziploan.team.databinding.FragmentSiteinfoBinding;
import com.ziploan.team.databinding.ItemFamilyReferenceBinding;
import com.ziploan.team.databinding.ItemReferenceBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.utils.ZiploanSPUtils;
import com.ziploan.team.verification_module.base.BaseFragment;
import com.ziploan.team.verification_module.borrowerslist.BorrowersUnverified;
import com.ziploan.team.verification_module.verifyekyc.PhotosAdapter;
import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.BusinessCategory;
import com.ziploan.team.webapi.model.sub_cat_.BusinessCategoryDatum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZIploan-Nitesh on 2/4/2017.
 */
public class SiteInfoFragment extends BaseFragment implements View.OnClickListener, PhotosAdapter.PhotosAdapterListener, LoadPhotoListener {

    private FragmentSiteinfoBinding binding;
    private ArrayList<ZiploanPhoto> arrPhotos = null;
    private ArrayList<ZiploanPhoto> arrLongShotPhotos = null;
    private ArrayList<ZiploanPhoto> arrIdProofPhotos = null;
    private int selectedOption;
    private PhotosAdapter adapter;
    private PhotosAdapter adapterLongShot;
    private PhotosAdapter adapterIdproof;
    private CompoundButton.OnCheckedChangeListener leftCheckListener;
    private CompoundButton.OnCheckedChangeListener rightCheckListener;
    private ZiploanSiteInfo siteInfo;
    private boolean isNeedToDisable = false;
    private static final int BUSINESS_PHOTO_SELECTED = 348;
    private static final int BUSINESS_LONG_SHOT_PHOTES_SELECTED = 689;
    private static final int ID_PROOF_PHOTOES_SELECTED = 172;
    private int appInstalledStatus = 0;
    String arrAppNotInstalledReasons[] = {"Choose why app is not installed",
            "Doesn't have android device",
            "No network available",
            "Month end", "Customer not available", "Customer not interested in app"};

    String natureOfWorkb2b[] = {"", "Auto Parts",
            "Corrugated Boxes",
            "Footwear",
            "Garments", "Hosiery", "Plastic Items", "Sanitary", "Other"};

    String natureOfWorkb2c[] = {"", "Other"};
    String natureOfWorkdefault[] = {""};
    String natureOfWorkServices[] = {"", "Garments", "Manpower", "Tour and Travels", "Other"};
    String natureOfWorkTrading[] = {"", "FMCG", "Fruits and Vegetables", "Garments", "Hardware", "Medicines", "Scrap Plastic", "Other"};
    String natureB2CServices[] = {"", "Educational Institute", "Restaurant", "Doctors", "Tour and Travels", "Other"};
    String natureB2cTrading[] = {"", "Chemist", "Electronics", "Garments", "Grocery Store", "Hardware", "Other"};
    String educationQualification[] = {"", "Uneducated", "Primary", "High School", "Intermediate", "Graduation", "Post Graduation", "Doctorate"};

    String designation[] = {"", "Proprietor",
            "Partner/Director",
            "Husband of owner",
            "Manager", "Other"};

    String businessStability[] = {"", "Less than 6 months",
            "6 to 12 months",
            "12 to 24 months",
            "More than 24 months"};

    String localityOfBusinessPlace[] = {"", "Industrial",
            "Market",
            "Residential",
            "Commercial", "Other"};

    String number_of_shareholder[] = {"", "1",
            "2",
            "3",
            "4", "5"};

    String number_of_Employeed_on_site[] = {"", "0",
            "1-3",
            "3-5",
            "5-10", "10-15", "15-30", "30+"};

    String number_of_machines[] = {"", "Not Applicable",
            "1-3",
            "3-5",
            "5-10", "10-15", "15-30", "30+"};

    String other_business_place[] = {"", "Yes", "No"};

    String sign_board_seen[] = {"", "Yes", "No"};

    String value_of_raw_material[] = {"", "Not Applicable", "0-10k", "10k-50K", "50k-1lakh", "1lakh-2lakh", "2lakh-4lakh", "4lakh-8lakh", "8lakh+"};

    String value_of_stock[] = {"", "Not Applicable", "0-10k", "10k-50K", "50k-1lakh", "1lakh-2lakh", "2lakh-4lakh", "4lakh-8lakh", "8lakh+"};

    String investment_in_business[] = {"", "0-10k", "10k-50K", "50k-1lakh", "1lakh-2lakh", "2lakh-4lakh", "4lakh-8lakh", "8lakh-15lakh", "15lakh-25lakh", "25lakh+"};

    ArrayList<BusinessCategory> businessCategories = ZiploanSPUtils.getInstance(getContext()).getBusinessCategories();
    List<BusinessCategoryDatum> subCategoryLists = ZiploanSPUtils.getInstance(getContext()).getBusinessSubCategories();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSiteinfoBinding.inflate(inflater, container, false);
        siteInfo = getSiteInfoFromBundle(getArguments());
        setAppNotInstalledSpinner();
        setDesignationSpinner();
        setNumber_of_Employeed_on_siteSpinner();
        setNumber_of_machinesSpinner();
        setNumber_of_shareholderSpinner();
//        setOther_business_placeSpinner();
        setSign_board_seenSpinner();
        setValue_of_raw_materialSpinner();
        setValue_of_stockSpinner();
        setInvestment_in_businessSpinner();
        setBCSpinner();
        setListeners();
        arrPhotos = new ArrayList<>();
        arrLongShotPhotos = new ArrayList<>();
        arrIdProofPhotos = new ArrayList<>();
        fillDataToViewIfAny(siteInfo);
        setBusinessStabilitySpinner();
        setLocalityOfBusinessPlaceSpinner();
        setEducationQualificationSpinner();
        if (!TextUtils.isEmpty(getArguments().getString("business_address")))
            binding.dashboardBusinessAddressTxt.setText(getArguments().getString("business_address"));
        setBusinessAsDashboardSpinner();
        return binding.getRoot();
    }

    private void setBCSpinner() {
        int selectedPosition = -1;
        BusinessCategory businessCategory = new BusinessCategory();
        businessCategory.setBusiness_category_id("-1");
        businessCategory.setBusiness_category_name("");
        businessCategories.add(0, businessCategory);


        binding.spinnerBc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String data = adapterView.getSelectedItem().toString();
                if (!TextUtils.isEmpty(data)) {
                    setNatureOfWorkSpinner(data);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        if (businessCategories != null) {
            String[] arrBC = new String[businessCategories.size()];
            for (int i = 0; i < businessCategories.size(); i++) {
                arrBC[i] = businessCategories.get(i).getBusiness_category_name();
                if (siteInfo != null) {
                    if (siteInfo.getBusiness_category() != null && siteInfo.getBusiness_category().equals(businessCategories.get(i).getBusiness_category_id())) {
                        selectedPosition = i;
                    }
                }
            }

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, arrBC);
            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            binding.spinnerBc.setAdapter(stringArrayAdapter);
            if (selectedPosition != -1) {
                binding.spinnerBc.setSelection(selectedPosition);
            }


        }
    }

    private void setNatureOfWorkSpinner(String name) {
        int selectedPosition = -1;
        ArrayList<BusinessCategoryDatum> businessCategoryDatas = new ArrayList<>(subCategoryLists);

        ArrayList<String> arrBC = new ArrayList<>();
        arrBC.add("");
//        String[] arrBC = new String[businessCategoryDatas.size()];
        for (int i = 0; i < businessCategoryDatas.size(); i++) {
            if (businessCategoryDatas.get(i).getBusinessCategoryName().equalsIgnoreCase(name)) {
                arrBC.add(businessCategoryDatas.get(i).getSubCategoryName());
            }
        }

        for (int j = 0; j < arrBC.size(); j++) {
            if (siteInfo != null) {
                if (siteInfo.getNature_of_work() != null
                        && siteInfo.getNature_of_work().equals(arrBC.get(j))) {
                    selectedPosition = j;
                }
            }
        }


        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, arrBC);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.natureOfWork.setAdapter(stringArrayAdapter);

        if (selectedPosition != -1) {
            binding.natureOfWork.setSelection(selectedPosition);
        }

        binding.natureOfWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.etNatureOfWork.setVisibility(View.VISIBLE);

//                if (adapterView.getSelectedItem().toString().equalsIgnoreCase("others")) {
//                } else {
//                    binding.etNatureOfWork.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

//    private void getSubBusinessCats(String id){
//        Call<SubCatResponse> call = APIExecutor.getAPIServiceSerializeNull(mContext)
//                .getBusinessSubCatList(id);
//
//        call.enqueue(new Callback<SubCatResponse>() {
//            @Override
//            public void onResponse(Call<SubCatResponse> call, ResponseData<SubCatResponse> response) {
//                if(response != null && response.isSuccessful() && response.body() != null){
//                    subCategoryLists = response.body().getSubCategoryList();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SubCatResponse> call, Throwable t) {
//
//            }
//        });
//    }

    private void setAppNotInstalledSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrAppNotInstalledReasons);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.spinnerAppNotInstalled.setAdapter(stringArrayAdapter);
        binding.spinnerAppNotInstalled.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setBusinessAsDashboardSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, other_business_place);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.businessDashboardSpinner.setAdapter(stringArrayAdapter);

        for (int i = 0; i < other_business_place.length; i++) {
            if (!TextUtils.isEmpty(siteInfo.getBusiness_address_same_as_dashboard())) {
                if (siteInfo.getBusiness_address_same_as_dashboard().startsWith("No")) {
                    binding.businessDashboardSpinner.setSelection(2);
                    String[] data = siteInfo.getBusiness_address_same_as_dashboard().split("-");
                    if (data.length > 1)
                        binding.businessAsDashboard.setText(data[1].trim());
                } else {
                    binding.businessDashboardSpinner.setSelection(1);
                }
                break;
            }
        }

        binding.businessDashboardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("No")) {
                    binding.businessAsDashboard.setVisibility(View.VISIBLE);
                    binding.businessAsDashboard.requestFocus();
                    UIErrorUtils.showSoftKeyboard(getActivity(), binding.businessAsDashboard);
                } else {
                    binding.businessAsDashboard.setText("");
                    binding.businessAsDashboard.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setBusinessStabilitySpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, businessStability);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.spinnerBisniessStability.setAdapter(stringArrayAdapter);
        for (int i = 0; i < businessStability.length; i++) {
            if (businessStability[i].equalsIgnoreCase(siteInfo.getBusiness_stability())) {
                binding.spinnerBisniessStability.setSelection(i);
                break;
            }
        }
        binding.spinnerBisniessStability.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String stability = businessStability[position];
                binding.reasonChangingPlace.setVisibility((!TextUtils.isEmpty(stability) && stability.contains("Less than")) ? View.VISIBLE : View.GONE);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setLocalityOfBusinessPlaceSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, localityOfBusinessPlace);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.spinnerLocality.setAdapter(stringArrayAdapter);
        for (int i = 0; i < localityOfBusinessPlace.length; i++) {
            if (localityOfBusinessPlace[i].equalsIgnoreCase(siteInfo.getLocality_business_place())) {
                binding.spinnerLocality.setSelection(i);
                break;
            }
        }
        binding.spinnerLocality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setEducationQualificationSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, educationQualification);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.spinnerEducation.setAdapter(stringArrayAdapter);
        for (int i = 0; i < educationQualification.length; i++) {
            if (educationQualification[i].equalsIgnoreCase(siteInfo.getEducation_qualification())) {
                binding.spinnerEducation.setSelection(i);
                break;
            }
        }

        binding.spinnerEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setNatureOfWorkSpinner(final String[] data) {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, data);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.natureOfWork.setAdapter(stringArrayAdapter);
        for (int ii = 0; ii < data.length; ii++) {
            if (data[ii].equalsIgnoreCase(siteInfo.getNature_of_work())) {
                binding.natureOfWork.setSelection(ii);
                break;
            }
        }
        binding.natureOfWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                binding.etNatureOfWork.setVisibility(View.VISIBLE);
                binding.etNatureOfWork.requestFocus();
//                if(adapterView.getSelectedItem().toString().equalsIgnoreCase("other")){
//                    binding.etNatureOfWork.setVisibility(View.VISIBLE);
//                    binding.etNatureOfWork.requestFocus();
//                } else {
//                    binding.etNatureOfWork.setVisibility(View.GONE);
//                    UIErrorUtils.hideSoftKeyboard(getContext());
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //
    private void setDesignationSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, designation);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.designationspinner.setAdapter(stringArrayAdapter);
        binding.designationspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getSelectedItem().toString().equalsIgnoreCase("other")) {
                    binding.textDesignationInOffice.setVisibility(View.VISIBLE);
                    binding.textDesignationInOffice.requestFocus();
                } else {
                    binding.textDesignationInOffice.setVisibility(View.GONE);
                    //UIErrorUtils.hideSoftKeyboard(getActivity());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //
    private void setNumber_of_shareholderSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, number_of_shareholder);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.shareHolderSpinner.setAdapter(stringArrayAdapter);
        binding.shareHolderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setNumber_of_Employeed_on_siteSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, number_of_Employeed_on_site);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.employeedOnSiteSpinner.setAdapter(stringArrayAdapter);
        for (int ii = 0; ii < number_of_Employeed_on_site.length; ii++) {
            if (number_of_Employeed_on_site[ii].equalsIgnoreCase(siteInfo.getActual_no_of_employees())) {
                binding.employeedOnSiteSpinner.setSelection(ii);
                break;
            }
        }
        binding.employeedOnSiteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setNumber_of_machinesSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, number_of_machines);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.numberOfMachines.setAdapter(stringArrayAdapter);
        for (int ii = 0; ii < number_of_machines.length; ii++) {
            if (number_of_machines[ii].equalsIgnoreCase(siteInfo.getNo_of_machines())) {
                binding.numberOfMachines.setSelection(ii);
                break;
            }
        }
        binding.numberOfMachines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setOther_business_placeSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, other_business_place);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.natureOfWork.setAdapter(stringArrayAdapter);
        binding.natureOfWork.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSign_board_seenSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, sign_board_seen);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.signBoard.setAdapter(stringArrayAdapter);
        for (int ii = 0; ii < sign_board_seen.length; ii++) {
            if (sign_board_seen[ii].equalsIgnoreCase(siteInfo.getSign_board_observed())) {
                binding.signBoard.setSelection(ii);
                break;
            }
        }
        binding.signBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setValue_of_raw_materialSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, value_of_raw_material);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.rawMaterial.setAdapter(stringArrayAdapter);
        for (int ii = 0; ii < value_of_raw_material.length; ii++) {
            if (value_of_raw_material[ii].equalsIgnoreCase(siteInfo.getRaw_material())) {
                binding.rawMaterial.setSelection(ii);
                break;
            }
        }
        binding.rawMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setValue_of_stockSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, value_of_stock);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.stockAmount.setAdapter(stringArrayAdapter);
        for (int ii = 0; ii < value_of_stock.length; ii++) {
            if (value_of_stock[ii].equalsIgnoreCase(siteInfo.getStock_inventory_amount())) {
                binding.stockAmount.setSelection(ii);
                break;
            }
        }
        binding.stockAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setInvestment_in_businessSpinner() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, investment_in_business);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        binding.businessInvestment.setAdapter(stringArrayAdapter);
        for (int ii = 0; ii < investment_in_business.length; ii++) {
            if (investment_in_business[ii].equalsIgnoreCase(siteInfo.getInvestment_in_business())) {
                binding.businessInvestment.setSelection(ii);
                break;
            }
        }
        binding.businessInvestment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void getAppInstallStatus() {
        showProgressDialog(getActivity(), "Please wait...");
        Call<BorrowersUnverified> call = APIExecutor.getAPIService(mContext).getAppInstallStatus(getArguments().getString(AppConstant.Key.LOAN_REQUEST_ID));
        call.enqueue(new Callback<BorrowersUnverified>() {
            @Override
            public void onResponse(Call<BorrowersUnverified> call, Response<BorrowersUnverified> response) {
                hideProgressDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    BorrowersUnverified borrowersUnverified = response.body();
                    appInstalledStatus = borrowersUnverified.getApp_install_status();
                    if (borrowersUnverified.getApp_install_status() == 1) {
                        binding.spinnerAppNotInstalled.setVisibility(View.GONE);
                        binding.imgAppInstallStatusRefresh.setVisibility(View.GONE);
                    }
                } else {
                    checkTokenValidity(response);
                }
            }

            @Override
            public void onFailure(Call<BorrowersUnverified> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

    private void fillDataToViewIfAny(ZiploanSiteInfo siteInfo) {
        if (appInstalledStatus != 1) {
            int spinnerSelectedIndex = 0;
            for (int i = 0; i < arrAppNotInstalledReasons.length; i++) {
                if (arrAppNotInstalledReasons[i].equalsIgnoreCase(siteInfo.getApp_not_installed_reason())) {
                    spinnerSelectedIndex = i;
                    break;
                }
            }
            binding.spinnerAppNotInstalled.setSelection(spinnerSelectedIndex);
            binding.imgAppInstallStatusRefresh.setOnClickListener(this);
        } else {
            binding.spinnerAppNotInstalled.setVisibility(View.GONE);
            binding.imgAppInstallStatusRefresh.setVisibility(View.GONE);
        }


        List<ZiploanPhoto> arrPhotos = siteInfo.getBusiness_place_photo_url();
        List<ZiploanPhoto> arrLongShotPhotos = siteInfo.getLong_shot_photos();
        List<ZiploanPhoto> arrIdproofPhotos = siteInfo.getId_proof_photos();
        ArrayList<ReferenceUser> arrReferenceUsers = siteInfo.getArrReferenceUsers();
        ArrayList<FamilyReferences> arrFamilyReferenceUsers = siteInfo.getArrFamilyReferenceUsers();
        String stockAmount = siteInfo.getStock_inventory_amount();
        String machineryAmount = siteInfo.getFixed_asset_machinery_amount();
        int employeeNo = siteInfo.getNo_employees();
        //int isAddSeperate = siteInfo.getBusiness_place_seperate_residence_place();

        String actual_no_of_employees = siteInfo.getActual_no_of_employees();
        int rent_amount = siteInfo.getRent_amount();
        String investment_in_business = siteInfo.getInvestment_in_business();
        String no_of_machines = siteInfo.getNo_of_machines();
        String raw_material = siteInfo.getRaw_material();

        String person_met = siteInfo.getPerson_met();
        String designation_in_office = siteInfo.getDesignation_in_office();
        String sign_board_observed = siteInfo.getSign_board_observed();
        String nature_of_work = siteInfo.getNature_of_work();

        String text_nature_of_work = siteInfo.getText_nature_of_work();
        String text_designation = siteInfo.getText_designation();
        String newAddress = siteInfo.getBusiness_address_same_as_dashboard_reason();

        String businessStability = siteInfo.getBusiness_stability();
        String businessPlaceChangeReason = siteInfo.getBusiness_place_change_reason();

        String landmark = siteInfo.getLandmark();
        binding.landmark.setText(landmark);

        if (isNeedToDisable) {
            binding.tvAddMore.setVisibility(View.GONE);
            binding.tvAddMoreFamily.setVisibility(View.GONE);
            enableField(binding.etInventoryAmount, false);
            enableField(binding.etAssetsMachinaryValue, false);
            enableField(binding.etEmployeeNo, false);
            enableField(binding.cbLeft, false);
            enableField(binding.cbRight, false);

            enableField(binding.etActualNoOfEmployee, false);
            enableField(binding.etRentAmount, false);
            enableField(binding.etBusineesInvestment, false);
            enableField(binding.etNoOfMachines, false);
            enableField(binding.etRawMaterialAmount, false);
            enableField(binding.etPersonMet, false);
            enableField(binding.etDesignationInOffice, false);
            enableField(binding.etSignObserved, false);
            enableField(binding.etNatureOfWork, false);
            ((View) binding.etActualNoOfEmployee.getParent()).setVisibility(TextUtils.isEmpty(actual_no_of_employees) ? View.GONE : View.VISIBLE);
            ((View) binding.etRentAmount.getParent()).setVisibility(rent_amount == 0 ? View.GONE : View.VISIBLE);
            ((View) binding.etBusineesInvestment.getParent()).setVisibility(TextUtils.isEmpty(investment_in_business) ? View.GONE : View.VISIBLE);
            ((View) binding.etNoOfMachines.getParent()).setVisibility(TextUtils.isEmpty(no_of_machines) ? View.GONE : View.VISIBLE);
            ((View) binding.etRawMaterialAmount.getParent()).setVisibility(!TextUtils.isEmpty(raw_material) ? View.GONE : View.VISIBLE);

            ((View) binding.etPersonMet.getParent()).setVisibility(TextUtils.isEmpty(person_met) ? View.GONE : View.VISIBLE);
            ((View) binding.etDesignationInOffice.getParent()).setVisibility(TextUtils.isEmpty(designation_in_office) ? View.GONE : View.VISIBLE);
            ((View) binding.etSignObserved.getParent()).setVisibility(TextUtils.isEmpty(sign_board_observed) ? View.GONE : View.VISIBLE);
            ((View) binding.etNatureOfWork.getParent()).setVisibility(TextUtils.isEmpty(nature_of_work) ? View.GONE : View.VISIBLE);


            binding.reasonChangingPlace.setVisibility((!TextUtils.isEmpty(businessStability) && businessStability.contains("Less than")) ? View.VISIBLE : View.GONE);

            if (!TextUtils.isEmpty(businessPlaceChangeReason)){
               binding.reasonChangingPlace.setText(businessPlaceChangeReason);
            }

            if (arrReferenceUsers == null || arrReferenceUsers.size() == 0 || TextUtils.isEmpty(arrReferenceUsers.get(0).getMobile())) {
                binding.labelReferences.setVisibility(View.GONE);
                binding.cardViewReferences.setVisibility(View.GONE);
            }



            if (arrFamilyReferenceUsers == null || arrFamilyReferenceUsers.size() == 0 || TextUtils.isEmpty(arrFamilyReferenceUsers.get(0).getMobile())) {
                binding.labelReferencesbb.setVisibility(View.GONE);
                binding.cardViewReferencesFamily.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(stockAmount) && TextUtils.isEmpty(machineryAmount) && employeeNo == 0) {
                binding.lableAdditionalInfo.setVisibility(View.GONE);
                binding.cardViewAdditionalInfo.setVisibility(View.GONE);
            }
            binding.cbLeft.setVisibility(View.GONE);
            binding.cbRight.setVisibility(View.GONE);
            binding.tvPlaceSeparateStatus.setVisibility(View.VISIBLE);
            // binding.tvPlaceSeparateStatus.setText(isAddSeperate == 1 ? "YES" : "NO");
            ((View) binding.etInventoryAmount.getParent()).setVisibility(!TextUtils.isEmpty(stockAmount) ? View.GONE : View.VISIBLE);
            ((View) binding.etAssetsMachinaryValue.getParent()).setVisibility(TextUtils.isEmpty(machineryAmount) ? View.GONE : View.VISIBLE);
            ((View) binding.etEmployeeNo.getParent()).setVisibility(employeeNo == 0 ? View.GONE : View.VISIBLE);

            if (isPhotoArrayNotEmpty(arrPhotos)) {
                binding.tvViewFile.setVisibility(View.VISIBLE);
                binding.tvViewFile.setTag(arrPhotos.get(0).getPhotoPath());
            } else {
                binding.labelBusinessPlacePhotos.setVisibility(View.GONE);
                binding.cardViewBusinessPlacePhotos.setVisibility(View.GONE);
            }
        } else {
            if (arrPhotos != null && arrPhotos.size() > 0) {
                for (ZiploanPhoto photo : arrPhotos) {
                    setBusinessPhotosViews(photo);
                }
            }
            setBusinessPhotosViews(new ZiploanPhoto(""));

            if (arrLongShotPhotos != null && arrLongShotPhotos.size() > 0) {
                for (ZiploanPhoto photo : arrLongShotPhotos) {
                    setLongShotViews(photo);
                }
            }
            setLongShotViews(new ZiploanPhoto(""));

            if (arrIdproofPhotos != null && arrIdproofPhotos.size() > 0) {
                for (ZiploanPhoto photo : arrIdproofPhotos) {
                    setIdProofViews(photo);
                }
            }
            setIdProofViews(new ZiploanPhoto(""));
        }


        //Setting Reference Users data --------------------------
        if (arrReferenceUsers != null && arrReferenceUsers.size() > 0) {
            for (ReferenceUser user : arrReferenceUsers) {
                addReferenceBox(user);
            }
        } else {
            addReferenceBox(new ReferenceUser("", "","", ""));
        }


        //Setting family Reference Users data --------------------------
        if (arrFamilyReferenceUsers!= null && arrFamilyReferenceUsers.size() > 0) {
            for (FamilyReferences user : arrFamilyReferenceUsers) {
                addReferenceBoxFamily(user);
            }
        } else {
            addReferenceBoxFamily(new FamilyReferences("", "","", ""));
        }

        //Setting Additional Information --------------------------
        //  binding.cbLeft.setChecked(isAddSeperate == 1 ? true : false);
        binding.cbRight.setChecked(!binding.cbLeft.isChecked());

        //if(stockAmount == null) stockAmount = "";
        binding.etInventoryAmount.setText(stockAmount + "");
        if (machineryAmount == null) machineryAmount = "";
        binding.etAssetsMachinaryValue.setText(machineryAmount + "");
        binding.etEmployeeNo.setText(String.valueOf(employeeNo));

        binding.etActualNoOfEmployee.setText(actual_no_of_employees + "");
        binding.etRentAmount.setText(rent_amount + "");
        binding.etBusineesInvestment.setText(investment_in_business + "");
        binding.etNoOfMachines.setText(no_of_machines + "");
        binding.etRawMaterialAmount.setText(raw_material + "");

        binding.etPersonMet.setText(person_met);
        binding.businessAsDashboard.setText(newAddress);
        binding.etDesignationInOffice.setText(designation_in_office);
        for (int i = 0; i < designation.length; i++) {
            if (designation[i].equalsIgnoreCase(designation_in_office)) {
                binding.designationspinner.setSelection(i);
                break;
            }
        }
//        for (int i=0;i<number_of_shareholder.length;i++){
//            if(number_of_shareholder[i].equalsIgnoreCase(designation_in_office)){
//                binding.shareHolderSpinner.setSelection(i);
//                break;
//            }
//        }
        if (!TextUtils.isEmpty(siteInfo.getPerson_email()))
            binding.etEmail.setText(siteInfo.getPerson_email());
        binding.etSignObserved.setText(sign_board_observed);
        if (!TextUtils.isEmpty(text_nature_of_work)) {
            binding.etNatureOfWork.setVisibility(View.VISIBLE);
            binding.etNatureOfWork.setText(text_nature_of_work);
        }

        binding.reasonChangingPlace.setVisibility((!TextUtils.isEmpty(businessStability) && businessStability.contains("Less than")) ? View.VISIBLE : View.GONE);

        if (!TextUtils.isEmpty(businessPlaceChangeReason)){
            binding.reasonChangingPlace.setText(businessPlaceChangeReason);
        }

        if (!TextUtils.isEmpty(text_designation)) {
            binding.textDesignationInOffice.setVisibility(View.VISIBLE);
            binding.textDesignationInOffice.setText(text_designation);
        }


    }

    private ZiploanSiteInfo getSiteInfoFromBundle(Bundle bundle) {
        ZiploanSiteInfo siteInfo = new ZiploanSiteInfo();
        if (bundle != null && bundle.containsKey(AppConstant.Key.EXTRA_SITE_INFO)) {
            siteInfo = bundle.getParcelable(AppConstant.Key.EXTRA_SITE_INFO);
        }
        if (bundle != null && bundle.containsKey(AppConstant.Key.IS_NEED_TO_DISABLE)) {
            isNeedToDisable = bundle.getBoolean(AppConstant.Key.IS_NEED_TO_DISABLE);
        }
        if (bundle != null && bundle.containsKey(AppConstant.Key.APP_INTSALLED_STATUS)) {
            appInstalledStatus = bundle.getInt(AppConstant.Key.APP_INTSALLED_STATUS, 0);
        }
        return siteInfo;
    }

    private void setBusinessPhotosViews(ZiploanPhoto photo) {
        arrPhotos.add(photo);
        adapter = new PhotosAdapter(mContext, arrPhotos, this, BUSINESS_PHOTO_SELECTED);
        binding.rvBusinessPhotos.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.rvBusinessPhotos.setAdapter(adapter);
    }

    private void setLongShotViews(ZiploanPhoto photo) {
        arrLongShotPhotos.add(photo);
        adapterLongShot = new PhotosAdapter(mContext, arrLongShotPhotos, this, BUSINESS_LONG_SHOT_PHOTES_SELECTED);
        binding.rvBusinessPhotosLongShot.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.rvBusinessPhotosLongShot.setAdapter(adapterLongShot);
    }

    private void setIdProofViews(ZiploanPhoto photo) {
        arrIdProofPhotos.add(photo);
        adapterIdproof = new PhotosAdapter(mContext, arrIdProofPhotos, this, ID_PROOF_PHOTOES_SELECTED);
        binding.rvIdProof.setLayoutManager(new GridLayoutManager(mContext, 3));
        binding.rvIdProof.setAdapter(adapterIdproof);
    }

    private void setListeners() {
        leftCheckListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setCustomChecked(b, false, compoundButton, binding.cbRight, rightCheckListener);
            }
        };
        rightCheckListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setCustomChecked(b, false, compoundButton, binding.cbLeft, leftCheckListener);
            }
        };
        binding.tvAddMore.setOnClickListener(this);
        binding.tvAddMoreFamily.setOnClickListener(this);
        binding.tvViewFile.setOnClickListener(this);
        binding.cbLeft.setOnCheckedChangeListener(leftCheckListener);
        binding.cbRight.setOnCheckedChangeListener(rightCheckListener);
    }

    private void setCustomChecked(boolean b1, boolean b, CompoundButton compoundButton, CheckBox cb, CompoundButton.OnCheckedChangeListener listener) {
        if (!b1) {
            compoundButton.setChecked(!b1);
        } else {
            cb.setOnCheckedChangeListener(null);
            cb.setChecked(b);
            cb.setOnCheckedChangeListener(listener);
        }
        compoundButton.setTextColor(getResources().getColor(compoundButton.isChecked() ? android.R.color.white : R.color.colorPrimary));
        cb.setTextColor(getResources().getColor(cb.isChecked() ? android.R.color.white : R.color.colorPrimary));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_more:
                addReferenceBox(new ReferenceUser("", "", "",""));
                break;

                case R.id.tv_add_moreFamily:
                addReferenceBoxFamily(new FamilyReferences("", "", "",""));
                break;
            case R.id.tv_view_file:
                openPDFBrowser(view);
                break;

            case R.id.img_app_install_status_refresh:
                getAppInstallStatus();
                break;
        }
    }

    private void addReferenceBox(ReferenceUser referenceUser) {
        ItemReferenceBinding referenceBinding = ItemReferenceBinding.inflate(LayoutInflater.from(mContext), null, false);
        binding.llReferences.addView(referenceBinding.getRoot());
        referenceBinding.setReference(referenceUser);
        if (isNeedToDisable) {
            enableField(referenceBinding.etMobile, !isNeedToDisable);
            enableField(referenceBinding.etName, !isNeedToDisable);
        }
    }


    private void addReferenceBoxFamily(FamilyReferences referenceUser) {
        ItemFamilyReferenceBinding referenceBinding = ItemFamilyReferenceBinding.inflate(LayoutInflater.from(mContext), null, false);
        binding.llReferencesFamily.addView(referenceBinding.getRoot());
        referenceBinding.setReference(referenceUser);
        if (isNeedToDisable) {
            enableField(referenceBinding.etMobile, !isNeedToDisable);
            enableField(referenceBinding.etName, !isNeedToDisable);
        }
    }


    @Override
    public void deletePhoto(int position, int index) {
        this.selectedOption = index;
        if (selectedOption == BUSINESS_PHOTO_SELECTED) {
            if (arrPhotos.get(position).getUpload_status() == AppConstant.UploadStatus.UPLOADING_SUCCESS) {
                FileUploader.getInstance(mContext).deleteFile(arrPhotos.get(position), AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, ZiploanSPUtils.getInstance(mContext).getSelectedLoanRequestId());
            }
            arrPhotos.remove(position);
            adapter.notifyItemRemoved(position);
        } else if (selectedOption == BUSINESS_LONG_SHOT_PHOTES_SELECTED) {
            if (arrLongShotPhotos.get(position).getUpload_status() == AppConstant.UploadStatus.UPLOADING_SUCCESS) {
                FileUploader.getInstance(mContext).deleteFile(arrLongShotPhotos.get(position), AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, ZiploanSPUtils.getInstance(mContext).getSelectedLoanRequestId());
            }
            arrLongShotPhotos.remove(position);
            adapterLongShot.notifyItemRemoved(position);
        } else if (selectedOption == ID_PROOF_PHOTOES_SELECTED) {
            if (arrIdProofPhotos.get(position).getUpload_status() == AppConstant.UploadStatus.UPLOADING_SUCCESS) {
                FileUploader.getInstance(mContext).deleteFile(arrIdProofPhotos.get(position), AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, ZiploanSPUtils.getInstance(mContext).getSelectedLoanRequestId());
            }
            arrIdProofPhotos.remove(position);
            adapterIdproof.notifyItemRemoved(position);
        }
    }

    @Override
    public void retryUpload(int position, int index) {
        this.selectedOption = index;
        if (selectedOption == BUSINESS_PHOTO_SELECTED) {
            uploadFiles(arrPhotos.subList(position, position + 1));
        } else if (selectedOption == BUSINESS_LONG_SHOT_PHOTES_SELECTED) {
            uploadFiles(arrLongShotPhotos.subList(position, position + 1));
        } else if (selectedOption == ID_PROOF_PHOTOES_SELECTED) {
            uploadFiles(arrIdProofPhotos.subList(position, position + 1));
        }
    }

    @Override
    public void openCameraGalleyOptions(int position, int index, boolean multi_selection) {
        this.selectedOption = index;
        openCameraGalleryOptions(multi_selection);
    }

    private void uploadFiles(List<ZiploanPhoto> arrImages) {
        String fileType = AppConstant.FileType.PD_BUSINESS_PLACE_PHOTO;
        if (selectedOption == BUSINESS_LONG_SHOT_PHOTES_SELECTED) {
            fileType = AppConstant.FileType.PD_LONG_SHOT_PHOTO;
        } else if (selectedOption == ID_PROOF_PHOTOES_SELECTED) {
            fileType = AppConstant.FileType.PD_ID_PROOF_PHOTO;
        }

        FileUploader.getInstance(mContext).upload(fileType, arrImages, AppConstant.FileUploadBucketId.BUSINESS_PLACE_PHOTO, ZiploanSPUtils.getInstance(mContext).getSelectedLoanRequestId(), new PhotoUploadListener() {
            @Override
            public void onUploadSuccess(ZiploanPhoto photo) {
                if (selectedOption == BUSINESS_PHOTO_SELECTED) {
                    photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_SUCCESS);
                    adapter.notifyItemChanged(arrPhotos.indexOf(photo));
                } else if (selectedOption == BUSINESS_LONG_SHOT_PHOTES_SELECTED) {
                    photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_SUCCESS);
                    adapterLongShot.notifyItemChanged(arrLongShotPhotos.indexOf(photo));
                } else if (selectedOption == ID_PROOF_PHOTOES_SELECTED) {
                    photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_SUCCESS);
                    adapterIdproof.notifyItemChanged(arrIdProofPhotos.indexOf(photo));
                }
            }

            @Override
            public void onUploadFailed(ZiploanPhoto photo) {
                if (selectedOption == BUSINESS_PHOTO_SELECTED) {
                    photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_FAILED);
                    adapter.notifyItemChanged(arrPhotos.indexOf(photo));
                } else if (selectedOption == BUSINESS_LONG_SHOT_PHOTES_SELECTED) {
                    photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_FAILED);
                    adapterLongShot.notifyItemChanged(arrLongShotPhotos.indexOf(photo));
                } else if (selectedOption == ID_PROOF_PHOTOES_SELECTED) {
                    photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_FAILED);
                    adapterIdproof.notifyItemChanged(arrIdProofPhotos.indexOf(photo));
                }
            }

            @Override
            public void onUploadStarted(ZiploanPhoto photo) {
                if (selectedOption == BUSINESS_PHOTO_SELECTED) {
                    photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_STARTED);
                    adapter.notifyItemChanged(arrPhotos.indexOf(photo));
                } else if (selectedOption == BUSINESS_LONG_SHOT_PHOTES_SELECTED) {
                    photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_STARTED);
                    adapterLongShot.notifyItemChanged(arrLongShotPhotos.indexOf(photo));
                } else if (selectedOption == ID_PROOF_PHOTOES_SELECTED) {
                    photo.setUpload_status(AppConstant.UploadStatus.UPLOADING_STARTED);
                    adapterIdproof.notifyItemChanged(arrIdProofPhotos.indexOf(photo));
                }
            }
        });
    }

    public ZiploanSiteInfo getSiteInfo(boolean isFinal) {
        ZiploanSiteInfo info = new ZiploanSiteInfo();
        try {
            int count = binding.llReferences.getChildCount();
            int countReference = binding.llReferencesFamily.getChildCount();

            if (isFinal && TextUtils.isEmpty(binding.etPersonMet.getText().toString().trim())) {
                binding.etPersonMet.setError("Value must be filled");
                UIErrorUtils.scrollToView(binding.scrollView, binding.etPersonMet);
                return null;
            } else {
                info.setPerson_met(binding.etPersonMet.getText().toString().trim());
            }

            if (isFinal && binding.designationspinner.getSelectedItemPosition() == 0) {
                showShortToast("Please select designation");
                return null;
            } else {
                if (binding.designationspinner.getSelectedItem().toString().equalsIgnoreCase("other")) {
                    if (TextUtils.isEmpty(binding.textDesignationInOffice.getText())) {
                        showShortToast("Please select designation");
                        binding.textDesignationInOffice.setError("Please select designation");
                        binding.textDesignationInOffice.setVisibility(View.VISIBLE);
                        UIErrorUtils.scrollToView(binding.scrollView, binding.textDesignationInOffice);
                        binding.textDesignationInOffice.requestFocus();
                        return null;
                    }
                    if (isFinal) {
                        info.setDesignation_in_office(binding.designationspinner.getSelectedItem().toString());
                        info.setText_designation(binding.textDesignationInOffice.getText().toString());
                    } else {
                        info.setDesignation_in_office(binding.designationspinner.getSelectedItem().toString());
                        info.setText_designation(binding.textDesignationInOffice.getText().toString());
                    }
                } else
                    info.setDesignation_in_office(binding.designationspinner.getSelectedItem().toString());
            }

            info.setPerson_email(binding.etEmail.getText().toString());

            if (isFinal && binding.spinnerBc.getSelectedItemPosition() == 0) {
                showShortToast("Please select business category first");
                return null;
            } else {
                info.setBusiness_category(businessCategories.get(binding.spinnerBc.getSelectedItemPosition()).getBusiness_category_id());
            }

            if (isFinal && binding.spinnerBisniessStability.getSelectedItemPosition() == 0) {
                showShortToast("Please select Stability at current premise");
                return null;
            } else {
                info.setBusiness_stability(binding.spinnerBisniessStability.getSelectedItem().toString());
            }


            if (isFinal && binding.reasonChangingPlace.getVisibility()==View.VISIBLE) {
                if (TextUtils.isEmpty(binding.reasonChangingPlace.getText().toString())){
                    showShortToast("Please enter reason for business place change");
                    return null;
                }else{
                    info.setBusiness_place_change_reason(binding.reasonChangingPlace.getText().toString());
                }

            } else {
                info.setBusiness_place_change_reason(binding.reasonChangingPlace.getText().toString());
            }

            if (isFinal && TextUtils.isEmpty(binding.landmark.getText())) {
                showShortToast("Please select Landmark");
                return null;
            } else {
                info.setLandmark(binding.landmark.getText().toString());
                UIErrorUtils.scrollToView(binding.scrollView, binding.landmark);
                binding.landmark.requestFocus();
            }

            if (isFinal && binding.businessDashboardSpinner.getSelectedItemPosition() == 0) {
                showShortToast("Please select business address same as dashboard");
                return null;
            } else {
                if (binding.businessDashboardSpinner.getSelectedItemPosition() == 2) {
                    if (TextUtils.isEmpty(binding.businessAsDashboard.getText())) {
                        showShortToast("Please Enter current business address same as dashboard");
                        return null;
                    } else {
                        info.setBusiness_address_same_as_dashboard("No");
                        info.setBusiness_address_same_as_dashboard_reason(binding.businessAsDashboard.getText().toString());
                    }
                } else {
                    info.setBusiness_address_same_as_dashboard(binding.businessDashboardSpinner.getSelectedItem().toString());
                }
            }

            if (isFinal && binding.spinnerLocality.getSelectedItemPosition() == 0) {
                showShortToast("Please select locality of the Business place");
                return null;
            } else {
                info.setLocality_business_place(binding.spinnerLocality.getSelectedItem().toString());
            }

            if (isFinal && binding.spinnerEducation.getSelectedItemPosition() == 0) {
                showShortToast("Please select Education qualification");
                return null;
            } else {
                info.setEducation_qualification(binding.spinnerEducation.getSelectedItem().toString());
            }


            if (isFinal && binding.natureOfWork.getSelectedItemPosition() == 0) {
                showShortToast("Please select nature of work");
                return null;
            } else {
                if (binding.natureOfWork.getSelectedItem()!=null)
                info.setNature_of_work(binding.natureOfWork.getSelectedItem().toString());
            }

            if (isFinal && TextUtils.isEmpty(binding.etNatureOfWork.getText().toString())) {
                showShortToast("Please enter nature of work details");
                return null;
            } else {
                info.setText_nature_of_work(binding.etNatureOfWork.getText().toString());
            }

            if (isFinal && binding.employeedOnSiteSpinner.getSelectedItemPosition() == 0) {
                showShortToast("Please select Employees on site");
                return null;
            } else {
                info.setActual_no_of_employees(binding.employeedOnSiteSpinner.getSelectedItem().toString());
            }

            if (isFinal && binding.numberOfMachines.getSelectedItemPosition() == 0) {
                showShortToast("Please select number of machines");
                return null;
            } else {
                info.setNo_of_machines(binding.numberOfMachines.getSelectedItem().toString());
            }

            if (isFinal && TextUtils.isEmpty(binding.etAssetsMachinaryValue.getText().toString())) {
                showShortToast("Please select Fixed Asset value");
                binding.etAssetsMachinaryValue.setError("Please select Fixed Asset value");
                UIErrorUtils.scrollToView(binding.scrollView, binding.etAssetsMachinaryValue);
                binding.etAssetsMachinaryValue.requestFocus();
                return null;
            } else {
                info.setFixed_asset_machinery_amount(binding.etAssetsMachinaryValue.getText() == null ? "" : binding.etAssetsMachinaryValue.getText().toString().trim());
            }

            if (isFinal && binding.rawMaterial.getSelectedItemPosition() == 0) {
                showShortToast("Please select Raw material");
                return null;
            } else {
                info.setRaw_material(binding.rawMaterial.getSelectedItem().toString());
            }

            if (isFinal && binding.stockAmount.getSelectedItemPosition() == 0) {
                showShortToast("Please select Stock amount");
                return null;
            } else {
                info.setStock_inventory_amount(binding.stockAmount.getSelectedItem().toString());
            }

            if (isFinal && binding.businessInvestment.getSelectedItemPosition() == 0) {
                showShortToast("Please select business investment");
                return null;
            } else {
                info.setInvestment_in_business(binding.businessInvestment.getSelectedItem().toString());
            }

            if (isFinal && binding.signBoard.getSelectedItemPosition() == 0) {
                showShortToast("Please select sign board seen");
                return null;
            } else {
                info.setSign_board_observed(binding.signBoard.getSelectedItem().toString());
            }

//            if (appInstalledStatus == 1) {
//                info.setApp_not_installed_reason("");
//            } else if (isFinal && binding.spinnerAppNotInstalled.getSelectedItemPosition() == 0) {
//                showShortToast("Please select reason why app is not installed?");
//                return null;
//            } else {
//                info.setApp_not_installed_reason(binding.spinnerAppNotInstalled.getSelectedItem().toString());
//            }

            //info.setBusiness_place_seperate_residence_place(binding.cbLeft.isChecked() ? 1 : 0);

            info.setNo_employees(Integer.parseInt(!TextUtils.isEmpty(binding.etEmployeeNo.getText().toString().trim()) ? binding.etEmployeeNo.getText().toString().trim() : "0"));
            info.setBusiness_place_photo_url(arrPhotos.subList(0, arrPhotos.size() - 1));
            info.setLong_shot_photos(arrLongShotPhotos.subList(0, arrLongShotPhotos.size() - 1));
            info.setId_proof_photos(arrIdProofPhotos.subList(0, arrIdProofPhotos.size() - 1));
            info.setRent_amount(binding.etRentAmount.getText() == null ? 0 : Integer.parseInt(binding.etRentAmount.getText().toString().trim()));

            if (count>0){
                for (int i = 0; i < count; i++) {
                    View view = binding.llReferences.getChildAt(i);
                    String name = ((EditText) view.findViewById(R.id.et_name)).getText().toString();
                    String mobile = ((EditText) view.findViewById(R.id.et_mobile)).getText().toString();
                    String address = ((EditText) view.findViewById(R.id.et_address)).getText().toString();
                    String relationship = ((EditText) view.findViewById(R.id.et_relationship)).getText().toString();
//                if (TextUtils.isEmpty(name)){
//                    ((EditText) view.findViewById(R.id.et_name)).setError(getString(R.string.reference_name_error));
//                }
//                if (TextUtils.isEmpty(mobile)){
//                    ((EditText) view.findViewById(R.id.et_mobile)).setError(getString(R.string.reference_mobile_error));
//                }
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(address)) {//&& !TextUtils.isEmpty(relationship)
                        info.getArrReferenceUsers().add(new ReferenceUser(name.trim(), mobile.trim(),address.trim(), relationship.trim()));
                    }
                }

            }


            if (countReference>0){
                for (int i = 0; i < countReference; i++) {
                    View view = binding.llReferencesFamily.getChildAt(i);
                    String name = ((EditText) view.findViewById(R.id.et_name)).getText().toString();
                    String mobile = ((EditText) view.findViewById(R.id.et_mobile)).getText().toString();
                    String address = ((EditText) view.findViewById(R.id.et_address)).getText().toString();
                    String relationship = ((EditText) view.findViewById(R.id.et_relationship)).getText().toString();
//                if (TextUtils.isEmpty(name)){
//                    ((EditText) view.findViewById(R.id.et_name)).setError(getString(R.string.reference_name_error));
//                }
//                if (TextUtils.isEmpty(mobile)){
//                    ((EditText) view.findViewById(R.id.et_mobile)).setError(getString(R.string.reference_mobile_error));
//                }
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(address)) {//&& !TextUtils.isEmpty(relationship)
                        info.getArrFamilyReferenceUsers().add(new FamilyReferences(name.trim(), mobile.trim(),address.trim(), relationship.trim()));
                    }
                }

            }



            return info;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    @Override
    public void getImages(List<ZiploanPhoto> arrImages, int sourceType) {
        int positionStart = 0;
        if (selectedOption == BUSINESS_PHOTO_SELECTED) {
            positionStart = arrPhotos.size();
            arrPhotos.addAll(arrPhotos.size() - 1, arrImages);
            adapter.notifyItemRangeInserted(positionStart, arrImages.size());
        } else if (selectedOption == BUSINESS_LONG_SHOT_PHOTES_SELECTED) {
            positionStart = arrLongShotPhotos.size();
            arrLongShotPhotos.addAll(arrLongShotPhotos.size() - 1, arrImages);
            adapterLongShot.notifyItemRangeInserted(positionStart, arrImages.size());
        } else if (selectedOption == ID_PROOF_PHOTOES_SELECTED) {
            positionStart = arrIdProofPhotos.size();
            arrIdProofPhotos.addAll(arrIdProofPhotos.size() - 1, arrImages);
            adapterIdproof.notifyItemRangeInserted(positionStart, arrImages.size());
        }

        hideProgressDialog();
//        System.out.println("Selected Photos = " + new Gson().toJson(arrImages));
        uploadFiles(arrImages);
    }

    @Override
    public void processingImages() {
        showProgressDialog(mContext, "Compressing images");
    }

    private void postMedia() {
//        Call<CibilResponse> call = APIExecutor.getAPIService(mContext)
//                .media(getArguments().getString(AppConstant.Key.LOAN_REQUEST_ID));
//        call.enqueue(new Callback<CibilResponse>() {
//            @Override
//            public void onResponse(Call<CibilResponse> call, ResponseData<CibilResponse> response) {
//                hideProgressDialog();
//                if (response != null && response.isSuccessful() && response.body() != null) {
//
//                } else {
//                    checkTokenValidity(response);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CibilResponse> call, Throwable t) {
//                hideProgressDialog();
//            }
//        });
    }
}