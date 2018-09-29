package com.johnson.commonlibs.common_utils.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwentao on 2017/1/4.
 * 管理activity
 */

public class ActivityController {
    public static List<Activity> activities = new ArrayList<Activity>();
    public static List<Activity> loginActivities = new ArrayList<Activity>();//登录找回密码 激活相关activity
    public static List<Activity> homeworkActivities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void addLoginActivity(Activity activity) {
        loginActivities.add(activity);
    }

    public static void addHomeworkActivity(Activity activity) {
        homeworkActivities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAllActivities() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
//        android.os.Process.killProcess(android.os.Process.myPid());//杀掉当前进程 整个程序都会退出
    }

    public static void finishLoginActivities() {
        for (Activity activity : loginActivities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void finishHomeworkActivities() {
        for (Activity activity : homeworkActivities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
