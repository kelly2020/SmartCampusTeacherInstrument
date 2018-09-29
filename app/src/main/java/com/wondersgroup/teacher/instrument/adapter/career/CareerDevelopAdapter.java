package com.wondersgroup.teacher.instrument.adapter.career;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wondersgroup.teacher.instrument.R;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangwentao on 18/9/25.
 * Description :职业发展适配器
 * Version :1.0
 */
public class CareerDevelopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Map<String, List<String>> mMap;

    private Context mContext;

    public CareerDevelopAdapter(Map<String, List<String>> map) {
        mMap = map;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new CareerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_career_develop_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CareerViewHolder) {
            CareerViewHolder viewHolder = (CareerViewHolder) holder;

            List<String> datas = mMap.get("key");
            CareerChildAdapter adapter = new CareerChildAdapter(datas);
            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            viewHolder.recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public int getItemCount() {
        return (mMap != null && mMap.size() > 0) ? mMap.size() : 0;
    }


    class CareerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.recycler_view_career_develop)
        RecyclerView recyclerView;

        public CareerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }
}
