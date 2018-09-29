package com.johnson.commonlibs.common_utils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * @author zhangwentao
 * @since 12/03/2014
 */
public class IntentFactory {

    public static final String MIMETYPE = "application/vnd.android.package-archive";

    /*
    安装下载包
    */
    public static void openInstaller(Context context, File file) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), MIMETYPE);
        context.startActivity(intent);
    }


    public static Intent createCheckFile(Context context, File file, String type) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, type);
        return intent;
    }

    public static Intent createPickImageIntent() {
        Intent intent = null;
        intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        return intent;
    }

    public static Intent createCaptureImageIntent(File captureImageFile, Context context, String fileprovider) {
        Uri uri = null;
        // 7.0 中的处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //FileProvider 将文件进行分装, 然后供外部应用（相机）访问提高了当前应用的安全性
            uri = FileProvider.getUriForFile(context, fileprovider, captureImageFile);//通过FileProvider 来获取本地图片文件
        } else {
            uri = Uri.fromFile(captureImageFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * 录制视频
     *
     * @param captureMediaFile
     * @param limit_time       时长 1 = 1s
     * @return
     */
    public static Intent createCaptureMediaIntent(File captureMediaFile, long limit_time, Context context, String fileprovider) {
        Uri uri = null;
        // 7.0 中的处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //FileProvider 将文件进行分装, 然后供外部应用（相机）访问提高了当前应用的安全性
            uri = FileProvider.getUriForFile(context, fileprovider, captureMediaFile);//通过FileProvider 来获取本地图片文件
        } else {
            uri = Uri.fromFile(captureMediaFile);
        }
        Intent intent = new Intent();
        intent.setAction("android.media.action.VIDEO_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        // 保存录像到指定的路径
//        Uri uri = Uri.fromFile(captureMediaFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, limit_time);
        return intent;
    }

    public static Intent createCropImageIntent(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", true);
        return intent;
    }

    public static Intent createCropImageIntent(Uri uri, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", true);
        return intent;
    }

    public static Intent createShareIntent(String title, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent;
    }

    public static Intent createWifiIntent() {
        Intent intent = new Intent("android.settings.WIFI_SETTINGS");
//        ComponentName cName = new ComponentName("com.android.phone", "com.android.phone.Settings");
//        intent.setComponent(cName);
        return intent;
    }

    /**
     * 进入相册
     *
     * @return
     */
    public static Intent createAlbumIntent() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        return albumIntent;
    }

    public static Intent createVideoIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    public static boolean checkPermission(Activity context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{permission}, 1);
        } else {

        }
        return true;
    }

    /**
     * 进入设置页面
     *
     * @return
     */
    public static Intent createSettingIntent() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        return intent;
    }
}
