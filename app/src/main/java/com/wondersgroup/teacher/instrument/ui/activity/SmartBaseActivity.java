package com.wondersgroup.teacher.instrument.ui.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.johnson.commonlibs.common_utils.BaseActivity;
import com.johnson.commonlibs.common_utils.api.ServiceGenerator;
import com.johnson.commonlibs.common_utils.utils.ActivityController;
import com.johnson.commonlibs.common_utils.utils.StrUtils;
import com.wondersgroup.teacher.instrument.R;
import com.wondersgroup.teacher.instrument.base.SmartApplication;
import com.wondersgroup.teacher.instrument.http.ApiService;
import com.wondersgroup.teacher.instrument.http.BaseApiUrl;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangwentao on 16/10/31.
 * Description : 基类
 * Version :1.0
 */
public class SmartBaseActivity extends BaseActivity {
    public RequestManager requestManager;
    public Gson gson;
    public ApiService apiService;
    private Activity topActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_base);

        apiService = ServiceGenerator.generate(SmartApplication.getInstance()).setEndpoint(BaseApiUrl.class).getApiService(ApiService.class);


        ActivityController.addActivity(this);

        requestManager = Glide.with(this);
        gson = new Gson();
    }

    @Override
    public void onResume() {
        super.onResume();
        topActivity = this;
        SmartApplication.setIsAppRunningFront(true);
        MobclickAgent.onPageStart(getPackageName());
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        SmartApplication.setIsAppRunningFront(false);
        MobclickAgent.onPageEnd(getPackageName());
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestManager != null && !requestManager.isPaused()) {
            requestManager.pauseRequests();
        }
    }

    /**
     * 开启权限
     *
     * @param permission
     * @param requestCode
     * @return
     */
    protected boolean checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission}, requestCode);
            return false;
        }
        return true;
    }

    /**
     * 开启权限
     *
     * @param permissions
     * @param requestCode
     * @return
     */
    protected boolean checkPermission(String[] permissions, int requestCode) {
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]), requestCode);
            return false;
        }
        return true;
    }

    protected String getRequestKey(File file) {
        String key = file.getAbsolutePath();
        String type = StrUtils.substring(key, key.lastIndexOf(".") - 1);
        key = System.currentTimeMillis() + type;
        return key;
    }
}
