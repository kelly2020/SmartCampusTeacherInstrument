package com.johnson.commonlibs.common_utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.johnson.commonlibs.common_utils.R;

/**
 * Created by zhangwentao on 16/10/31.
 * Description : 水平进度条
 * Version :1.0
 */
public class RoundedHorizontalProgressBar  extends ProgressBar {

    private int mBackgroundColor = Color.GRAY;
    private int mProgressColor = Color.BLUE;

    public RoundedHorizontalProgressBar(Context context) {
        super(context);
        init();
    }

    public RoundedHorizontalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundedHorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init() {
        LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.rounded_progress_bar_horizontal);
        setProgressDrawable(layerDrawable);
        setProgressColors(mBackgroundColor, mProgressColor);
    }

    private void init(Context context, AttributeSet attrs) {
        setUpStyleable(context, attrs);
        LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable(R.drawable.rounded_progress_bar_horizontal);
        setProgressDrawable(layerDrawable);

        setProgressColors(mBackgroundColor, mProgressColor);
    }

    private void setUpStyleable(Context context , AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedHorizontalProgress);

        mBackgroundColor = typedArray.getColor(R.styleable.RoundedHorizontalProgress_backgroundColor, Color.GRAY);
        mProgressColor = typedArray.getColor(R.styleable.RoundedHorizontalProgress_progressColor, Color.BLUE);

        typedArray.recycle();
    }

    public void setProgressColors(int backgroundColor, int progressColor) {
        LayerDrawable layerDrawable = (LayerDrawable) getProgressDrawable();
        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(android.R.id.background);
        gradientDrawable.setColor(backgroundColor);

        ScaleDrawable scaleDrawable = (ScaleDrawable) layerDrawable.findDrawableByLayerId(android.R.id.progress);
        GradientDrawable progressGradientDrawable = (GradientDrawable) scaleDrawable.getDrawable();
        progressGradientDrawable.setColor(progressColor);
        setProgressDrawable(layerDrawable);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    /**
     * Method to animate the progress bar from old value to new Value
     * @param animationDuration Duration of the animation
     * @param from Old value of Progress
     * @param to New value of Progress
     */
    public void animateProgress(int animationDuration, int from, int to) {
        ProgressBarAnimation progressBarAnimation = new ProgressBarAnimation(this, from, to);
        progressBarAnimation.setDuration(animationDuration);
        startAnimation(progressBarAnimation);
    }

    /**
     * Method to animate the progress bar from old value to new Value
     * @param animationDuration Duration of the animation
     * @param to New value of Progress
     */
    public void animateProgress(int animationDuration, int to) {
        ProgressBarAnimation progressBarAnimation = new ProgressBarAnimation(this, getProgress(), to);
        progressBarAnimation.setDuration(animationDuration);
        startAnimation(progressBarAnimation);
    }
}