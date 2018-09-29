package com.johnson.commonlibs.common_utils.views;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;

/**
 * Created by zhangwentao on 16/10/31.
 * Description : progressDialog 背景view
 * Version :1.0
 */
public class BackgroundView {
    public static Drawable setBackground(View v, int color, int borderColor, int r, int borderStroke) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { r, r, r, r, r, r, r, r });
        shape.setColor(color);
        shape.setStroke(borderStroke, borderColor);
        return shape;
    }

    public int createTransparentColor(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        Log.v("color", String.valueOf(Color.argb(alpha, red, green, blue)));
        return Color.argb(alpha, red, green, blue);
    }

}
