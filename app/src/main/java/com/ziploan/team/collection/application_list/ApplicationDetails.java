//package com.ziploan.team.collection.application_list;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.databinding.ViewDataBinding;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.Browser;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.text.Html;
//import android.text.TextUtils;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.ziploan.team.BuildConfig;
//import com.ziploan.team.PhotoUploadListener;
//import com.ziploan.team.R;
//import com.ziploan.team.asset_module.FileUploader;
//import com.ziploan.team.asset_module.change_request.BusinessAddressChangeRequest;
//import com.ziploan.team.asset_module.change_request.ChangeRequestActivity;
//import com.ziploan.team.asset_module.change_request.MobileChangeRequest;
//import com.ziploan.team.collection.model.app_list.OverdueBreakup;
//import com.ziploan.team.collection.model.app_list.Reference;
//import com.ziploan.team.collection.model.app_list.Result;
//import com.ziploan.team.collection.model.app_list.co_applicant.CoApplicantData;
//import com.ziploan.team.collection.utils.UIErrorUtils;
//import com.ziploan.team.databinding.CoapplicantItemLayoutBinding;
//import com.ziploan.team.databinding.CollectionApplicationDetailLayoutBinding;
//import com.ziploan.team.databinding.ReferenceMobileLayoutBinding;
//import com.ziploan.team.utils.AppConstant;
//import com.ziploan.team.utils.ZiploanSPUtils;
//import com.ziploan.team.utils.ZiploanUtil;
//import com.ziploan.team.verification_module.base.BaseActivity;
//import com.ziploan.team.verification_module.verifyekyc.ZiploanPhoto;
//import com.ziploan.team.webapi.APIExecutor;
//import com.ziploan.team.webapi.ApiResponse;
//import com.ziploan.team.webapi.Loan;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.ResponseData;
//
//public class ApplicationDetails extends BaseActivity implements View.OnClickListener {
//
//    private CollectionApplicationDetailLayoutBinding allViews;
//    private Result result;
//    private ReferenceListAdapter referenceListAdapter;
//    private CoApplicantListAdapter coApplicantListAdapter;
//
//    public static void start(Context mContext, Bundle bundle) {
//        Intent intent = new Intent(mContext, ApplicationDetails.class);
//        intent.putExtras(bundle);
//        mContext.startActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppThemeDark);
//        super.onCreate(savedInstanceState);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        setTitle(getString(R.string.collection_detail));
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.collection_application_detail_layout;
//    }
//
//    @Override
//    protected void onViewMapped(ViewDataBinding views) {
//        allViews = (CollectionApplicationDetailLayoutBinding) views;
//        if (getIntent().getExtras() != null) {
//            result = (Result) getIntent().getSerializableExtra(AppConstant.Key.LOAN_REQUEST_DATA);
//            allViews.setModel(result);
//        }
//        if (result.getReferences() != null && result.getReferences().size() > 0) {
//            allViews.referenceRecycler.setVisibility(View.VISIBLE);
//            allViews.referenceNumberText.setVisibility(View.VISIBLE);
//        }
//
//
//        allViews.overdueBreakup.setOnClickListener(this);
//        allViews.pastVisit.setOnClickListener(this);
//        allViews.recordNewVisit.setOnClickListener(this);
//        allViews.viewSoa.setOnClickListener(this);
//        allViews.viewKyc.setOnClickListener(this);
//        allViews.residentMap.setOnClickListener(this);
//        allViews.officeMap.setOnClickListener(this);
//        allViews.editNumber.setOnClickListener(this);
//        allViews.editAddress.setOnClickListener(this);
//        allViews.editResAddress.setOnClickListener(this);
//
//        referenceListAdapter = new ReferenceListAdapter(result.getReferences());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        allViews.referenceRecycler.setLayoutManager(mLayoutManager);
//        allViews.referenceRecycler.setItemAnimator(new DefaultItemAnimator());
//        allViews.referenceRecycler.setAdapter(referenceListAdapter);
//
//        if (result.getCoApplicants() != null && result.getCoApplicants().size() > 0) {
//            List<CoApplicantData> coApplicantList = new ArrayList<>();
//            for(CoApplicantData coApplicantData : result.getCoApplicants()){
//                if(!TextUtils.isEmpty(coApplicantData.getApplicantType()) && !coApplicantData.getApplicantType().equalsIgnoreCase("1")){
//                    coApplicantList.add(coApplicantData);
//                }
//            }
//            if(coApplicantList.size() > 0) {
//                allViews.coApplicantRecycler.setVisibility(View.VISIBLE);
//                allViews.coApplicantText.setVisibility(View.VISIBLE);
//                coApplicantListAdapter = new CoApplicantListAdapter(coApplicantList);
//                RecyclerView.LayoutManager mLayoutManagerCo = new LinearLayoutManager(getApplicationContext());
//                allViews.coApplicantRecycler.setLayoutManager(mLayoutManagerCo);
//                allViews.coApplicantRecycler.setItemAnimator(new DefaultItemAnimator());
//                allViews.coApplicantRecycler.setAdapter(coApplicantListAdapter);
//            }
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
////    @Override
////    protected void onResume() {
////        super.onResume();
////        loadApplicationDetails();
////    }
//
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.overdue_breakup:
//                if (result.getOverdueBreakup() != null && result.getOverdueBreakup().size() > 0) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("overdue", result.getAmountOverdue());
//                    bundle.putSerializable("data", (ArrayList<OverdueBreakup>) result.getOverdueBreakup());
//                    OverdueBreakupActivity.start(this, bundle);
//                }
//                break;
//            case R.id.past_visit:
//                Bundle bundleRecord = new Bundle();
//                bundleRecord.putString("id", result.getLoanApplicationNumber());
//                PastVisitsActivity.start(this, bundleRecord);
//                break;
//            case R.id.resident_map:
//                openMap(result.getResidenceAddress());
//                break;
//            case R.id.office_map:
//                openMap(result.getBusinessAddress());
//                break;
//            case R.id.view_soa:
//                showDataOnBrowser();
//                break;
//            case R.id.view_kyc:
//                Bundle kycIntent = new Bundle();
//                kycIntent.putString("id", result.getLoanApplicationNumber());
//                ViewKYCDocumentsActivity.start(this, kycIntent);
//                break;
//            case R.id.record_new_visit:
//                Bundle bundleRecordw = new Bundle();
//                bundleRecordw.putSerializable(AppConstant.Key.LOAN_REQUEST_DATA, result);
//                bundleRecordw.putString("id", result.getLoanApplicationNumber());
//                RecordVisitActivity.start(this, bundleRecordw);
//                break;
//            case R.id.edit_number:
//                moveToChange();
////                editNumberDialogue();
//                break;
//            case R.id.close_img:
//                dialog.dismiss();
//                break;
//            case R.id.submit_edit_number:
//                submitMobileChangeRequest();
//                break;
//            case R.id.edit_address:
//                moveToChange();
//                break;
//            case R.id.edit_res_address:
//                moveToChange();
//                break;
//            case R.id.submit_edit_address:
//                submitAddressChangeRequest();
//                break;
//
//        }
//    }
//
//    public void moveToChange() {
//        Intent intent = new Intent(mContext, ChangeRequestActivity.class);
//        Bundle bundle = new Bundle();
//        Loan loan = new Loan();
//        loan.setBusiness_address(result.getBusinessAddress());
//        loan.setResidence_address(result.getResidenceAddress());
//        loan.setMobile_number(result.getMobileNumber());
//        loan.setIdentifier(result.getIdentifier());
//        bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT, loan);
//        bundle.putBoolean("collection",true);
//        intent.putExtras(bundle);
//        mContext.startActivity(intent);
//    }
//
//    private void openMap(String address) {
//        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        startActivity(mapIntent);
//
//    }
//
//    private void openMapWithCoordinates(List<String> address) {
//        if(address != null && address.size() == 2 && !address.get(0).equalsIgnoreCase("No visits recorded for this record")) {
//            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + address.get(0) + "," + address.get(1));
//            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//            mapIntent.setPackage("com.google.android.apps.maps");
//            startActivity(mapIntent);
//        }
//    }
//
//    private void showDataOnBrowser() {
//        if(UIErrorUtils.isNetworkConnected(this)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("id", result.getLoanApplicationNumber());
//            bundle.putString("name", "soa.pdf");
//            ViewerActivity.start(this, bundle);
//        } else {
//            showToast(this,getString(R.string.no_internet));
//        }
//    }
//
//    Dialog dialog;
//    EditText mobile;
//    EditText etBusinessAddress;
//    TextView mobile_invalid;
//
//
//    private void editNumberDialogue() {
//        dialog = ZiploanUtil.getCustomDialogCollection(this, R.layout.edit_number_layout);
//        dialog.show();
//        ImageView close = dialog.findViewById(R.id.close_img);
//        mobile = dialog.findViewById(R.id.mobile);
//        mobile_invalid = dialog.findViewById(R.id.layout_mobile_invalid);
//        dialog.findViewById(R.id.submit_edit_number).setOnClickListener(this);
//
//        close.setOnClickListener(this);
//    }
//
//    private void submitMobileChangeRequest() {
//        if (!TextUtils.isEmpty(mobile.getText().toString())) {
//            mobile_invalid.setVisibility(View.GONE);
//            UIErrorUtils.hideFromDialogue(dialog.getContext(),mobile);
//            apiCallMobile();
//        } else {
//            mobile_invalid.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void apiCallMobile() {
//        final MobileChangeRequest request = new MobileChangeRequest();
//        request.setNew_mobile_number(mobile.getText().toString());
//        request.setConfirm_mobile_number(mobile.getText().toString());
//        request.setLoan_request_id(result.getIdentifier());
//        request.setRequest_category(String.valueOf(6));
//        showProgressDialog("Please wait...");
//        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).changeMobile(request);
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, ResponseData<ApiResponse> response) {
//                hideProgressDialog();
//                checkTokenValidity(response);
//                if (response != null && response.body() != null && response.body().getResult().equalsIgnoreCase("1")) {
//                    showAlertInfo("Mobile Number change request has been sent.", new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialogInterface) {
//                            dialog.dismiss();
//                        }
//                    });
//                } else {
//                    showAlertInfo(getResources().getString(R.string.something_went_wrong));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                hideProgressDialog();
//                showAlertInfo(getResources().getString(R.string.server_not_responding));
//            }
//        });
//    }
//
//    private void submitAddressChangeRequest() {
//        if (!TextUtils.isEmpty(etBusinessAddress.getText().toString())) {
//            mobile_invalid.setVisibility(View.GONE);
//            UIErrorUtils.hideFromDialogue(dialog.getContext(),etBusinessAddress);
//            saveBusinessChangeAddress();
//        } else {
//            mobile_invalid.setVisibility(View.VISIBLE);
//        }
//    }
//
//    private void editAddressDialogue() {
//        dialog = ZiploanUtil.getCustomDialogCollection(this, R.layout.business_addess_chnage_layout);
//        dialog.show();
//        ImageView close = dialog.findViewById(R.id.close_img);
//        etBusinessAddress = dialog.findViewById(R.id.address);
//        mobile_invalid = dialog.findViewById(R.id.layout_mobile_invalid);
//        dialog.findViewById(R.id.submit_edit_address).setOnClickListener(this);
//
//        close.setOnClickListener(this);
//    }
//
//    private void saveBusinessChangeAddress() {
//        final BusinessAddressChangeRequest request = new BusinessAddressChangeRequest();
//        request.setBusiness_address(etBusinessAddress.getText().toString());
//        request.setBusiness_city(etBusinessAddress.getText().toString());
//        request.setBusiness_state(etBusinessAddress.getText().toString());
//        request.setBusiness_pincode(etBusinessAddress.getText().toString());
//        request.setLoan_request_id(result.getIdentifier());
//        request.setRequest_category("1");
//        callBusinessAddressChangeZipLoanapi(request);
//    }
//
//    private void callBusinessAddressChangeZipLoanapi(BusinessAddressChangeRequest request) {
//        showProgressDialog("Please wait...");
//        Call<ApiResponse> call = APIExecutor.getAPIService(mContext).changeRequestBusinessAddress(request);
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, ResponseData<ApiResponse> response) {
//                hideProgressDialog();
//                checkTokenValidity(response);
//                if(response!=null && response.body()!=null && response.body().getResult().equalsIgnoreCase("1")){
//                    showAlertInfo("Business Address change request has been sent.", new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialogInterface) {
//                            finish();
//                        }
//                    });
//                }else {
//                    showAlertInfo(getResources().getString(R.string.something_went_wrong));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                hideProgressDialog();
//                showAlertInfo(getResources().getString(R.string.server_not_responding));
//            }
//        });
//    }
//
//    /**
//     * Reference Numbers Adapter
//     */
//
//    public class ReferenceListAdapter extends RecyclerView.Adapter {
//
//        private List<Reference> data;
//
//        public ReferenceListAdapter(List<Reference> data){
//            this.data = data;
//        }
//
//        class ReferenceMobileLayoutHolder extends RecyclerView.ViewHolder {
//
//            private ReferenceMobileLayoutBinding itemView;
//
//            ReferenceMobileLayoutHolder(ReferenceMobileLayoutBinding itemView) {
//                super(itemView.getRoot());
//                this.itemView = itemView;
//            }
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            ReferenceMobileLayoutBinding binding = ReferenceMobileLayoutBinding.inflate(getLayoutInflater(), parent, false);
//            return new ReferenceMobileLayoutHolder(binding);
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//            final ReferenceMobileLayoutHolder bindingViewholder = (ReferenceMobileLayoutHolder) holder;
//            Reference result = data.get(position);
//            bindingViewholder.itemView.setModel(result);
//            bindingViewholder.itemView.executePendingBindings();
//        }
//
//        @Override
//        public int getItemCount() {
//            return data != null ? data.size() : 0;
//        }
//
//    }
//
//    public class CoApplicantListAdapter extends RecyclerView.Adapter {
//
//        private List<CoApplicantData> data;
//
//        public CoApplicantListAdapter(List<CoApplicantData> data){
//            this.data = data;
//        }
//
//        class CoApplicantLayoutHolder extends RecyclerView.ViewHolder {
//
//            private CoapplicantItemLayoutBinding itemView;
//
//            CoApplicantLayoutHolder(CoapplicantItemLayoutBinding itemView) {
//                super(itemView.getRoot());
//                this.itemView = itemView;
//                itemView.editAddress.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        moveToChangeForCoApplicants(data.get(getAdapterPosition()));
//                    }
//                });
//
//                itemView.editNumber.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        moveToChangeForCoApplicants(data.get(getAdapterPosition()));
//                    }
//                });
//            }
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            CoapplicantItemLayoutBinding binding = CoapplicantItemLayoutBinding.inflate(getLayoutInflater(), parent, false);
//            return new CoApplicantLayoutHolder(binding);
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//            final CoApplicantLayoutHolder bindingViewholder = (CoApplicantLayoutHolder) holder;
//            CoApplicantData result = data.get(position);
//            bindingViewholder.itemView.setModel(result);
//            bindingViewholder.itemView.executePendingBindings();
//        }
//
//        @Override
//        public int getItemCount() {
//            return data != null ? data.size() : 0;
//        }
//
//    }
//    public void moveToChangeForCoApplicants(CoApplicantData coApplicantData) {
//        Intent intent = new Intent(mContext, ChangeRequestActivity.class);
//        Bundle bundle = new Bundle();
//        Loan loan = new Loan();
//        loan.setmApplicantType(coApplicantData.getApplicantType());
//        loan.setBusiness_address(result.getBusinessAddress());
//        loan.setResidence_address(coApplicantData.getAddress());
//        loan.setMobile_number(coApplicantData.getMobile());
//        loan.setIdentifier(result.getIdentifier());
//        bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT, loan);
//        bundle.putBoolean("collection",true);
//        bundle.putBoolean("coApplicant",true);
//        intent.putExtras(bundle);
//        mContext.startActivity(intent);
//    }
//}
