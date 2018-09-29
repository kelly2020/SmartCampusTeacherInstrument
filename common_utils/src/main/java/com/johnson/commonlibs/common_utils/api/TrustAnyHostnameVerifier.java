package com.johnson.commonlibs.common_utils.api;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by zhangwentao on 16/8/17.
 * Description :请求服务器证书问题
 * Version :1.0
 */
public class TrustAnyHostnameVerifier implements HostnameVerifier
{
    public boolean verify(String hostname, SSLSession session)
    {
        return true;
    }
}