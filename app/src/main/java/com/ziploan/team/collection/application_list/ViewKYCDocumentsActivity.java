package com.ziploan.team.collection.application_list;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ViewDataBinding;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ziploan.team.BuildConfig;
import com.ziploan.team.R;
import com.ziploan.team.asset_module.rv.AllRvResponse;
import com.ziploan.team.asset_module.rv.Rv;
import com.ziploan.team.collection.model.kyc_response.FileDataApplicant;
import com.ziploan.team.collection.model.kyc_response.KycResponse;
import com.ziploan.team.collection.utils.UIErrorUtils;
import com.ziploan.team.databinding.ViewKycBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.verification_module.base.BaseActivity;
import com.ziploan.team.webapi.APIExecutor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewKYCDocumentsActivity extends BaseActivity {
    private String mLoanId;
    private String mLoanNum;
    private KycDocAdapter kycDocAdapter;
    private OtherKycDocAdapter otherKycDocAdapter;
    private PhotoKycDocAdapter BusinessPhotoesDocAdapter;
    private FrameLayout relative_no_network;
    private ViewKycBinding viewKycBinding;
    private AllRvResponse rvResponse;
    private boolean fromAsset;

    @Override
    protected int getLayoutId() {
        return R.layout.view_kyc;
    }

    public static void start(Activity mContext, Bundle bundle) {
        Intent intent = new Intent(mContext, ViewKYCDocumentsActivity.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.view_kyc_documents));
        relative_no_network = findViewById(R.id.relative_no_network);
    }

    @Override
    protected void onViewMapped(ViewDataBinding views) {
        viewKycBinding = (ViewKycBinding) views;
        mLoanId = getIntent().getStringExtra("id");
        mLoanNum = getIntent().getStringExtra("num");
        fromAsset = getIntent().getBooleanExtra("asset",false);
//        if(UIErrorUtils.isNetworkConnected(this)){
//            getkyc();
//        } else {
//            viewKycBinding.cardView.setVisibility(View.GONE);
//        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = viewKycBinding.kycList;
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(mLayoutManager);
        kycDocAdapter = new KycDocAdapter();
        recyclerView.setAdapter(kycDocAdapter);

        if(fromAsset) {
            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this);
            RecyclerView recyclerView1 = viewKycBinding.residenceVerificationList;
            recyclerView1.setNestedScrollingEnabled(false);
            recyclerView1.setLayoutManager(mLayoutManager1);
            otherKycDocAdapter = new OtherKycDocAdapter();
            recyclerView1.setAdapter(otherKycDocAdapter);

            RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(this);
            RecyclerView recyclerView2 = viewKycBinding.businessPhotoesList;
            recyclerView2.setNestedScrollingEnabled(false);
            recyclerView2.setLayoutManager(mLayoutManager2);
            BusinessPhotoesDocAdapter = new PhotoKycDocAdapter();
            recyclerView2.setAdapter(BusinessPhotoesDocAdapter);

            viewKycBinding.fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNewDataOnBrowser(BuildConfig.IMAGE_BASE_URL + rvResponse.getBv(), "Business Verification");
                }
            });
        } else {
            viewKycBinding.bvLayout.setVisibility(View.GONE);
            viewKycBinding.bpText.setVisibility(View.GONE);
            viewKycBinding.businessPhotoesList.setVisibility(View.GONE);
            viewKycBinding.rvLayout.setVisibility(View.GONE);
            viewKycBinding.residenceVerificationList.setVisibility(View.GONE);
        }
    }

    private void showUI() {
        getkyc();
        if(fromAsset) {
            getotherData();
        }
    }

    private void getkyc() {
        showProgressDialog();
        Call<KycResponse> call = APIExecutor.getAPIServiceWithLS(mContext).getkycdocs(mLoanId,AppConstant.Key.VIEW_VALUE);
        call.enqueue(new Callback<KycResponse>() {
            @Override
            public void onResponse(Call<KycResponse> call, Response<KycResponse> response) {
                hideProgressDialog();
                if (response != null && response.isSuccessful() && response.body() != null && response.body().getResponse() != null
                        && response.body().getResponse().getFileDataApplicant() != null) {
                    kycDocAdapter.setData(response.body().getResponse().getFileDataApplicant());
                    viewKycBinding.cardView.setVisibility(View.VISIBLE);
                } else {
                    viewKycBinding.cardView.setVisibility(View.GONE);
                    if(!fromAsset)
                        viewKycBinding.noKyc.setVisibility(View.VISIBLE);
                    checkTokenValidity(response);
                }
            }

            @Override
            public void onFailure(Call<KycResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

    private void getotherData() {
        Call<AllRvResponse> call = APIExecutor.getAPIService(mContext).getRvResponse(mLoanNum);
        call.enqueue(new Callback<AllRvResponse>() {
            @Override
            public void onResponse(Call<AllRvResponse> call, Response<AllRvResponse> response) {
                if(response != null && response.isSuccessful() && response.body() != null){
                    rvResponse = response.body();
                    if(rvResponse.getBv() != null){
                        viewKycBinding.bvLayout.setVisibility(View.VISIBLE);
                    } else {
                        viewKycBinding.bvLayout.setVisibility(View.GONE);
                    }

                    if(rvResponse.getBusinessPlacePhotos() != null && rvResponse.getBusinessPlacePhotos().size() > 0){
                        viewKycBinding.bpText.setVisibility(View.VISIBLE);
                        viewKycBinding.businessPhotoesList.setVisibility(View.VISIBLE);
                    } else {
                        viewKycBinding.bpText.setVisibility(View.GONE);
                        viewKycBinding.businessPhotoesList.setVisibility(View.GONE);
                    }

                    if(rvResponse.getRv() != null && rvResponse.getRv().size() > 0){
                        viewKycBinding.rvLayout.setVisibility(View.VISIBLE);
                        viewKycBinding.residenceVerificationList.setVisibility(View.VISIBLE);
                    } else {
                        viewKycBinding.rvLayout.setVisibility(View.GONE);
                        viewKycBinding.residenceVerificationList.setVisibility(View.GONE);
                    }

                    viewKycBinding.fileName.setText("View");
                    otherKycDocAdapter.setData(rvResponse.getRv());
                    List<Rv> bPhotos = new ArrayList<>();
                    int index = 0;
                    for (String rv : rvResponse.getBusinessPlacePhotos()) {
                        Rv rv1 = new Rv("Business Photo " + (index + 1), rv);
                        bPhotos.add(rv1);
                        index++;
                    }
                    BusinessPhotoesDocAdapter.setData(bPhotos);
                } else {
                    viewKycBinding.bvLayout.setVisibility(View.GONE);
                    viewKycBinding.bpText.setVisibility(View.GONE);
                    viewKycBinding.businessPhotoesList.setVisibility(View.GONE);
                    viewKycBinding.rvLayout.setVisibility(View.GONE);
                    viewKycBinding.residenceVerificationList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AllRvResponse> call, Throwable t) {
            }
        });
    }

    public class KycDocAdapter extends RecyclerView.Adapter<KycDocAdapter.ApplicationListViewHolder> {

        private List<FileDataApplicant> data;

        public KycDocAdapter() {
            data = null;
        }

        @Override
        public ApplicationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.kyc_item_layout,parent,false);
            return new ApplicationListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ApplicationListViewHolder holder, int position) {
            FileDataApplicant fileDataApplicant = data.get(position);
            if(fileDataApplicant.getSubCategory() != null){
                holder.doc_number.setText((position + 1) + ". " +  fileDataApplicant.getSubCategory());
            } else if(fileDataApplicant.getCategory() != null){
                if(fileDataApplicant.getCategory().equalsIgnoreCase("pan")){
                    holder.doc_number.setText((position + 1) + ". " +  fileDataApplicant.getCategory().toUpperCase());
                } else {
                    holder.doc_number.setText((position + 1) + ". " +  fileDataApplicant.getCategory());
                }
            } else {
                holder.doc_number.setText((position+ 1) + ".");
            }
            holder.file_name.setText(fileDataApplicant.getOrigFileName());
        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        class ApplicationListViewHolder extends RecyclerView.ViewHolder {

            TextView doc_number;
            TextView file_name;

            ApplicationListViewHolder(View itemView) {
                super(itemView);
                doc_number = itemView.findViewById(R.id.doc_number);
                file_name = itemView.findViewById(R.id.file_name);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FileDataApplicant fileDataApplicant = data.get(getAdapterPosition());
                        showDataOnBrowser(BuildConfig.IMAGE_BASE_URL + fileDataApplicant.getFileUrl(),fileDataApplicant);
                    }
                });
            }
        }

        public void setData(List<FileDataApplicant> data) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public class OtherKycDocAdapter extends RecyclerView.Adapter<OtherKycDocAdapter.ApplicationListViewHolder> {

        private List<Rv> data;

        public OtherKycDocAdapter() {
            data = null;
        }

        @Override
        public ApplicationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.kyc_item_layout,parent,false);
            return new ApplicationListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ApplicationListViewHolder holder, int position) {
            Rv fileDataApplicant = data.get(position);
            holder.doc_number.setText("Applicant " + fileDataApplicant.getApplicant() + ".");
            holder.file_name.setText("View");
        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        class ApplicationListViewHolder extends RecyclerView.ViewHolder {

            TextView doc_number;
            TextView file_name;

            ApplicationListViewHolder(View itemView) {
                super(itemView);
                doc_number = itemView.findViewById(R.id.doc_number);
                file_name = itemView.findViewById(R.id.file_name);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Rv fileDataApplicant = data.get(getAdapterPosition());
                        showNewDataOnBrowser(BuildConfig.IMAGE_BASE_URL + fileDataApplicant.getUrl(), "Applicant " + fileDataApplicant.getApplicant());
                    }
                });
            }
        }

        public void setData(List<Rv> data) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    public class PhotoKycDocAdapter extends RecyclerView.Adapter<PhotoKycDocAdapter.ApplicationListViewHolder> {

        private List<Rv> data;

        public PhotoKycDocAdapter() {
            data = null;
        }

        @Override
        public ApplicationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.kyc_item_layout,parent,false);
            return new ApplicationListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ApplicationListViewHolder holder, int position) {
            Rv fileDataApplicant = data.get(position);
            holder.doc_number.setText(fileDataApplicant.getApplicant());
            holder.file_name.setText("View");
        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        class ApplicationListViewHolder extends RecyclerView.ViewHolder {

            TextView doc_number;
            TextView file_name;

            ApplicationListViewHolder(View itemView) {
                super(itemView);
                doc_number = itemView.findViewById(R.id.doc_number);
                file_name = itemView.findViewById(R.id.file_name);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Rv fileDataApplicant = data.get(getAdapterPosition());
                        showNewDataOnBrowser(BuildConfig.IMAGE_BASE_URL + fileDataApplicant.getUrl(), fileDataApplicant.getApplicant());
                    }
                });
            }
        }

        public void setData(List<Rv> data) {
            this.data = data;
            notifyDataSetChanged();
        }
    }

    private void showDataOnBrowser(String url,FileDataApplicant fileDataApplicant) {
        if(UIErrorUtils.isNetworkConnected(this)) {
            Bundle kycIntent = new Bundle();
            kycIntent.putString("url", url);
            kycIntent.putString("name", fileDataApplicant.getOrigFileName());
            ViewerActivity.start(this, kycIntent);
        } else {
            showToast(this,getString(R.string.no_internet));
        }
    }

    private void showNewDataOnBrowser(String url,String name) {
        if(UIErrorUtils.isNetworkConnected(this)) {
            Bundle kycIntent = new Bundle();
            kycIntent.putString("url", url);
            kycIntent.putString("name", name);
            ViewerActivity.start(this, kycIntent);
        } else {
            showToast(this,getString(R.string.no_internet));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mMessageReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mMessageReceiver);
        super.onStop();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false);
            if (noConnectivity) {
                if(kycDocAdapter == null || kycDocAdapter.getItemCount() == 0)
                    relative_no_network.setVisibility(View.VISIBLE);
            } else {
                if(kycDocAdapter == null || kycDocAdapter.getItemCount() == 0)
                    showUI();
                relative_no_network.setVisibility(View.GONE);
            }
        }
    };
}
