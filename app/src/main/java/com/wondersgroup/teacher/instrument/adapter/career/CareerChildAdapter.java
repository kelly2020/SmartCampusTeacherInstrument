package com.wondersgroup.teacher.instrument.adapter.career;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wondersgroup.teacher.instrument.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangwentao on18/9/25.
 * Description :职业发展内容适配器
 * Version :1.0
 */
public class CareerChildAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mDatas;
    private Context mContext;


    public CareerChildAdapter(List<String> datas) {
        mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ChildViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_career_child_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ChildViewHolder) {
            ChildViewHolder viewHolder = (ChildViewHolder) holder;
            switch (position){
                case 0:
                    viewHolder.trainTypeView.setText(mContext.getResources().getString(R.string.education_train));
                    viewHolder.childLay.setBackgroundResource(R.mipmap.education_train);
                    break;
                case 1:
                    viewHolder.trainTypeView.setText(mContext.getResources().getString(R.string.teacher_trainment));
                    viewHolder.childLay.setBackgroundResource(R.mipmap.teacher_train);
                    break;
                case 2:
                    viewHolder.trainTypeView.setText(mContext.getResources().getString(R.string.feature_couse));
                    viewHolder.childLay.setBackgroundResource(R.mipmap.feature_couse);
                    break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return (mDatas != null && mDatas.size() > 0) ? mDatas.size() : 0;
    }


    class ChildViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_view_train_type)
        TextView trainTypeView;
        @Bind(R.id.layout_train_child)
        RelativeLayout childLay;

        public ChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
