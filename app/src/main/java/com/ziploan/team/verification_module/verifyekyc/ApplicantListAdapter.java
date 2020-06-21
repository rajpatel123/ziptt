package com.ziploan.team.verification_module.verifyekyc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ziploan.team.databinding.ItemApplicantBinding;
import com.ziploan.team.utils.ZiploanUtil;
import com.ziploan.team.webapi.EkycDetail;

import java.util.ArrayList;
import java.util.List;

public class ApplicantListAdapter extends RecyclerView.Adapter<ApplicantListAdapter.MyViewHolder> {

    private final Context mContext;
    private List<EkycDetail> mainList;
    private List<EkycDetail> applicantsList;
    private AdapterView.OnItemClickListener onItemClickListener;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemApplicantBinding mBinding;

        public MyViewHolder(ItemApplicantBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            binding.tvVerifyEkyc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(null,null,getAdapterPosition(),0);
                }
            });
        }
    }
    public ApplicantListAdapter(Context context, List<EkycDetail> moviesList) {
        this.mainList = getMainArrayList(moviesList);
        this.applicantsList = moviesList;
        this.mContext = context;
        this.onItemClickListener = (AdapterView.OnItemClickListener) mContext;
    }

    private List<EkycDetail> getMainArrayList(List<EkycDetail> moviesList) {
        ArrayList<EkycDetail> arrayList = new ArrayList<>();
        for(int i=0;i<moviesList.size();i++){
            arrayList.add(moviesList.get(i));
        }
        return arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemApplicantBinding binding = ItemApplicantBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EkycDetail applicant = applicantsList.get(position);
        holder.mBinding.tvUserName.setText(ZiploanUtil.capitalize(applicant.getApplicant_name()));
        holder.mBinding.tvUserNo.setText(getNoLabel(position));
        if(applicant.getStatus()!=null && applicant.getStatus().equalsIgnoreCase("Verified successfully.")){
            holder.mBinding.tvUserStatusVerified.setVisibility(View.VISIBLE);
            holder.mBinding.tvVerifyEkyc.setVisibility(View.GONE);
        }else {
            holder.mBinding.tvUserStatusVerified.setVisibility(View.GONE);
            holder.mBinding.tvVerifyEkyc.setVisibility(View.VISIBLE);
        }
    }

    private String getNoLabel(int position) {
        switch (position){
            case 0:
                return "First Applicant";
            case 1:
                return "Second Applicant";
            case 2:
                return "Third Applicant";
            default:
                break;
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return applicantsList.size();
    }
}