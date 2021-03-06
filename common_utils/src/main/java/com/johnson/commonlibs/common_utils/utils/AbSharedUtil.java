package com.johnson.commonlibs.common_utils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;

//TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbSharedUtil.java
 * 描述：保存到 SharedPreferences 的数据.
 *
 * @author zhangwt
 * @version v1.0
 * @date：2014-10-09 下午11:52:13
 */
public class AbSharedUtil {

    //	private static final String SHARED_PATH = AbAppConfig.SHARED_PATH;
    private static final String SHARED_PATH = "shared_files";

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PATH, Context.MODE_PRIVATE);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void putSets(Context context, String key, Set<String> sets) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        Editor edit = sharedPreferences.edit();
        edit.putStringSet(key, sets);
        edit.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null) == null ? "0" : sharedPreferences.getString(key, null);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static Set<String> getSets(Context context, String key, Set<String> sets) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        return sharedPreferences.getStringSet(key, sets);
    }

}
