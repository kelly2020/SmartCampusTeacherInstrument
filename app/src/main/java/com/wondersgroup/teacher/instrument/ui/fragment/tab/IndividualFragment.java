package com.wondersgroup.teacher.instrument.ui.fragment.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wondersgroup.teacher.instrument.R;
import com.wondersgroup.teacher.instrument.ui.activity.SmartHomeActivity;

import butterknife.ButterKnife;

/**
 * Created by zhangwentao on 16/10/31.
 * Description :个人中心
 * Version :1.0
 */
public class IndividualFragment extends Fragment implements View.OnClickListener  {

    private SmartHomeActivity homeActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeActivity = (SmartHomeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_individual_home_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

    }

    @Override
    public void onClick(View view) {

    }
}
