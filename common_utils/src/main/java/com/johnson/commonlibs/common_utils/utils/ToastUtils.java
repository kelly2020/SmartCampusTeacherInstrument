package com.johnson.commonlibs.common_utils.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhangwentao on 16/8/4.
 * Description :吐司
 * Version :1.0
 */
public class ToastUtils {
    private static Toast toast;

    public static void showToast(Context context, String content) {
        if (toast == null) {
           toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }

        toast.show();
    }
}
