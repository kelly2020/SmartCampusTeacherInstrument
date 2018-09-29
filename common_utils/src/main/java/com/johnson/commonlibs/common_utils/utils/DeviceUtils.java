package com.johnson.commonlibs.common_utils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.Rect;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author zhanggaobo
 * @since 11/29/2014
 */
public class DeviceUtils {


    //网络传输版本号格式
    public static String getPimConnectVersion(Context context) {
        String packageVersion = DeviceUtils.getVersionName(context);
        return packageVersion;
    }

    public static String getVersionName(Context context) {
        try {
            String packageName = context.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getVersionCode(Context context) {
        try {
            String packageName = context.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMEID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * 得到自适应列高度
     *
     * @param context
     * @return
     */
    public static int getPreferItemHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        android.util.TypedValue value = new android.util.TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.listPreferredItemHeight, value, true);
//        String s = TypedValue.coerceToString(value.type, value.data);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return Math.round(value.getDimension(metrics));
    }

    public static float getDisplayDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getStatusBarHeight(Activity context) {
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public static int getWindowHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getWindowWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static String getSignNumber(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Signature[] signs = packageInfo.signatures;
        Signature sign = signs[0];
        CertificateFactory certFactory = null;
        try {
            certFactory = CertificateFactory.getInstance("X.509");

            X509Certificate cert = (X509Certificate) certFactory
                    .generateCertificate(new ByteArrayInputStream(sign.toByteArray()));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
            return signNumber;
        } catch (CertificateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    //根据字节数切割字符串
    public static String substring(String str, int subSLength) {
        if (str == null)
            return "";
        else {
            int tempSubLength = subSLength;
            String subStr = str.substring(0, str.length() < subSLength ? str.length() : subSLength);
            int subStrByetsL = 0;
            try {
                subStrByetsL = subStr.getBytes("GBK").length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            while (subStrByetsL > tempSubLength) {
                int subSLengthTemp = --subSLength;
                subStr = str.substring(0, subSLengthTemp > str.length() ? str.length() : subSLengthTemp);
                try {
                    subStrByetsL = subStr.getBytes("GBK").length;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return subStr;
        }
    }

    public static int getPx(Context context, int dp) {
        float deviceDs = 0;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        deviceDs = dm.density;
        return (int) (dp * deviceDs);


    }

    public static void setDefaultMessageApp(Context context) {
        try {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, context.getPackageName());
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "该手机不支持设置默认短信应用。", Toast.LENGTH_SHORT).show();
        }
    }


    public static PackageInfo getInstallPackageInfo(Context context, String packName) {
        PackageInfo packageInfo = null;
        PackageManager manager = context.getPackageManager();
        // 只查找启动方式为LAUNCHER并且是ACTION_MAIN的APP
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // 根据Intent值查询这样的app
        final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo app : apps) {
            //该应用的包名
            String pkg = app.activityInfo.packageName;
            if (pkg.equals(packName)) {
                try {
                    packageInfo = manager.getPackageInfo(pkg, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return packageInfo;
    }


    public static int getIntVersion(String packageVersion) {
        String version = packageVersion.replace(".", "");
        if (StrUtils.isEmpty(version)) {
            return 0;
        }
        return Integer.parseInt(version);
    }

    public static String getConnectVersion(Context context) {
        String packageVersion = DeviceUtils.getVersionName(context);
        return packageVersion;
    }
}
