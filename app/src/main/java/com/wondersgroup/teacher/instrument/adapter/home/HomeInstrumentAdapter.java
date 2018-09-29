package com.wondersgroup.teacher.instrument.adapter.home;

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
 * Created by zhangwentao on 18/9/21
 * Description :首页适配
 * Version :1.0
 */
public class HomeInstrumentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mData;

    public HomeInstrumentAdapter(List<String> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstrumentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_instrument, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InstrumentViewHolder) {
            InstrumentViewHolder viewHolder = (InstrumentViewHolder) holder;

            switch (position) {
                case 0:
                    viewHolder.homeImage.setImageResource(R.mipmap.net_apply);
                    viewHolder.homeView.setText(R.string.net_apply);
                    break;
                case 1:
                    viewHolder.homeImage.setImageResource(R.mipmap.inform_search);
                    viewHolder.homeView.setText(R.string.inform_search);
                    break;
                case 2:
                    viewHolder.homeImage.setImageResource(R.mipmap.compile_exam);
                    viewHolder.homeView.setText(R.string.compile_exam);
                    break;
                case 3:
                    viewHolder.homeImage.setImageResource(R.mipmap.check);
                    viewHolder.homeView.setText(R.string.check);
                    break;
                case 4:
                    viewHolder.homeImage.setImageResource(R.mipmap.education_consult);
                    viewHolder.homeView.setText(R.string.education_consult);
                    break;
                case 5:
                    viewHolder.homeImage.setImageResource(R.mipmap.declare);
                    viewHolder.homeView.setText(R.string.declare);
                    break;
            }


        }
    }

    @Override
    public int getItemCount() {
        return (mData != null && mData.size() > 0) ? mData.size() : 0;
    }

    class InstrumentViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image_view_home)
        ImageView homeImage;
        @Bind(R.id.text_view_home)
        TextView homeView;


        public InstrumentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
