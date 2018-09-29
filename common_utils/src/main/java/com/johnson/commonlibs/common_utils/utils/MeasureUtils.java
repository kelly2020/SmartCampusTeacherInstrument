package com.johnson.commonlibs.common_utils.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by zhangwt on 15/12/21.<br/>
 * 测量的工具类
 *
 * @author zhangwt
 * @version 1.0
 * @date 15/12/21
 */
public final class MeasureUtils {

    /**
     * 计算测量的宽
     *
     * @return
     */
    public static int measureViewWidth(int measureWidth) {
        int result = 0;
        int specMode = View.MeasureSpec.getMode(measureWidth);
        int specWidth = View.MeasureSpec.getSize(measureWidth);

        if (specMode == View.MeasureSpec.EXACTLY) {//match_parent跟给定数值的时候
            result = specWidth;
        } else {//wrap_content;这个时候要求我们自己计算
            //TODO 根据view自己来
        }
        return result;
    }

    /**
     * 测量屏幕的宽
     * @param context 上下文
     * @return
     */
    public static int measureScreenWidth(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }

    /**
     * 测量屏幕的高
     * @param context
     * @return
     */
    public static int measureScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return height;
    }

    /**
     * 获取状态栏的高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
