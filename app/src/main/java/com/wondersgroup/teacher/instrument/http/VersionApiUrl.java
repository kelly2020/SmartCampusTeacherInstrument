package com.wondersgroup.teacher.instrument.http;


import com.johnson.commonlibs.common_utils.annotations.Host;
import com.johnson.commonlibs.common_utils.contants.AppURL;

/**
 * Created by zhangwt on 18/9/20..<br/>
 * 项目中普通url
 * @author zhangwt
 * @version 1.0
 * @date 16/1/5
 */
@Host(value = ConfigUtil.BASE_URL_VERSION)
public class VersionApiUrl extends AppURL {

    @Override
    public String getUrl() {
        return null;
    }
}
