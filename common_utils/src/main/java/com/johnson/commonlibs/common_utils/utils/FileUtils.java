package com.johnson.commonlibs.common_utils.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zhanggaobo
 * @since 12/02/2014
 */
public class FileUtils {
//    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class.getSimpleName());

    private static File sdcard = Environment.getExternalStorageDirectory();

    private static String files = "files";

    public static final String ROOT_DIRECTORY = "smartTeacher";

    /**
     * @return sd卡是否已经加载
     */
    public static boolean isCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static File getImagesDirectory() {
        return mkdirs(new File(ROOT_DIRECTORY + File.separator + "images"));
    }

    public static File getFileDirectory() {
        return mkdirs(new File(ROOT_DIRECTORY + File.separator + "files"));
    }

    public static File getVideoDirectory() {
        return mkdirs(new File(ROOT_DIRECTORY + File.separator + "videos"));
    }

    private static File mkdirs(File directory) {
        if (!directory.exists()) {
            directory = createFolder(directory.getAbsolutePath());
        }
        return directory;
    }

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }


    /**
     * * 清除SDCard下的内容 * *
     *
     * @param context
     */
    public static void cleanSDCardFiles(Context context) {
//        deleteFilesByDirectory(getFileDirectory());
//        deleteFilesByDirectory(getImagesDirectory());
        deleteDir(getFileDirectory());
        deleteDir(getImagesDirectory());
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    public static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    public static String getCacheStrK(long size) {
        long mb = 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else
            return String.format("%d KB", size);
    }

    public static String getCacheStr(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

//    public static long getAppCacheCount(Context context) {
//        long cacheCount = 0;
//        try {
//            cacheCount = getFolderSize(getFileDirectory()) + getFolderSize(getImagesDirectory())
//                    + getFolderSize(context.getCacheDir()) + getFolderSize(context.getFilesDir()) + getFolderSize(ImageCache.getExternalCacheDir(context));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return cacheCount;
//    }

    // 获取文件
//    Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * create folder
     *
     * @param folder folder
     * @return successful
     */
    public static File createFolder(String folder) {
        File directory = null;
        String f = folder;
        if (!folder.startsWith("/")) {
            f = "/" + folder;
        }
        if (!folder.endsWith("/")) {
            f = f + "/";
        }
        if (isCardMounted()) {
            try {
                directory = new File(sdcard.getAbsolutePath() + f);
                if (!directory.exists() && directory.mkdirs()) {
//                    logger.info("create folder:{} success", folder);
                    return directory;
                }
            } catch (Exception ex) {
//                logger.warn("can not create folder:{}", f, ex);
            }
        } else {
//            logger.warn("sdcard is not mounted,can not create folder");
        }
        return directory;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static File uriToFile(Context context, Uri uri) {
        if (isMediaDocument(uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }
            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{split[1]};
            String path = getDataColumn(context, contentUri, selection, selectionArgs);
            return new File(path);
        }
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
// DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
// ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore (and general)
            if (isGooglePhotosUri(uri))// Return the remote address
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority()) || "media".equals(uri.getAuthority());
    }

    public static File generateCaptureImageFile() {
        return new File(getImagesDirectory(), System.currentTimeMillis() + ".jpg");
    }

    //    /**
//     * 拷贝SD卡上的单个文件
//     *
//     * @throws IOException
//     */
    public static File copySDFileTo(File srcFile, String destFileName) {
        File destFile = new File(getImagesDirectory(), destFileName);
        boolean copyFlag = false;
        try {
            copyFlag = copyFileTo(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (copyFlag) {
            return destFile;
        }
        return null;
    }

    /**
     * 拷贝一个文件,srcFile源文件，destFile目标文件
     *
     * @throws IOException
     */
    public static boolean copyFileTo(File srcFile, File destFile) throws IOException {
        if (srcFile.isDirectory() || destFile.isDirectory()) {
            return false;// 判断是否是文件
        }
        FileInputStream fis = new FileInputStream(srcFile);
        FileOutputStream fos = new FileOutputStream(destFile);
        int readLen = 0;
        byte[] buf = new byte[1024];
        while ((readLen = fis.read(buf)) != -1) {
            fos.write(buf, 0, readLen);
        }
        fos.flush();
        fos.close();
        fis.close();
        return true;
    }

    public static Bitmap revisionImageSize(File path, int size) {
        // 取得图片
        Bitmap bitmap = null;
        try {
            InputStream temp = null;
            temp = new FileInputStream(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
            options.inJustDecodeBounds = true;
            // 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
            bitmap = BitmapFactory.decodeStream(temp, null, options);
            // 关闭流
            temp.close();
//            // 生成压缩的图片
            int i = 0;
            while (true) {
                // 这一步是根据要设置的大小，使宽和高都能满足
                if ((options.outWidth >> i <= size)
                        && (options.outHeight >> i <= size)) {
                    // 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
                    temp = new FileInputStream(path);
                    // 这个参数表示 新生成的图片为原始图片的几分之一。
                    options.inSampleSize = (int) Math.pow(2.0D, i);
                    // 这里之前设置为了true，所以要改为false，否则就创建不出图片
                    options.inJustDecodeBounds = false;

                    bitmap = BitmapFactory.decodeStream(temp, null, options);
                    break;
                }
                i += 1;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

//
//    public static void loadAndSaveImageByUrl(Context context, String url) {
//        try {
//            URL myFileUrl = new URL(url);
//            HttpURLConnection conn = null;
//            conn = (HttpURLConnection) myFileUrl.openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//
//            InputStream is = conn.getInputStream();
//            saveImageBitmap(context, url, is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private static void saveImageBitmap(Context context, String url, InputStream is) {
//        try {
//            url = new String(Base64.encode(url.getBytes()));
//            OutputStream outputStream = context.openFileOutput(url, Context.MODE_WORLD_WRITEABLE);
//            IOUtils.copy(is, outputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static Bitmap findImageBitmap(Context context, String url) {
//        Bitmap bitmap = null;
//        try {
//            url = new String(Base64.encode(url.getBytes()));
//            InputStream in = context.openFileInput(url);
//            bitmap = BitmapFactory.decodeStream(in);
//        } catch (FileNotFoundException e) {
////            e.printStackTrace();
//        }
//        return bitmap;
//    }

    public static InputStream findInputStream(File file) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return inputStream;
    }

    //缩图片大小
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static Bitmap compressImage(File file) {

        InputStream inputStream = findInputStream(file);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        bitmap = compressImage(bitmap);
        return bitmap;
    }

    public static Bitmap getImageByPath(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public static File saveSDBitmapFile(String path, Bitmap bitmap) {
        File file = new File(path);
        path = new String(file.getName());
        File f = new File(getImagesDirectory() + File.separator + path + ".png");
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public static File writeData(String jsonData, String jsonName) {
        File file = new File(getFileDirectory() + File.separator + jsonName);
        try {
            BufferedReader reader = new BufferedReader(new StringReader(jsonData));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, "gb2312");
//            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            int len = 0;
            char[] buffer = new char[1024];
            while ((len = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, len);
            }
            writer.flush();
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static File saveJson(String json, String jsonName) {
        File file = new File(getFileDirectory() + File.separator + jsonName);
        if (!file.getParentFile().exists()) {//判断父文件是否存在，如果不存在则创建
            file.getParentFile().mkdirs();
        }
        PrintStream out = null;   //打印流
        try {
            out = new PrintStream(new FileOutputStream(file));  //实例化打印流对象
//            FileOutputStream fileOutputStream = new FileOutputStream(file);  //实例化打印流对象
//            out = new PrintStream(new OutputStreamWriter(fileOutputStream, "UTF-8"));  //实例化打印流对象
            out.print(json);     //输出数据
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        } finally {
            if (out != null) {    //如果打印流不为空，则关闭打印流
                out.close();
            }

        }
        return file;
    }

    public static void clearFileImage(Context context) {
//        getImagesDirectory().delete();
        File fileSD = getImagesDirectory();
        if (fileSD != null && fileSD.exists() && fileSD.isDirectory()) {
            for (File item : fileSD.listFiles()) {
                item.delete();
            }
        }
        File fileDir = context.getFilesDir();
        if (fileDir != null && fileDir.exists() && fileDir.isDirectory()) {
            for (File item : fileDir.listFiles()) {
                item.delete();
            }
        }
        File cacheDir = context.getCacheDir();
        if (cacheDir != null && cacheDir.exists() && cacheDir.isDirectory()) {
            for (File item : cacheDir.listFiles()) {
                item.delete();
            }
        }
    }

    public static Bitmap rotateImageView(int angle, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static File writeUpdateFile(String downloadUrl) {
        File tempFile = null;
        String temDownloadName;
        HttpURLConnection connection = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(downloadUrl);
            temDownloadName = url.getPath();
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            int oldPercent = 0;
            int length = connection.getContentLength();
            is = connection.getInputStream();
            tempFile = getFile(temDownloadName);
            fos = new FileOutputStream(tempFile);
            byte[] buf = new byte[1024 * 2];
            double received = 0;
            do {
                int numread = is.read(buf);
                if (numread <= 0) {
                    break;
                }
                fos.write(buf, 0, numread);
                received += numread;
                int percent = (int) (received / length * 100);
                if (percent > oldPercent) {
                    oldPercent = percent;
                }
            } while (true);
            return tempFile;
        } catch (IOException ex) {
            ex.printStackTrace();
            removeFile(tempFile);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        }
        return null;
    }

    private static void removeFile(String temDownloadName) {
        File temp = getFile(temDownloadName);
        if (temp != null && temp.exists()) {
            temp.delete();
        }
    }

    private static void removeFile(File temFile) {
        if (temFile != null && temFile.exists()) {
            temFile.delete();
        }
    }

    private static File getFile(String fileName) {
        return new File(getFileDirectory(), fileName);
    }

    /**
     * 根据uri获取文件真实地址
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
