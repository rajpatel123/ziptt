package com.ziploan.team.verification_module.borrowerdetails.credit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ziploan.team.R;
import com.ziploan.team.databinding.CreditLayoutBinding;
import com.ziploan.team.utils.AppConstant;
import com.ziploan.team.verification_module.base.BaseFragment;
import com.ziploan.team.webapi.APIExecutor;
import com.ziploan.team.webapi.cibil.CibilLiveDatum;
import com.ziploan.team.webapi.cibil.CibilResponse;
import com.ziploan.team.webapi.cibil.ReqCibilDatum;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreditFragment extends BaseFragment {
    private CreditLayoutBinding binding;
    private RecyclerView cibil_recycle_view;
    private RecyclerView sec_cibil_recycle_view;
    private CibilResponse cibilResponse;
    private TextView bank_name;
    private TextView holder_name;
    private TextView total_debit;
    private TextView total_credit;
    private TextView total_bounce;
    private TextView total_cash_deposit;
    private TextView sec_bank_name;
    private TextView sec_holder_name;

    private TextView sec_total_debit;
    private TextView sec_total_credit;
    private TextView sec_total_bounce;
    private TextView sec_cash_deposite;
    private TextView secondary_bank_title;
    private TableLayout secondary_table;
    private TextView sec_cbil_info_title;
    private MyAdapter mAdapter;
    private MyAdapter1 mAdapter1;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLayoutManager1;


    public static CreditFragment newInstance(String id) {
        CreditFragment creditFragment = new CreditFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.Key.LOAN_REQUEST_ID, id);
        creditFragment.setArguments(bundle);
        return creditFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreditLayoutBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cibil_recycle_view = view.findViewById(R.id.cibil_recycle_view);
        sec_cibil_recycle_view = view.findViewById(R.id.sec_cibil_recycle_view);
        secondary_bank_title = view.findViewById(R.id.secondary_bank_title);
        secondary_table = view.findViewById(R.id.secondary_table);
        sec_cbil_info_title = view.findViewById(R.id.sec_cbil_info_title);
        bank_name  = view.findViewById(R.id.bank_name);
        holder_name  = view.findViewById(R.id.holder_name);
        total_debit  = view.findViewById(R.id.total_debit);
        total_credit  = view.findViewById(R.id.total_credit);
        total_bounce  = view.findViewById(R.id.total_bounce);
        total_cash_deposit  = view.findViewById(R.id.total_cash_deposit);
        sec_bank_name  = view.findViewById(R.id.sec_bank_name);
        sec_holder_name  = view.findViewById(R.id.sec_holder_name);
        sec_total_debit  = view.findViewById(R.id.sec_total_debit);
        sec_total_credit  = view.findViewById(R.id.sec_total_credit);
        sec_total_bounce  = view.findViewById(R.id.sec_total_bounce);
        sec_cash_deposite  = view.findViewById(R.id.sec_cash_deposite);
        getBankInfo();

        mAdapter = new MyAdapter();
        mAdapter1 = new MyAdapter1();
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        cibil_recycle_view.setHasFixedSize(true);
        cibil_recycle_view.setLayoutManager(mLayoutManager);
        cibil_recycle_view.setAdapter(mAdapter);

        sec_cibil_recycle_view.setHasFixedSize(true);
        sec_cibil_recycle_view.setLayoutManager(mLayoutManager1);
        sec_cibil_recycle_view.setAdapter(mAdapter1);

    }

    private void getBankInfo() {
        //showProgressDialog(getActivity(), "Please wait...");
        Call<CibilResponse> call = APIExecutor.getAPIServiceSerializeNull(mContext)
                .getBankInfo(getArguments().getString(AppConstant.Key.LOAN_REQUEST_ID));
        call.enqueue(new Callback<CibilResponse>() {
            @Override
            public void onResponse(Call<CibilResponse> call, Response<CibilResponse> response) {
                hideProgressDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    cibilResponse = response.body();
                    if(cibilResponse.getResponse() != null) {
                        if (cibilResponse.getResponse().getScannedData() != null
                                && cibilResponse.getResponse().getScannedData().size() > 0) {
                            bank_name.setText(cibilResponse.getResponse().getScannedData().get(0).getBankName());
                            holder_name.setText(cibilResponse.getResponse().getScannedData().get(0).getAccountHolderName());
                            total_debit.setText(cibilResponse.getResponse().getScannedData().get(0).getTotalDebit());
                            total_credit.setText(cibilResponse.getResponse().getScannedData().get(0).getTotalCredit());
                            total_bounce.setText(cibilResponse.getResponse().getScannedData().get(0).getTotalOutwBounce());
                            total_cash_deposit.setText(cibilResponse.getResponse().getScannedData().get(0).getTotalCashDeposit());

                            if (cibilResponse.getResponse().getScannedData().size() > 1) {
                                secondary_bank_title.setVisibility(View.VISIBLE);
                                secondary_table.setVisibility(View.VISIBLE);
                                sec_bank_name.setText(cibilResponse.getResponse().getScannedData().get(1).getBankName());
                                sec_holder_name.setText(cibilResponse.getResponse().getScannedData().get(1).getAccountHolderName());
                                sec_total_debit.setText(cibilResponse.getResponse().getScannedData().get(1).getTotalDebit());
                                sec_total_credit.setText(cibilResponse.getResponse().getScannedData().get(1).getTotalCredit());
                                sec_total_bounce.setText(cibilResponse.getResponse().getScannedData().get(1).getTotalOutwBounce());
                                sec_cash_deposite.setText(cibilResponse.getResponse().getScannedData().get(1).getTotalCashDeposit());
                            }
                        }
                        if(cibilResponse.getResponse().getReqCibilData() != null) {
                            for (ReqCibilDatum reqCibilDatum : cibilResponse.getResponse().getReqCibilData()) {
                                if (reqCibilDatum.getApplicantType().equals(1)) {
                                    mAdapter.setData(cibilResponse.getResponse().getReqCibilData().get(0).getCibilLiveData());
                                } else if (reqCibilDatum.getApplicantType().equals(2)) {
                                    sec_cbil_info_title.setVisibility(View.VISIBLE);
                                    mAdapter1.setData(cibilResponse.getResponse().getReqCibilData().get(1).getCibilLiveData());
                                }
                            }
                        }
                    }

                } else {
                    checkTokenValidity(response);
                }
            }

            @Override
            public void onFailure(Call<CibilResponse> call, Throwable t) {
                t.printStackTrace();
                hideProgressDialog();
            }
        });
    }

    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<CibilLiveDatum> mDataset;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView loan_name;
            public TextView loan_type;
            public TextView sanctioned_amount;
            public TextView current_balance;
            public TextView date_opened;
            public TextView date_reported;
            public TextView emi;

            public ViewHolder(View v) {
                super(v);
                loan_name = v.findViewById(R.id.loan_name);
                loan_type = v.findViewById(R.id.loan_type);
                sanctioned_amount = v.findViewById(R.id.sanctioned_amount);
                current_balance = v.findViewById(R.id.current_balance);
                date_opened = v.findViewById(R.id.date_opened);
                date_reported = v.findViewById(R.id.date_reported);
                emi = v.findViewById(R.id.emi);
            }
        }

        public void setData(List<CibilLiveDatum> dataset){
            this.mDataset = dataset;
            notifyDataSetChanged();
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cibil_info_item_layout, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.loan_type.setText(mDataset.get(position).getLoanType());
            holder.loan_name.setText(mDataset.get(position).getAccountType());
            holder.current_balance.setText(mDataset.get(position).getCurrentbalance());
            holder.date_opened.setText(mDataset.get(position).getDateopeneddisbursed().getDate() + "");
            holder.date_reported.setText(mDataset.get(position).getDatereportedcertified().getDate() + "");
            holder.emi.setText(mDataset.get(position).getEmiFromatted());
            holder.sanctioned_amount.setText(mDataset.get(position).getHighcreditsanctionedamount());
        }

        @Override
        public int getItemCount() {
            return mDataset != null ? mDataset.size() : 0;
        }
    }

    static class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.ViewHolder> {
        private List<CibilLiveDatum> mDataset;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView loan_name;
            public TextView loan_type;
            public TextView sanctioned_amount;
            public TextView current_balance;
            public TextView date_opened;
            public TextView date_reported;
            public TextView emi;

            public ViewHolder(View v) {
                super(v);
                loan_name = v.findViewById(R.id.loan_name);
                loan_type = v.findViewById(R.id.loan_type);
                sanctioned_amount = v.findViewById(R.id.sanctioned_amount);
                current_balance = v.findViewById(R.id.current_balance);
                date_opened = v.findViewById(R.id.date_opened);
                date_reported = v.findViewById(R.id.date_reported);
                emi = v.findViewById(R.id.emi);
            }
        }

        public void setData(List<CibilLiveDatum> dataset){
            this.mDataset = dataset;
            notifyDataSetChanged();
        }

        @Override
        public MyAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cibil_info_item_layout, parent, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.loan_type.setText(mDataset.get(position).getLoanType());
            holder.loan_name.setText(mDataset.get(position).getAccountType());
            holder.current_balance.setText(mDataset.get(position).getCurrentbalance());
            holder.date_opened.setText(mDataset.get(position).getDateopeneddisbursed().getDate() + "");
            holder.date_reported.setText(mDataset.get(position).getDatereportedcertified().getDate() + "");
            holder.emi.setText(mDataset.get(position).getEmi() + "");
            holder.sanctioned_amount.setText(mDataset.get(position).getHighcreditsanctionedamount());
        }

        @Override
        public int getItemCount() {
            return mDataset != null ? mDataset.size() : 0;
        }
    }

    public CibilResponse getCibilResponse() {
        return cibilResponse;
    }
}
