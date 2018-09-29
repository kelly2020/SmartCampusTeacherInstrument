package com.johnson.commonlibs.common_utils.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.view.*;
import android.widget.LinearLayout;
import com.johnson.commonlibs.common_utils.R;

/**
 * @author zhangwentao
 * @since 12/02/2014
 */
public class DialogFactory {

    /**
     * 确认对话框
     *
     * @param context                  Context
     * @param message                  提示信息
     * @param positiveOnClickListener  确定按钮按下事件
     * @param negativeOnClicklListener 取消按钮按下事件
     * @param positiveTitle            positive title
     * @param negativeTitle            negative title
     * @return AlertDialog
     */
    public static Dialog createConfirmDialog(
            Context context, String title, String message,
            DialogInterface.OnClickListener positiveOnClickListener, String positiveTitle,
            DialogInterface.OnClickListener negativeOnClicklListener, String negativeTitle) {
        if (Build.VERSION.SDK_INT >= 16) {
            return new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveTitle, positiveOnClickListener)
                    .setNegativeButton(negativeTitle, negativeOnClicklListener).setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return false;
                        }
                    }).create();
        } else {
            return new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(title)
                    .setMessage(message)
                    .setNegativeButton(positiveTitle, positiveOnClickListener)
                    .setPositiveButton(negativeTitle, negativeOnClicklListener).setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return false;
                        }
                    }).create();
        }
    }

    /**
     * 确认对话框(无图标)
     *
     * @param context                  Context
     * @param message                  提示信息
     * @param positiveOnClickListener  确定按钮按下事件
     * @param negativeOnClicklListener 取消按钮按下事件
     * @param positiveTitle            positive title
     * @param negativeTitle            negative title
     * @return AlertDialog
     */
    public static Dialog createConfirmDialogIcon(
            Context context, String title, String message,
            DialogInterface.OnClickListener positiveOnClickListener, String positiveTitle,
            DialogInterface.OnClickListener negativeOnClicklListener, String negativeTitle) {
        if (Build.VERSION.SDK_INT >= 16) {
            return new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveTitle, positiveOnClickListener)
                    .setNegativeButton(negativeTitle, negativeOnClicklListener).setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return false;
                        }
                    }).create();
        } else {
            return new AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setNegativeButton(positiveTitle, positiveOnClickListener)
                    .setPositiveButton(negativeTitle, negativeOnClicklListener).setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return false;
                        }
                    }).create();
        }
    }


    public static Dialog showDialog(Context context, View view) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.addContentView(view, layoutParams);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();

//        if (display == null) {
//            attributes.width = 700;
//        } else {
//            attributes.width = (int) (display.getWidth() * 0.8);
//        }
////
//        attributes.width = (int) (display.getWidth() * 0.8);
        window.setAttributes(attributes);
        return dialog;
    }

    public static ProgressDialog createProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
//        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDialog;

    }


}
