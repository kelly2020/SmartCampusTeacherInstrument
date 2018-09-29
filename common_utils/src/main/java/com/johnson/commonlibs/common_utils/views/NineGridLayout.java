package com.johnson.commonlibs.common_utils.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhangwt on 15/12/14.<br/>
 *
 * @author zhangwt
 * @version 1.0
 * @date 15/12/14
 */
public class NineGridLayout extends ViewGroup {

    /**
     * 水平方向的间隔
     */
    private int hSpace = 10;
    /**
     * 垂直方向的间隔
     */
    private int vSpace = 10;

    int childWidth;
    int chileHeight;

    public NineGridLayout(Context context) {
        this(context, null);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int screenWidth = MeasureSpec.getSize(widthMeasureSpec);
        int layoutHeight = MeasureSpec.getSize(heightMeasureSpec);

        childWidth = screenWidth - (screenWidth - 2 * hSpace) / 3;
        chileHeight = childWidth;

        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
            LayoutParams params =  child.getLayoutParams();
            params.width = (i % 3) * (childWidth + hSpace);
            params.height = (i / 3) * (chileHeight + vSpace);
        }

        int vw = screenWidth;
        int vh = layoutHeight;

        if (childCount < 3) {
            vw = childCount * (childWidth + hSpace);
        }

        vh = ((childCount + 3) / 3) * (chileHeight + vSpace);
        setMeasuredDimension(vw, vh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            LayoutParams params = child.getLayoutParams();
            child.layout(params.width, params.height, params.width + childWidth, params.height + chileHeight);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new NineGridLayoutParams(getContext(), attrs);
    }

    private class NineGridLayoutParams extends ViewGroup.LayoutParams {
        int top;
        int left;

        public NineGridLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public NineGridLayoutParams(int width, int height) {
            super(width, height);
        }

        public NineGridLayoutParams(LayoutParams source) {
            super(source);
        }
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof NineGridLayoutParams;
    }
}
