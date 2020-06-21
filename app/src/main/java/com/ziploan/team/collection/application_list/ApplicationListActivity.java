//package com.ziploan.team.collection.application_list;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.databinding.ViewDataBinding;
//import android.net.ConnectivityManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.app.ActionBar;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.text.format.DateUtils;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.ziploan.team.App;
//import com.ziploan.team.BuildConfig;
//import com.ziploan.team.GPSTracker;
//import com.ziploan.team.R;
//import com.ziploan.team.collection.model.app_list.ApplicationListModel;
//import com.ziploan.team.collection.model.app_list.Result;
//import com.ziploan.team.collection.model.bank_names.BankNameModel;
//import com.ziploan.team.collection.service.GetBankListJob;
//import com.ziploan.team.collection.service.PostRecordVisitJob;
//import com.ziploan.team.collection.utils.UIErrorUtils;
//import com.ziploan.team.databinding.ApplicationItemLayoutBinding;
//import com.ziploan.team.databinding.CollectionApplicationListActivityBinding;
//import com.ziploan.team.utils.AppConstant;
//import com.ziploan.team.utils.EndlessRecyclerViewScrollListener;
//import com.ziploan.team.utils.ZiploanSPUtils;
//import com.ziploan.team.verification_module.base.BaseActivity;
//import com.ziploan.team.verification_module.borrowerslist.SimpleTextChangeLister;
//import com.ziploan.team.verification_module.caching.DatabaseManger;
//import com.ziploan.team.webapi.APIExecutor;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.ResponseData;
//
//public class ApplicationListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
//
//    private EndlessRecyclerViewScrollListener scrollListener;
//    private CollectionApplicationListActivityBinding allViews;
//    private ArrayList<Result> arrApplicationList = new ArrayList<>();
//    private ApplicationListAdapter mAdapter;
//    private static final int RECORD_COUNT = 10;
//    private ApplicationListModel loanListResponse;
//    private String query;
//    private boolean doubleBackToExitPressedOnce;
//    private FrameLayout relative_no_network;
//
//    public static void start(Context mContext, Bundle bundle) {
//        Intent intent = new Intent(mContext, ApplicationListActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//        intent.putExtras(bundle);
//        mContext.startActivity(intent);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.AppThemeDark);
//        super.onCreate(savedInstanceState);
//        App.activityListVisible = true;
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setDisplayHomeAsUpEnabled(true);
//        actionbar.setHomeAsUpIndicator(R.drawable.collection_logout);
//        setTitle(getString(R.string.collection_home));
//        registerReceiver(mMessageReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//
//        if(ZiploanSPUtils.getInstance(this).getBankSaved()){
//            if(App.refreshBankNames){
//                GetBankListJob.runJobImmediately();
//            } else
//                getBankData();
//        } else {
//            if(UIErrorUtils.isNetworkConnected(this))
//                GetBankListJob.runJobImmediately();
//        }
//        getLocations();
//    }
//
//    private void getLocations(){
//        new GPSTracker(this);
//    }
//
//    public void getBankData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                App.BankData = DatabaseManger.getInstance().getBankListFromDb();
//            }
//        }).start();
//    }
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.filter_menu, menu);
////        return super.onCreateOptionsMenu(menu);
////    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                displayLogoutAlert(getResources().getString(R.string.want_to_logout));
//                return true;
////            case R.id.filter:
////                FilterActivity.start(this,new Bundle());
////                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        App.activityListVisible = true;
////        reset();
////        loadDataOffline();
////        if(UIErrorUtils.isNetworkConnected(this))
////            loadApplicationList(0);
//    }
//
//    private void loadDataOffline() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final ArrayList<Result> results = DatabaseManger.getInstance().getCollectionListDataFromDb();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (results != null && results.size() > 0) {
//                            loanListResponse = new ApplicationListModel();
//                            try {
//                                loanListResponse.setResults(results);
//                                loanListResponse.setTotal(Long.parseLong(results.size() + ""));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            setDataToView(results);
//                            relative_no_network.setVisibility(View.GONE);
//                        }
//                    }
//                });
//            }
//        }).start();
//    }
//
//    @Override
//    public void onRefresh() {
//        if(UIErrorUtils.isNetworkConnected(this)) {
//            allViews.swipeLayout.setRefreshing(true);
//            reset();
//            loadApplicationList(0);
//        } else {
//            allViews.swipeLayout.setRefreshing(false);
//        }
//    }
//
//    private void reset(){
//        if(arrApplicationList != null)
//            arrApplicationList.clear();
//        if(mAdapter != null)
//            mAdapter.clear();
//        scrollListener.resetState();
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.collection_application_list_activity;
//    }
//
//    @Override
//    protected void onViewMapped(ViewDataBinding views) {
//        allViews = (CollectionApplicationListActivityBinding) views;
//        relative_no_network = findViewById(R.id.relative_no_network);
//        setListeners();
//        mAdapter = new ApplicationListAdapter();
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        allViews.rvApplicationList.setLayoutManager(mLayoutManager);
//        allViews.rvApplicationList.setItemAnimator(new DefaultItemAnimator());
//        allViews.rvApplicationList.setAdapter(mAdapter);
//
//        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) allViews.rvApplicationList.getLayoutManager()) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                if(loanListResponse != null
//                        && loanListResponse.getTotal() >= page) {
//                    if (UIErrorUtils.isNetworkConnected(ApplicationListActivity.this))
//                        loadApplicationList(page);
//                }
//            }
//        };
//
//        allViews.rvApplicationList.addOnScrollListener(scrollListener);
//        allViews.search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    if(v.getText().length() > 3) {
//                        query = v.getText().toString();
//                        search(v.getText().toString());
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
//        allViews.search.addTextChangedListener(new TextWatcher() {
//            boolean isOnTextChanged = false;
//            int i1 = 0;
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                isOnTextChanged = true;
//                this.i1 = i1;
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (isOnTextChanged) {
//                    isOnTextChanged = false;
//                    if(editable.toString().length() == 0 && i1 > 0){
//                        resetSearch();
//                    }
//                }
//            }
//        });
//        checkNextDay();
////        if(UIErrorUtils.isNetworkConnected(this))
////            loadApplicationList(0);
//    }
//
//    private void resetSearch(){
//        UIErrorUtils.hideSoftKeyboard(this);
//        query = null;
//        reset();
//        if(UIErrorUtils.isNetworkConnected(this))
//            loadApplicationList(0);
//        else
//            loadDataOffline();
//    }
//
//    private void search(String s) {
//        UIErrorUtils.hideSoftKeyboard(this);
//        searchList(s);
//    }
//
//    private void setDataToView(List<Result> _data) {
//        if(_data == null){
//            if(mAdapter != null){
//                if(mAdapter.data == null || mAdapter.data.size() == 0 ){
//                    allViews.rvApplicationList.setVisibility(View.GONE);
//                    allViews.noApplication.setVisibility(View.VISIBLE);
//                }
//            } else {
//                allViews.rvApplicationList.setVisibility(View.GONE);
//                allViews.noApplication.setVisibility(View.VISIBLE);
//            }
//        } else {
//            allViews.rvApplicationList.setVisibility(View.VISIBLE);
//            allViews.noApplication.setVisibility(View.GONE);
//
//            if (arrApplicationList != null) {
//                arrApplicationList.addAll(_data);
//                if (mAdapter != null)
//                    mAdapter.addAllData(_data);
//            }
//        }
//    }
//
//    public class ApplicationListAdapter extends RecyclerView.Adapter {
//
//        private List<Result> data;
//
//        public ApplicationListAdapter(){
//            data = null;
//        }
//
//        public void setData(List<Result> data) {
//            this.data = data;
//            notifyDataSetChanged();
//        }
//
//        public void addData(Result result) {
//            if (data == null)
//                data = new ArrayList<>();
//
//            data.add(result);
//            notifyItemInserted(0);
//        }
//
//        public void addAllData(List<Result> _data) {
//            if (data == null)
//                data = new ArrayList<>();
//
//            data.addAll(_data);
//            notifyDataSetChanged();
//        }
//
//        public void clear() {
//            if (data != null)
//                data.clear();
//            notifyDataSetChanged();
//        }
//
//        class ApplicationListViewHolder extends RecyclerView.ViewHolder {
//
//            private ApplicationItemLayoutBinding itemView;
//
//            ApplicationListViewHolder(ApplicationItemLayoutBinding itemView) {
//                super(itemView.getRoot());
//                this.itemView = itemView;
//            }
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            ApplicationItemLayoutBinding binding = ApplicationItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//            return new ApplicationListViewHolder(binding);
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//            final ApplicationListViewHolder bindingViewholder = (ApplicationListViewHolder) holder;
//            Result result = data.get(position);
//            bindingViewholder.itemView.setModel(result);
//            bindingViewholder.itemView.executePendingBindings();
//            bindingViewholder.itemView.getRoot().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable(AppConstant.Key.LOAN_REQUEST_DATA,data.get(bindingViewholder.getAdapterPosition()));
//                    ApplicationDetails.start(mContext,bundle);
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return data != null ? data.size() : 0;
//        }
//
//        public void filter(String constraint) {
////            if (data != null) {
////                data.clear();
////                for (int i = 0; i < arrApplicationList.size(); i++) {
////                    if (arrApplicationList.get(i).getBusinessName().toLowerCase().startsWith(constraint)
////                            || arrApplicationList.get(i).getLoanApplicationNumber().toLowerCase().startsWith(constraint)
////                            || arrApplicationList.get(i).getApplicantName().toLowerCase().startsWith(constraint)) {
////                        data.add(arrApplicationList.get(i));
////                    }
////                }
////                notifyDataSetChanged();
////            }
//        }
//    }
//
//    private void setListeners() {
//        allViews.search.addTextChangedListener(new SimpleTextChangeLister() {
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (mAdapter != null)
//                    mAdapter.filter(charSequence.toString().toLowerCase());
//            }
//        });
//        allViews.swipeLayout.setOnRefreshListener(this);
//    }
//
//    private void loadApplicationList(final int page) {
//        showProgressDialog();
//        Call<ApplicationListModel> call;
//        if (!TextUtils.isEmpty(query))
//            call = APIExecutor.getAPIService(mContext).searchApplication(query,page, AppConstant.Key.VIEW_VALUE);
//        else
//            call = APIExecutor.getAPIService(mContext).getCollectionApplicationList(RECORD_COUNT, page,AppConstant.Key.VIEW_VALUE);
//        call.enqueue(new Callback<ApplicationListModel>() {
//            @Override
//            public void onResponse(Call<ApplicationListModel> call, ResponseData<ApplicationListModel> response) {
//                allViews.swipeLayout.setRefreshing(false);
//                hideProgressDialog();
//                if (response != null && response.isSuccessful() && response.body() != null && response.body().getResults() != null && response.body().getResults().size() > 0) {
//                    loanListResponse = response.body();
//                    if(page == 0 && TextUtils.isEmpty(query)){
//                        deleteListFromDB();
//                        reset();
//                        setDataToView(response.body().getResults());
//                    } else {
//                        setDataToView(response.body().getResults());
//                    }
//                    if(TextUtils.isEmpty(query)) {
//                        DatabaseManger.getInstance().saveCollectionApplicationList(loanListResponse.getResults());
//                        ZiploanSPUtils.getInstance(ApplicationListActivity.this).setCollectionSavedDate(new Date().getTime());
//                    }
//                } else {
//                    checkTokenValidity(response);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApplicationListModel> call, Throwable t) {
//                hideProgressDialog();
//                allViews.swipeLayout.setRefreshing(false);
//                setDataToView(null);
//            }
//        });
//    }
//
//    private void deleteListFromDB() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                DatabaseManger.getInstance().deleteCollectionAppListData();
//            }
//        }).start();
//    }
//
//
//    private void searchList(String query) {
//        showProgressDialog();
//        Call<ApplicationListModel> call = APIExecutor.getAPIService(mContext).searchApplication(query,0, AppConstant.Key.VIEW_VALUE);
//        call.enqueue(new Callback<ApplicationListModel>() {
//            @Override
//            public void onResponse(Call<ApplicationListModel> call, ResponseData<ApplicationListModel> response) {
//                allViews.swipeLayout.setRefreshing(false);
//                hideProgressDialog();
//                if (response != null && response.isSuccessful() && response.body() != null) {
//                    if(response.body().getResults() != null && response.body().getResults().size() > 0){
//                        loanListResponse = response.body();
//                        reset();
//                        setDataToView(response.body().getResults());
//                    } else {
//                        allViews.rvApplicationList.setVisibility(View.GONE);
//                        allViews.noApplication.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    allViews.rvApplicationList.setVisibility(View.GONE);
//                    allViews.noApplication.setVisibility(View.VISIBLE);
//                    checkTokenValidity(response);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApplicationListModel> call, Throwable t) {
//                hideProgressDialog();
//                allViews.swipeLayout.setRefreshing(false);
//                setDataToView(null);
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        unregisterReceiver(mMessageReceiver);
//        super.onDestroy();
//    }
//
//    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager
//                    .EXTRA_NO_CONNECTIVITY, false);
//            if (!noConnectivity) {
//                if(App.activityListVisible) {
//                    reset();
//                    loadApplicationList(0);
//                    relative_no_network.setVisibility(View.GONE);
//                }
//                PostRecordVisitJob.scheduleAdvancedJob();
//            } else {
////                if (loanListResponse == null)
//                    relative_no_network.setVisibility(View.VISIBLE);
//            }
//        }
//    };
//
//    private void checkNextDay(){
//        long savedDay = ZiploanSPUtils.getInstance(this).getCollectionSavedDate();
//        boolean isSameDay = DateUtils.isToday(savedDay);
//        if(!isSameDay){
//            deleteListFromDB();
//        } else {
//            loadDataOffline();
//        }
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, getString(R.string.press_exit), Toast.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
//    }
//
//    @Override
//    protected void onStop() {
//        App.activityListVisible = false;
//        super.onStop();
//    }
//}
