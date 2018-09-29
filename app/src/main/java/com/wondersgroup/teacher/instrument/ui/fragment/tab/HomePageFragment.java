package com.wondersgroup.teacher.instrument.ui.fragment.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wondersgroup.teacher.instrument.R;
import com.wondersgroup.teacher.instrument.adapter.home.HomeInstrumentAdapter;
import com.wondersgroup.teacher.instrument.ui.activity.SmartHomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhangwentao on 16/10/31.
 * Description : 首页
 * Version :1.0
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.recycler_view_home_page)
    RecyclerView recyclerView;

    private HomeInstrumentAdapter adapter;

    private SmartHomeActivity homeActivity;

    private Context mContext;
    private List<String> mData;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        homeActivity = (SmartHomeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mContext = view.getContext();

        initView();

    }

    private void initView() {
        if (mContext != null) {

            if (mData == null){
                mData = new ArrayList<>();
            }

            for (int i =0;i < 6;i++){
                mData.add("");
            }

            recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

                //分割线颜色
                @Override
                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    super.onDraw(c, parent, state);
                    c.drawColor(getResources().getColor(R.color.text_input_focus));
                }

                //绘制前景，所谓前景就是相当于在你的RecyclerView上面加上一层，他不会影响你的控件的使用，我们也可以绘制图片图形之类的
                @Override
                public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                    super.onDrawOver(c, parent, state);
                }

//               设置分割线，我们可以在这里设置分割线的宽高
//                这里的左上右下参数，如果是list的话那么左右设置为0，如果是Grid或者是瀑布流这样设置的话就不会有左右分割线。

                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    //参数 分割线的间距（左上右下）
                    outRect.set(0,0,1,1);

                }
            });



            adapter = new HomeInstrumentAdapter(mData);
            recyclerView.setAdapter(adapter);
        }


    }


    @Override
    public void onClick(View view) {

    }
}
