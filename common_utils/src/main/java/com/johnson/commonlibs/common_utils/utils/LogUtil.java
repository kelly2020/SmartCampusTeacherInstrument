package com.johnson.commonlibs.common_utils.utils;

import android.util.Log;

/**
 * Created by zhangwentao on 2016/12/14.
 * log 日志工具类,当在开发环境 level = verbose  当生产环境 level = nothing 这样不会打印任何日志
 */

public class LogUtil {
    public static final int VERBOSE = 1;//开放环境标志值
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;//生产环境标志值
    public static final int level = VERBOSE;//中间变量


    public static void v(String tag,String msg){
        if (level <= VERBOSE){
            Log.v(tag,msg);
        }
    }
    public static void d(String tag,String msg){
        if (level <= DEBUG){
            Log.d(tag,msg);
        }
    }

    public static void i(String tag,String msg){
        if (level <= INFO){
            Log.i(tag,msg);
        }
    }

    public static void w(String tag,String msg){
        if (level <= WARN){
            Log.w(tag,msg);
        }
    }
    public static void e(String tag,String msg){
        if (level <= ERROR){
            Log.e(tag,msg);
        }
    }

}
