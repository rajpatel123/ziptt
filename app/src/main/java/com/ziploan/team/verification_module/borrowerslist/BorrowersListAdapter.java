package com.ziploan.team.verification_module.borrowerslist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ziploan.team.R;
import com.ziploan.team.verification_module.borrowerdetails.BorrowerDetailActivity;
import com.ziploan.team.databinding.ItemBorrowerBinding;
import com.ziploan.team.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class BorrowersListAdapter extends RecyclerView.Adapter<BorrowersListAdapter.MyViewHolder> {

    private final Context mContext;
    private List<BorrowersUnverified> mainList;
    private List<BorrowersUnverified> borrowersList;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ItemBorrowerBinding mBinding;

        public MyViewHolder(ItemBorrowerBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            ImageView imageView = binding.getRoot().findViewById(R.id.action_phone);
            ImageView detail_imageIV = binding.getRoot().findViewById(R.id.detail_imageIV);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" +borrowersList.get(getAdapterPosition()).getSm_mobile() ));
                    view.getContext().startActivity(intent);
                }
            });

            detail_imageIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstant.Key.EXTRA_OBJECT,borrowersList.get(getAdapterPosition()));
                    BorrowerDetailActivity.start(mContext,bundle);
                }
            });
        }
    }
    public BorrowersListAdapter(Context context,List<BorrowersUnverified> moviesList) {
        this.mainList = getMainArrayList(moviesList);
        this.borrowersList = moviesList;
        this.mContext = context;
    }

    private List<BorrowersUnverified> getMainArrayList(List<BorrowersUnverified> moviesList) {
        ArrayList<BorrowersUnverified> arrayList = new ArrayList<>();
        for(int i=0;i<moviesList.size();i++){
            arrayList.add(moviesList.get(i));
        }
        return arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemBorrowerBinding binding = ItemBorrowerBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BorrowersUnverified borrower = borrowersList.get(position);
        holder.mBinding.setBorrower(borrower);
    }

    @Override
    public int getItemCount() {
        return borrowersList.size();
    }

    public void filter(String constraint){
        borrowersList.clear();
        for (int i=0;i<mainList.size();i++){
            if(mainList.get(i).getFirst_name().toLowerCase().startsWith(constraint) || mainList.get(i).getBusiness_name().toLowerCase().startsWith(constraint) || mainList.get(i).getLast_name().toLowerCase().startsWith(constraint) || mainList.get(i).getLoan_application_number().toLowerCase().startsWith(constraint)){
                borrowersList.add(mainList.get(i));
            }
        }
        notifyDataSetChanged();
    }
}