package com.wondersgroup.teacher.instrument.base;

import android.app.Application;

/**
 * Created by zhangwentao on 18/9/20.
 * Description :
 * Version :
 */
public class SmartApplication extends Application {
    private static SmartApplication application;
    private static int activityVisiableCount;

    public static SmartApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static boolean isAppRunningFront() {
        return activityVisiableCount > 0;
    }

    public static void setIsAppRunningFront(boolean isAppRunningFront) {
        if (isAppRunningFront) {
            activityVisiableCount++;
        } else if (activityVisiableCount > 0) {
            activityVisiableCount--;
        }
    }
}
