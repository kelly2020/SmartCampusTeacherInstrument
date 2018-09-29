package com.wondersgroup.teacher.instrument.ui.fragment.tab;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.wondersgroup.teacher.instrument.R;
import com.wondersgroup.teacher.instrument.adapter.service.ServiceAdapter;
import com.wondersgroup.teacher.instrument.ui.activity.SmartHomeActivity;
import com.wondersgroup.teacher.instrument.ui.view.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangwentao on 18/9/25.
 * Description :增值服务
 * Version :1.0
 */
public class AppreciationServiceFragment extends Fragment {
    @Bind(R.id.recycler_view_refresh)
    RefreshRecyclerView recyclerView;

    private ServiceAdapter adapter;
    private SmartHomeActivity homeActivity;


    private List<String> mData;

    Handler handler;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeActivity = (SmartHomeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appreciation_service, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = view.getContext();
        ButterKnife.bind(this, view);
        handler = new Handler();

        initView();
        initListener();

    }

    private void initView() {
        if (mContext != null) {
            recyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light,
                    android.R.color.holo_green_light, android.R.color.holo_blue_dark);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));



            if (mData == null){
                mData = new ArrayList<>();
            }

            for (int i =0;i < 3;i++){
                mData.add("");
            }
            adapter = new ServiceAdapter(mData);
            recyclerView.setAdapter(adapter);
        }


    }


    private void initListener() {
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            dismissRefresh();

//                isShowProgress = false;
            }


        });

        recyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                recyclerView.hideMoreProgress();
                recyclerView.setRefreshing(false);
            }
        }, 2);
    }

    private void dismissRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.hideMoreProgress();
                recyclerView.setRefreshing(false);
            }
        }, 2000);
    }
}
