package com.johnson.commonlibs.common_utils.views;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by zhangwentao on 16/5/9.
 * Description : popUpWindow
 * Version :1.0
 */
public class MenuShowPopWindow extends PopupWindow {
    private Context mContext;

    public MenuShowPopWindow(Context context) {
        mContext = context;
    }

    public void initPopWindow(View view, int animationStyle) {
        setContentView(view);
        //设置宽度
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置高度
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置显示隐藏动画
        setAnimationStyle(animationStyle);

        setOutsideTouchable(true);
        setFocusable(false);

        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }

    public void initPopWindow(View view, int animationStyle,int width,int height) {
        setContentView(view);
        //设置宽度
        setWidth(width);
        //设置高度
        setHeight(height);
        //设置显示隐藏动画
        setAnimationStyle(animationStyle);

        setOutsideTouchable(true);
        setFocusable(false);

        //设置背景透明
        setBackgroundDrawable(new ColorDrawable(0));
    }

}
