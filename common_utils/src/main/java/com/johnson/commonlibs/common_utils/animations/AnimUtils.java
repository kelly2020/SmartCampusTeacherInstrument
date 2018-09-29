package com.johnson.commonlibs.common_utils.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by zhangwt on 15/12/22.<br/>
 *
 * @author zhangwt
 * @version 1.0
 * @date 15/12/22
 */
public class AnimUtils {
    /**
     * 产生旋转动画
     *
     * @param view
     */
    public static void rotation3D(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotationY", 0f, 180f);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.start();
    }

    /**
     * 产生位置移动动画
     *
     * @param view
     */
    public static void translate(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f, 60f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

    }/**
     * 产生位置移动动画
     *
     * @param view
     */
    public static void scale(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.5f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

    }

    /**
     * 产生透明动画
     *
     * @param view
     */
    public static void alphaViewShow(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    public static void alphaAndScaleSets(View view) {
        AnimatorSet animatorSet = new AnimatorSet();

//        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY" , 0.0f , 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1f);

        animatorSet.play(scaleX).with(scaleY);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(500);

        animatorSet.start();
    }

    /**
     * 显示动画
     *
     * @param view
     */
    public static void exposeShow(View view) {
        if (view != null) {
            // get the center for the clipping circle
            int cx = (view.getLeft() + view.getRight()) / 2;
            int cy = (view.getTop() + view.getBottom()) / 2;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(view.getWidth(), view.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
            view.setVisibility(View.VISIBLE);
            anim.start();
        }
    }

    /**
     * 消失动画
     *
     * @param myView
     */
    public static void exposeExit(final View myView) {
        // get the center for the clipping circle
        int cx = (myView.getLeft() + myView.getRight()) / 2;
        int cy = (myView.getTop() + myView.getBottom()) / 2;

        // get the initial radius for the clipping circle
        int initialRadius = myView.getWidth();

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
    }

}
