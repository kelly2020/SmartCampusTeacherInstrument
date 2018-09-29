package com.wondersgroup.teacher.instrument.ui.fragment.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wondersgroup.teacher.instrument.R;
import com.wondersgroup.teacher.instrument.adapter.career.CareerDevelopAdapter;
import com.wondersgroup.teacher.instrument.ui.activity.SmartHomeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangwentao on 18/9/25.
 * Description :职业发展
 * Version :1.0
 */
public class CareerDevelopmentFragment extends Fragment {
    @Bind(R.id.recycler_view_career)
    RecyclerView mRecyclerView;

    private SmartHomeActivity homeActivity;

    private Context mContext;

    private Map<String,List<String>> map;

    private CareerDevelopAdapter adapter;

    private List<String> mDatas;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeActivity = (SmartHomeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_career_development, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = view.getContext();
        ButterKnife.bind(this, view);

        initView();


    }

    private void initView() {

        if (mDatas == null){
            mDatas = new ArrayList<>();
        }

        if (map == null){
            map = new HashMap<>();
        }
        mDatas.add("");
        mDatas.add("");
        mDatas.add("");

        map.put("key",mDatas);
        if (mContext != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            adapter = new CareerDevelopAdapter(map);

            mRecyclerView.setAdapter(adapter);
        }
    }

}
