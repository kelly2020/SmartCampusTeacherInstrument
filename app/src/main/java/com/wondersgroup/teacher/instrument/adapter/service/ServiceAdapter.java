package com.wondersgroup.teacher.instrument.adapter.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wondersgroup.teacher.instrument.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangwentao on 16/10/31.
 * Description :
 * Version :
 */
public class ServiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mData;
    private Context mContext;

    public ServiceAdapter(List<String> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ServiceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ServiceViewHolder) {
            ServiceViewHolder viewHolder = (ServiceViewHolder) holder;

            switch (position) {
                case 0:
                    viewHolder.serviceView.setText(mContext.getResources().getString(R.string.insurance_service));
                    viewHolder.serviceImg.setImageResource(R.mipmap.insurance_service);
                    break;
                case 1:
                    viewHolder.serviceView.setText(mContext.getResources().getString(R.string.medical_service));
                    viewHolder.serviceImg.setImageResource(R.mipmap.medical_service);
                    break;
                case 2:
                    viewHolder.serviceView.setText(mContext.getResources().getString(R.string.training_service));
                    viewHolder.serviceImg.setImageResource(R.mipmap.train_service);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return (mData != null && mData.size() > 0) ? mData.size() : 0;
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_view_servie)
        TextView serviceView;
        @Bind(R.id.image_view_servie)
        ImageView serviceImg;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
