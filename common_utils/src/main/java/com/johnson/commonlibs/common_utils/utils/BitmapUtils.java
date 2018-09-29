package com.johnson.commonlibs.common_utils.utils;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Base64;

import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

/***
 * @author zhangwt
 * @version 1.0
 * @date 16/3/24
 */
public class BitmapUtils {

    /**
     * 网络返回图片路径为中文和空格的处理
     * @param imagePath
     * @return  URLEncoder会将空格转化为加号，之后我们得将： 和 /还原 这样就可以正常下载了
     */
    public static String encodeImageUrl(String imagePath) {
        try {
            String url = URLEncoder.encode(imagePath, "utf-8").replaceAll("\\+", "%20");
            String path = url.replaceAll("%3A", ":").replaceAll("%2F", "/");
            return path;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static File bitmap2file(Context context, Bitmap bitmap) {
        Date date = new Date();
        long t = date.getTime();
        File file = new File(context.getCacheDir(), t + ".jpg");
        try {
            file.createNewFile();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
            byte[] b = outputStream.toByteArray();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(b);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static Bitmap decodeBitmapFromResource(Resources res, int resId,
                                                  int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;

        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * decode bitmap在一个适合的范围内
     *
     * @param path
     * @return
     */
    private static Bitmap decodeBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int inSampleSize = calculcateInSampleSize(options, 100, 100);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        return bm;
    }


    /**
     * 进行bitmap压缩
     *
     * @param options
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static int calculcateInSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight) {
        // 压缩图片的宽和高
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        int inSampleSize = 1;
        if (imageWidth > requestWidth || imageHeight > requestHeight) {
            int halfWidth = imageWidth / 2;
            int halfHeight = imageHeight / 2;

            while ((halfHeight / inSampleSize) > requestHeight
                    && (halfWidth / inSampleSize) > requestWidth) {
                inSampleSize *= 2;
            }

        }
        return inSampleSize;
    }


    /**
     * 图片转Base64
     *
     * @param path
     * @return
     * @notice 已压缩bitmap
     */
    public static String encodeBitmapToBase64(String path) {
        Bitmap bm = decodeBitmap(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        bm.recycle();
        return imageEncoded;
    }
}
